/**
 * @sparrow
 * @Dec 11, 2014   @3:58:57 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

/**
 * @author sparrow
 *
 */
public class LLDeckSeatScoreBroad {
	
	int score;
	int oldScore;
	int currentScore;
	int addition;
	
	

	/**
	 * 
	 */
	public LLDeckSeatScoreBroad() {
		// TODO Auto-generated constructor stub
	}
	
	void reset(){
		
		score=0;
		oldScore=0;
		currentScore=0;
		addition=0;
	}

	@Override
	public String toString() {
		return "LLDeckSeatScoreBroad [score=" + score + ", oldScore="
				+ oldScore + ", currentScore=" + currentScore + ", addition="
				+ addition + "]";
	}
	
	

}
