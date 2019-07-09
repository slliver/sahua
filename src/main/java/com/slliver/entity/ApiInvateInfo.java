package com.slliver.entity;

public class ApiInvateInfo {

	private String code;
	private String invater;
	private int total;
	private int left;

	public String getInvater() {
		return invater;
	}

	public void setInvater(String invater) {
		this.invater = invater;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}
}
