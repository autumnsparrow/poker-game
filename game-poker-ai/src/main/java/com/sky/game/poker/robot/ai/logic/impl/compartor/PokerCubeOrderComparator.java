/**
 * 
 */
package com.sky.game.poker.robot.ai.logic.impl.compartor;

import java.util.Comparator;
import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.ai.PokerCubeLogic.PokerCubeOrder;

/**
 * @author sparrow
 * 
 */
public class PokerCubeOrderComparator implements Comparator<Integer> {

	PokerCubeOrder pokerCubeOrder;
	IPokerCubeAnalyzer analyzer;

	/**
	 * 
	 */
	public PokerCubeOrderComparator() {
		// TODO Auto-generated constructor stub
	}

	public PokerCubeOrderComparator(PokerCubeOrder pokerCubeOrder,
			IPokerCubeAnalyzer baseAiLogic) {
		super();
		this.pokerCubeOrder = pokerCubeOrder;
		this.analyzer = baseAiLogic;
	}

	int singles=2;
	int pairs=3;
	
	/**
	 * Sort the poker cube type by orders.
	 * determine the out order.
	 * 
	 * 
	 * @param pt
	 * @return
	 */
	private int getPokerCubeTypeComparatorValue(PokerCubeType pt) {
		int comp = 0;

		if(pt.isPokerCubeType(PokerCubeType.Bomb)){
			comp=0;
		}else if(
				pt.isPokerCubeType(PokerCubeType.Inconsecutive)){
			comp=1;
		}else if(pt.isPokerCubeType(PokerCubeType.Single)){
			comp=singles;
		}else if(pt.isPokerCubeType(PokerCubeType.Pairs)){
			comp=pairs;
		}else if(pt.isPokerCubeType(PokerCubeType.Triple)){
			comp=4;
		}else if(pt.isPokerCubeType(PokerCubeType.ConsecutiveSingle)){
			comp=5;
		}else if(pt.isPokerCubeType(PokerCubeType.ConsecutiveTriples)){
			comp=6;
		}else if(pt.isPokerCubeType(PokerCubeType.ConsecutivePairs)){
			comp=7;
		}

		return comp;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		PokerCubeType pt1 = analyzer.getPokerCubes()
				.get(pokerCubeOrder.getPositionOfCombination())
				.get(o1.intValue()).getPokerCubeType();
		PokerCubeType pt2 = analyzer.getPokerCubes()
				.get(pokerCubeOrder.getPositionOfCombination())
				.get(o2.intValue()).getPokerCubeType();
		// defined here.
		
		
		
		int comp1 = getPokerCubeTypeComparatorValue(pt1);
		int comp2 = getPokerCubeTypeComparatorValue(pt2);
		
		//RobotAiAdapter.logger.info(String.format("\n(pt=%-20s,v=%02d )\n(pt=%-20s,v=%02d )", pt1.toString(),comp1,pt2.toString(),comp2));
	

		return comp2-comp1;
	}

}
