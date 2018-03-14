package com.sky.poke.regulation.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;
import com.sky.poke.util.PokeHelper;

/**
 * 飞机带翅膀
 * 
 * @author chshyin
 * 
 */
public class RegulationAirCraft implements PokeRegulation {

	
	public int judeRegulation(int[] appears,int len,int maxAppar) {

		if (len < 8 || !(maxAppar == 3||maxAppar==4)) {

			return UNKNOWN;
		}
		// 带单个的
		if (len % 4 == 0) {

			return checkWithOne(appears);
			// 带两个的
		} else if (len % 5 == 0) {

			return checkWithTwo(appears);
			
		}else {

			return UNKNOWN;
		}
	}

	private int checkWithTwo(int[] appears) {
		
		int threeCount = 0;
		int singleCount = 0;
		int pairCount=0;
		// 0 未找到3个的,1找到，2结束
		int status = 0;
		
		for(int i = 0;i < appears.length - 1; i++){
			
			switch (appears[i]) {
			
			case 2:
				
				if(status == 1){
					
					status = 2;
				}
				singleCount ++;
				break;
				
			case 3:
				// 2
//				if(i >= 12 || status == 2){
//					
//					return UNKNOWN;
//				}
				//add by sijunjie   ask for supporting joker and Joker
				if(status == 2){
					
					return UNKNOWN;
				}
				status = 1;
				threeCount ++;
				break;
			case 4:
				pairCount+=2;
				break;
			case 0:
				
				break;

			default:
				
				return UNKNOWN;
			}
		}
        if(threeCount == singleCount||threeCount == pairCount){
        	return AIRCRAFT;
        }else{
        	return  UNKNOWN;	
        }
//		return threeCount == singleCount ? AIRCRAFT : UNKNOWN;
	}

	private int checkWithOne(int[] appears) {
		
		int threeCount = 0;
		int singleCount = 0;
		int forCount=0;  //用于特殊情况 333+444+555+3+4+5
		// 0 未找到3个的,1找到，2结束
		int status = 0;
		
		for(int i = 0;i < appears.length; i++){
			
			switch (appears[i]) {
			
			case 1:
				
//				if(status == 1){
//					
//					status = 2;
//				}
				singleCount ++;
				break;
				
			case 3:
				// 2
//				if(i >= 12 || status == 2){
//				
//				return UNKNOWN;
//			}
			//add by sijunjie   ask for supporting joker and Joker
			if(status == 2){
				
				return UNKNOWN;
			}
				
				status = 1;
				threeCount ++;
				break;
				
			case 0:
				
				break;
				
			case 2:
				
				if(status == 1){
					
					status = 2;
				}
				singleCount += 2;
				break;
			case 4:
				 //用于特殊情况 333+444+555+3+4+5   add by sijunjie
				forCount++;
				break;
			default:
				
				return UNKNOWN;
			}
		}
          if(forCount==3){
        	  return AIRCRAFT;
          }else{
		  return threeCount == singleCount ? AIRCRAFT : UNKNOWN;
          }
	}

	
	public Poke getOneSendCardBiggerButleast(Poke playerPoke, Poke preOnePoke) {
		// 上一手牌的index
		int preIndex = preOnePoke.getMinIndexOfMaxAppear();
		// 循环手牌，查找三张的牌
		int[] playAppears = playerPoke.getAppears();
		// 判断是带一张还是两张
		int otherLen = preOnePoke.getLength() % 4 == 0 ? 1 : 2;
		// 总数
		int count = preOnePoke.getLength() / (3 + otherLen);
		
		long pokeValue = 0;
		// 三张牌的数组
		List<Integer> threeList = new LinkedList<Integer>();
		// 或多张牌数组
		List<Integer> singleList = new LinkedList<Integer>();
		// 其它的牌数组
		List<Integer> otherList = new LinkedList<Integer>();
		// 找出所有牌的数组
		for (int i = preIndex + 1; i < playAppears.length; i++) {
					
			if(playAppears[i] == 3){
				
				threeList.add(i);
				
			} else if(playAppears[i] == otherLen){
				
				singleList.add(i);
			} else if(playAppears[i] > otherLen){
				
				otherList.add(i);
			}
		}
		// 查找三张的连牌
		int[] findIndexArray = new int[count];
		int index = 0;
		
		for(int i=0;i<threeList.size();i++){

			if(index == count){
				
				break;
			}
			
			if(index == 0){
				
				findIndexArray[index++] = threeList.get(i);
				continue;
			}
			// 三连
			if(findIndexArray[index - 1] + 1 == threeList.get(i)){
				
				findIndexArray[index++] = threeList.get(i);
				continue;
			} else{
				
				index = 0;
			}
		}
		// 没有三连
		if(index != count){
			
			return null;
		}
		
		for(int i = findIndexArray.length - 1;i>=0;i--){
			
			pokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke, findIndexArray[i], 3);
			threeList.remove(i);
		}
		
		otherList.addAll(threeList);
		
		index = 0;
		
		for (Integer integer : singleList) {
			
			if(index == count){
				
				return new Poke(pokeValue);
			}
			
			pokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke, integer, otherLen);
			
			index++;
		}
		
		for (Integer integer : otherList) {
			
			if(index == count){
				
				return new Poke(pokeValue);
			}
			
			pokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke, integer, otherLen);
			
			index++;
		}
		
		return null;
	}

	
	public List<Poke> getAllSamePoke(Poke playerPoke,int type) {
		// TODO Auto-generated method stub
		List<Poke> pokes=new ArrayList<Poke>();
		// 循环手牌，查找三张的牌
		int[] playAppears = playerPoke.getAppears();
			
		long pokeValue = 0;
		// 三张牌的数组
		List<Integer> threeList = new LinkedList<Integer>();
		// 或多张牌数组
		// 找出所有牌的数组
		for (int i = 0; i < playAppears.length; i++) {
					
			if(playAppears[i] >= 3){
				
				threeList.add(i);
				
			} 
		}
		
		// 查找三张的连牌
		int index = 0;
		
		Integer temp=null;
		for(int i=0;i<threeList.size();i++){
			
			Integer temp1 = threeList.get(i);
			if(temp==null){//第一次进入循环
				temp=temp1;
				index++;
				continue;
			}
			
			if(temp+1==temp1){
				index++;
				pokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke, temp, 3);
				temp=temp1;
				continue;
			}
			
			if(temp+1!=temp1){
				if(index>=2){
				   Poke poke=new Poke(pokeValue);
				   pokes.add(poke);
				}
				pokeValue=0;
				temp=temp1;
				index=0;
			}
		}
		
		if(temp!=null&&index>=2){
			  pokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke, temp, 3);
			   Poke poke=new Poke(pokeValue);
			   pokes.add(poke);
		}
		
		return pokes;
	}

	
	public Poke pushCardPoke(CardSolution solution, Poke preOnePoke,Poke finishPoke,int role) {
		// TODO Auto-generated method stub
		return null;
	}

}
