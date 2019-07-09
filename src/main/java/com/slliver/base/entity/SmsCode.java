package com.slliver.base.entity;

import com.slliver.base.domain.BaseDomain;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sms_code")
public class SmsCode extends BaseDomain {

    /**
     * 手机号码
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 验证码
     */
    @Column(name = "code")
    private String code;

    /**
     * 短信内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 如果是新建的话，此字段是空的，如果是复检的话，此字段是原检测通知单编号，并且不能为空
     */
    @Column(name = "ip_address")
    private String ipAddress;

    /**
     * 当前日期获取验证码次数，防止恶意获取，即同一个手机号，24小时之内不能够超过5条
     */
    @Column(name = "count")
    private Integer count;

    @Column(name = "expire_date")
    private Date expireDate;

    /**
     * 验证码失效时间，long型
     */
    @Column(name = "expire_time")
    private Long expireTime;

    @Column(name = "make_date")
    private String makeDate;

    /**
     *  0-无效，1-有效
     */
    @Column(name = "flag_valid")
    private Boolean flagValid;

    /**
     * 预留字段
     */
    private String reserved1;

    /**
     * 预留字段
     */
    private String reserved2;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public Boolean getFlagValid() {
        return flagValid;
    }

    public void setFlagValid(Boolean flagValid) {
        this.flagValid = flagValid;
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