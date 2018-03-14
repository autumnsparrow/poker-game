/**
 * 
 */
package com.sky.game.poker.robot.ai;

import java.util.Stack;

import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class AiLogicaEntry {
	
	PokerCube pokerCube;
	PokerCube bottomPokerCube;
	
	Stack<PokerCube> left;
	Stack<PokerCube> current;
	Stack<PokerCube> right;
	
	Stack<PokerCube> stackOfDeck;

	public AiLogicaEntry(PokerCube pokerCube, PokerCube bottomPokerCube,
			Stack<PokerCube> left, Stack<PokerCube> current, Stack<PokerCube> right,
			Stack<PokerCube> stackOfDeck) {
		super();
		this.pokerCube = pokerCube;
		this.bottomPokerCube = bottomPokerCube;
		this.left = left;
		this.current = current;
		this.right = right;
		this.stackOfDeck = stackOfDeck;
	}

	

}
