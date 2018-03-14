package com.sky.game.service.lottery;

import java.io.InputStream;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sky.game.context.configuration.GameContxtConfigurationLoader;

public class Lottery{
@JsonIgnoreProperties(ignoreUnknown = true)
	public Lottery() {
		// TODO Auto-generated constructor stub
	}
	long id;
	String name;
	long itemId;
	int value;
	float odds;
	String iconImg;
	
	
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


	public long getItemId() {
		return itemId;
	}


	public void setItemId(long itemId) {
		this.itemId = itemId;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}


	public float getOdds() {
		return odds;
	}


	public void setOdds(float odds) {
		this.odds = odds;
	}


	public String getIconImg() {
		return iconImg;
	}


	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}


	@Override
	public String toString() {
		return "Lottery [id=" + id + ", name=" + name + ", itemId=" + itemId
				+ ", value=" + value + ", odds=" + odds + ", iconImg=" + iconImg + "]";
	}


	public Lottery(long id, String name, long itemId, int value, float odds,String iconImg) {
		super();
		this.id = id;
		this.name = name;
		this.itemId = itemId;
		this.value = value;
		this.odds = odds;
		this.iconImg=iconImg;
	}


	public static void main(String args[]){
		InputStream is=Lottery.class.getResourceAsStream("/META-INF/lottery.json");
		Lottery[] a = GameContxtConfigurationLoader.loadConfiguration(is,Lottery[].class);
		System.out.println(a[0].getItemId()+"   "+a[1].getValue());
//		String java1=GameContextGlobals.getJsonConvertor().format(lottery);
//		System.out.println(java1);
		
	}
	
}
