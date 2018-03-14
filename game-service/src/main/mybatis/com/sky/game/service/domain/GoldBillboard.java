package com.sky.game.service.domain;

import java.util.Arrays;

public class GoldBillboard {

	public GoldBillboard() {
		// TODO Auto-generated constructor stub
	}
	UserBillboard userBillboard;
	
	int[][] record=new int[3][4];

	public UserBillboard getUserBillboard() {
		return userBillboard;
	}

	public void setUserBillboard(UserBillboard userBillboard) {
		this.userBillboard = userBillboard;
	}

	public int[][] getRecord() {
		return record;
	}

	public void setRecord(int[][] record) {
		this.record = record;
	}

	public GoldBillboard(UserBillboard userBillboard, int[][] record) {
		super();
		this.userBillboard = userBillboard;
		this.record = record;
	}

	@Override
	public String toString() {
		return "GoldBillboard [record=" + Arrays.toString(record) + "]";
	}
	
	
}
