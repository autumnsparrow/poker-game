/**
 * 
 */
package com.sky.game.poker.robot.ai;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class PokerAnalyzerSchema {
	private static final Log logger=LogFactory.getLog(PokerAnalyzerSchema.class);
	
	Integer[]  pokerCubeTypesCombination;
	int[]      pokerCubeSteps;
	String  pokerCubeHexCombination;
	List<PokerCube> pokerCubes;
	Map<PokerCubeType,PokerCube> pokerCubeMaps;
	
	

	/**
	 * 
	 */
	public PokerAnalyzerSchema() {
		// TODO Auto-generated constructor stub
		pokerCubes=new LinkedList<PokerCube>();
		pokerCubeMaps=new HashMap<PokerCubeType, PokerCube>();
	}
	
	
	/**
	 * 
	 * anaylze a poker cube by PokerCubeType
	 * @param cube
	 * @param pokerCubeTypes
	 */
	public void analyze(PokerCube cube,Integer[] pokerCubeTypes){
		
		pokerCubes.clear();
		this.pokerCubeTypesCombination=pokerCubeTypes;
		
		PokerCube pokerCube=cube.clone();
		
		
		//logger.debug(pokerCube.cubeToHexString());
	   
		StringBuffer buffer=new StringBuffer();  
		for(int i=0;i<pokerCubeTypesCombination.length;i++){
			PokerCube pc=split(pokerCube,pokerCubeTypesCombination[i].intValue());
			
			if(!pc.isEmpty()){
			  buffer.append(pc.cubeToHexString());
			  pokerCubes.add(pc);
			}
		}
		// if the pokerCube is empty don't add it.
		if(!pokerCube.isEmpty()){
			// the PokerCube may contains the in-consecutive elements.
			PokerCube pokerCubeInconsecutive=pokerCube.clone();
			int k=0;
			// the current poker cube contains only the singles.
			pokerCube=pokerCube.maskInConsecutiveCube();
			pokerCube.setPokerCubeType(PokerCubeType.Single);
			pokerCubeInconsecutive.subPokerCube(pokerCube);
			if(!pokerCube.isEmpty()){
				pokerCubes.add(pokerCube);
				buffer.append(pokerCube.cubeToHexString());
			}
			if(!pokerCubeInconsecutive.isEmpty()){
				pokerCubeInconsecutive.setPokerCubeType(PokerCubeType.Inconsecutive);
				pokerCubes.add(pokerCubeInconsecutive);
				
				buffer.append(pokerCubeInconsecutive.cubeToHexString());
			}
		}
		//logger.info(pokerCubesToString());
		this.pokerCubeHexCombination=buffer.toString();
		
		for(PokerCube pt:pokerCubes){
			pokerCubeMaps.put(pt.getPokerCubeType(), pt);
		}
		
	}
	
	@Override
	public String toString() {
		return  pokerCubesToString() ;
	}

	
	private int getPokerCubeValue(String hex){
		int value=0;
		for(int i=0;i<hex.length();i++){
			if(hex.charAt(i)!='0'){
				value+=i;
			}
		}
		
		return value;
	}
	
	private String pokerCubesToString(){
		
			StringBuffer sb=new StringBuffer();
			sb.append("\r\n[ ").append(String.format(" %02d - %02d", getPokerCubeHands(),getPokerCubeValues()));
			for(PokerCube pCube:pokerCubes){
				String hex=pCube.cubeToHexString();
				sb.append(String.format("\n%-30s - %s - %05d - %02d ", pCube.getPokerCubeType().toString(),hex,pCube.remains(),pCube.getPokerCubeValue()));
			}
			sb.append("]\r\n");
			//RobotAiAdapter.logger.info(sb.toString());
			return sb.toString();
		
	}

	public Integer[] getPokerCubeTypesCombination() {
		
		return pokerCubeTypesCombination;
	}


	public String getPokerCubeHexCombination() {
		return pokerCubeHexCombination;
	}
	
	public PokerCube getPokerCube(PokerCubeType pt){
		return pokerCubeMaps.get(pt);
	}


	public List<PokerCube> getPokerCubes() {
		
		return pokerCubes;
	}


	private  PokerCube split(PokerCube pCube,int t){
		
		//log(pCube.toString());
		PokerCubeTypes types=IPokerCubeAnalyzer.pokerCubeTypes[t];
		PokerCube pokerCube=pCube.subPokerCubeByConsectiveCompare(types.cube, types.consecutiveLength);
//		if(!pokerCube.isEmpty())
//			logger.debug(pokerCube.cubeToHexString());
		pokerCube.setPokerCubeType(PokerCubeType.getPokerCubeType(t));
		
		return pokerCube;
		
	}
	
	
	public int getPokerCubeValues(){
		int values=0;
		for(PokerCube cube:pokerCubes){
			
			cube.calculate();
			if(cube.isPokerCubeType(PokerCubeType.Bomb)){
				values+=12*getPokerCubeValue(cube.cubeToHexString());
			}else if(cube.isPokerCubeType(PokerCubeType.ConsecutiveSingle)){
				values+=5*getPokerCubeValue(cube.cubeToHexString());
			}else if(cube.isPokerCubeType(PokerCubeType.ConsecutivePairs)){
				values+=6*getPokerCubeValue(cube.cubeToHexString());
			}else if(cube.isPokerCubeType(PokerCubeType.ConsecutiveTriples)){
				values+=8*getPokerCubeValue(cube.cubeToHexString());
			}else if(cube.isPokerCubeType(PokerCubeType.Pairs)){
				values+=2*getPokerCubeValue(cube.cubeToHexString());
			}else if(cube.isPokerCubeType(PokerCubeType.Single)){
				values+=1*getPokerCubeValue(cube.cubeToHexString());
			}else if(cube.isPokerCubeType(PokerCubeType.Triple)){
				values+=4*getPokerCubeValue(cube.cubeToHexString());
			}
			
		}
		return values;
		
	}
	
	public int getPokerCubeHands(){
		int count2=0;
		for(PokerCube cube:pokerCubes){
			cube.calculate();
			if(cube.isPokerCubeType(PokerCubeType.Bomb)){
				count2=count2+cube.consectiveQuplex().sumOfElements();
			}else if(cube.isPokerCubeType(PokerCubeType.ConsecutiveSingle)){
				count2=count2+1;
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
		return count2;
	}
	
	
	

}
