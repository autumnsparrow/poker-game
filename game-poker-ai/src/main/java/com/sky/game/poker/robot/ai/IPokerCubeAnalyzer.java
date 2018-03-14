/**
 * 
 */
package com.sky.game.poker.robot.ai;

import java.util.List;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public interface IPokerCubeAnalyzer {
	
	public int countPokerCubeCombinations();
	
	

	/**
	 * 
	 * @param pokerCube  the current user pokerCube
	 * @param left   the history of left user show hands.
	 * @param current the history of current user show hands.
	 * @param right   the history of right user show hands. 
	 * @param bottom  the bottom of the Suits.
	 * @param stackOfDeck the history of deck user.
	 */
	public void analyze(PokerCube[][]  cubes);
	
	
	
	public PokerCubeLogic  getPokerCubeLogic();
	public List<List<PokerCube>> getPokerCubes();
	
	public PokerCube getBestActivePokerCube();
	
	public PokerCube getBestPassivePokerCube();
	
	/**
	 * {@link PokerCubeTypes} define the advance poker card pattern {@link PokerCubeType}.
	 * PokerCubeTypes.state equals PokerCubeType.state
	 *  
	 * 
	 */
	public static final PokerCubeTypes[]  pokerCubeTypes={
		
		PokerCubeTypes.instance(PokerCube.Cube.ConsectiveQuplex, 1,0),// bomb
		PokerCubeTypes.instance(PokerCube.Cube.ConsectiveTriple, 2,1),// triple consecutive
		PokerCubeTypes.instance(PokerCube.Cube.ConsectiveTriple, 1,2), // triple  2 , 5 ,4
		PokerCubeTypes.instance(PokerCube.Cube.ConsectivePair, 3,3), // pair consecutive
		PokerCubeTypes.instance(PokerCube.Cube.ConsectiveSingle, 5,4), //single consecutive
		PokerCubeTypes.instance(PokerCube.Cube.ConsectivePair, 1,5), // pair
		PokerCubeTypes.instance(PokerCube.Cube.ConsectiveSingle, 1,6), // single.
		
	};
	
}
