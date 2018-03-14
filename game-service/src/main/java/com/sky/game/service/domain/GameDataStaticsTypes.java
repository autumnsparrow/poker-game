/**
 * sparrow
 * game-service 
 * Jan 21, 2015- 2:49:05 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;

/**
 * 
 * 
 * 
 * @author sparrow
 * 
 *         join                              end
 * TournamentTimes  --- > StageBTimes  ->TournamentFinished
 * 										   	WinnerXTimes 
 *                   leave
 *                  TournamentUnfinshed.
 *                  
 *                  RoundTimes
 *                     RoundWinTimes
 *                     RoundLoseTimes
 *
 */
public enum GameDataStaticsTypes {
	Undefine(-1),
	TournamentTimes(0),
	Winner1Times(1),
	Winner2Times(2),
	Winner3Times(3),
	StageBTimes(4),
	//RoundTimes(5),
	RoundWinTimes(5),
	RoundLoseTimes(6),
	TournamentUnfinished(7);
	
	public static final int TT=0; //TournamentTimes
	public static final int WT1=1;//Winner1Times
	public static final int WT2=2;
	public static final int WT3=3;
	public static final int SBT=4;
	public static final int RWT=5;
	public static final int RLT=6;
	public static final int TU=7;
	public int type;
	

	/**
	 * @param type
	 */
	private GameDataStaticsTypes(int type) {
		this.type = type;
	}
	
	public boolean eq(GameDataStaticsTypes t){
		return type==t.type;
	}
	
	private static GameDataStaticsTypes tt[]=new GameDataStaticsTypes[]{
		TournamentTimes,
		Winner1Times,
		Winner2Times,
		Winner3Times,
		StageBTimes,
		//RoundTimes(5),
		RoundWinTimes,
		RoundLoseTimes,
		TournamentUnfinished
		
	};
	
	public static GameDataStaticsTypes getType(int loc){
		return tt[loc];
	}
}
