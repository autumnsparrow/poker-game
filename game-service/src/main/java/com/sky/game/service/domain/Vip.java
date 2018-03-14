package com.sky.game.service.domain;

public class Vip {

	public Vip() {
		// TODO Auto-generated constructor stub
	}
	int point;
	int level;
	double sale;   //道具打折
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public double getSale() {
		return sale;
	}
	public void setSale(double sale) {
		this.sale = sale;
	}
	public Vip(int point, int level, double sale) {
		super();
		this.point = point;
		this.level = level;
		this.sale = sale;
	}
	@Override
	public String toString() {
		return "Vip [point=" + point + ", level=" + level + ", sale=" + sale
				+ "]";
	}
	
}
