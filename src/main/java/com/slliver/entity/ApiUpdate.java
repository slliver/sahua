package com.slliver.entity;

import java.io.Serializable;

public class ApiUpdate implements Serializable {

	private boolean update;	//true有升级， false无升级
	private boolean force; //true 强制升级，false普通升级
	private String version; //版本
	private String title;	//标题
	private String[] desc;	//描述
	private String url;		//下载地址

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getDesc() {
		return desc;
	}

	public void setDesc(String[] desc) {
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
