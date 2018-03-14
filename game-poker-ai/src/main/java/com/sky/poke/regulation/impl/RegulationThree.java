package com.sky.poke.regulation.impl;

import java.util.ArrayList;
import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;
import com.sky.poke.util.PokeHelper;
/**
 * 三张牌
 * @author chshyin
 *
 */
public class RegulationThree implements PokeRegulation {

	
	public int judeRegulation(int[] appears,int len,int maxAppar) {
		
		return (len == 3 && maxAppar == 3) ? THREE : UNKNOWN;
		
	}

	
	public Poke getOneSendCardBiggerButleast(Poke playerPoke, Poke preOnePoke) {
		// 上一手牌的index
		int preIndex = preOnePoke.getMinIndexOfMaxAppear();
		// 循环手牌，查找单张的牌
		int[] playAppears = playerPoke.getAppears();
		// 查找最小的单牌
		for (int i = preIndex + 1; i < playAppears.length; i++) {
			
			if(playAppears[i] == 3){
				
				return new Poke(PokeHelper.findPokeAtAppearIndex(playerPoke, i, 3));
			}
		}

		return null;
	}

	
	public List<Poke> getAllSamePoke(Poke playerPoke,int type) {
		// TODO Auto-generated method stub
		List<Poke> pokes=new ArrayList<Poke>();
		// 循环手牌，查找单张的牌
		int[] playAppears = playerPoke.getAppears();
		// 查找最小的单牌
		for (int i = 0; i < playAppears.length; i++) {
			
			if(playAppears[i] == 3){
				
				Poke poke= new Poke(PokeHelper.findPokeAtAppearIndex(playerPoke, i, 3));
				pokes.add(poke);
			}
		}

		return pokes;
	}

	
	public Poke pushCardPoke(CardSolution solution, Poke preOnePoke,Poke finishPoke,int role) {
		// TODO Auto-generated method stub
		return null;
	}

}
