/**
 * 
 */
package com.sky.game.poker.robot.ai.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.poker.robot.ai.PokerAnalyzerSchema;
import com.sky.game.poker.robot.math.Permutations;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class CardStateDivsion {

	private static final Log logger=LogFactory.getLog(CardStateDivsion.class);
	
	public PokerAnalyzerSchema[] getPokerAnalyzerSchemas() {
		return pokerAnalyzerSchemas;
	}

	PokerCube pokerCube;
	HashMap<String, PokerAnalyzerSchema> merge ;
	
	PokerAnalyzerSchema[] pokerAnalyzerSchemas;
	
	private void permutation(PokerCube handsA) {
		Permutations<Integer> perm = new Permutations<Integer>(new Integer[] {
				0, 1, 2, 3, 4, 5 });
	

		// merge the result is the same.

			//
		// choose from one PokerAnalyzerSchema ,then decide the best hand.
		//
		while (perm.hasNext()) {
			Integer[] arrays = perm.next();

			PokerAnalyzerSchema schema = new PokerAnalyzerSchema();
			schema.analyze(handsA, arrays);
			if (!merge.containsKey(schema.getPokerCubeHexCombination())) {

				merge.put(schema.getPokerCubeHexCombination(), schema);
				
			}

		}
		
		
		
		pokerAnalyzerSchemas=merge.values().toArray(new PokerAnalyzerSchema[]{});
		
		
		Arrays.sort(pokerAnalyzerSchemas, new Comparator<PokerAnalyzerSchema>() {

			
			public int compare(PokerAnalyzerSchema o1, PokerAnalyzerSchema o2) {
				// TODO Auto-generated method stub
				return o2.getPokerCubeValues()-o1.getPokerCubeValues();
			}
		});
		
		logger.info(Arrays.toString(pokerAnalyzerSchemas));
		
	}

	public CardStateDivsion(PokerCube pokerCube) {
		super();
		this.pokerCube = pokerCube;
		this.merge=new HashMap<String, PokerAnalyzerSchema>();
		if(pokerCube!=null)
			this.permutation(this.pokerCube);
	}


}
