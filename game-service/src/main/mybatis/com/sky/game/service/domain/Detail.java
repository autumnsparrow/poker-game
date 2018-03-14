/**
 * 
 */
package com.sky.game.service.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class Detail {

	/**
	 * 
	 */
	public Detail() {
		// TODO Auto-generated constructor stub
	}
	Date time;//时间
	String type;//消费类型
	String goods;//物品名
	int budget;  //收支
	int balance;//余额
	
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public static void main(String[] args) {
		Long timetamp = 1422084549000L;
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timetamp));
		System.out.println(date);
	}
}
