/**
 * 
 */
package com.sky.game.poker.robot.ai;

/**
 * 
 * The advance poker card pattern:
 * 
 *  Bomb,
 *  ConsecutiveTriples
 *  Triple
 *  ConsecutivePairs
 *  ConsecutiveSingle
 *  Pairs
 *  Single
 *  TripleWithSingle
 *  TripleWithPair
 *  ConsecutiveTriplesWithSingles
 *  ConsecutiveTriplesWithPairs.
 * 
 *  
 * 
 * 
 * @author sparrow
 *
 */
public enum PokerCubeType {
	
	Bomb(0),
	ConsecutiveTriples(1),
	Triple(2),
	ConsecutivePairs(3),
	ConsecutiveSingle(4),
	Pairs(5),
	Single(6),
	TriplesWithSingle(7),
	TriplesWithPair(8),
	ConsecutiveTriplesWithSingles(9),
	ConsecutiveTriplesWithPairs(10),
	Inconsecutive(11),
	QuplexWithSinge(12),
	QuplexWithPair(13),
	Rocket(14),
	Empty(15)
	;
	
	public int state;

	private PokerCubeType(int state) {
		this.state = state;
	}
	
	public static PokerCubeType getPokerCubeType(int i){
		PokerCubeType type=Single;
		switch (i) {
		case 0:
			type=Bomb;
			break;
		case 1:
			type=ConsecutiveTriples;
			break;
		case 2:
			type=Triple;
			break;
		case 3:
			type=ConsecutivePairs;
			break;
		case 4:
			type=ConsecutiveSingle;
			break;
		case 5:
			type=Pairs;
			break;
		case 6:
			type=Single;
			break;
			

		default:
			break;
		}
		return type;
	}
	
	
	public boolean isPokerCubeType(PokerCubeType pt){
		return this.state==pt.state;//this.compareTo(pt)==0;
	}
	
	public String toString(){
		return this.name();
	}

}
