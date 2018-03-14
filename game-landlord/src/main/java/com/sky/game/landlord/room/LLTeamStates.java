/**
 * @sparrow
 * @Jan 7, 2015   @6:22:57 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum LLTeamStates {
	Invalid(0),
	Born(1),
	Waiting(2),
	Begin(3),
	StageA(4),
	StageAEnd(5),
	StageB(6),
	StageBEnd(7),
	StageC(8),
	StageCEnd(9),
	Cancel(10),
	End(11),
	Final(12),
	StageWaiting(13);
	
	public static final int BORN=1;
	public static final int WAITING=2;
	public static final int BEGIN=3;
	public static final int STAGEA=4;
	public static final int STAGEA_END=5;
	public static final int STAGEB=6;
	public static final int STAGEB_END=7;
	public static final int STAGEC=8;
	public static final int STAGEC_END=9;
	public static final int CANCEL=10;
	public static final int END=11;
	public static final int FINAL=12;
	public static final int STAGE_WAITING=13;
	
	
	 public int state;

	/**
	 * @param state
	 */
	private LLTeamStates(int state) {
		this.state = state;
	}
	
	public boolean eq(LLTeamStates  states){
		return state==states.state;
	}
	
	private static final LLTeamStates states[]={
		Invalid,
		Born,
		Waiting,
		Begin,
		StageA,
		StageAEnd,
		StageB,
		StageBEnd,
		StageC,
		StageCEnd,
		Cancel,
		End,
		Final,
		StageWaiting
		
	};
	public static LLTeamStates getState(int loc){
		return states[loc];
	}
	
	public boolean inStage(){
		return this.eq(LLTeamStates.StageA)||this.eq(LLTeamStates.StageB)||this.eq(LLTeamStates.StageC);
	}
	
	public boolean inStageEnd(){
		return this.eq(LLTeamStates.StageWaiting);
	
	}
	
	public State getState(){
		return State.obtain(state, toString());
	}
}
