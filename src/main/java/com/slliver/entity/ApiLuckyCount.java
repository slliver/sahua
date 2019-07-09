package com.slliver.entity;

import java.io.Serializable;

public class ApiLuckyCount implements Serializable {

	private int count;
	private int chance; //送金币概率

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}
}
