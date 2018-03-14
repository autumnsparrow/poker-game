package com.sky.game.service.domain;

import java.io.Serializable;

public class Goods implements Serializable{

	public Goods() {
		// TODO Auto-generated constructor stub
	}
	Long id;
	String iconImg;
	public String getIconImg() {
		return iconImg;
	}
	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}
	String itemName;
	Long itemCount;
	String description;
	int con;
	int resDay;
	int resStopDay;
	int resCount;
	String fromName;
	public int getResDay() {
		return resDay;
	}
	public void setResDay(int resDay) {
		this.resDay = resDay;
	}
	public int getResStopDay() {
		return resStopDay;
	}
	public void setResStopDay(int resStopDay) {
		this.resStopDay = resStopDay;
	}
	public int getResCount() {
		return resCount;
	}
	public void setResCount(int resCount) {
		this.resCount = resCount;
	}
	public int getCon() {
		return con;
	}
	public void setCon(int con) {
		this.con = con;
	}
	String userCondition;
	
	public String getUserCondition() {
		String uc=null;
		if(getCon()>0){
			uc="你还需要"+getCon()+"金币";
		}else{
			uc="你可以兑换该物品";
		}
		return uc;
	}
	public void setUserCondition(String userCondition) {
		this.userCondition = userCondition;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getItemCount() {
		return itemCount;
	}
	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
}

