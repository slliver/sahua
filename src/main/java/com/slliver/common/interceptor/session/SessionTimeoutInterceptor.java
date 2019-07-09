package com.slliver.common.interceptor.session;

import com.slliver.common.Constant;
import com.slliver.entity.ApiUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/5 14:13
 * @version: 1.0
 */
public class SessionTimeoutInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(SessionTimeoutInterceptor.class);

    private static final String TIMEOUT_MESSAGE = "Session Time Out.";

    private static final String ACCEPT = "Accept";

    /**
     * 从配置文件中获取值，控制哪些url可以没有session就访问
     */
    private List<String> allowUrls;

    public List<String> getAllowUrls() {
        return allowUrls;
    }

    public void setAllowUrls(List<String> allowUrls) {
        this.allowUrls = allowUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        for (String url : allowUrls) {
            String urlPath = request.getContextPath() + url;
            if (requestUrl.startsWith(urlPath)) {
                return true;
            }
        }

        // 通过检查session user判断服务器端session是否超时。
        ApiUser sessionUser = (ApiUser) WebUtils.getSessionAttribute(request, Constant.SESSION_KEY.USER);
        if (sessionUser != null) {
            return true;
        } else {
//            if (requestUrl.startsWith("/resource/")) {
//                return true;
//            }
            String accept = request.getHeader(ACCEPT);
            if (accept.indexOf(Constant.SYS_REQUEST_HEADERS.APPLICATION_JSON) != -1) {
                response.sendRedirect("/loginIndex");
                logger.info(TIMEOUT_MESSAGE);
//				throw new RQJsonSessionTimeoutException(TIMEOUT_MESSAGE);
            } else {
                response.sendRedirect("/loginIndex");
                logger.info(TIMEOUT_MESSAGE);
//				throw new RQSessionTimeoutException(TIMEOUT_MESSAGE);
            }
            return false;
        }
    }
}
