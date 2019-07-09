package com.slliver.base.entity;

import com.slliver.base.domain.BaseDomain;

import java.util.Date;
import javax.persistence.*;

public class User extends BaseDomain {

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    private String password;

    private String salt;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 用户昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    @Column(name = "avatar_pkid")
    private String avatarPkid;

    /**
     * 用户头像地址
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 访问token
     */
    @Column(name = "access_token")
    private String accessToken;

    /**
     * token过期时间
     */
    @Column(name = "expire_date")
    private Date expireDate;

    /**
     * token过期时间
     */
    @Column(name = "expire_time")
    private Long expireTime;

    /**
     * 渠道号
     */
    @Column(name = "channel_no")
    private String channelNo;

    /**
     * 设备
     */
    @Column(name = "device")
    private String device;


    /**
     * 用户角色用户可以多角色
     */
    private String role;

    /**
     * 预留字段
     */
    private String reserved1;

    /**
     * 预留字段
     */
    private String reserved2;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarPkid() {
        return avatarPkid;
    }

    public void setAvatarPkid(String avatarPkid) {
        this.avatarPkid = avatarPkid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

}