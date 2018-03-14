package com.sky.poke.regulation.impl;

import java.util.ArrayList;
import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;
import com.sky.poke.util.PokeHelper;

/**
 * 单张牌
 * 
 * @author chshyin
 * 
 */
public class RegulationSingle implements PokeRegulation {

	
	public Poke getOneSendCardBiggerButleast(Poke playerPoke, Poke preOnePoke) {
		// 上一手牌的index
		int preIndex = preOnePoke == null ? -1 : preOnePoke.getMinIndexOfMaxAppear();
		// 循环手牌，查找单张的牌
		int[] playAppears = playerPoke.getAppears();
		// 查找最小的单牌
		for (int i = preIndex + 1; i < playAppears.length; i++) {
			
			if(playAppears[i] == 1){
				
				return new Poke(PokeHelper.findPokeAtAppearIndex(playerPoke, i, 1));
			}
		}
		// 没找到单牌,返回比这个大的一张单牌
		for (int i = preIndex + 1; i < playAppears.length; i++) {
			
			if(playAppears[i] >= 1){
				
				return new Poke(PokeHelper.findPokeAtAppearIndex(playerPoke, i, 1));
			}
		}

		return null;
	}

	
	public int judeRegulation(int[] appears, int len, int maxAppearCount) {

		if (len == 1) {

			return SINGLE;
		}

		return UNKNOWN;
	}

	
	public List<Poke> getAllSamePoke(Poke playerPoke,int type) {
		// TODO Auto-generated method stub
		List<Poke> pokes=new ArrayList<Poke>();
		// 循环手牌，查找单张的牌
		int[] playAppears = playerPoke.getAppears();
		// 查找最小的单牌
		for (int i = 0; i < playAppears.length; i++) {
			
			if(playAppears[i] == 1){
				
				pokes.add(new Poke(PokeHelper.findPokeAtAppearIndex(playerPoke, i, 1)));
			}
		}
		return pokes;
	}

	
	public Poke pushCardPoke(CardSolution solution, Poke preOnePoke,Poke finishPoke,int role) {
		// TODO Auto-generated method stub
		
		if(role==PokeRegulation.ROLE_FARMER_FORFARMER)//为同一方不出牌
			return null;
		
		//如果有可单张可押，就放单张
		if(solution.getSingle()!=null&&solution.getSingle().size()>0){
			for (int i = 0; i < solution.getSingle().size(); i++) {
				Poke poke=solution.getSingle().get(i);
				if(poke.getMinIndexOfMaxAppear()>preOnePoke.getMinIndexOfMaxAppear()){
					solution.getSingle().remove(poke);
					solution.getSingleOrDouble().remove(poke);
					return poke;
				}
			}
		}
		
		if(solution.getKeycard()!=null&&solution.getKeycard().size()>0){
			for (int i = 0; i < solution.getKeycard().size(); i++) {
				Poke poke=solution.getKeycard().get(i);
//				if(poke.getMinIndexOfMaxAppear()==12)
			}
		}
		
		return null;
	}

}
