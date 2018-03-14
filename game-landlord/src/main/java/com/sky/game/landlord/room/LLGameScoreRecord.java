/**
 * @sparrow
 * @2:29:17 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.protocol.commons.GL0000Beans.LG2001Response;

/**
 * @author sparrow
 *
 */
public class LLGameScoreRecord {
	
	
	int bid;
	 int bomb;
	 int rocket;
	boolean noDealPlayed;

	
	
	
	
	

	/**
	 * 
	 */
	public LLGameScoreRecord() {
		// TODO Auto-generated constructor stub
		bomb=0;
		rocket=0;
		noDealPlayed=false;
		
	}

	
	
	
	/**
	 * total score calculated.
	 *  
	 * @return
	 */
	int calculate(){
		int factor=(noDealPlayed?1:0)+bomb+rocket;
		//int base=bid;
		
		int score=(int) (Math.pow(2, factor));
		return score;
	}
	
	int factor(){
		 int factor=(noDealPlayed?1:0)+bomb+rocket;
		 return factor;
	}
	
	int calculateWithoutKickerOrPull(int addition){
		
		int factor=(noDealPlayed?1:0)+bomb+rocket+addition;
		int base=bid;
		
		int score=(int) (base*Math.pow(2, factor));
		return score;
		
	}




	/**
	 * 
	 * @return
	 */
	public LG2001Response wrap(){
		LG2001Response o=LLU.o(LG2001Response.class);
		o.setMulti(calculate());
		
		return o;
	}
	
	
	
	
	
	
}
