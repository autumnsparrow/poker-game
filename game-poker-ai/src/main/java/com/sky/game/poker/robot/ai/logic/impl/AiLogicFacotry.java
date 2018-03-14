/**
 * 
 */
package com.sky.game.poker.robot.ai.logic.impl;

import java.util.HashMap;

import com.sky.game.poker.robot.ai.IAiLogic;

/**
 * @author sparrow
 *
 */
public class AiLogicFacotry {

	/**
	 * 
	 */
	public AiLogicFacotry() {
		// TODO Auto-generated constructor stub
	}
	
	private static final HashMap<String,IAiLogic> LOGIC_MAP=new HashMap<String,IAiLogic>();
	
	static{
		LOGIC_MAP.put("default", new DefaultAiLogic());
		LOGIC_MAP.put("basic", new BasicAiLogic());
		LOGIC_MAP.put("predictive", new PredictiveAiLogic());
	}
	
	
	public static IAiLogic getLogic(String name){
		IAiLogic logic=null;
		if(LOGIC_MAP.containsKey(name)){
			logic=LOGIC_MAP.get(name);
		}
		return logic;
	}

}
