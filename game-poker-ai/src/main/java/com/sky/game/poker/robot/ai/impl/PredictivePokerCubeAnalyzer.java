/**
 * 
 */
package com.sky.game.poker.robot.ai.impl;

import java.util.List;

import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.PokerCubeLogic;
import com.sky.game.poker.robot.ai.logic.impl.MainAiLogic;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class PredictivePokerCubeAnalyzer implements IPokerCubeAnalyzer {

	/**
	 * 
	 */
	public PredictivePokerCubeAnalyzer() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.poker.robot.ai.IPokerCubeAnalyzer#countPokerCubeCombinations()
	 */
	
	public int countPokerCubeCombinations() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.poker.robot.ai.IPokerCubeAnalyzer#analyze(com.sky.game.poker.robot.poker.PokerCube[][])
	 */
	
	public void analyze(PokerCube[][] cubes) {
		// TODO Auto-generated method stub
		
		// according with current,right,left ,bottom get the best	
		
		MainAiLogic.getInstance().think(this,
				cubes);

	}

	/* (non-Javadoc)
	 * @see com.sky.game.poker.robot.ai.IPokerCubeAnalyzer#getPokerCubeLogic()
	 */
	
	public PokerCubeLogic getPokerCubeLogic() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.poker.robot.ai.IPokerCubeAnalyzer#getPokerCubes()
	 */
	
	public List<List<PokerCube>> getPokerCubes() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.poker.robot.ai.IPokerCubeAnalyzer#getBestActivePokerCube()
	 */
	
	public PokerCube getBestActivePokerCube() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.poker.robot.ai.IPokerCubeAnalyzer#getBestPassivePokerCube()
	 */
	
	public PokerCube getBestPassivePokerCube() {
		// TODO Auto-generated method stub
		return null;
	}

}
