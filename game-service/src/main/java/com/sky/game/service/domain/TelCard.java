package com.sky.game.service.domain;

public class TelCard {

	public TelCard() {
		// TODO Auto-generated constructor stub
	}
	long id;
	int value;
	String description;
	String iconImg;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIconImg() {
		return iconImg;
	}
	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}
	public TelCard(long id, int value, String description, String iconImg) {
		super();
		this.id = id;
		this.value = value;
		this.description = description;
		this.iconImg = iconImg;
	}
	@Override
	public String toString() {
		return "TelCards [id=" + id + ", value=" + value + ", description="
				+ description + ", iconImg=" + iconImg + "]";
	}
	
}
