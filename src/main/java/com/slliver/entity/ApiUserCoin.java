package com.slliver.entity;

import com.slliver.base.entity.LoanDetail;
import com.slliver.base.entity.UserCoin;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/21 10:16
 * @version: 1.0
 */
@Table(name = "user_coin")
public class ApiUserCoin extends UserCoin {

	@Transient
	private String balance;

	@Transient
	private Long todayAmount;

	@Transient
	private boolean isRecord;

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

	public boolean isRecord() {
		return isRecord;
	}

	public void setRecord(boolean record) {
		isRecord = record;
	}
}
