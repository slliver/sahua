package com.slliver.base.entity;

import com.slliver.base.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "user_coin_history")
public class UserCoinHistory extends BaseDomain {
    /**
     * 用户pkid
     */
    @Column(name = "user_pkid")
    private String userPkid;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "action")
    private String action;

    @Column(name = "action_name")
    private String actionName;

    @Column(name = "timestamp")
    private String timestamp;

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}