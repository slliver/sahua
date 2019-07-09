package com.slliver.entity;

import com.slliver.base.entity.User;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/9 15:07
 * @version: 1.0
 */
@Table(name = "user")
public class ApiUser extends User {

    @Transient
    private String newPassword;
    @Transient
    private String confirmPassword;
    @Transient
    private String code;


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
