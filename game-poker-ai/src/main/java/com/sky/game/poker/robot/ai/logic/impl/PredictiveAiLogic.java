/**
 * 
 */
package com.sky.game.poker.robot.ai.logic.impl;

import java.util.Arrays;

import com.sky.game.poker.robot.RobotAiAdapter;
import com.sky.game.poker.robot.ai.IAiLogic;
import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.impl.CardStateDivsion;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 * 
 */
public class PredictiveAiLogic implements IAiLogic {

	

	/**
	 * 
	 */
	public PredictiveAiLogic() {
		// TODO Auto-generated constructor stub
	}

	IPokerCubeAnalyzer analyzer;
	PokerCube[][] cubes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.poker.robot.ai.IAiLogic#think(com.sky.game.poker.robot.ai.IPokerCubeAnalyzer
	 * , com.sky.game.poker.robot.poker.PokerCube[][])
	 */

	PokerCube currentPokerCube, rightPokerCube, leftPokerCube;
	CardStateDivsion currentCardStateDivsion, rightCardStateDivision,
			leftCardStateDivistion;
	PokerCube basePokerCube;

	
	public void think(IPokerCubeAnalyzer analyzer, PokerCube[][] cubes) {
		// TODO Auto-generated method stub
		this.analyzer = analyzer;
		this.cubes = cubes;
		this.basePokerCube = cubes[3][2];

		currentPokerCube = getPlayerPokerCube(CUR_POS);
		rightPokerCube = getPlayerPokerCube(RIGHT_POS);
		leftPokerCube = getPlayerPokerCube(LEFT_POS);
		currentCardStateDivsion = getCardStateDivsion(currentPokerCube);
		rightCardStateDivision = getCardStateDivsion(rightPokerCube);
		leftCardStateDivistion = getCardStateDivsion(leftPokerCube);

		if (currentCardStateDivsion != null
				&& currentCardStateDivsion.getPokerAnalyzerSchemas() != null) {
			RobotAiAdapter.logger.info("Current\n"+currentPokerCube.toString());
			RobotAiAdapter.logger.info(Arrays.toString(currentCardStateDivsion
					.getPokerAnalyzerSchemas()));
		}

		if (rightCardStateDivision != null
				&& rightCardStateDivision.getPokerAnalyzerSchemas() != null) {
			RobotAiAdapter.logger.info("Right\n"+rightPokerCube.toString());
			RobotAiAdapter.logger.info(Arrays.toString(rightCardStateDivision
					.getPokerAnalyzerSchemas()));
		}

		if (leftCardStateDivistion != null
				&& leftCardStateDivistion.getPokerAnalyzerSchemas() != null) {
			RobotAiAdapter.logger.info("Left\n"+leftPokerCube.toString());
			RobotAiAdapter.logger.info(Arrays.toString(leftCardStateDivistion
					.getPokerAnalyzerSchemas()));
		}
		
		
		
		

	}

	/**
	 * 
	 * 
	 * 
	 * @param pos
	 * @return
	 */
	private PokerCube[] getPokerCubes(int pos) {
		return (cubes != null && pos < cubes.length) ? cubes[pos] : null;
		// return leftPokerCubes;
	}

	/**
	 * 
	 * 
	 * @param pos
	 * @param loc
	 * @return
	 */
	private PokerCube getPokerCube(int pos, int loc) {
		return (cubes != null && pos < cubes.length && cubes[pos] != null && loc < cubes[pos].length) ? cubes[pos][loc]
				: null;
	}

	private PokerCube getPlayerPokerCube(int pos) {
		//
		PokerCube[] pCubes = getPokerCubes(pos);

		PokerCube pCube = null;
		if (pCubes != null) {
			pCube = pCubes[0];
			if (pCube.isBelongToLandloard()) {
				pCube = pCube.addPokerCube(this.basePokerCube);
			}
			for (int i = 1; i < pCubes.length; i++) {
				pCube.subPokerCube(pCubes[i]);
			}
		}

		return pCube;

	}

	private CardStateDivsion getCardStateDivsion(PokerCube pCube) {
		return new CardStateDivsion(pCube);
	}

}
