/**
 * @sparrow
 * @Jan 14, 2015   @10:23:14 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

/**
 * @author sparrow
 *
 */
public class LLGamePlayerMeta {

	/**
	 * 
	 */
	public LLGamePlayerMeta() {
		// TODO Auto-generated constructor stub

	}

	// Object in

	IGamePlayerObserver room, team, deck, game, level;
	// LLGameObjectMap<IGamePlayerObserver> room,team,deck,game,level;

	// LLGameObjectMap<LLGamePlayer> rp,tp,dp,gp,lp,bp;
	// enable player robot state
	// robot or player
	boolean enableRobot;
	// the player is the robot.
	boolean isRobot;
	// eliminate or not
	boolean elimination;
	
	boolean joinRoom;
	boolean online;
	boolean resume;
	boolean shouldHandleResume;
	
	boolean alwaysWin;
	
	long lastAcess;
	 
	
	
	// broken & resume
	int deckRank;
	int roomType;
	String roomName;
	
	int win;
	int lose;
	

	public <T> T getObject(Object o) {
		return o == null ? null : (T) o;
	}

	public boolean isEnableRobot() {
		return enableRobot;
	}

	public void setEnableRobot(boolean enableRobot) {
		this.enableRobot = enableRobot;
	}

	public boolean isElimination() {
		return elimination;
	}

	public void setElimination(boolean elimination) {
		this.elimination = elimination;
	}

	

	
	
	
}
