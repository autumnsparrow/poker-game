package com.sky.game.service.domain;

import java.io.Serializable;

public class GoldExchange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8978691288564774279L;
	public GoldExchange() {
		// TODO Auto-generated constructor stub
	}
	long id;
	int totalCount;
	String toName;
	String fromName;
	int fromCount;
	String iconImg;
	int resDay;
	int resStopDay;
	int resCount;
	String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
	public String getIconImg() {
		return iconImg;
	}
	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public int getFromCount() {
		return fromCount;
	}
	public void setFromCount(int fromCount) {
		this.fromCount = fromCount;
	}
	public GoldExchange(long id, int totalCount, String toName,
			String fromName, int fromCount, String iconImg, int resDay,
			int resStopDay, int resCount,String description) {
		super();
		this.id = id;
		this.totalCount = totalCount;
		this.toName = toName;
		this.fromName = fromName;
		this.fromCount = fromCount;
		this.iconImg = iconImg;
		this.resDay = resDay;
		this.resStopDay = resStopDay;
		this.resCount = resCount;
		this.description=description;
	}
	@Override
	public String toString() {
		return "GoldExchange [id=" + id + ", totalCount=" + totalCount
				+ ", toName=" + toName + ", fromName=" + fromName
				+ ", fromCount=" + fromCount + ", iconImg=" + iconImg
				+ ", resDay=" + resDay + ", resStopDay=" + resStopDay
				+ ", resCount=" + resCount + ", description=" + description
				+ "]";
	}	
}
