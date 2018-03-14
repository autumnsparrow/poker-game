/**
 * 
 */
package com.sky.game.poker.robot.ai.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.PokerAnalyzerSchema;
import com.sky.game.poker.robot.ai.PokerCubeLogic;
import com.sky.game.poker.robot.ai.PokerCubeLogic.PokerCubeOrder;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.ai.PokerCubeTypes;
import com.sky.game.poker.robot.ai.logic.impl.MainAiLogic;
import com.sky.game.poker.robot.math.Permutations;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * 
 * 
 * 
 Old Ai main logic We should not re-implement the old ai logic Jus improve it
 * 
 * 
 * 机器人逻辑主要分为2部分，主动出牌和跟牌的逻辑
 * 
 * 主动出牌逻辑
 * 
 * 包含以下几个主要方法
 * a.processingFirstOutLastOne 当敌方只剩一张牌时：出非单张的牌，如果没有单张，出最大的单张
 * b.processingFirstOutLastTwo 当敌方只剩2张牌是：出非对子的牌（不包括单张）， 当没有非对子牌时，
 * 如果手上有王，出单张，没单张拆对子 随机猜是对子或者单张， 如果随机出来是单张，出对子，没有对子出最大的单张 如果随机出来是对子，出单张，没单张拆对子打
 * 
 * 
 * 
 * 1.只有一手牌时，直接出
 * 2.如果有2手牌 有火箭，先出火箭 有炸弹，并且有对子，出四带二 有炸弹，先出炸弹 
 * 3.我是农民 下一家是农民
 * 农民只有一张牌，获取最小的一张非炸弹的手牌 农民2张牌，获取最小的非炸弹的一对 下一家是地主
 * 地主只有一张牌，processingFirstOutLastOne 地主2张牌，processingFirstOutLastTwo 下下一家是农民
 * 农民只有一张牌，获取最小的一张非炸弹的手牌 农民2张牌，获取最小的非炸弹的一对 4.我是地主 下一家是农民 农民只有一张牌，获取最小的一张非炸弹的手牌
 * 农民2张牌，获取最小的非炸弹的一对 下下一家是农民 农民只有一张牌，获取最小的一张非炸弹的手牌 农民2张牌，获取最小的非炸弹的一对 5.2手牌的其它情况
 * 有非单张的牌型：单顺，双顺，三顺，三个，对子 有单顺，获取小的单顺 有双顺，获取小的双顺 有三顺，获取飞机，
 * 优先带单张，如果单张不够，带对子，如果对子也不够，并且三顺是双顺，将对子当单张来带 出不了飞机，出三带一，优先带单张，其次对子，没有
 * 有三张，获取三带一，优先带单张，其次对子 有对子，获取最小的对子 以上的几种情况，拿出最小的牌出
 * 
 * 6.如果敌方只有一张牌，processingFirstOutLastOne 
 * 7.如果地方只有2张牌，processingFirstOutLastTwo
 * 8.如果全部是炸弹，出一个炸弹
 *  9.剩余的其它情况 出最小的牌，最小的牌根据牌型中关键牌的最小值来进行判断，例如556677和444 ，则出444
 * 
 * @author sparrow
 * 
 */
public class DefaultPokerCubeAnalyzer implements IPokerCubeAnalyzer {
	private static final Log logger = LogFactory
			.getLog(DefaultPokerCubeAnalyzer.class);

	private boolean debug = true;

	PokerCubeLogic logic;
	List<List<PokerCube>> pokerCubes;
	PokerCube pokerCube;

	/**
	 * 
	 * 1.Using  the permutation of {@link PokerCubeTypes} to analyze the {@link PokerCubeType} combinations.
	 * 2.Unique the combinations by {@link HashMap}.
	 * 
	 * All possible combination of card pattern.
	 * To describe the problem:
	 * 
	 * Permutations of 0,1,2,3,4,5 coordinate the {@link PokerCubeType} 
   
    Bomb(0),
	ConsecutiveTriples(1),
	Triple(2),
	ConsecutivePairs(3),
	ConsecutiveSingle(4),
	Pairs(5)
	
	 Each permutation get a different result of {@link PokerAnalyzerSchema}.
	 
	 One PokerCube using the permutations of PokerCubeType get result of List<List<PokerCube>> pokerCubes.
	 
	 The elements of pokerCubes are mutex exclusion. 
	 That means ,we can only get one elements from the pokerCubes,one more best element.
	 The ONE BEST ELEMENT is List<PokerCube> .
	 The order of ONE BEST ELEMENT also can be ordered the BEST ORDER .
	 So, we can sort at two points.
	 	1. using the permutations to get combinations of one PokerCube.
	 	2. using KEY DATA 1 choose the ONE BEST ELEMENT from the Combinations.
	 	3. using KEY DATA 2 order the ONE BEST ELEMENT.
	 	4. the 2 choose using Collections.sort to realize .
	 	
	
	 The main job of the AI should be find the KEY DATA .
	 The default AI using the following KEY DATA:
	 KEY DATA 1:
	 	find the ONE BEST ELEMENT by which one has the smallest single cards.
	 KEY DATA 2:
	 			if(pt.compareTo(PokerCubeType.Bomb)==0){
					comp=1;
				}else if(pt.compareTo(PokerCubeType.Single)==0){
					comp=2;
				}else if(pt.compareTo(PokerCubeType.Pairs)==0){
					comp=3;
				}else if(pt.compareTo(PokerCubeType.Triple)==0){
					comp=4;
				}else if(pt.compareTo(PokerCubeType.ConsecutiveSingle)==0){
					comp=5;
				}else if(pt.compareTo(PokerCubeType.ConsecutiveTriples)==0){
					comp=6;
				}else if(pt.compareTo(PokerCubeType.ConsecutivePairs)==0){
					comp=7;
				}
		order the BEST ORDER as ConsecutivePairs>ConsecutiveTriples>ConsecutiveSingle>Triple>Pair>Single>Bomb
		
		in advance mode the BEST ORDER should consider the alliance out cards, Poker Suits left cards,owner cards,opponents out cards.
		
		
	
	 * 
	 * 
	 * ONE BEST ELEMENT:
	 *  non-consecutive elements.
	 *  1. the smallest hands.
	 *  2. the max hands that poker value larger than six.
	 * 	
	 * 
	 * 
	 * BEST ORDER: (PokerCubeType,Poker values).
	 *   
	 *    1.out the consecutive (consecutive-paris,consecutive-triples,consecutive-singles)
	 *    2.out the smallest non-consecutive (singles,pairs,triples)
	 *    									singles,pairs,triples order by smallest at the top of stack.
	 *    									such as if singles has 3,5,7
	 *    											   pairs has 4,6
	 *    									then  top [singles>pairs] bottom.
	 *    
	 *    3.out the smallest non-consecutive (singles numbers,pairs numbers,triple numbers)
	 *    									such as if  singles has  [9 10]   number=2
	 *    												pairs has [3 6 7 8 9] number=5
	 *    									then top[pairs>singles] bottom.
	 *    4.if 2 and 3 conflicts, then find which one has the biggest ,the one has not the biggest out first.
	 *    
	 *    5.out the biggest (largest singles,largest pairs,largest triples,bombs)
	 *    
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param handsA
	 */
	private void permutation(PokerCube handsA) {
		Permutations<Integer> perm = new Permutations<Integer>(new Integer[] {
				0, 1, 2, 3, 4, 5 });
		// the poker cube combinations
		List<Integer> pokerCubeCombinations;
		// the poker cube orders.
		List<PokerCubeOrder> pokerCubeOrders;

		// merge the result is the same.

		HashMap<String, PokerAnalyzerSchema> merge = new HashMap<String, PokerAnalyzerSchema>();
		//
		// choose from one PokerAnalyzerSchema ,then decide the best hand.
		//
		while (perm.hasNext()) {
			Integer[] arrays = perm.next();

			PokerAnalyzerSchema schema = new PokerAnalyzerSchema();
			schema.analyze(handsA, arrays);
			if (!merge.containsKey(schema.getPokerCubeHexCombination())) {

				merge.put(schema.getPokerCubeHexCombination(), schema);
			}

		}

		pokerCubes = new LinkedList<List<PokerCube>>();

		List<String> keys = new LinkedList<String>();
		for (String s : merge.keySet())
			keys.add(s);

		pokerCubeCombinations = new LinkedList<Integer>();
		pokerCubeOrders = new LinkedList<PokerCubeOrder>();

		int loc = 0;
		for (String k : keys) {
			List<PokerCube> cubes = merge.get(k).getPokerCubes();
			PokerCubeOrder order = new PokerCubeOrder();

			order.setPositionOfCombination(loc);
			order.setPokerCubeOrder(new LinkedList<Integer>());
			for (int pos = 0; pos < cubes.size(); pos++) {
				order.getPokerCubeOrder().add(Integer.valueOf(pos));

			}
			pokerCubeOrders.add(order);
			pokerCubes.add(cubes);

			pokerCubeCombinations.add(Integer.valueOf(loc));
			loc++;

			logger.info(k);
			//logger.info(Arrays.toString(cubes.toArray(new PokerCube[]{})));
		}

		logic = new PokerCubeLogic(pokerCubeCombinations, pokerCubeOrders);


	}

	/**
	 * 
	 */
	public DefaultPokerCubeAnalyzer() {
		// TODO Auto-generated constructor stub

	}
	
	public static DefaultPokerCubeAnalyzer getInstance(){
		return new DefaultPokerCubeAnalyzer();
	}

	public boolean hasMorePokerCube() {
		return pokerCubes.size() > 0;
	}

	public PokerCube getSmallestPokerCube(PokerCube cubes[]) {
		PokerCube pc = null;
		// order the
		for (PokerCube cube : cubes) {
			if (cube.getPokerCubeType().compareTo(PokerCubeType.Single) == 0) {
				pc = cube.clone();
				break;
			}
		}

		return pc;
	}

	/**
	 * return the PokerCube Apply the old ai logica here.
	 * 
	 * Ai show hands basic one the information gathered.
	 * 
	 */
	public PokerCube getBestActivePokerCube() {
		PokerCube pc = new PokerCube();
		pc.cubeHextoCube(PokerCube.ZERO);
		//logger.debug("Parent:" + this.pokerCube);

		// sort

		PokerCube bestPokerCube = getFirstPokerCube();
		
		if(bestPokerCube.getPokerCubeType()!=null){
		if (bestPokerCube.getPokerCubeType().compareTo(PokerCubeType.Pairs) == 0
				|| bestPokerCube.getPokerCubeType().compareTo(
						PokerCubeType.Single) == 0
				|| bestPokerCube.getPokerCubeType().compareTo(
						PokerCubeType.Triple) == 0) {
			pc = bestPokerCube.getSmallestNonconsecutive();
		} else if (bestPokerCube.getPokerCubeType().compareTo(
				PokerCubeType.ConsecutivePairs) == 0
				|| bestPokerCube.getPokerCubeType().compareTo(
						PokerCubeType.ConsecutiveSingle) == 0
				|| bestPokerCube.getPokerCubeType().compareTo(
						PokerCubeType.ConsecutiveTriples) == 0) {
			pc = bestPokerCube.getSmallestConsecutive();

		} else if(bestPokerCube.isPokerCubeType(PokerCubeType.Inconsecutive)) {
			pc = bestPokerCube.getSmallestConsecutive();
		}

		// checking when the triples
		// checking the attaches.
		// find the smallest pairs or singles.

		if (bestPokerCube.getPokerCubeType().compareTo(PokerCubeType.Triple) == 0) {
			//
			PokerCube lastAttaches=getLastPokerCube()==null?PokerCube.getPokerCubeByHex(PokerCube.ZERO):getLastPokerCubeAttaches().clone();
			lastAttaches=lastAttaches.maskInConsecutiveCube();
			if(lastAttaches!=null&&lastAttaches.remains()>0){
				pc=pc.addPokerCube(lastAttaches.getSmallestNonconsecutive());
				if(lastAttaches.getPokerCubeType().compareTo(PokerCubeType.Single)==0){
					pc.setPokerCubeType(PokerCubeType.TriplesWithSingle);
				}else if(lastAttaches.getPokerCubeType().compareTo(PokerCubeType.Pairs)==0){
					pc.setPokerCubeType(PokerCubeType.TriplesWithPair);
				}
				
			}
			

		} else if (bestPokerCube.getPokerCubeType().compareTo(
				PokerCubeType.ConsecutiveTriples) == 0) {
			PokerCube lastAttaches=getLastPokerCubeAttaches().clone();
			lastAttaches=lastAttaches.maskInConsecutiveCube();
			
			
			int needAttaches = bestPokerCube.consectiveTriple().sumOfElements();
			lastAttaches = lastAttaches.maskInConsecutiveCube();
			lastAttaches.calculate();
			// just attach the consecutive cube
			// last cube to remove the jokers,and 2
			if (lastAttaches.getPokerCubeType().compareTo(PokerCubeType.Single) == 0) {
				
				if (lastAttaches.consectiveSolo().sumOfElements() >= needAttaches) {
					while (needAttaches > 0) {
						PokerCube attachPokerCube = lastAttaches
								.getSmallestNonconsecutive();
						if (!attachPokerCube.isEmpty()) {
							pc = pc.addPokerCube(attachPokerCube);
							lastAttaches.subPokerCube(attachPokerCube);

						}
						needAttaches--;
					}
					pc.setPokerCubeType(PokerCubeType.ConsecutiveTriplesWithSingles);
				}

			}else if(lastAttaches.getPokerCubeType().compareTo(PokerCubeType.Pairs) == 0) {
				
				if (lastAttaches.consectiveSolo().sumOfElements() >= needAttaches) {
					while (needAttaches > 0) {
						PokerCube attachPokerCube = lastAttaches
								.getSmallestNonconsecutive();
						if (!attachPokerCube.isEmpty()) {
							pc = pc.addPokerCube(attachPokerCube);
							lastAttaches.subPokerCube(attachPokerCube);

						}
						needAttaches--;
					}
					pc.setPokerCubeType(PokerCubeType.ConsecutivePairs);
				}

			}
		}
		}
		
		
		
		pc.setDeckPoistion(this.pokerCube.getDeckPosition());
		

		return pc;
	}

	public int countPokerCubeCombinations() {
		// TODO Auto-generated method stub
		return pokerCubes.size();
	}

	

	public List<List<PokerCube>> getPokerCubes() {
		return pokerCubes;
	}

	/**
	 * Ai according the previous hands to show hands.
	 * 
	 */
	public PokerCube getBestPassivePokerCube() {
		// TODO Auto-generated method stub
		PokerCube pc = new PokerCube();
		pc.cubeHextoCube(PokerCube.ZERO);
	//	logger.debug("Parent:" + this.pokerCube);
		
		// Can we pass
		//previousHands.calculate();
		
		PokerCubeType pokerCubeType =null;
		if(previousHands!=null){
			pokerCubeType=previousHands.judgePokerCubeType();	
		}
		//PokerCube.getPokerCubeTypes(previousHands) ;//previousHands.getPokerCubeType();
		//previousHands.setPokerCubeType(pokerCubeType);

		if (pokerCubeType != null) {
			// analyze the poker
			// analyze(pokerCube);
			// search the pattern
			if(pokerCubeType.isPokerCubeType(PokerCubeType.Rocket)){
				pc=PokerCube.getPokerCubeByHex(PokerCube.ZERO);
			}else{

			// should checking if the triples with attaches.
			// search from the combinations.
			for (int i = 0; i < countPokerCubeCombinations(); i++) {
				List<PokerCube> cubes = pokerCubes.get(i);
				for (PokerCube cube : cubes) {
					if (cube.getPokerCubeType().compareTo(pokerCubeType) == 0) {
						if(!cube.isEmpty()){
						pc = cube.getLargerPokerCube(previousHands,false);
						if (!pc.isEmpty())
							break;
						}
					}
				}
				
				if (!pc.isEmpty())
					break;
			}
			if (pc.isEmpty()) {
				pc = this.pokerCube.getLargerPokerCube(this.previousHands,true);
			}
			}

		}

		pc.setDeckPoistion(this.pokerCube.getDeckPosition());
		

		return pc;
	}

	public PokerCube getFirstPokerCube() {
		Integer order = logic.getPokerCubeCombinations().get(0);
		PokerCubeOrder pokerCubeOrder = logic.getPokerCubeOrder(order);
		List<PokerCube> cubes = pokerCubes.get(order.intValue());
		PokerCube pc =cubes.size()>0?
				cubes.get(pokerCubeOrder.getPokerCubeOrder().get(0)):PokerCube.getPokerCubeByHex(PokerCube.ZERO);
		if(pc==null){
			pc=PokerCube.getPokerCubeByHex(PokerCube.ZERO);
		}
		return pc;
	}
	
	

	public PokerCube getLastPokerCube() {
		Integer order = logic.getPokerCubeCombinations().get(0);
		PokerCubeOrder pokerCubeOrder = logic.getPokerCubeOrder(order);
		List<PokerCube> cubes = pokerCubes.get(order.intValue());
		PokerCube pc = cubes.get(pokerCubeOrder.getPokerCubeOrder().get(
				cubes.size() - 1));
		return pc;
	}
	
	/**
	 * get special poker cube 
	 * @param pCubes
	 * @param pt
	 * @return
	 */
	private PokerCube getPokerCube(List<PokerCube> pCubes,PokerCubeType pt){
		PokerCube pc=null;
		for(PokerCube pCube:pCubes){
			if(pCube.isPokerCubeType(pt)){
				pc=pCube;
				break;
			}
		}
		return pc;
	}
	
	/**
	 * get the single or pairs from the combinations.
	 * 
	 * @return
	 */
	public PokerCube getLastPokerCubeAttaches(){
		Integer order = logic.getPokerCubeCombinations().get(0);
		//PokerCubeOrder pokerCubeOrder = logic.getPokerCubeOrder(order);
		List<PokerCube> cubes = pokerCubes.get(order.intValue());
		PokerCube pc=null;
		PokerCube single=null;
		PokerCube pair=null;
		
		single=getPokerCube(cubes, PokerCubeType.Single);
		pair=getPokerCube(cubes,PokerCubeType.Pairs);
		int offsetSingle=-1;
		int offsetPair=-1;
		if(single!=null){
			single.calculate();
			offsetSingle=single.consectiveSolo().offsetBeforeNonZeroElementN(1);
		}
		if(pair!=null){
			pair.calculate();
			offsetPair=pair.consectivePair().offsetBeforeNonZeroElementN(1);
		}
		
		if(offsetSingle!=-1&&offsetPair!=-1){
			if(offsetSingle<offsetPair){
				pc=single;
			}else {
				pc=pair;
			}
		}else if(offsetSingle==-1&&offsetPair!=-1){
			pc=pair;
		}else if(offsetSingle!=-1&&offsetPair==-1){
			pc=single;
		}else {
			pc=null;
		}
		
		
		if(pc==null){
			pc=PokerCube.getPokerCubeByHex(PokerCube.ZERO);
		}
		
//		for(Integer loc:pokerCubeOrder.getPokerCubeOrder()){
//			pc=cubes.get(loc);
//			if(pc.getPokerCubeType().compareTo(PokerCubeType.Single)==0){
//				single=pc;
//			}else if(
//					pc.getPokerCubeType().compareTo(PokerCubeType.Pairs)==0){
//				pair=pc;
//			}
//			
//		}
//		
//		//
//		if(single!=null&&pair!=null){
//			int offsetSingle=single.consectiveSolo().offsetBeforeNonZeroElementN(1);
//			int offsetPair=pair.consectivePair().offsetBeforeNonZeroElementN(1);
//			if(offsetSingle<offsetPair){
//				pc=single;
//			}else{
//				pc=pair;
//			}
//		}
		
		return pc;
		
	}

	private PokerCube previousHands;
	
	
	

	/**
	 * 
	 * 
	 * 
	 * 
	 */
	
	public void analyze( PokerCube[][] cubes) {
		// TODO Auto-generated method stub
		// analyze the poker cube 
		this.pokerCube=cubes[3][0];
		this.previousHands=cubes[3][1];
		this.pokerCube.calculate();
		this.permutation(this.pokerCube);
		// according with current,right,left ,bottom get the best	
		
		MainAiLogic.getInstance().think(this,
				cubes);

	}

	
	public PokerCubeLogic getPokerCubeLogic() {
		// TODO Auto-generated method stub
		return logic;
	}

}
