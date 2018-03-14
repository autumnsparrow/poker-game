package com.sky.poke.regulation.impl;

import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;
// 火箭
// 13位为小王，14位为大王
public class RegulationRocket implements PokeRegulation {

	
	public int judeRegulation(int[] appears,int len,int maxAppar) {
		
		return (len == 2 && appears[13] == 1 && appears[14] == 1) ? ROCKET : UNKNOWN;
	}

	
	public Poke getOneSendCardBiggerButleast(Poke playerPoke, Poke preOnePoke) {
		
		if(preOnePoke != null && preOnePoke.getType() == ROCKET){
			
			return null;
		}
		
		int[] appears = playerPoke.getAppears();
		
		if(appears[13] == 1 && appears[14] == 1){
			
			return new Poke((long)0x3 << 52);
		}
		// 没有火箭
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
