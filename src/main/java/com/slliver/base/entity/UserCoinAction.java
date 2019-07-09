package com.slliver.base.entity;

import com.slliver.base.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "user_coin_action")
public class UserCoinAction extends BaseDomain {

    @Column(name = "action")
    private String action;

    @Column(name = "action_name")
    private String actionName;

    @Column(name = "min_power")
    private Long minPower;

    @Column(name = "max_power")
    private Long maxPower;

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

    public Long getMinPower() {
        return minPower;
    }

    public void setMinPower(Long minPower) {
        this.minPower = minPower;
    }

    public Long getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(Long maxPower) {
        this.maxPower = maxPower;
    }
}