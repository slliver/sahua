package com.slliver.entity;

import java.io.Serializable;

public class ApiConfig implements Serializable {

	private int gameAdChance;
	private int online;		//1上线，0审核
	private boolean isShowBBX;	//true显示，false不显示

	public int getGameAdChance() {
		return gameAdChance;
	}

	public void setGameAdChance(int gameAdChance) {
		this.gameAdChance = gameAdChance;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public boolean isShowBBX() {
		return isShowBBX;
	}

	public void setShowBBX(boolean showBBX) {
		isShowBBX = showBBX;
	}
}
