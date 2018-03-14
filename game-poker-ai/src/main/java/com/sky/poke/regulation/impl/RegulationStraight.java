package com.sky.poke.regulation.impl;

import java.util.ArrayList;
import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;
import com.sky.poke.util.PokeHelper;

/**
 * 顺子
 * 
 * @author chshyin
 * 
 */
public class RegulationStraight implements PokeRegulation {

	
	public int judeRegulation(int[] appears, int len, int maxAppar) {

		if (len < 5 || maxAppar != 1) {

			return UNKNOWN;
		}

		int status = 0;

		for (int i = 0; i < appears.length - 1; i++) {

			switch (appears[i]) {

			case 0:

				if (status == 1) {

					status = 2;
				}

				break;
			case 1:
				// 不包括2，王
				if (status == 2 || i >= 12) {

					return UNKNOWN;
				} else {

					status = 1;
				}
				break;

			default:
				return UNKNOWN;
			}

		}

		return STRAIGHT;
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
			// 没有牌
			if (playAppears[i] <= 0) {

				isStart = false;
				findCount = 0;
				findPokeValue = 0;
				continue;
			}
			// 存在牌
			if (isStart) {
				// 数量+1
				findCount++;
				// 加上该牌
				findPokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke,
						i, 1);
			} else {

				isStart = true;
				// 数量+1
				findCount = 1;
				// 加上该牌
				findPokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke,
						i, 1);
			}
			// 顺子长度够了
			if (findCount >= preOnePoke.getLength()) {

				return new Poke(findPokeValue);
			}

		}

		return null;
	}

	
	public List<Poke> getAllSamePoke(Poke playerPoke,int type) {
		// TODO Auto-generated method stub
		List<Poke> pokes=new ArrayList<Poke>();
		// 循环手牌，查找最大的连续的顺子的长度
		int[] playAppears = playerPoke.getAppears();
		// 查找最小的单牌
		long findPokeValue = 0;

		boolean isStart = false;

		int findCount = 0;
		// 除去2和王
		for (int i = 0; i < playAppears.length && i < 12; i++) {
			
			
			// 没有牌
			if (playAppears[i] <= 0) {
				
				// 顺子长度够了
				if(findCount>=5){
					Poke poke=new Poke(findPokeValue);
					pokes.add(poke);
				}

				isStart = false;
				findCount = 0;
				findPokeValue = 0;
				continue;
			}
			// 存在牌
			if (isStart) {
				// 数量+1
				findCount++;
				// 加上该牌
				findPokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke,
						i, 1);
			} else {

				isStart = true;
				// 数量+1
				findCount = 1;
				// 加上该牌
				findPokeValue |= PokeHelper.findPokeAtAppearIndex(playerPoke,
						i, 1);
			}


		}

		return pokes;
	}

	
	public Poke pushCardPoke(CardSolution solution, Poke preOnePoke,Poke finishPoke,int role) {
		// TODO Auto-generated method stub
		return null;
	}

}
