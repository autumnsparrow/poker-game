package com.sky.poke.regulation.impl;

import java.util.LinkedList;
import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;
import com.sky.poke.util.PokeHelper;
/**
 * 四带二
 * @author chshyin
 *
 */
public class RegulationFourTwo implements PokeRegulation {

	
	public int judeRegulation(int[] appears,int len,int maxAppar) {
		
		if(len < 6 || maxAppar != 4){
			
			return UNKNOWN;
		}
		
		boolean hasFour = false;
		// 带2个单的
		if(len == 6){
//			// 不能带双王
//			if(appears[13] == 1 && appears[14] == 1){
//				
//				return UNKNOWN;
//			}
			
			for (int i : appears) {
				
				switch (i) {
				case 0:
				case 1:
				case 2:
					
					break;
				case 4:
					
					hasFour = true;
					break;

				default:
					
					return UNKNOWN;
				}
			}
			
			return hasFour ? FOUR_TWO : UNKNOWN;
		}
		// 带2对
		if(len == 8){
			
			for (int i : appears) {
				
				switch (i) {
				case 0:
				case 2:
					
					break;
				case 4:
					
					hasFour = true;
					break;

				default:
					return UNKNOWN;
				}
			}
			
			return hasFour ? FOUR_TWO : UNKNOWN;
		}
		
		return UNKNOWN;
	}

	
	public Poke getOneSendCardBiggerButleast(Poke playerPoke, Poke preOnePoke) {
		// 上一手牌的index
		int preIndex = preOnePoke.getMinIndexOfMaxAppear();
		// 循环手牌，查找三张的牌
		int[] playAppears = playerPoke.getAppears();
		
		int otherLen = (preOnePoke.getLength() - 4) / 2;

		long pokeValue = 0;
		// 单张牌数组
		List<Integer> singleList = new LinkedList<Integer>();
		// 其它的牌数组
		List<Integer> otherList = new LinkedList<Integer>();
		// 查找最小的三张的牌
		for (int i = preIndex + 1; i < playAppears.length; i++) {
			
			if (playAppears[i] == 4) {
				
				if(pokeValue == 0){
										
					pokeValue = PokeHelper.findPokeAtAppearIndex(playerPoke, i, 4);
				} else{
					
					otherList.add(i);
				}
			} else if(playAppears[i] == otherLen){
				
				singleList.add(i);
			} else if(playAppears[i] > otherLen){
				
				otherList.add(i);
			}
		}
		// 没有三张的牌
		if (pokeValue == 0) {

			return null;
		}
		
		int count = 0;
		
		for (Integer integer : singleList) {
			
			if(count == 2){
				
				return new Poke(pokeValue);
			}
			
			pokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke, integer, otherLen);
			
			count++;
		}
		
		for (Integer integer : otherList) {
			
			if(count == 2){
				
				return new Poke(pokeValue);
			}
			
			pokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke, integer, otherLen);
			
			count++;
		}
		
		return null;
		
	}

	
	public List<Poke> getAllSamePoke(Poke playerPoke,int type) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Poke pushCardPoke(CardSolution solution, Poke preOnePoke,Poke finishPoke,int role) {
		// TODO Auto-generated method stub
		return null;
	}

}
