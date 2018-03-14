package com.sky.poke.regulation.impl;

import java.util.ArrayList;
import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;
import com.sky.poke.util.PokeHelper;
/**
 * 对子
 * @author chshyin
 *
 */
public class RegulationPair implements PokeRegulation {

	
	public int judeRegulation(int[] appears,int len,int maxAppar) {
		
		return (len == 2 && maxAppar == 2) ? PAIR : UNKNOWN;
		
	}

	
	public Poke getOneSendCardBiggerButleast(Poke playerPoke, Poke preOnePoke) {
		// 上一手牌的index
		int preIndex = preOnePoke.getMinIndexOfMaxAppear();
		// 循环手牌，查找单张的牌
		int[] playAppears = playerPoke.getAppears();
		// 查找最小的单牌
		for (int i = preIndex + 1; i < playAppears.length; i++) {
			
			if(playAppears[i] == 2){
				
				return new Poke(PokeHelper.findPokeAtAppearIndex(playerPoke, i, 2));
			}
		}
		// 没找到单牌,返回比这个大的一张单牌
		for (int i = preIndex + 1; i < playAppears.length; i++) {
			
			if(playAppears[i] >= 2){
				
				return new Poke(PokeHelper.findPokeAtAppearIndex(playerPoke, i, 2));
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
			
			if(playAppears[i] == 2){
				
				pokes.add(new Poke(PokeHelper.findPokeAtAppearIndex(playerPoke, i, 2)));
			}
		}
		

		return pokes;
	}

	
	public Poke pushCardPoke(CardSolution solution, Poke preOnePoke,Poke finishPoke,int role) {
		// TODO Auto-generated method stub
		return null;
	}

}
