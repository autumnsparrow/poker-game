package com.sky.game.service.domain;

public class SysActivity {

	public SysActivity() {
		// TODO Auto-generated constructor stub
	}
	long id;
	String name;
	String description;
	String startTime;
	String stopTime;
	String iconImg;
	int type;
	int state;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	public String getIconImg() {
		return iconImg;
	}
	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public SysActivity(long id, String name, String description,
			String startTime, String stopTime, String iconImg, int type,
			int state) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.iconImg = iconImg;
		this.type = type;
		this.state = state;
	}
	@Override
	public String toString() {
		return "SysActivity [id=" + id + ", name=" + name + ", description="
				+ description + ", startTime=" + startTime + ", stopTime="
				+ stopTime + ", iconImg=" + iconImg + ", type=" + type
				+ ", state=" + state + "]";
	}
	
	
}
