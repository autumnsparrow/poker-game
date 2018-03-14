package com.sky.poke.solution;

import java.util.ArrayList;
import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.util.PokeHelper;

/**
 *  * 拆牌工厂，通过指定的拆牌规则，返回拆牌方案
 *  
 * 获取拆牌方案 -》计算最优出牌方案（单牌手数越小越好）-》
 * 将单牌和双牌分配给飞机等（）
 * -》计算最先出牌方案
 * @author shahuaitao
 *
 */
public class CardSolutionFactory {
	
	private static CardSolutionFactory factory;
	
	public CardSolutionFactory(){
		
	}

	public static CardSolutionFactory getInstance(){
		if(factory==null){
			factory=new CardSolutionFactory();
		}
		return factory;
	}
	
	/**
	 * 获取拆牌解决方案
	 * @param cards  所有牌
	 * solutions 所有解决方案的引用
	 * currSolution 当前solution
	 * @return false表示不可再拆出 龙，炸弹，飞机，连队，3张
	 */
	public void getDevidedCardSolution(Poke poke,List<CardSolution> cardSolutions,CardSolution cardsolution){
		
		//拆出所有可能的 龙，炸弹，飞机，连队，3张
		List<Poke> allpokes=getAllPossibleOneSendCard(poke);
		
		//如果有，遍历，将任何一个加入到currSolution中
		if(allpokes!=null&&allpokes.size()>0){
			for (int i = 0; i < allpokes.size(); i++) {
				Poke spoke=allpokes.get(i);
				CardSolution nextsolution=new CardSolution();
				List<Poke> spokes=new ArrayList<Poke>();
				//将之前的OneSendCard放进来
				if(cardsolution!=null&&cardsolution.getOneSendCards()!=null&&cardsolution.getOneSendCards().size()>0){
					spokes.addAll(cardsolution.getOneSendCards());
				}
				spokes.add(spoke);
				nextsolution.setOneSendCards(spokes);
				
				Poke leftPoke=PokeHelper.playsPokes(poke, spoke);
				this.getDevidedCardSolution(leftPoke, cardSolutions, nextsolution);
				
			}
		
		}else{
			//如果没有，说明不可以再拆解了，
			//1将cards加入到solution的rabisish中
			//2将currSolution加入到solutions中，并将currSolution
			List<Poke> spokes=PokeHelper.getAllSamePokes(poke, PokeRegulation.SINGLE);
			List<Poke> dpokes=PokeHelper.getAllSamePokes(poke, PokeRegulation.PAIR);
			spokes.addAll(dpokes);
			if(cardsolution==null){
				cardsolution = new CardSolution();
			}
			cardsolution.setSingleOrDouble(spokes);
			cardSolutions.add(cardsolution);
			
		}
		
	}
	
	
	/**
	 * 获取最优解决方案
	 * @param solutions
	 * @return
	 */
	public CardSolution getBestDevidedCardSolution(List<CardSolution> solutions){
		//根据单牌手数比较，单牌手数越小，解决方案越好,
		//double initValue=100;
		CardSolution best=null;
		/*for(DevidedCardSolution solution:solutions){
			if(solution.getSingleCount()<initValue){
				best=solution;
				initValue=solution.getSingleCount();
			}
		}*/
		//先找出单牌手数最少的所有解决方案
		List<CardSolution> allBestSolutions=getAllBestDevidedCardSolution(solutions);
		//再找出出牌次数最少的方案
		double shouShu=100;
		for(CardSolution temp:allBestSolutions){
			if(shouShu>temp.getSendCount()){
				best=temp;
			}
		}
		return best;
	}
	/**
	 * 获取所有最优解决方案,因为可能有很多好的解决方案
	 * @param solutions
	 * @return
	 */
	public List<CardSolution> getAllBestDevidedCardSolution(List<CardSolution> solutions){
		//根据单牌手数比较，单牌手数越小，解决方案越好
		double initValue=100;
		CardSolution best=null;
		 List<CardSolution> tempList=new ArrayList<CardSolution>();
		for(CardSolution solution:solutions){
			if(solution.getSingleCount()<initValue){
				//如果小于
				best=solution;
				initValue=solution.getSingleCount();
				tempList.clear();
				tempList.add(solution);
			}else if(solution.getSingleCount()==initValue){
				tempList.add(solution);
			}
		}
		return tempList;
	}
	
	
	/**
	 *找出最先出牌，通过比较压牌比值，压牌比值最小的最先出
	 * @param dcs
	 * @return
	 */
//	public Poke getFirstOneSendCard(CardSolution dcs){
//		List<Poke> oldSingleOrDouble=dcs.getSingleOrDouble();
//		//先将未 分配之前的解决方案保存
//		List<Poke> saveSingleOrDouble=new ArrayList<Poke>();
//		for(Poke osc:oldSingleOrDouble){
//			Poke newOsc=new Poke(osc.getOneSendCardList(),osc.getCardType());
//			saveSingleOrDouble.add(newOsc);
//		}
//
//		//是否有必要先出单牌
//		boolean hasNessary=false;
//		//最后判断是否要先出单牌
//		boolean isSingleFirst=false;
//		
//		//先判断有没有必要出单牌
//		if(dcs.getSingleCount()>0){
//			//单牌手数>0
//			hasNessary=true;
//		}else{
//			hasNessary=false;
//		}
//		
//		Poke osc=null;
//		if(hasNessary==false){
//			//没有必要出单牌，即所有单牌都能带出去
//			//计算所有OneSendCard 的压牌比值和被压牌比值，压牌笔值越小越先出，压牌比值相等则被压牌比值越小越先出
//			
////			dispatchSingleOrDouble(dcs);
//			
//			//目前只用压牌比值比较
//			double minRate=1;
//			if(dcs.getOneSendCards()!=null){
//				for(Poke temp:dcs.getOneSendCards()){
//					if(temp.getBiggerRate()<minRate){
//						osc=temp;
//						minRate=temp.getBiggerRate();
//					}
//				}
//			}
//			
//			
//		}else{
//			//如果有必要出单牌
//			//需要先把单牌带到 3张或4张上
//			dispatchSingleOrDouble(dcs);
//			
//			double minRate=2;
//			if(dcs.getOneSendCards()!=null){
//				for(OneSendCard temp:dcs.getOneSendCards()){
//					if(temp.getBiggerRate()<minRate){
//						osc=temp;
//						minRate=temp.getBiggerRate();
//					}
//				}
//			}
//			if(dcs.getSingleOrDouble()!=null){
//				for(OneSendCard temp:dcs.getSingleOrDouble()){
//					if(temp.getBiggerRate()<minRate){
//						osc=temp;
//						minRate=temp.getBiggerRate();
//					}
//				}
//			}
//
//		}
//		//如果最先出牌是单牌，或对牌，则不要根据之前的匹配进行出牌，而是从单牌和双牌中找出最小牌出
//		if(osc==null||osc.getCardType().equals(CardTypeString.SINGLE_CARDTYPE)
//				||osc.getCardType().equals(CardTypeString.DOUBLE_CARDTYPE)){
//			osc=getMinSingleOrDouble(saveSingleOrDouble);
//		}
//		
//		return osc;
//	}
	
	
	/**
	 * //拆出所有可能的 龙，炸弹，飞机，连队，3张
	 * @param cards
	 * @return
	 */
	private List<Poke> getAllPossibleOneSendCard(Poke playerPoke) {
		// TODO Auto-generated method stub
		List<Poke> retList=new ArrayList<Poke>();
	
		//1找出所有的炸弹
		List<Poke> bombList=PokeHelper.getAllSamePokes(playerPoke, PokeRegulation.BOMB);
		//2找出所有的3张相同
		List<Poke> threeList=PokeHelper.getAllSamePokes(playerPoke, PokeRegulation.THREE);
		//3找出所有的飞机
		List<Poke> planeList=PokeHelper.getAllSamePokes(playerPoke, PokeRegulation.AIRCRAFT);
		//4找出所有的连对
		List<Poke> lianDuiList=PokeHelper.getAllSamePokes(playerPoke, PokeRegulation.STRAIGHT_PAIR);
		//5找出所有的的连子
		List<Poke> lianZiList=PokeHelper.getAllSamePokes(playerPoke, PokeRegulation.STRAIGHT);
//		
//		List<Poke> doubleList=PokeHelper.getAllSamePokes(playerPoke, PokeRegulation.BOMB);
		//将所有的加入到retList中
		retList.addAll(bombList);
		retList.addAll(threeList);
		retList.addAll(planeList);
		retList.addAll(lianDuiList);
		retList.addAll(lianZiList);
//		retList.addAll(doubleList);
		return retList;
	}
	
}
