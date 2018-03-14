/**
 * @Apr 17, 2015
 * @sparrow 
 * 
 * @TODO
 *  @copyright Beijing BZWT Technology Co., Ltd.
 */
package com.sky.game.poker.ai.decision.impl;

import java.util.LinkedList;
import java.util.List;

import com.sky.game.poker.ai.decision.Action;

import com.sky.game.poker.ai.decision.Decision;
import com.sky.game.poker.ai.decision.INode;

import com.sky.game.poker.ai.decision.NoAction;

/**
 * examples for a decistion tree:
 * 
 * 
 *           visible |           
 *                   ^
 *                no   yes        -----   test value
 *               /         \  
 *           audible       close     ----- yesNode & noNode for visible(Desicion Node)
 *              ^              ^
 *            yes no]       no   yes
 *           /             /       \
 *          creep         /        attack       ---- yesNode for close (Action Node)
 *                       /
 *                     flank
 *                       ^
 *                     no  yes
 *                     /      \ 
 *                    attack   move  
 * 
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class DecistionTestApp {

	/**
	 * 
	 */
	public DecistionTestApp() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Action creepAction=new CreepAction();
		Action attackActioin=new AttackAction();
		Action moveAction=new MoveAction();
		//Action runawayAction=new RunawayAction();
		
		
		Decision visibleDecision=new Decision();
		Decision audibleDecision=new Decision();
		Decision closeDecision=new Decision();
		Decision flankDecision=new Decision();
		
		visibleDecision.update( closeDecision,audibleDecision);
		audibleDecision.update(creepAction, null);
		closeDecision.update(attackActioin, flankDecision);
		flankDecision.update(moveAction, attackActioin);
		
		
		boolean visible=true;
		boolean close=false;
		boolean flank=true;
		boolean audible=true;
		
		visibleDecision.setValue(visible);
		closeDecision.setValue(close);
		flankDecision.setValue(flank);
		audibleDecision.setValue(audible);

		INode node=visibleDecision.decide();
		System.out.println(node);
		//List<Decision> decisions=new LinkedList<Decision>();
		

	}

}
