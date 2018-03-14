/**
 * @sparrow
 * @Dec 27, 2014   @11:20:57 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config;

/**
 * @author sparrow
 *
 */
public enum TournamentTypes {
	//the tournament type.
	Tournament(1,"user number reached ,start"),
	FreeTournament(2,"free tournament"),
	
	//
	BlockadeTournament(3,"blocked tournament");
	
	public int category;
	public String description;
	private TournamentTypes(int category, String description) {
		this.category = category;
		this.description = description;
	}
	

}
