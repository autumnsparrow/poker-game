package com.sky.poke.regulation.impl;

import java.util.ArrayList;
import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;
import com.sky.poke.util.PokeHelper;

/**
 * 炸弹
 */
public class RegulationBomb implements PokeRegulation {

	
	public int judeRegulation(int[] appears,int len,int maxAppar) {

		return (len == 4 && maxAppar == 4) ? BOMB : UNKNOWN;
	}

	
	public Poke getOneSendCardBiggerButleast(Poke playerPoke, Poke preOnePoke) {
		
		// 上一手牌的index,如果不是炸弹，则从0开始找，找一个炸弹
		int preIndex = preOnePoke.getType() == BOMB ? preOnePoke.getMinIndexOfMaxAppear() : 0;
		// 循环手牌，查找单张的牌
		int[] playAppears = playerPoke.getAppears();
		// 查找最小的炸弹
		for (int i = preIndex + 1; i < playAppears.length; i++) {
			
			if(playAppears[i] == 4){
				
				return new Poke(PokeHelper.findPokeAtAppearIndex(playerPoke, i, 4));
			}
		}
		// 没找到炸弹
		return null;
	}

	
	public List<Poke> getAllSamePoke(Poke playerPoke,int type) {
		// TODO Auto-generated method stub
		List<Poke> pokes=new ArrayList<Poke>();
		// 循环手牌，查找单张的牌
		int[] playAppears = playerPoke.getAppears();
		// 查找所有的炸弹
		for (int i = 0; i < playAppears.length; i++) {
			
			if(playAppears[i] == 4){
				
				Poke poke= new Poke(PokeHelper.findPokeAtAppearIndex(playerPoke, i, 4));
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
