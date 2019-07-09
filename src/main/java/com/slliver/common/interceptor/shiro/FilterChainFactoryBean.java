package com.slliver.common.interceptor.shiro;

import org.springframework.beans.factory.FactoryBean;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/2/1 14:10
 * @version: 1.0
 */
public class FilterChainFactoryBean implements FactoryBean<Map<String, String>> {

    /**
     * anon:匿名拦截器，即不需要登录即可访问；一般用于静态资源过滤
     * authc:如果没有登录会跳到相应的登录页面登录
     * user:用户拦截器，用户已经身份验证/记住我登录的都可
     */
    @Override
    public Map<String, String> getObject() throws Exception {
        Map<String,String>  map = new LinkedHashMap<String,String>();
        map.put("/static/css/**", "anon");
        map.put("/static/js/sys/login/**", "anon");
        map.put("/static/js/sys/**", "authc");
        map.put("/static/**", "anon");
        map.put("/assets/**", "anon");
        map.put("/favicon.ico", "anon");
        map.put("/resource/**", "anon");
        map.put("/*.jsp", "anon"); // 404.jsp , 500.jsp ,  timeout.jsp , index.jsp
        map.put("/verifyCode/**", "anon");
        map.put("/login", "anon");
        map.put("/upload/**", "anon");
        map.put("/api/**", "anon");
        map.put("/**", "user");

        return map;
    }

    @Override
    public Class<?> getObjectType() {
        return Map.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
