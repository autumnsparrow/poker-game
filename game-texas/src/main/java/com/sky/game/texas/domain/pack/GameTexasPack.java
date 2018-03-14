/**
 * 
 */
package com.sky.game.texas.domain.pack;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 
 * @author sparrow
 *
 */
public class GameTexasPack {
	
	List<GameTexasCard> cards;

	/**
	 * 
	 * 
	 * 
	 */
	public GameTexasPack() {
		// TODO Auto-generated constructor stub
		cards=new LinkedList<GameTexasCard>();
		
	}
	
	/**
	 * 
	 * 
	 */
	private void reset(){
		cards.clear();
		for(byte i=GameTexasCardValueEnum.v2.value;i<GameTexasCardValueEnum.vA.value+1;i++){
			
			for(GameTexasCardSuitsEnum  v:GameTexasCardSuitsEnum.values())
			{
				cards.add(new GameTexasCard(v,GameTexasCardValueEnum.getCardValue(i)));
			}
			
		}
		
	}
	
	/**
	 * pack 52 cards.
	 * shuffle the cards
	 * 
	 * 
	 */
	public void shuffles(){
		reset();
	}
	
	/**
	 *  get a card from the pack
	 * 
	 * 
	 * @return
	 */
	public GameTexasCard dealCard(){
		// get the card from the last
		int last=cards.size()-1;
		GameTexasCard card=null;
		if(last>=0){
			card=cards.remove(last);
			
		}
		return card;
	}
	
	
	
	
	
	
	
	

}
