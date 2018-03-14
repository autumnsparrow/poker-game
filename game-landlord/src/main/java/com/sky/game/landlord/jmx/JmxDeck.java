/**
 * @sparrow
 * @Feb 11, 2015   @11:15:26 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.jmx;

import java.util.List;

import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
public class JmxDeck  {
	
	List<JmxPlayer> players;
	JmxGame game;
	
	/**
	 * 
	 */
	public JmxDeck() {
		// TODO Auto-generated constructor stub
		players=GameUtil.getList();
		
	}

	public List<JmxPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(List<JmxPlayer> players) {
		this.players = players;
	}

	public JmxGame getGame() {
		return game;
	}

	public void setGame(JmxGame game) {
		this.game = game;
	}



	
	
	

}
