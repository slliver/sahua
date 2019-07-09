package com.slliver.entity;

import java.io.Serializable;

public class ApiValidateCode implements Serializable {
	private String sessionId;
	private String codeImg;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getCodeImg() {
		return codeImg;
	}

	public void setCodeImg(String codeImg) {
		this.codeImg = codeImg;
	}
}
