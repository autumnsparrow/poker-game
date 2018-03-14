/**
 * 
 */
package com.sky.game.poker.robot.ai.logic.impl;

import com.sky.game.poker.robot.ai.IAiLogic;
import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class MainAiLogic implements IAiLogic {

	/**
	 * 
	 */
	public MainAiLogic() {
		// TODO Auto-generated constructor stub
	}
	
	
	private String chooseLogic(){
		return "basic";
	}

	
	
	private static final MainAiLogic mainLogic=new MainAiLogic();
	
	
	public static MainAiLogic getInstance(){
		return mainLogic;
	}
	

	
	public void think(IPokerCubeAnalyzer analyzer, PokerCube[][] cubes) {
		// TODO Auto-generated method stub
		
		
		String logicName=chooseLogic();
		
		// find the algorithm of the logic
		IAiLogic aiLogic=AiLogicFacotry.getLogic(logicName);
		
		// sort the poker cubes.
		aiLogic.think(analyzer, cubes);
	}
	



}
