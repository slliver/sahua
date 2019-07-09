package com.slliver.common.domain;

/**
 * @Description: 用户token
 * @author: slliver
 * @date: 2018/3/12 10:07
 * @version: 1.0
 */
public class UserToken implements java.io.Serializable {


    private static final long serialVersionUID = 1930343029088676849L;

    private String userPkid;
    private String token;
    private String sercrtKey;
    private String code;
    private String message;

    public UserToken() {
    }

    public UserToken(String userPkid, String code, String message) {
        this.userPkid = userPkid;
        this.code = code;
        this.message = message;
    }

    public UserToken(String userPkid, String token) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
