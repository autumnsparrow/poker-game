package com.sky.game.service.domain;

public class TaskState {

	public TaskState() {
		// TODO Auto-generated constructor stub
	}
	long id;
	int userRate;
	int neckType;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getUserRate() {
		return userRate;
	}
	public void setUserRate(int userRate) {
		this.userRate = userRate;
	}
	public int getNeckType() {
		return neckType;
	}
	public void setNeckType(int neckType) {
		this.neckType = neckType;
	}
	public TaskState(long id, int userRate, int neckType) {
		super();
		this.id = id;
		this.userRate = userRate;
		this.neckType = neckType;
	}
	@Override
	public String toString() {
		return "TaskState [id=" + id + ", userRate=" + userRate + ", neckType="
				+ neckType + "]";
	}
	
}
