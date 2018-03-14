/**
 * @sparrow
 * @Jan 12, 2015   @1:20:54 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

/**
 * @author sparrow
 *
 */
public enum LLFTTeamStates {
	
	Invalid(0),
	Born(1),
	Waiting(2),
	Begin(3),
	Stage(4),
	StageEnd(5),
	Cancel(6),
	End(7);
	
	public static final int BORN=1;
	public static final int WAITING=2;
	public static final int BEGIN=3;
	public static final int STAGE=4;
	public static final int STAGE_END=5;
	
	public static final int CANCEL=6;
	public static final int END=7;
	
	
	 public int state;

	/**
	 * @param state
	 */
	private LLFTTeamStates(int state) {
		this.state = state;
	}
	
	public boolean eq(LLFTTeamStates  states){
		return state==states.state;
	}
	
	private static final LLFTTeamStates states[]={
		Invalid,
		Born,
		Waiting,
		Begin,
		Stage,
		StageEnd,
	
		Cancel,
		End
	};
	public static LLFTTeamStates getState(int loc){
		return states[loc];
	}
	
	public boolean inStage(){
		return this.eq(LLFTTeamStates.Stage);
	}

}
