/**
 * @sparrow
 * @Jan 30, 2015   @5:34:50 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.achievement;

/**
 * @author sparrow
 *
 */
public class UserWinnerType {
	long roomId;
	Long userId;
	int rank;
	boolean alwaysWin;
	
	int tournamentType;
	//boolean landlord;

	/**
	 * 
	 */
	public UserWinnerType() {
		// TODO Auto-generated constructor stub
	}

	public boolean isAlwaysWin() {
		return alwaysWin;
	}

	public void setAlwaysWin(boolean alwaysWin) {
		this.alwaysWin = alwaysWin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	
}
