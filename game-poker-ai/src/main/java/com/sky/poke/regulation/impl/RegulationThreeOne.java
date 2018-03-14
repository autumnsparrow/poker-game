package com.sky.poke.regulation.impl;

import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;
import com.sky.poke.util.PokeHelper;

/**
 * 三带一
 * 
 * @author chshyin
 * 
 */
public class RegulationThreeOne implements PokeRegulation {

	
	public int judeRegulation(int[] appears, int len, int maxAppar) {

		if (maxAppar != 3) {

			return UNKNOWN;
		}

		if (len == 4) {

			return THREE_ONE;

		} else if (len == 5) {

			for (int i : appears) {

				switch (i) {
				case 1:
					 if(appears[14]==1&&appears[13]==1){
						return  THREE_ONE;
					 }
				case 2:

					return THREE_ONE;

				default:
					break;
				}
			}
		}

		return UNKNOWN;
	}

	
	public Poke getOneSendCardBiggerButleast(Poke playerPoke, Poke preOnePoke) {
		// 上一手牌的index
		int preIndex = preOnePoke.getMinIndexOfMaxAppear();
		// 循环手牌，查找三张的牌
		int[] playAppears = playerPoke.getAppears();

		long pokeValue = 0;

		int findIndex = -1;
		// 单张的最小的牌
		long signleValue = 0;
		// 最小的牌
		long minValue = 0;
		// 查找最小的三张的牌
		for (int i = preIndex + 1; i < playAppears.length; i++) {

			if (playAppears[i] == 3) {

				findIndex = i;

				pokeValue = PokeHelper.findPokeAtAppearIndex(playerPoke, i, 3);

				break;
			}
		}
		// 没有三张的牌
		if (pokeValue == 0) {

			return null;
		}
		
		int otherLen = preOnePoke.getLength() - 3;
		
		// 找到一个单张的牌和最小的牌
		for (int i = 0; i < playAppears.length; i++) {
			// 单张
			if (playAppears[i] == otherLen) {

				signleValue = PokeHelper.findPokeAtAppearIndex(playerPoke, i, otherLen);

				break;
			}
			
			if (playAppears[i] > otherLen && i != findIndex) {
				
				if(minValue == 0){
					
					minValue = PokeHelper.findPokeAtAppearIndex(playerPoke, i, otherLen);
				}
			}
		}

		if(signleValue != 0){
			
			pokeValue |= signleValue;
			
			return new Poke(pokeValue);
		} else if(minValue != 0){
			
			pokeValue |= minValue;
			
			return new Poke(pokeValue);
		} else {
			
			return null;
		}

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
