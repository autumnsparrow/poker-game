package com.sky.game.service.domain;

public class WarReport {

	public WarReport() {
		// TODO Auto-generated constructor stub
	}
	
	int winner1Times;
	int winner2Times;
	int winner3Times;
	int stagebTimes;
	
	
	
	public int getStagebTimes() {
		return stagebTimes;
	}
	public void setStagebTimes(int stagebTimes) {
		this.stagebTimes = stagebTimes;
	}
	public WarReport(int winneer1Times, int winneer2Times, int winneer3Times,
			int stagebTimes) {
		super();
		this.winner1Times = winneer1Times;
		this.winner2Times = winneer2Times;
		this.winner3Times = winneer3Times;
		this.stagebTimes = stagebTimes;
	}
	@Override
	public String toString() {
		return "WarReport [winneer1Times=" + winner1Times + ", winneer2Times="
				+ winner2Times + ", winneer3Times=" + winner3Times
				+ ", stagebTimes=" + stagebTimes + "]";
	}
	public int getWinner1Times() {
		return winner1Times;
	}
	public void setWinner1Times(int winner1Times) {
		this.winner1Times = winner1Times;
	}
	public int getWinner2Times() {
		return winner2Times;
	}
	public void setWinner2Times(int winner2Times) {
		this.winner2Times = winner2Times;
	}
	public int getWinner3Times() {
		return winner3Times;
	}
	public void setWinner3Times(int winner3Times) {
		this.winner3Times = winner3Times;
	}

}
