package com.sky.game.service.domain;

import java.sql.Timestamp;


public class StoreLog {

	public StoreLog() {
		// TODO Auto-generated constructor stub
	}
	int gold;
	int price;
	Timestamp payTime;
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Timestamp getPayTime() {
		return payTime;
	}
	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}
	public StoreLog(int gold, int price, Timestamp payTime) {
		super();
		this.gold = gold;
		this.price = price;
		this.payTime = payTime;
	}
	@Override
	public String toString() {
		return "StoreLog [gold=" + gold + ", price=" + price + ", payTime="
				+ payTime + "]";
	}
}
