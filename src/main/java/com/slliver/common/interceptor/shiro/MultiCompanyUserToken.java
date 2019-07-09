package com.slliver.common.interceptor.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/2/1 14:15
 * @version: 1.0
 */
public class MultiCompanyUserToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 5752265707150991986L;

    public MultiCompanyUserToken(String userName, String password){
        super(userName, password);
    }
}
