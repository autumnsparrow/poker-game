/**
 * 
 */
package com.sky.game.poker.robot.ai.logic.impl.compartor;

import java.util.Comparator;
import java.util.List;

import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class PokerCubeCombinationComparator implements Comparator<Integer> {
	
	IPokerCubeAnalyzer analyzer;
	
	public PokerCubeCombinationComparator(IPokerCubeAnalyzer analyzer) {
		super();
		this.analyzer = analyzer;
	}



	/**
	 * 
	 */
	public PokerCubeCombinationComparator() {
		// TODO Auto-generated constructor stub
	}
	
	
	

	/**
	 * 
	 * Sort all the combinations, find which combination has less singles.
	 * 
	 *  non-consecutive elements.
	 *  1. the smallest hands.
	 *  2. the max hands that poker value larger than seven.
	 * 
	 * 
	 */
	public int compare(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		List<PokerCube> cubes=analyzer.getPokerCubes().get(o1.intValue());
		List<PokerCube> cubes2=analyzer.getPokerCubes().get(o2.intValue());
		
		int count1=0;
		int count2=0;
		for(PokerCube cube:cubes){
			if(cube.isPokerCubeType(PokerCubeType.Bomb)){
				count1=count1+cube.consectiveQuplex().sumOfElements();
			}else if(cube.isPokerCubeType(PokerCubeType.ConsecutivePairs)){
				count1=count1+1;
			}else if(cube.isPokerCubeType(PokerCubeType.ConsecutiveTriples)){
				count1=count1+1;
			}else if(cube.isPokerCubeType(PokerCubeType.Pairs)){
				count1=count1+cube.consectivePair().sumOfElements();
			}else if(cube.isPokerCubeType(PokerCubeType.Single)){
				count1=count1+cube.remains();
			}else if(cube.isPokerCubeType(PokerCubeType.Triple)){
				count1=count1+cube.consectiveTriple().sumOfElements();
			}
			
		}
		
		for(PokerCube cube:cubes2){
			if(cube.isPokerCubeType(PokerCubeType.Bomb)){
				count2=count2+cube.consectiveQuplex().sumOfElements();
			}else if(cube.isPokerCubeType(PokerCubeType.ConsecutivePairs)){
				count2=count2+1;
			}else if(cube.isPokerCubeType(PokerCubeType.ConsecutiveTriples)){
				count2=count2+1;
			}else if(cube.isPokerCubeType(PokerCubeType.Pairs)){
				count2=count2+cube.consectivePair().sumOfElements();
			}else if(cube.isPokerCubeType(PokerCubeType.Single)){
				count2=count2+cube.remains();
			}else if(cube.isPokerCubeType(PokerCubeType.Triple)){
				count2=count2+cube.consectiveTriple().sumOfElements();
			}
			
		}
		
		
//		// that may be contains the 
//		PokerCube single=PokerCube.getPokerCubeByType(PokerCubeType.Single, cubes);
//		PokerCube single2=PokerCube.getPokerCubeByType(PokerCubeType.Single, cubes2);
//		
//		single.calculate();
//		single2.calculate();
//		
//		PokerCube pairs=PokerCube.getPokerCubeByType(PokerCubeType.Pairs, cubes);
//		PokerCube pairs2=PokerCube.getPokerCubeByType(PokerCubeType.Pairs, cubes2);
//		
//		pairs.calculate();
//		pairs2.calculate();
//		
//		PokerCube triples=PokerCube.getPokerCubeByType(PokerCubeType.Triple, cubes);
//		PokerCube triples2=PokerCube.getPokerCubeByType(PokerCubeType.Triple, cubes);
//		
//		triples.calculate();
//		triples2.calculate();
//		
//		PokerCube inconsecutive=PokerCube.getPokerCubeByType(PokerCubeType.Inconsecutive, cubes);
//		PokerCube inconsecutive2=PokerCube.getPokerCubeByType(PokerCubeType.Inconsecutive, cubes);
////		
//		int nonConsecutive=single.consectiveSolo().sumOfElements()+pairs.consectivePair().sumOfElements()
//				;
////		
//		int nonConsecutive2=single2.consectiveSolo().sumOfElements()+pairs2.consectivePair().sumOfElements()
//				;
////		
//		
//		nonConsecutive=nonConsecutive+inconsecutive.remains();
//		nonConsecutive2=nonConsecutive2+inconsecutive2.remains();
//		
		//RobotAiAdapter.logger.info(String.format("\n[PokerCubeOrder (o1=%d,single=%d) (o2=%d,single=%d)]", o1.intValue(),single.remains(),o2.intValue(),single2.remains()));
		return count1-count2;
		
	}

}
