/**
 * 
 */
package com.sky.game.poker.robot.ai;

import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.game.poker.robot.poker.PokerCube.Cube;

/**
 * 
 * PokerCubeTypes define the advance card pattern which base of basic card pattern {@link Cube} 
 * 
 * @author sparrow
 *
 */
public class PokerCubeTypes {
	
	public PokerCube.Cube cube;
	public int consecutiveLength;
	int id;
	
	
	/**
	 * 
	 * @param cube poker basic pattern. 
	 * @param consecutiveLength poker pattern consecutive length.
	 * @param id  the value of PokerCubeType.state
	 */
	public PokerCubeTypes(Cube cube, int consecutiveLength, int id) {
		super();
		this.cube = cube;
		this.consecutiveLength = consecutiveLength;
		this.id = id;
	}



	public static PokerCubeTypes instance(Cube cube,int consecutiveLength,int id){
		return new PokerCubeTypes(cube, consecutiveLength,id);
	}
	

}
