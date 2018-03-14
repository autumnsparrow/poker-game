/**
 * 
 * @Date:Nov 21, 2014 - 4:11:43 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.pack;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum GameCardRankEnum {
	
	
	Nothing(0),
	Pair(1), // 6-6-4-3-2
	TwoPairs(2),// Q-Q-5-5-4.
	ThreeOfKind(3),// 5-5-5-3-2
	Straight(4),//A-K-Q-J-10 , 5-4-3-2-A, known as a wheel
	Flush(5),// spadeK-spadeJ-spade9-spade3-spade2,
	FullHouse(6),//Full House 9-9-9-4-4 
	FourOfind(7),// Four of a kind   heartJ-diamondJ-clubJ-spadeJ-spade9
	StraightFlush(8);//Straight Flush  // clubJ-club10-club9-club8-club7
	
	public int value;

	private GameCardRankEnum(int value) {
		this.value = value;
	}
	
	
	public boolean largerThan(GameCardRankEnum rank){
		return value>rank.value;
	}
	
	public boolean eqauls(GameCardRankEnum rank){
		return value==rank.value;
	}
	
	public State getState(){
		return State.obtain(value, toString());
	}

}
