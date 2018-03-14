/**
 * @sparrow
 * @Mar 12, 2015   @2:51:55 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.achievement;

import com.sky.game.landlord.room.LLU;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class UserState {
	int roomId;
	long userId;
	boolean landlord;
	boolean winner;
	PokerCube pokerCube;
	PokerCube lastHand;
	
	long channelId;
	
	int multi;
	
	long duration;
	
	boolean winWithoutPattern;

	boolean spring;
	/**
	 * 
	 */
	public UserState() {
		// TODO Auto-generated constructor stub
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isLandlord() {
		return landlord;
	}

	public void setLandlord(boolean landlord) {
		this.landlord = landlord;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public PokerCube getPokerCube() {
		return pokerCube;
	}

	public void setPokerCube(PokerCube pokerCube) {
		this.pokerCube = pokerCube;
	}

	/**
	 * @param userId
	 * @param landlord
	 * @param winner
	 * @param pokerCube
	 */
	public static UserState  obtain(long userId, boolean landlord, boolean winner,
			PokerCube pokerCube) {
		UserState o=LLU.o(UserState.class);
		
		o.userId = userId;
		o.landlord = landlord;
		o.winner = winner;
		o.pokerCube = pokerCube;
		return o;
	}

	public PokerCube getLastHand() {
		return lastHand;
	}

	public void setLastHand(PokerCube lastHand) {
		this.lastHand = lastHand;
	}

	public int getMulti() {
		return multi;
	}

	public void setMulti(int multi) {
		this.multi = multi;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public boolean isWinWithoutPattern() {
		return winWithoutPattern;
	}

	public void setWinWithoutPattern(boolean winWithoutPattern) {
		this.winWithoutPattern = winWithoutPattern;
	}

	public boolean isSpring() {
		return spring;
	}

	public void setSpring(boolean spring) {
		this.spring = spring;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
	
	

}
