package com.slliver.common.interceptor.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slliver.common.Constant;
import com.slliver.common.domain.AjaxRichResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/2/1 10:03
 * @version: 1.0
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    protected Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    /**
     * 如果使用了@Token自定义注解，将根据注解的值设定是否把Token放入Session,还是将Token移出
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    request.getSession(true).setAttribute(Constant.SESSION_KEY.TOKEN, UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        logger.warn("please don't repeat submit,url:" + request.getServletPath());
                        return handleInvalidToken(request, response, handler);
                    }
                    request.getSession(true).removeAttribute(Constant.SESSION_KEY.TOKEN);
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 是否重复提交校验
     * @param request
     * @return
     */
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(true).getAttribute(Constant.SESSION_KEY.TOKEN);
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter(Constant.SESSION_KEY.TOKEN);
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }

    /**
     * 当出现一个非法令牌时调用 ，页面不返回任何值，调用此方法后页面不会有任何响应，如果页面没有设定hidden的token，点击提交按钮会出现没有任何提示的问题。
     * 如果需要有提示请将res.setFailMsg("xxx")的注释放开
     * 如果不想要二次对话框提示，直接将res.setFailMsg("xxx")注释掉
     */
    protected boolean handleInvalidToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            response.setCharacterEncoding("UTF-8");
            AjaxRichResult res = new AjaxRichResult();
            res.setFailMsg("请关闭本页面，并请不要在操作完成之前重复提交表单。");
            res.setRes(3);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().print(mapper.writeValueAsString(res));
        } finally {
            response.getWriter().close();
        }
        return false;
    }

}
