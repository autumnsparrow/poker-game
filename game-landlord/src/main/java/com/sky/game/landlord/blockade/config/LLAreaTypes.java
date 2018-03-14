/**
 * @sparrow
 * @Dec 27, 2014   @11:34:37 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config;

/**
 * @author sparrow
 *
 */
public enum LLAreaTypes {
	
	FreeTournamentArea(1,"free tournament"),
	TournamentArea(2,""),
	VipTournamentArea(3,""),
	BlockadeTournamentArea(4,""),
	RacingTournamentArea(5,"");
	
	
	public static final int FREE_TOURNAMENT=1;
	public static final int ELIMINATION_TOURNAMENT=2;
	public static final int VIP_ELIMINATION_TOURNAMENT=3;
	public static final int BLOCKED_TOURNAMENT=4;
	
	
	public int type;
	public String description;
	private LLAreaTypes(int type, String description) {
		this.type = type;
		this.description = description;
	}
	

}
