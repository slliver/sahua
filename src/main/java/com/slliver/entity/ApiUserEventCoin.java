package com.slliver.entity;

import com.slliver.base.entity.UserCoin;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/21 10:16
 * @version: 1.0
 */

public class ApiUserEventCoin {

	private String event;

	private String balance;

	private Long userAmount;

	private Long todayAmount;

	private Long power;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Long getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(Long userAmount) {
		this.userAmount = userAmount;
	}

	public Long getTodayAmount() {
		return todayAmount;
	}

	public void setTodayAmount(Long todayAmount) {
		this.todayAmount = todayAmount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public Long getPower() {
		return power;
	}

	public void setPower(Long power) {
		this.power = power;
	}
}
