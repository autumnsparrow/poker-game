/**
 * 
 */
package com.sky.game.texas.domain.pack;

import java.util.LinkedList;
import java.util.List;

import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.util.Hex;

/**
 * @author sparrow
 *
 */
public class GameTexasCards {
	
	private static final  int MAX_CARDS=16;
	
	List<GameTexasCard> cards;
	
	String hex;


	/**
	 * 
	 */
	public GameTexasCards() {
		// TODO Auto-generated constructor stub
		cards=new LinkedList<GameTexasCard>();
	}
	
	
	
	public void clear(){
		cards.clear();
	}
	public void addCard(GameTexasCard card){
		cards.add(card);
	}


	public GameTexasCards(String hex) throws GameTexasException {
		super();
		this.hex = hex;
		cards=new LinkedList<GameTexasCard>();
		Hex h=new Hex(hex);
		byte[]  bytes=h.getBytes();
		cards.clear();
		for(int i=0;i<bytes.length;i++){
			cards.add(new GameTexasCard(bytes[i]));
		}
		
	}


	public String getHex() {
		if(cards.size()==0){
			return "0000000000000000";
		}else{
		byte[]  bytes=new byte[cards.size()];
		for(int i=0;i<bytes.length;i++){
			bytes[i]=cards.get(i).value;
		}
		
		Hex h=new Hex(bytes);
		this.hex=h.getHex();
		}
		return hex;
	}
	
	
	  
	
	
	
	
	
	
	

}
