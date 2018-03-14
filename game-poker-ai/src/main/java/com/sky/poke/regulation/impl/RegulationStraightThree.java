package com.sky.poke.regulation.impl;

import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;
import com.sky.poke.util.PokeHelper;

/**
 * 三连顺
 * 
 * @author chshyin
 * 
 */
public class RegulationStraightThree implements PokeRegulation {

	
	public int judeRegulation(int[] appears,int len,int maxAppar) {
		
		if (len < 6 || maxAppar != 3) {

			return UNKNOWN;
		}

		int status = 0;

		for (int i = 0; i < appears.length - 1; i++) {
			
			switch (appears[i]) {
			
			case 0:
				
				if(status == 1){
					
					status = 2;
				}
				
				break;
			case 3:
				
				if(status == 2 || i >= 12){
					
					return UNKNOWN;
				} else{
					
					status = 1;
				}
				break;

			default:
				
				return UNKNOWN;
			}
			
		}
		
		return STRAIGHT_THREE;
	}
	
	public static void main(String[] args) {
		
//		int[] showValues = {12,12,13,13,13,13,1,1,1};
//		
//		System.out.println(new RegulationStraightThree().judeRegulation(showValues));
	}

	
	public Poke getOneSendCardBiggerButleast(Poke playerPoke, Poke preOnePoke) {
		// 上一手牌的index
		int preIndex = preOnePoke.getMinIndexOfMaxAppear();
		// 循环手牌，查找最大的连续的顺子的长度
		int[] playAppears = playerPoke.getAppears();
		// 查找最小的单牌
		long findPokeValue = 0;

		boolean isStart = false;

		int findCount = 0;
		// 除去2和王
		for (int i = preIndex + 1; i < playAppears.length && i < 12; i++) {
			// 牌数不够
			if (playAppears[i] < 3) {

				isStart = false;
				findCount = 0;
				findPokeValue = 0;
				continue;
			}
			// 存在二张牌以上
			if (isStart) {
				// 数量+1
				findCount++;
				// 加上该牌
				findPokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke,
						i, 3);
			} else {

				isStart = true;
				// 数量+1
				findCount = 1;
				// 加上该牌
				findPokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke,
						i, 3);
			}
			// 顺子长度够了
			if (findCount * 3 >= preOnePoke.getLength()) {

				return new Poke(findPokeValue);
			}

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
