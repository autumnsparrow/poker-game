package com.sky.poke.regulation.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.solution.CardSolution;

public class PokeRegulationImpl implements PokeRegulation {
	
	private Map<Integer, PokeRegulation> typeRegulation = new HashMap<Integer, PokeRegulation>();
	
	private List<PokeRegulation> threeAppearChain = new LinkedList<PokeRegulation>();;
	
	{
		threeAppearChain.add(new RegulationAirCraft());
		threeAppearChain.add(new RegulationStraightThree());
		
		typeRegulation.put(SINGLE,new RegulationSingle());
		typeRegulation.put(STRAIGHT,new RegulationStraight());
		typeRegulation.put(ROCKET,new RegulationRocket());
		typeRegulation.put(BOMB,new RegulationBomb());
		typeRegulation.put(PAIR,new RegulationPair());
		typeRegulation.put(THREE,new RegulationThree());
		typeRegulation.put(THREE_ONE,new RegulationThreeOne());
		typeRegulation.put(STRAIGHT_PAIR,new RegulationStraightPair());
		typeRegulation.put(STRAIGHT_THREE,new RegulationStraightThree());
		typeRegulation.put(AIRCRAFT,new RegulationAirCraft());
		typeRegulation.put(FOUR_TWO,new RegulationFourTwo());
	}
	
	
	public int judeRegulation(int[] appears,int len,int maxAppar) {
		
		if(len == 0){
			
			return EMPTY;
		}
		
		int regulation = UNKNOWN;
		
		switch (maxAppar) {
		
		case 0:
			
			regulation = EMPTY;
			break;
			
		case 1:
			
			if(len == 1){
				
				return SINGLE;
			} else if(len == 2){
				
				return typeRegulation.get(ROCKET).judeRegulation(appears, len, maxAppar);
			} else{
				
				return typeRegulation.get(STRAIGHT).judeRegulation(appears, len, maxAppar);
			}
			
		case 2:
			
			if(len == 2){
				
				return typeRegulation.get(PAIR).judeRegulation(appears, len, maxAppar);
			} else{
				
				return typeRegulation.get(STRAIGHT_PAIR).judeRegulation(appears, len, maxAppar);
			}
			
		case 3:
			
			if(len == 3){
				
				return typeRegulation.get(THREE).judeRegulation(appears, len, maxAppar);
			}else if(len == 4 || len == 5){
				
				return typeRegulation.get(THREE_ONE).judeRegulation(appears, len, maxAppar);
			}else if(len == 10||len == 8){
				return typeRegulation.get(AIRCRAFT).judeRegulation(appears, len, maxAppar);
			}else{
				
				for (PokeRegulation pokeRegulation : threeAppearChain) {
					
					int result = pokeRegulation.judeRegulation(appears, len, maxAppar);
					
					if(UNKNOWN != result){
						
						return result;
					}
				}
				
				return UNKNOWN;
			}
			
		case 4:
			
			if(len == 4){
				
				return typeRegulation.get(BOMB).judeRegulation(appears, len, maxAppar);
			}if(len==12){
				return typeRegulation.get(AIRCRAFT).judeRegulation(appears, len, maxAppar);
			} else{
				
				return typeRegulation.get(FOUR_TWO).judeRegulation(appears, len, maxAppar);
			}
			

		default:
			break;
		}
		
		return regulation;
	}

	
	public Poke getOneSendCardBiggerButleast(Poke playerPoke, Poke preOnePoke) {
		
		if(preOnePoke == null){
			
			return typeRegulation.get(SINGLE).getOneSendCardBiggerButleast(playerPoke, preOnePoke);
		}
		
		PokeRegulation regulation =	typeRegulation.get(preOnePoke.getType());
		
		if(regulation == null){
			
			return typeRegulation.get(SINGLE).getOneSendCardBiggerButleast(playerPoke, preOnePoke);
		}
		
		Poke leastPoke = regulation.getOneSendCardBiggerButleast(playerPoke, preOnePoke);
		
		if(leastPoke == null){
			
			if(preOnePoke.getType() == ROCKET){
				
				return null;
			}
			// 是否有炸弹
			leastPoke = typeRegulation.get(BOMB).getOneSendCardBiggerButleast(playerPoke, preOnePoke);
			
			if(leastPoke != null){
				
				return leastPoke;
			}
			// 是否有火箭
			leastPoke = typeRegulation.get(ROCKET).getOneSendCardBiggerButleast(playerPoke, preOnePoke);
		}
		
		return leastPoke;
	}

	
	public List<Poke> getAllSamePoke(Poke playerPoke,int type) {
		// TODO Auto-generated method stub
		if(playerPoke==null){
			return null;
		}
		PokeRegulation regulation =	typeRegulation.get(type);
		if(regulation==null){
			return null;
		}
		return regulation.getAllSamePoke(playerPoke,type);
	}

	
	public Poke pushCardPoke(CardSolution solution, Poke preOnePoke,Poke finishPoke,int role) {
		// TODO Auto-generated method stub
		if(preOnePoke==null){
			return null;
		}
		PokeRegulation regulation =	typeRegulation.get(preOnePoke.getType());
		
		return regulation.pushCardPoke(solution, preOnePoke,finishPoke,role);
		
		
	}

}
