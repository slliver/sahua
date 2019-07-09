package com.slliver.base.entity;

import com.slliver.base.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_coin_withdraw")
public class UserCoinWithdraw extends BaseDomain {
    /**
     * 用户pkid
     */
    @Column(name = "user_pkid")
    private String userPkid;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "status")
    private String status;

    @Column(name = "cause")
    private String cause;

    @Column(name = "pay_time")
    private Date payTime;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取渠道编号
     *
     * @return user_pkid - 渠道编号
     */
    public String getUserPkid() {
        return userPkid;
    }

    /**
     * 设置渠道编号
     *
     * @param userPkid 渠道编号
     */
    public void setUserPkid(String userPkid) {
        this.userPkid = userPkid;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}