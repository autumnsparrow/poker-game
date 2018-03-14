/**
 * @sparrow
 * @1:17:15 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
public class DeckConfiguration {
	
	@JsonIgnore
	int baseChips;
	@JsonIgnore
	int gamePerDeck;
	@JsonIgnore
	String roomName;
	@JsonIgnore
	int roomType;
	
	int winExp;
	int loseExp;
	
	@JsonIgnore
	boolean firstCallRandom;
	@JsonIgnore
	long bidWaitingTime;
	@JsonIgnore
	long kickWaitingTime;
	@JsonIgnore
	long pullWaitingTime;
	@JsonIgnore
	long showHandWaitingTime;
	
	@JsonIgnore
	long robotBidWaitingTime;
	@JsonIgnore
	long robotKickWaitingTime;
	@JsonIgnore
	long robotPullWaitingTime;
	@JsonIgnore
	long robotShowHandWaitingTime;
	@JsonIgnore
	int aiLevel;
	
	

	

	/**
	 * 
	 */
	public DeckConfiguration() {
		// TODO Auto-generated constructor stub
		super();
//		baseChips=1000;
//		
//		winExp=50;
//		loseExp=10;
		gamePerDeck=1;
		firstCallRandom=true;
//		
		bidWaitingTime=30*1000;//30 seconds.
		kickWaitingTime=5*1000;
		pullWaitingTime=5*1000;
		showHandWaitingTime=30*1000;
		
		
		
		robotBidWaitingTime=(3+GameUtil.getRandom(3))*1000;
		robotKickWaitingTime=(3+GameUtil.getRandom(3))*1000;
		robotPullWaitingTime=(3+GameUtil.getRandom(3))*1000;
		robotShowHandWaitingTime=(2+GameUtil.getRandom(3))*1000;
		aiLevel=0;
		

	}


	public int getWinExp() {
		return winExp;
	}


	public void setWinExp(int winExp) {
		this.winExp = winExp;
	}


	public int getLoseExp() {
		return loseExp;
	}


	public void setLoseExp(int loseExp) {
		this.loseExp = loseExp;
	}


	public boolean isFirstCallRandom() {
		return firstCallRandom;
	}


	public void setFirstCallRandom(boolean firstCallRandom) {
		this.firstCallRandom = firstCallRandom;
	}


	public long getBidWaitingTime() {
		return bidWaitingTime;
	}


	public void setBidWaitingTime(long bidWaitingTime) {
		this.bidWaitingTime = bidWaitingTime;
	}


	public long getRobotBidWaitingTime() {
		return robotBidWaitingTime;
	}


	public void setRobotBidWaitingTime(long robotBidWaitingTime) {
		this.robotBidWaitingTime = robotBidWaitingTime;
	}


	public long getKickWaitingTime() {
		return kickWaitingTime;
	}


	public void setKickWaitingTime(long kickWaitingTime) {
		this.kickWaitingTime = kickWaitingTime;
	}


	public long getRobotKickWaitingTime() {
		return robotKickWaitingTime;
	}


	public void setRobotKickWaitingTime(long robotKickWaitingTime) {
		this.robotKickWaitingTime = robotKickWaitingTime;
	}


	public long getPullWaitingTime() {
		return pullWaitingTime;
	}


	public void setPullWaitingTime(long pullWaitingTime) {
		this.pullWaitingTime = pullWaitingTime;
	}


	public long getRobotPullWaitingTime() {
		return robotPullWaitingTime;
	}


	public void setRobotPullWaitingTime(long robotPullWaitingTime) {
		this.robotPullWaitingTime = robotPullWaitingTime;
	}


	public long getShowHandWaitingTime() {
		return showHandWaitingTime;
	}


	public void setShowHandWaitingTime(long showHandWaitingTime) {
		this.showHandWaitingTime = showHandWaitingTime;
	}


	public long getRobotShowHandWaitingTime() {
		return robotShowHandWaitingTime;
	}


	public void setRobotShowHandWaitingTime(long robotShowHandWaitingTime) {
		this.robotShowHandWaitingTime = robotShowHandWaitingTime;
	}


	public int getBaseChips() {
		return baseChips;
	}


	public void setBaseChips(int baseChips) {
		this.baseChips = baseChips;
	}


	public int getGamePerDeck() {
		return gamePerDeck;
	}


	public void setGamePerDeck(int gamePerDeck) {
		this.gamePerDeck = gamePerDeck;
	}


	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public int getRoomType() {
		return roomType;
	}


	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}


	public int getAiLevel() {
		return aiLevel;
	}


	public void setAiLevel(int aiLevel) {
		this.aiLevel = aiLevel;
	}

	
	

}
