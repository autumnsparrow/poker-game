package com.sky.game.service.domain;

public class TaskSet {

	public TaskSet() {
		// TODO Auto-generated constructor stub
	}
	long id;
	String name;
	int rate;
	int rewardValue;
	String rewardType;
	int taskType;
	int neckType;
	String icon;
	String description;
	int userRate;
	String rewardName;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getRewardValue() {
		return rewardValue;
	}
	public void setRewardValue(int rewardValue) {
		this.rewardValue = rewardValue;
	}
	public String getRewardType() {
		return rewardType;
	}
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public int getNeckType() {
		return neckType;
	}
	public void setNeckType(int neckType) {
		this.neckType = neckType;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getUserRate() {
		return userRate;
	}
	public void setUserRate(int userRate) {
		this.userRate = userRate;
	}
	public String getRewardName() {
		return rewardName;
	}
	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
	public TaskSet(long id, String name, int rate, int rewardValue,
			String rewardType, int taskType, int neckType, String icon,
			String description, int userRate, String rewardName) {
		super();
		this.id = id;
		this.name = name;
		this.rate = rate;
		this.rewardValue = rewardValue;
		this.rewardType = rewardType;
		this.taskType = taskType;
		this.neckType = neckType;
		this.icon = icon;
		this.description = description;
		this.userRate = userRate;
		this.rewardName = rewardName;
	}
	@Override
	public String toString() {
		return "TaskSet [id=" + id + ", name=" + name + ", rate=" + rate
				+ ", rewardValue=" + rewardValue + ", rewardType=" + rewardType
				+ ", taskType=" + taskType + ", neckType=" + neckType
				+ ", icon=" + icon + ", description=" + description
				+ ", userRate=" + userRate + ", rewardName=" + rewardName + "]";
	}
	
	
}
