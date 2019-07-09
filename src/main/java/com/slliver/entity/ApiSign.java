package com.slliver.entity;

import java.io.Serializable;

public class ApiSign implements Serializable {

	private String hour;
	private String sign; //0 否, 1是 2当前 3未开始

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
