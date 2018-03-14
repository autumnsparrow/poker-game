/**
 * 
 */
package com.sky.game.poker.robot.ai.logic.impl;


import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.sky.game.poker.robot.RobotAiAdapter;
import com.sky.game.poker.robot.ai.IAiLogic;
import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.PokerCubeLogic.PokerCubeOrder;
import com.sky.game.poker.robot.ai.logic.impl.compartor.PokerCubeCombinationComparator;
import com.sky.game.poker.robot.ai.logic.impl.compartor.PokerCubeOrderComparator;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * 
 * 
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
 * 
 * 
 * 
 * 
 六、	出牌的一般原则

1 出牌的原则一般按照从小到大的原则，即首先出包含最小牌的组合（单牌、对子、双顺、连牌、三顺、三条等，炸弹、火箭不包括在内）。
2 三条的出牌原则：因为三条出牌可以带一张单牌或一个对子，所以在出三条时需要检测是否有单牌，如果有，则带一张最小的单牌，如果没
有，则再检测是否存在对子，如果有，则跟一个最小的对子，如果单牌和对子都没有，则出三条。
在带牌时，除非是只剩两手牌，否则不能带王或2。
3 三顺的出牌原则：因为三顺出牌可以带两张（或更多）单牌或两个（或更多）对子，所以与出三条一样，需要检测是否有单牌或对子。如果
有足够多的单牌或对子，则将其带出。如果有单牌，但没有足够多的单牌，则检查是否有6连以上的连牌，如果有将连牌的最小张数当作单牌
带出。如果有对子，但没有足够多的对子，则检查是否有4连以上的双顺，如果有将双顺的最小对子当作对子带出。
在带牌时，除非是只剩两手牌，否则不能带王或2。
4 连牌的出牌原则：直接出。
5 双顺的出牌原则：直接出。
6 对子的出牌原则：因为对子可以用三条、三顺等带出，所以在出对子时，应该先检测一下三条＋三顺（中三条）的数量，如果所有三条数量
<= 对子＋单牌数量总和－2时，出对子，否则出三带2等等。
7 单牌的出牌原则：因为单牌可以用三条、三顺等带出，所以在出单牌时，应该先检测一下三条＋三顺（中三条）的数量，如果所有三条数量
<= 对子＋单牌数量总和－2时，出单牌，否则出三带1等等。

七、	跟牌的一般原则

1 如果手中有独立的，与所出的牌一样牌型的牌时，先跟之。
2 2可以作为单牌、对子、三条等形式跟出。
3 当手中没有相应牌跟时，如果是本方人员出的牌，可以不跟，如果是对方出的牌，则必须拆牌跟，如果再没有，出炸弹或火箭，否则PASS。
4 如果手中的牌除了炸弹。火箭外还剩一手牌，则如果牌型相符，则先跟之，否则炸之。
5 单牌的跟牌原则：如果手中有单牌，则跟之，否则拆2跟之，否则拆对牌跟之，否则拆6连以上的单顺顶张跟之，否则拆三条跟之，否则拆三
顺跟之，否则拆5连单顺跟之，否则拆双顺跟之，否则炸之，否则PASS。
6 对牌的根牌原则：如果手中有对子，则跟之，否则拆4连以上的双顺顶张跟之，否则拆三条跟之，否则拆双顺跟之，否则拆三顺跟之，否则
炸之，否则PASS。
7 三条、三带1、三带2等牌的根牌原则：如果手中有相同牌型的牌则跟之，否则拆三顺跟之，否则炸之，否则PASS。注意，只有在手中牌在出
了以后还剩一手牌时，或直接出完的情况下，才允许带王或2。
在没有足够牌带的情况下，参照单牌。对子的拆牌原则进行拆牌处理。
8 三顺及三顺带牌的根牌原则：如果有相应的牌型，则跟之，否则可以将大的三顺拆成小的三顺跟之，否则炸之，否则PASS。注意，只有在手
中牌在出了以后还剩一手牌时，或直接出完的情况下，才允许带王或2。
在没有足够牌带的情况下，参照单牌。对子的拆牌原则进行拆牌处理。
9 连牌的跟牌原则：如果有相应的牌型，则跟之，否则拆相同张数的双顺，否则拆相同张数的三顺，否则拆不同张数的连牌，否则拆不同张数
的双顺，否则拆不同张数的三顺，否则炸之，否则PASS。
10 双顺的跟牌原则：有相同牌型的牌，则跟之，否则拆不同张数的双顺，否则拆不同张数的三顺，否则拆相同张数的三顺，否则炸之，否则
PASS。
11 炸弹的跟牌原则：有超过所出炸弹的炸弹，或有火箭，则炸之，否则PASS。
12 炸弹带两手牌的跟牌原则：如果有炸弹，则炸之，否则PASS。

八、	打牌原则解析

1 坐庄打法：因为坐庄的只是自己一个人，不存在配合问题，所以一般按照前面的原则出牌即可。
a) 在出牌时，如果偏家有一个人只剩一张牌时，尽量不出单牌，否则单牌由大到小出。
b) 在跟牌时，如果偏家有一个人只剩一张牌时，跟手中最大的牌。
2 偏家打法：偏家因为牵涉到配合问题，所以打法有一些不同。
a) 在出牌时，如果是庄家的上家，且庄家只剩一张牌时，尽量不出单牌，否则单牌由大到小出。
b) 在跟牌时，如果是庄家的上家，且庄家只剩一张牌时，跟手中最大的牌。
c) 当一个偏家打出的是单牌时，一般情况下能跟就跟。如果手中必须跟2或以上的牌时，选择PASS。
d) 当一个偏家打出的是对子时，一般情况下能跟就跟。如果手中必须跟AA或以上的牌时，选择PASS。
e) 如果一个偏家打出的牌是除了单牌及对子以外的牌型，则选择PASS。
f) 如果处在下家的偏家只剩一张牌时，在出牌时出手中最小的牌。跟牌还按照一般的原则。

 * @author sparrow
 *
 */
public class BasicAiLogic implements IAiLogic{

	private static final Log logger=LogFactory.getLog(BasicAiLogic.class);
	/* 
	 * Basic Logic:
	 * 1, identify the landlord and farmers.
	 * 2, identify the largest combinations in the deck.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * @see com.sky.game.poker.robot.ai.IAiLogic#think(com.sky.game.poker.robot.ai.PokerCubeLogic, java.util.List, java.util.Stack<com.sky.game.poker.robot.poker.PokerCube>[], com.sky.game.poker.robot.poker.PokerCube[])
	 */
	
	IPokerCubeAnalyzer analyzer;
	PokerCube[][] cubes;
	
	public void think(IPokerCubeAnalyzer analyzer,
			PokerCube[][] cubes) {
		// TODO Auto-generated method stub
		// load the parameter process 
		this.analyzer=analyzer;
		this.cubes=cubes;
		
		// check if the last hand belongs to friends.
		// last hand not self , and last hand is friend.
		
		//RobotAiAdapter.logger.info(handsOfOutByDeck.toString());
		
		checkActiveAndPass();
		// 
		
		//RobotAiAdapter.logger.info(cubes[0].toString());
		
		log();
		
		
		// common logic
		
		Collections.sort(analyzer.getPokerCubeLogic().getPokerCubeCombinations(),new PokerCubeCombinationComparator(analyzer));
		
		for(final PokerCubeOrder pokerOrder:analyzer.getPokerCubeLogic().getPokerCubeOrders()){
			Collections.sort(pokerOrder.getPokerCubeOrder(), new PokerCubeOrderComparator(pokerOrder, analyzer));
		}
		
		
		
		
	}
	
	
	/**
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
	 * 
	 * 
	 */
	
	
	
	private boolean isOneLeft(int num){
		boolean oneLeft=false;
		PokerCube cCube=getPokerCube(CUR_POS, 0);
		PokerCube rCube=getPokerCube(RIGHT_POS, 0);
		PokerCube lCube=getPokerCube(LEFT_POS, 0);
		if(cCube!=null&&rCube!=null&&lCube!=null){
			int leftCards=getLeftCards(LEFT_POS);//handsOfOutByLeftPlayer.isBelongToLandloard()?LANDLORD_CARDS_SUMS:FARMER_CARDS_SUM- handsOfOutByLeftPlayer.remains();
			int rightCards=getLeftCards(RIGHT_POS);//handsOfOutByRightPlayer.isBelongToLandloard()?LANDLORD_CARDS_SUMS:FARMER_CARDS_SUM-handsOfOutByRightPlayer.remains();
			int currentCards=cCube.remains();
			
			if(cCube.isBelongToLandloard()){
				oneLeft=leftCards==num||rightCards==num;
			}else{
				if(rCube.isBelongToLandloard()){
					oneLeft=rightCards==num;
				}
				if(lCube.isBelongToLandloard()){
					oneLeft=leftCards==num;
				}
			}
		}
		
		return oneLeft;
		
	}
	

	
	
	
	
	
	
	/**
	 * 
	 * log the poker cube and card patterns.
	 */
	private void log(){
		PokerCube pokerCube=this.cubes[3][0];
		PokerCube preCube=this.cubes[3][1];
		RobotAiAdapter.logger.info(String.format("\n[Pass=%-5s,Active=%-5s]\n",pokerCube.canWePass(),pokerCube.isShouldActive() ));
		
		PokerCube cCube=getPokerCube(CUR_POS, 0);
		PokerCube rCube=getPokerCube(RIGHT_POS, 0);
		PokerCube lCube=getPokerCube(LEFT_POS, 0);
		if(cCube!=null&&rCube!=null&&lCube!=null){
		int leftCards=getLeftCards(LEFT_POS);//handsOfOutByLeftPlayer.isBelongToLandloard()?LANDLORD_CARDS_SUMS:FARMER_CARDS_SUM- handsOfOutByLeftPlayer.remains();
		int rightCards=getLeftCards(RIGHT_POS);//handsOfOutByRightPlayer.isBelongToLandloard()?LANDLORD_CARDS_SUMS:FARMER_CARDS_SUM-handsOfOutByRightPlayer.remains();
		int currentCards=pokerCube.remains();
		int[] lefts=new int[3];
		lefts[cCube.getDeckPosition()]=currentCards;
		lefts[rCube.getDeckPosition()]=rightCards;
		lefts[lCube.getDeckPosition()]=leftCards;
		
		boolean[] lords=new boolean[3];
		lords[cCube.getDeckPosition()]=cCube.isBelongToLandloard();
		lords[rCube.getDeckPosition()]=rCube.isBelongToLandloard();
		lords[lCube.getDeckPosition()]=lCube.isBelongToLandloard();
		
		RobotAiAdapter.logger.info(String.format("[\n%c \nA( %02d ,%-6s)\nB( %02d ,%-6s)\nC( %02d ,%-6s)] ",
				"ABC".charAt(cCube.getDeckPosition()),
				lefts[0],lords[0],
				lefts[1],lords[1],
				lefts[2],lords[2]
				)
				);
		}
		for(List<PokerCube> pCubes:analyzer.getPokerCubes()){
			StringBuffer sb=new StringBuffer();
			sb.append("\r\n[");
			for(PokerCube pCube:pCubes){
				sb.append(String.format("\n%-30s - %s - %05d ", pCube.getPokerCubeType().toString(),pCube.cubeToHexString(),pCube.remains()));
			}
			sb.append("]\r\n");
			RobotAiAdapter.logger.info(sb.toString());
		}
		
	}
	
	
	private PokerCube[] getPokerCubes(int pos){
		return (cubes!=null&&pos<cubes.length)?cubes[pos]:null;
		//return leftPokerCubes;
	}
	
	private PokerCube getPokerCube(int pos,int loc){
		return (cubes!=null&&pos<cubes.length&&cubes[pos]!=null&&loc<cubes[pos].length)?cubes[pos][loc]:null;
	}
	
	
	private int getLeftCards(int pos){
		PokerCube[] pCubes=getPokerCubes(pos);
		int total=getPokerCube(pos, 0)!=null?getPokerCube(pos, 0).remains():0;
		if(pCubes!=null){
			for(int i=1;i<pCubes.length;i++)
				total=total-pCubes[i].remains();
		}
		return total;
	}
		
	
	
	/**
	 * determinate pass and active state.
	 * 
	 */
	private void checkActiveAndPass(){
		
		boolean canWePass=false;
		boolean canActive=false;
		PokerCube pokerCube=this.cubes[3][0];
		PokerCube preCube=this.cubes[3][1];
		
		if(preCube!=null){
			//former.
			if(!preCube.isBelongToLandloard()&&!pokerCube.isBelongToLandloard()){
				canWePass=true;//preCube.getDeckPosition()!=pokerCube.getDeckPosition()?true:false;
				// previous hand is former,and is the former itself.
				canActive=preCube.getDeckPosition()==pokerCube.getDeckPosition();
			}
			// landlords
			if(preCube.isBelongToLandloard()&&pokerCube.isBelongToLandloard()){
				canActive=true;
			}
			
			// let's pass.
			// the current user pass on condition of
			// 1.last hand is friends.
			// 2. the opponents cards larger than  6
			//  3+2
			//  4+2
			//  when the right player is the land lord 
			// and the land lord less than 6 don't let's pass.
			if(canWePass){
					int leftCards=getLeftCards(LEFT_POS);
					PokerCube pc=getPokerCube(LEFT_POS,0);
					if(pc!=null&&pc.isBelongToLandloard()){
						canWePass=leftCards>15;
					}
					int rightCards=getLeftCards(RIGHT_POS);
					pc=getPokerCube(RIGHT_POS,0);
					if(pc!=null&&pc.isBelongToLandloard()){
						canWePass=rightCards>15;
					}
			
			}
			
			//pokerCube.setWePass(canWePass);
			//pokerCube.setShouldActive(canActive);
			
			
		}else{
			canActive=true;
		}
		
		pokerCube.setWePass(canWePass);
		pokerCube.setShouldActive(canActive);
	}

	/**
	 * 
	 */
	public BasicAiLogic() {
		// TODO Auto-generated constructor stub
	}

	
	

}
