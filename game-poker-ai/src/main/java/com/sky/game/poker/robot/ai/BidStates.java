/**
 * 
 */
package com.sky.game.poker.robot.ai;

/**
 * @author sparrow
 *
 */
public enum BidStates {
	ZeroPoint(0),
	OnePoint(1),
	TwoPoints(2),
	ThreePoints(3),
	Pass(0);
	
	private int state;

	private BidStates(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}
	
	
	
}
