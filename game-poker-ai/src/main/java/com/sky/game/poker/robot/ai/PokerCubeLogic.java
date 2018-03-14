package com.sky.game.poker.robot.ai;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * PokerCubeLogic record the position of PokerCube in all the permutations of card pattern.
 * 
 * Using the position sort to get the best order .
 * 
 * 
 *    
 * 
 * 
 * @author sparrow
 *
 */
public class PokerCubeLogic {
	
	public static class PokerCubeOrder{
		int positionOfCombination;
		List<Integer> pokerCubeOrder;
		public PokerCubeOrder(int positionOfCombination,
				List<Integer> pokerCubeOrder) {
			super();
			this.positionOfCombination = positionOfCombination;
			this.pokerCubeOrder = pokerCubeOrder;
		}
		public PokerCubeOrder() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getPositionOfCombination() {
			return positionOfCombination;
		}
		public void setPositionOfCombination(int positionOfCombination) {
			this.positionOfCombination = positionOfCombination;
		}
		public List<Integer> getPokerCubeOrder() {
			return pokerCubeOrder;
		}
		public void setPokerCubeOrder(List<Integer> pokerCubeOrder) {
			this.pokerCubeOrder = pokerCubeOrder;
		}
		@Override
		public String toString() {
			
			return "PokerCubeOrder [positionOfCombination="
					+ positionOfCombination + ", pokerCubeOrder="
					
					+ Arrays.toString(pokerCubeOrder.toArray(new Integer[]{})) + "]";
		}
		
		
		
		
	}
	
	List<Integer> pokerCubeCombinations;
	// the first orders.
	List<PokerCubeOrder> pokerCubeOrders;
	public PokerCubeLogic(List<Integer> pokerCubeCombinations,
			List<PokerCubeOrder> pokerCubeOrders) {
		super();
		this.pokerCubeCombinations = pokerCubeCombinations;
		this.pokerCubeOrders = pokerCubeOrders;
	}
	public PokerCubeLogic() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<Integer> getPokerCubeCombinations() {
		return pokerCubeCombinations;
	}
	public void setPokerCubeCombinations(List<Integer> pokerCubeCombinations) {
		this.pokerCubeCombinations = pokerCubeCombinations;
	}
	public List<PokerCubeOrder> getPokerCubeOrders() {
		return pokerCubeOrders;
	}
	public void setPokerCubeOrders(List<PokerCubeOrder> pokerCubeOrders) {
		this.pokerCubeOrders = pokerCubeOrders;
	}
	
	
	public PokerCubeOrder getPokerCubeOrder(Integer indexOfCombinations){
		PokerCubeOrder order=null;
		for(PokerCubeOrder pokerCubeOrder:pokerCubeOrders){
			if(pokerCubeOrder.getPositionOfCombination()==indexOfCombinations.intValue()){
				order=pokerCubeOrder;
				break;
			}
		}
		return order;
	}
	
	@Override
	public String toString() {
		return "PokerCubeLogic [pokerCubeCombinations=" + Arrays.toString(pokerCubeCombinations.toArray(new Integer[]{}))
				+ ", pokerCubeOrders=" +Arrays.toString(pokerCubeOrders.toArray(new PokerCubeOrder[]{})).replace('[', '\n')  + "]";
	}

	

	
	

}
