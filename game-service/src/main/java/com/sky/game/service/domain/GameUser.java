/**
 * sparrow
 * game-service 
 * Jan 10, 2015- 1:46:16 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;

/**
 * @author sparrow
 *
 */
public class GameUser {
	
	
	long userId;
	int exp;
	int coin;
	int gold;
	int level;
	String levelName;
	String icon;
	String nickName;
	
	long channelId;
	
	long gbuId;
	
	
	

	/**
	 * 
	 */
	public GameUser() {
		// TODO Auto-generated constructor stub
		userId=0;
		exp=0;
		coin=0;
		gold=0;
		level=0;
		levelName="";
		icon="";
		gbuId=0;
	}



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	public int getExp() {
		return exp;
	}



	public void setExp(int exp) {
		this.exp = exp;
	}



	public int getCoin() {
		return coin;
	}



	public void setCoin(int coin) {
		this.coin = coin;
	}



	public int getGold() {
		return gold;
	}



	public void setGold(int gold) {
		this.gold = gold;
	}



	public int getLevel() {
		return level;
	}



	public void setLevel(int level) {
		this.level = level;
	}



	public String getLevelName() {
		return levelName;
	}



	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}



	public String getIcon() {
		return icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}



	public String getNickName() {
		return nickName;
	}



	public void setNickName(String nickName) {
		this.nickName = nickName;
	}



	public long getGbuId() {
		return gbuId;
	}



	public void setGbuId(long gbuId) {
		this.gbuId = gbuId;
	}



	public long getChannelId() {
		return channelId;
	}



	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
	
	

}
