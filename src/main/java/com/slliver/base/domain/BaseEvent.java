package com.slliver.base.domain;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/12 16:43
 * @version: 1.0
 */
public class BaseEvent extends BaseCondition {

    private String phone;
    private String event;
    private String params;
    private String timestamp;
    private String hash;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
    	if (params != null) {
			this.params = StringEscapeUtils.unescapeHtml4(params);
		}
    }
}
