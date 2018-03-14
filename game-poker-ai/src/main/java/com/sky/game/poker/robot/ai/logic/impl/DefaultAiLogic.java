/**
 * 
 */
package com.sky.game.poker.robot.ai.logic.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.poker.robot.ai.IAiLogic;
import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.PokerCubeLogic;
import com.sky.game.poker.robot.ai.PokerCubeLogic.PokerCubeOrder;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class DefaultAiLogic implements IAiLogic {
	private static final Log logger=LogFactory.getLog(DefaultAiLogic.class);

	/**
	 * 
	 */
	public DefaultAiLogic() {
		// TODO Auto-generated constructor stub
	}

	/* 
	 * 
	 * Default Logic:
	 * 
	 * 1, find the combination that the singles less
	 * 2, the combination out be order consecutive( pair,triples,single) ,triple,pair,single
	 * 
	 * 
	 * (non-Javadoc)
	 * @see com.sky.game.poker.robot.ai.IAiLogic#think(java.util.List, java.util.Stack<com.sky.game.poker.robot.poker.PokerCube>[], com.sky.game.poker.robot.poker.PokerCube[])
	 */
	
 


	
	public void think(IPokerCubeAnalyzer analyzer, PokerCube[][] cubes) {
		
		
		final List<List<PokerCube>> pokerCubes=analyzer.getPokerCubes();
		final PokerCubeLogic logic=analyzer.getPokerCubeLogic();
		// TODO Auto-generated method stub
		Collections.sort(logic.getPokerCubeCombinations(),new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				List<PokerCube> cubes=pokerCubes.get(o1.intValue());
				List<PokerCube> cubes2=pokerCubes.get(o2.intValue());
				PokerCube cube=cubes.get(cubes.size()-1);
				PokerCube cube2=cubes2.get(cubes2.size()-1);
				int cubeRemains=cube.remains();
				int cube2Remains=cube2.remains();
				// checking all the cubes,if exist the triples.
				for(PokerCube c:cubes){
					if(c.getPokerCubeType().compareTo(PokerCubeType.Triple)==0){
						cubeRemains--;
					}
				}
				for(PokerCube c:cubes2){
					if(c.getPokerCubeType().compareTo(PokerCubeType.Triple)==0){
						cube2Remains--;
					}
				}
				
				
				
				return cubeRemains-cube2Remains;
			}
			
		});
		
		for(final PokerCubeOrder order:logic.getPokerCubeOrders()){
			
			Collections.sort(order.getPokerCubeOrder(), new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				
				PokerCubeType pt1=pokerCubes.get(order.getPositionOfCombination()).get(o1.intValue()).getPokerCubeType();
				PokerCubeType pt2=pokerCubes.get(order.getPositionOfCombination()).get(o2.intValue()).getPokerCubeType();
				// defined here.
				
				int comp1=getPokerCubeTypeComparatorValue(pt1);
				int comp2=getPokerCubeTypeComparatorValue(pt2);
				
				return comp2-comp1;
			}

			private int getPokerCubeTypeComparatorValue(PokerCubeType pt){
				int comp=0;
				
				if(pt.isPokerCubeType(PokerCubeType.Bomb)||pt.isPokerCubeType(PokerCubeType.Inconsecutive)){
					comp=1;
				}else if(pt.isPokerCubeType(PokerCubeType.Single)){
					comp=2;
				}else if(pt.isPokerCubeType(PokerCubeType.Pairs)){
					comp=3;
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
			

		});
		
		}
		
		//logger.debug(Arrays.toString(logic.getPokerCubeCombinations().toArray(new Integer[]{})));
		
		logger.debug(logic.toString());
	}

	
}
