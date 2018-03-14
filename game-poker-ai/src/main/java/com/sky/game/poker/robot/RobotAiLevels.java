/**
 * 
 */
package com.sky.game.poker.robot;

/**
 * @author sparrow
 *
 */
public enum RobotAiLevels {
	Level1(0),
	Level2(1),
	Level3(2),
	Level4(3),
	Level5(4),
	LevelMax(5);
	
	public int state;

	/**
	 * @param state
	 */
	private RobotAiLevels(int state) {
		this.state = state;
	}
	

}
