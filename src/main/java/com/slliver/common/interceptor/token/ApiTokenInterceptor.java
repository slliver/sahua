package com.slliver.common.interceptor.token;

import com.slliver.common.Constant;
import com.slliver.common.domain.UserToken;
import com.slliver.common.exception.RQException;
import com.slliver.common.utils.TokenUtil;
import com.slliver.entity.ApiUser;
import com.slliver.service.ApiUserService;
import com.slliver.token.RedisTokenManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * @Description: api网关访问拦截器
 * @author: slliver
 * @date: 2018/3/7 16:50
 * @version: 1.0
 */
public class ApiTokenInterceptor extends HandlerInterceptorAdapter {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    **/

    /**
     * 从配置文件中获取值，控制哪些url可以没有token就访问
     */
    private List<String> allowApiUrls;

    @Autowired
    ApiUserService apiUserService;

    /**
     * 请求进入前获取request中header中token信息，并解析成服务器需要的信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 默认不需要token可以访问的，比如获取验证码，登录等操作
        String requestUrl = request.getRequestURI();
        for (String url : allowApiUrls) {
            String urlPath = request.getContextPath() + url;
            if (requestUrl.startsWith(urlPath)) {
                return true;
            }
        }

        String token = this.getTokenFromRequestHeader(request);
        // 需要验证码可以访问，需要对请求中token的值进行拆解验证
        if (StringUtils.isBlank(token)) {
            String message = "{'code':801,'message':'缺少token，不能访问服务','data':null}";
            responseError(request, response, message);
            return false;
        }

        // 有token进行token验证
        String[] array = token.split("[.]");
        if (array == null || array.length < 3) {
            return false;
        }

        String userPkid = array[0];
        String expireTime = array[1];
        String sercetKey = array[2];

        // 验证token是否过期 TODO
        if (StringUtils.isBlank(sercetKey)) {
            // 没有秘钥
            String message = "{'code':801,'message':'缺少秘钥，不能访问服务','data':null}";
            responseError(request, response, message);
            return false;
        }

        String desercetKey = TokenUtil.decode(sercetKey);
        if (!Objects.equals(desercetKey, Constant.SERCET_KEY)) {
            // 秘钥不对
            String message = "{'code':801,'message':'秘钥不对，不能访问服务','data':null}";
            responseError(request, response, message);
            return false;
        }

        if (!validatePhone(request, token)) {
            responseError(request, response, "token验证失败或已过期");
            return false;
        }


        /**
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        if (factory.getConnection() == null) {
            // redis服务调用失败,抛出对应异常
            throw new RQException("redis 服务异常创建RedisConnectionFactory失败，请检查");
        } else {
            if (!redisTemplate.hasKey(token)) {
                // 如果token验证没问题，放入缓存
                redisTemplate.opsForValue().set(token, userPkid);
            }
        }
        **/

        // 验证通过
        return true;
    }

    private boolean validatePhone(HttpServletRequest request, String token) {
        String phone = request.getParameter("phone");
        if (StringUtils.isEmpty(phone)) {
            return true;
        }

        ApiUser user = apiUserService.selectByPhone(phone);
        if (user == null) {
            return false;
        }

        return StringUtils.equals(user.getAccessToken(), token);
    }

    private boolean validateToken(String token) {
        String[] array = token.split("[.]");
        if (array == null || array.length < 3) {
            return false;
        }

        String userPkid = array[0];
        String expireTime = array[1];
        String sercetKey = array[2];

        // 验证token是否过期

        if (StringUtils.isBlank(sercetKey)) {
            // 没有秘钥
            return false;
        }

        return !Objects.equals(TokenUtil.decode(sercetKey), Constant.SERCET_KEY);
    }


    /**
     * 从请求头中获取token信息
     */
    private String getTokenFromRequestHeader(HttpServletRequest request) {
        String token = "";
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            // 取出消息头的名字
            String headername = headers.nextElement();
            if (StringUtils.isNotBlank(headername)) {
                if (Objects.equals(Constant.REQUEST_TOKEN, headername.toLowerCase())) {
                    token = request.getHeader(headername);
                }
            }
            logger.info(headername + ": == >> " + request.getHeader(headername));
        }
        
        if(StringUtils.isBlank(token)){
            // 如果请求头中没有token参数，从传入的参数中获取
            // 获取请求参数中是否含有token元素
            token = request.getParameter(Constant.REQUEST_TOKEN);
        }
        System.out.println("request token is === >>> " + token);
        return token;
    }


    /**
     * 对token进行拆解验证
     */
    private UserToken getToken(String token) {
        UserToken userToken = null;
        // 客户端提供的token是经过base64加密的token,第一步进行base64解密


        return userToken;
    }


    /**
     * 检测到没有token，直接返回不验证
     *
     * @param request
     * @param response
     * @param obj
     */
    public void responseError(HttpServletRequest request, HttpServletResponse response, Object obj) {
        String message = (String) obj;
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(message);

        } catch (IOException ex) {
            logger.error("response error", ex);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public List<String> getAllowApiUrls() {
        return allowApiUrls;
    }

    public void setAllowApiUrls(List<String> allowApiUrls) {
        this.allowApiUrls = allowApiUrls;
    }
}
