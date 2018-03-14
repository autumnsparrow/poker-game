/**
 * 
 */
package com.sky.game.protocol;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.domain.MinaBeans;

import com.sky.game.protocol.commons.TG0007Beans;
import com.sky.game.protocol.commons.TG0007Beans.Gamebear;

/**
 * @author Administrator
 *
 */
public class MinaClient {

	/**
	 * 
	 */
	public MinaClient() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameContextGlobals.init(MinaClient.class
				.getResourceAsStream("/META-INF/game-context.conf"));
		
		long userId=1032l;
    	long chips=200l;
    	String pokerHand="deefbeac";
    	String deviceId="";
    	
    	TG0007Beans.Gamebear gamebear=Gamebear.getGamebear(userId, chips, pokerHand);
    	MinaBeans.sendMinaMessage("Token",deviceId, "TG0007", gamebear);
	}

}
