package com.slliver.common.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2018/3/27 0027
 * Time: 下午 20:13
 * To change this template use File | Settings | File Templates.
 */
public class UserValidate implements java.io.Serializable {

    private static final long serialVersionUID = 1930343029088676849L;

    private String userPkid;
    private String token;
    private String sercrtKey;
    private long power;
    /**
     * 短信验证码
     */
    private String code;
    /**
     * 验证结果状态码
     */
    private String statusCode;
    // 验证后的提示信息
    private String message;

    public UserValidate(){

    }

    public UserValidate(String userPkid, String token) {
        this.userPkid = userPkid;
        this.token = token;
    }

    public String getUserPkid() {
        return userPkid;
    }

    public void setUserPkid(String userPkid) {
        this.userPkid = userPkid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSercrtKey() {
        return sercrtKey;
    }

    public void setSercrtKey(String sercrtKey) {
        this.sercrtKey = sercrtKey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }
}
