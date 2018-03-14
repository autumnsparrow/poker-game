/**
 * 
 */
package com.sky.game.poker.robot;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Stack;

//import org.apache.commons.logging.LogFactory;







import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.game.poker.robot.ai.impl.CardStateDivsion;
import com.sky.game.poker.robot.poker.PokerCube;
//import com.sky.poke.bean.Poke;
//import com.sky.poke.util.PokeHelper;
import com.sky.poke.bean.Poke;
import com.sky.poke.util.PokeHelper;

/**
 * @author sparrow
 * 
 */
public class RobotAiAdapter {
	public static final Logger logger = LoggerFactory
			.getLogger("RobotAiAdapter");

	/**
	 * 
	 */
	public RobotAiAdapter() {
		// TODO Auto-generated constructor stub
	}

	public static PokerCube convertPokerCube(long value) {
		String currentPlayerHex = PokerCube.ZERO;
		
			currentPlayerHex = PokeHelper.formatToPokeStr(value);
		StringBuffer buffer = new StringBuffer();
		for (int i = currentPlayerHex.length() - 1; i >= 0; i--) {
			buffer.append(currentPlayerHex.charAt(i));
		}

		PokerCube pokerCube = PokerCube.getPokerCubeByHex(buffer.toString());
		// logger.info("HEX="+currentPlayerHex+" reverse "+buffer.toString()+"  "+pokerCube.toString());
		return pokerCube;
	}
	
	private static long converPoker(String pokerCubeHex){
		StringBuffer buffer = new StringBuffer();
		for (int i = pokerCubeHex.length() - 1; i >= 0; i--)
			buffer.append(pokerCubeHex.charAt(i));

		long v = PokeHelper.convertToPokeValue(buffer.toString());
		return v;
	}

//	private static long convertPoke(PokerCube pCube) {
//		String hex = pCube.cubeToHexString();
//		StringBuffer buffer = new StringBuffer();
//		for (int i = hex.length() - 1; i >= 0; i--)
//			buffer.append(hex.charAt(i));
//
//		long v = PokeHelper.convertToPokeValue(buffer.toString());
//		return v;
//	}
	
	public static Poke convertPoke(PokerCube pCube){
		String hex = pCube.cubeToHexString();
		StringBuffer buffer = new StringBuffer();
		for (int i = hex.length() - 1; i >= 0; i--)
			buffer.append(hex.charAt(i));

		long value= PokeHelper.convertToPokeValue(buffer.toString());
		//long value=convertPoke(pCube);
		Poke p=new Poke(value);
		return p;
	}

	private static Stack<PokerCube> convertPokerCubeStack(PokerCube[] pokers) {
		// PokerCube[] pokerCubes=new PokerCube[pokers.length];
		Stack<PokerCube> stack = new Stack<PokerCube>();
		for (int i = 0; i < pokers.length; i++) {
			stack.push((pokers[i]));
		}
		return stack;

	}

//	private static PokerCube[] convertPokerCubeStack(Poke[] pokers,
//			int position, boolean landlord) {
//		// PokerCube[] pokerCubes=new PokerCube[pokers.length];
//		List<PokerCube> cubes = new LinkedList<PokerCube>();
//		for (int i = 0; i < pokers.length; i++) {
//			PokerCube pCube = convertPokerCube(pokers[i]);
//			pCube.setBelongToLandloard(landlord);
//			pCube.setDeckPoistion(position);
//			cubes.add(pCube);
//		}
//		return cubes.toArray(new PokerCube[] {});
//
//	}

	private static final int CUR_POS = 0;
	private static final int RIGHT_POS = 1;
	private static final int LEFT_POS = 2;

	private static int getPositionByLastOutIndex(int lastOutIndex, int offset) {
		return (lastOutIndex + 3 + offset) % 3;
	}

	private static boolean getLandload(byte[] extra, int offset) {
		return extra[offset * 2] == 1 ? true : false;
	}

//	private static boolean checkPokeBelongs(Poke[] pokes, Poke poke) {
//		boolean belongs = false;
//		if (poke != null && pokes != null && pokes.length > 1) {
//			belongs = pokes[pokes.length - 1].getPokeValue() == poke
//					.getPokeValue();
//		}
//		return belongs;
//	}
//
//	public static Poke getOneSendCardBiggerButleast(Poke playerPoke,
//			Poke preOnePoke, byte[] extandInfo, byte lastOutIndex,
//			Poke[] myOutPokes, Poke[] rightOutPokes, Poke[] leftOutPokes,Poke baseCard) {
//
//		Poke poker = null;
//		PokerCube pc = null;
//		PokerCube previousPokerCube = null;
//		PokerCube[][] cubes = new PokerCube[4][];
//
//		PokerCube owner = convertPokerCube(playerPoke);
//
//		IPokerCubeAnalyzer pokerCubeAnalyzer = new DefaultPokerCubeAnalyzer();
//
//		if (preOnePoke != null) {
//			previousPokerCube = convertPokerCube(preOnePoke);
//		}
//
//		boolean isLandloard = false;// getLandload(extandInfo, CUR_POS);
//		int position = 0;// getPositionByLastOutIndex(lastOutIndex, 1);
//
//		// check the cube deck position
//		boolean preOneBelongs = false;
//		preOneBelongs = checkPokeBelongs(rightOutPokes, preOnePoke);
//		// belongs to the right.
//
//		// if the preOne belongs to right player.
//		if (preOneBelongs) {
//			isLandloard = getLandload(extandInfo, RIGHT_POS);
//			previousPokerCube.setBelongToLandloard(isLandloard);
//			previousPokerCube.setDeckPoistion(getPositionByLastOutIndex(
//					lastOutIndex, 0));
//			position = getPositionByLastOutIndex(lastOutIndex, -1);
//
//			cubes[1] = convertPokerCubeStack(rightOutPokes,
//					getPositionByLastOutIndex(lastOutIndex, 0),
//					getLandload(extandInfo, RIGHT_POS));
//			cubes[2] = convertPokerCubeStack(leftOutPokes,
//					getPositionByLastOutIndex(lastOutIndex, 1),
//					getLandload(extandInfo, LEFT_POS));
//			cubes[0] = convertPokerCubeStack(myOutPokes,
//					getPositionByLastOutIndex(lastOutIndex, 2),
//					getLandload(extandInfo, CUR_POS));
//		}
//
//		preOneBelongs = checkPokeBelongs(leftOutPokes, preOnePoke);
//		// belongs to the left players
//		if (preOneBelongs) {
//			isLandloard = getLandload(extandInfo, LEFT_POS);
//			previousPokerCube.setBelongToLandloard(isLandloard);
//			previousPokerCube.setDeckPoistion(getPositionByLastOutIndex(
//					lastOutIndex, 0));
//			position = getPositionByLastOutIndex(lastOutIndex, 1);
//
//			cubes[2] = convertPokerCubeStack(leftOutPokes,
//					getPositionByLastOutIndex(lastOutIndex, 0),
//					getLandload(extandInfo, LEFT_POS));
//			cubes[0] = convertPokerCubeStack(myOutPokes,
//					getPositionByLastOutIndex(lastOutIndex, 1),
//					getLandload(extandInfo, CUR_POS));
//			cubes[1] = convertPokerCubeStack(rightOutPokes,
//					getPositionByLastOutIndex(lastOutIndex, 2),
//					getLandload(extandInfo, RIGHT_POS));
//
//		}
//		// belong to the current user.
//		preOneBelongs = checkPokeBelongs(myOutPokes, preOnePoke);
//		if (preOneBelongs) {
//			isLandloard = getLandload(extandInfo, CUR_POS);
//			previousPokerCube.setBelongToLandloard(isLandloard);
//			previousPokerCube.setDeckPoistion(getPositionByLastOutIndex(
//					lastOutIndex, 0));
//			position = getPositionByLastOutIndex(lastOutIndex, 0);
//
//			cubes[0] = convertPokerCubeStack(myOutPokes,
//					getPositionByLastOutIndex(lastOutIndex, 0),
//					getLandload(extandInfo, CUR_POS));
//			cubes[1] = convertPokerCubeStack(rightOutPokes,
//					getPositionByLastOutIndex(lastOutIndex, 1),
//					getLandload(extandInfo, RIGHT_POS));
//			cubes[2] = convertPokerCubeStack(leftOutPokes,
//					getPositionByLastOutIndex(lastOutIndex, 2),
//					getLandload(extandInfo, LEFT_POS));
//
//		}
//
//		cubes[3] = new PokerCube[] { owner, previousPokerCube,convertPokerCube(baseCard) };
//
//		owner.setBelongToLandloard(getLandload(extandInfo, CUR_POS));
//		owner.setDeckPoistion(position);
//		
//		
//		//
//		//Notice : Before the poker cube analyze , landlord flag and deck position must be set.
//		//
//
//		logger.info("getOneSendCardBiggerButleast:"
//				+ (previousPokerCube != null ? previousPokerCube.toString()
//						: "") + owner.toString());
//
//		pokerCubeAnalyzer.analyze(cubes);
//
//		if (owner.canWePass()) {
//			pc = PokerCube.getPokerCubeByHex(PokerCube.ZERO);
//
//		} else {
//			if (owner.isShouldActive()) {
//				// active
//				pc = pokerCubeAnalyzer.getBestActivePokerCube();
//
//			} else {
//				// passive
//				pc = pokerCubeAnalyzer.getBestPassivePokerCube();
//
//			}
//
//		}
//
////		pc.setDeckPoistion(owner.getDeckPosition());
////		pc.setBelongToLandloard(owner.isBelongToLandloard());
//
//		long v = convertPoke(pc);
//		poker = new Poke(v);
//
//		logger.info("getOneSendCardBiggerButleast:" + pc.toString());
//		return poker;
//
//	}
//	
//	
//	public static Poke getZeroPoker(){
//		Poke poker=null;
//		PokerCube pc=PokerCube.getPokerCubeByHex(PokerCube.ZERO);
//		long v = convertPoke(pc);
//		poker = new Poke(v);
//		return poker;
//		
//	}
//	
//	public static long[] shufflesCheat(){
//		long[] pokers = new long[4];
//		
//		PokerCube pCubeA =null;
//		PokerCube pCubeB =null;
//		PokerCube pCubeC=null;
//		PokerCube pCubeR=null;
//		PokerCube[]  cubes=null;
//		for(int i=0;i<10;i++){
//		PokerCube pokerCube = new PokerCube();
//		pokerCube.shuffles();
//		//logger.info(pokerCube.toString());
//		
//		
//		pCubeA=pokerCube
//				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesA.value);
//		
//		pCubeB= pokerCube
//				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesB.value);
//		pCubeC = pokerCube
//				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesC.value);
//		pCubeR = pokerCube
//				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesRemains.value);
//		cubes=new PokerCube[]{pCubeA,pCubeB,pCubeC,pCubeR};
//		boolean shouldReshuffles=false;
//		
//		Arrays.sort(cubes,new Comparator<PokerCube>(){
//
//			@Override
//			public int compare(PokerCube arg0, PokerCube arg1) {
//				// TODO Auto-generated method stub
//				CardStateDivsion state0=new CardStateDivsion(arg0);
//				CardStateDivsion state2=new CardStateDivsion(arg1);
//				
//				return state2.getPokerAnalyzerSchemas()[0].getPokerCubeValues()-state0.getPokerAnalyzerSchemas()[0].getPokerCubeValues();
//			}
//			
//		});
//		
//		int value=cubes[0].getPokerCubeValue()-cubes[2].getPokerCubeValue();
//		Random r=new Random();
//		int limit=100+r.nextInt(100);
//		if(value>limit){
//			shouldReshuffles=true;
//		}
//		
//		
//		logger.info(String.format("\n[A=(%s,%d) ,B=(%s,%d) ,C=(%s,%d) ,R=(%s,%d)] ",
//				cubes[0].cubeToHexString(),cubes[0].getPokerCubeValue(),
//				cubes[1].cubeToHexString(),cubes[1].getPokerCubeValue(),
//				cubes[2].cubeToHexString(),cubes[2].getPokerCubeValue(),
//				cubes[3].cubeToHexString(),cubes[3].getPokerCubeValue()));
//		if(!shouldReshuffles){
//			//cubes=new PokerCube[]{pCubeA,pCubeB,pCubeC,pCubeR};
//			break;
//		}
//		
//			
//		}
//		
//		
//		// if has bomb or triples shuffles
//		//PokerCube[]  pcubes=new PokerCube[]{pCubeA,pCubeB,pCubeC,pCubeR};
//		
//		
//		
//		// find the best for the robot
//		//logger.info(pokerCube.toString());
//		// logger.info(String.format("\nPokerCube - [A=%s,B=%s,C=%s,R=%s]",
//		// pCubeA.cubeToHexString(),pCubeB.cubeToHexString(),pCubeC.cubeToHexString(),pCubeR.cubeToHexString()));
//		/**
//		 * A=090201e1b34061,B=40ad9a16403080,C=a6106408088f12,R=10400000040000
//		 * [A=015169248a6801,B=d4a00602711482,C=2a0e80d8048270,R=00001001000100]
//		 * - [A=c8d00382981882,B=
//		 * 120ee008658221,C=25011c54026550,R=00200021000000]
//		 * 
//		 * 
//		 */
//		// pCubeA.cubeHextoCube("c8d00382981882");
//		// pCubeB.cubeHextoCube("120ee008658221");
//		// pCubeC.cubeHextoCube("25011c54026550");
//		// pCubeR.cubeHextoCube("00200021000000");
//
////		logger.info(String.format("\nPokerCube - [A=%s,B=%s,C=%s,R=%s]",
////				pCubeA.cubeToHexString(), pCubeB.cubeToHexString(),
////				pCubeC.cubeToHexString(), pCubeR.cubeToHexString()));
////		
//		
//		
////		
////		Arrays.sort(cubes,new Comparator<PokerCube>(){
////
////			@Override
////			public int compare(PokerCube arg0, PokerCube arg1) {
////				// TODO Auto-generated method stub
////				CardStateDivsion state0=new CardStateDivsion(arg0);
////				CardStateDivsion state2=new CardStateDivsion(arg1);
////				
////				return state2.getPokerAnalyzerSchemas()[0].getPokerCubeValues()-state0.getPokerAnalyzerSchemas()[0].getPokerCubeValues();
////			}
////			
////		});
////		
//		
//		
//
////		CardStateDivsion state0=new CardStateDivsion(cubes[0]);
////		PokerAnalyzerSchema schema0=state0.getPokerAnalyzerSchemas()[0];
////		CardStateDivsion state1=new CardStateDivsion(cubes[1]);
////		PokerAnalyzerSchema schema1=state1.getPokerAnalyzerSchemas()[0];
////		CardStateDivsion state2=new CardStateDivsion(cubes[2]);
////		PokerAnalyzerSchema schema2=state2.getPokerAnalyzerSchemas()[0];
////		CardStateDivsion state3=new CardStateDivsion(cubes[3]);
////		PokerAnalyzerSchema schema3=state3.getPokerAnalyzerSchemas()[0];
////		
////		logger.info(String.format("\n[A=(%s,%d,%d) ,B=(%s,%d,%d) ,C=(%s,%d,%d) ,R=(%s,%d,%d)] ",
////				cubes[0].toString(),schema0.getPokerCubeValues(),schema0.getPokerCubeHands(),
////				cubes[1].toString(),schema1.getPokerCubeValues(),schema1.getPokerCubeHands(),
////				cubes[2].toString(),schema2.getPokerCubeValues(),schema2.getPokerCubeHands(),
////				cubes[3].toString(),schema3.getPokerCubeValues(),schema3.getPokerCubeHands()
////				));
//		
//	
//
//		pokers[0] = convertPoke(cubes[0]);
//		pokers[1] = convertPoke(cubes[1]);
//		pokers[2] = convertPoke(cubes[2]);
//		pokers[3] = convertPoke(cubes[3]);
//
//		return pokers;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public static long[] shuffles() {
//		long[] pokers = new long[4];
//		
//		PokerCube pCubeA =null;
//		PokerCube pCubeB =null;
//		PokerCube pCubeC=null;
//		PokerCube pCubeR=null;
//		PokerCube[]  cubes=null;
//		for(int i=0;i<10;i++){
//		PokerCube pokerCube = new PokerCube();
//		pokerCube.shuffles();
//		//logger.info(pokerCube.toString());
//		
//		
//		pCubeA=pokerCube
//				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesA.value);
//		
//		pCubeB= pokerCube
//				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesB.value);
//		pCubeC = pokerCube
//				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesC.value);
//		pCubeR = pokerCube
//				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesRemains.value);
//		cubes=new PokerCube[]{pCubeA,pCubeB,pCubeC,pCubeR};
//		boolean shouldReshuffles=false;
//		
//		Arrays.sort(cubes,new Comparator<PokerCube>(){
//
//			@Override
//			public int compare(PokerCube arg0, PokerCube arg1) {
//				// TODO Auto-generated method stub
//				CardStateDivsion state0=new CardStateDivsion(arg0);
//				CardStateDivsion state2=new CardStateDivsion(arg1);
//				
//				return state2.getPokerAnalyzerSchemas()[0].getPokerCubeValues()-state0.getPokerAnalyzerSchemas()[0].getPokerCubeValues();
//			}
//			
//		});
//		
//		int value=cubes[0].getPokerCubeValue()-cubes[2].getPokerCubeValue();
//		Random r=new Random();
//		int limit=100+r.nextInt(100);
//		if(value>limit){
//			shouldReshuffles=true;
//		}
//		
//		
//		logger.info(String.format("\n[A=(%s,%d) ,B=(%s,%d) ,C=(%s,%d) ,R=(%s,%d)] ",
//				cubes[0].cubeToHexString(),cubes[0].getPokerCubeValue(),
//				cubes[1].cubeToHexString(),cubes[1].getPokerCubeValue(),
//				cubes[2].cubeToHexString(),cubes[2].getPokerCubeValue(),
//				cubes[3].cubeToHexString(),cubes[3].getPokerCubeValue()));
//		if(!shouldReshuffles){
//			cubes=new PokerCube[]{pCubeA,pCubeB,pCubeC,pCubeR};
//			break;
//		}
//		
//			
//		}
//		
//		
//		// if has bomb or triples shuffles
//		//PokerCube[]  pcubes=new PokerCube[]{pCubeA,pCubeB,pCubeC,pCubeR};
//		
//		
//		
//		// find the best for the robot
//		//logger.info(pokerCube.toString());
//		// logger.info(String.format("\nPokerCube - [A=%s,B=%s,C=%s,R=%s]",
//		// pCubeA.cubeToHexString(),pCubeB.cubeToHexString(),pCubeC.cubeToHexString(),pCubeR.cubeToHexString()));
//		/**
//		 * A=090201e1b34061,B=40ad9a16403080,C=a6106408088f12,R=10400000040000
//		 * [A=015169248a6801,B=d4a00602711482,C=2a0e80d8048270,R=00001001000100]
//		 * - [A=c8d00382981882,B=
//		 * 120ee008658221,C=25011c54026550,R=00200021000000]
//		 * 
//		 * 
//		 */
//		// pCubeA.cubeHextoCube("c8d00382981882");
//		// pCubeB.cubeHextoCube("120ee008658221");
//		// pCubeC.cubeHextoCube("25011c54026550");
//		// pCubeR.cubeHextoCube("00200021000000");
//
////		logger.info(String.format("\nPokerCube - [A=%s,B=%s,C=%s,R=%s]",
////				pCubeA.cubeToHexString(), pCubeB.cubeToHexString(),
////				pCubeC.cubeToHexString(), pCubeR.cubeToHexString()));
////		
//		
//		
////		
////		Arrays.sort(cubes,new Comparator<PokerCube>(){
////
////			@Override
////			public int compare(PokerCube arg0, PokerCube arg1) {
////				// TODO Auto-generated method stub
////				CardStateDivsion state0=new CardStateDivsion(arg0);
////				CardStateDivsion state2=new CardStateDivsion(arg1);
////				
////				return state2.getPokerAnalyzerSchemas()[0].getPokerCubeValues()-state0.getPokerAnalyzerSchemas()[0].getPokerCubeValues();
////			}
////			
////		});
////		
//		
//		
//
////		CardStateDivsion state0=new CardStateDivsion(cubes[0]);
////		PokerAnalyzerSchema schema0=state0.getPokerAnalyzerSchemas()[0];
////		CardStateDivsion state1=new CardStateDivsion(cubes[1]);
////		PokerAnalyzerSchema schema1=state1.getPokerAnalyzerSchemas()[0];
////		CardStateDivsion state2=new CardStateDivsion(cubes[2]);
////		PokerAnalyzerSchema schema2=state2.getPokerAnalyzerSchemas()[0];
////		CardStateDivsion state3=new CardStateDivsion(cubes[3]);
////		PokerAnalyzerSchema schema3=state3.getPokerAnalyzerSchemas()[0];
////		
////		logger.info(String.format("\n[A=(%s,%d,%d) ,B=(%s,%d,%d) ,C=(%s,%d,%d) ,R=(%s,%d,%d)] ",
////				cubes[0].toString(),schema0.getPokerCubeValues(),schema0.getPokerCubeHands(),
////				cubes[1].toString(),schema1.getPokerCubeValues(),schema1.getPokerCubeHands(),
////				cubes[2].toString(),schema2.getPokerCubeValues(),schema2.getPokerCubeHands(),
////				cubes[3].toString(),schema3.getPokerCubeValues(),schema3.getPokerCubeHands()
////				));
//		
//	
//
//		pokers[0] = convertPoke(cubes[0]);
//		pokers[1] = convertPoke(cubes[1]);
//		pokers[2] = convertPoke(cubes[2]);
//		pokers[3] = convertPoke(cubes[3]);
//
//		return pokers;
//
//	}
	
	
	public static PokerCube[]  shuffles(int level){
		PokerCube pCubeA =null;
		PokerCube pCubeB =null;
		PokerCube pCubeC=null;
		PokerCube pCubeR=null;
		PokerCube[]  cubes=null;
		for(int i=0;i<1;i++){
		PokerCube pokerCube = new PokerCube();
		pokerCube.shuffles(level);
		//logger.info(pokerCube.toString());
		
		
		pCubeA=pokerCube
				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesA.value);
		
		pCubeB= pokerCube
				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesB.value);
		pCubeC = pokerCube
				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesC.value);
		pCubeR = pokerCube
				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesRemains.value);
		
		
		cubes=new PokerCube[]{pCubeA,pCubeB,pCubeC,pCubeR};
		
		
		
		Arrays.sort(cubes,new Comparator<PokerCube>(){

			
			public int compare(PokerCube arg0, PokerCube arg1) {
				// TODO Auto-generated method stub
				CardStateDivsion state0=new CardStateDivsion(arg0);
				CardStateDivsion state2=new CardStateDivsion(arg1);
				
				return state2.getPokerAnalyzerSchemas()[0].getPokerCubeValues()-state0.getPokerAnalyzerSchemas()[0].getPokerCubeValues();
			}
			
		});
		/*	
		 * boolean shouldReshuffles=false;
		int value=cubes[0].getPokerCubeValue()-cubes[2].getPokerCubeValue();
		Random r=new Random();
		int limit=level*50+r.nextInt(100);
		if(value>limit){
			shouldReshuffles=true;
		}
		
		
		logger.info(String.format("\n[A=(%s,%d) ,B=(%s,%d) ,C=(%s,%d) ,R=(%s,%d)] ",
				cubes[0].cubeToHexString(),cubes[0].getPokerCubeValue(),
				cubes[1].cubeToHexString(),cubes[1].getPokerCubeValue(),
				cubes[2].cubeToHexString(),cubes[2].getPokerCubeValue(),
				cubes[3].cubeToHexString(),cubes[3].getPokerCubeValue()));
		if(!shouldReshuffles){
			cubes=new PokerCube[]{pCubeA,pCubeB,pCubeC,pCubeR};
			break;
		}
		 */
			
		}
		
		
		// if has bomb or triples shuffles
		PokerCube[]  pcubes=new PokerCube[]{pCubeA,pCubeB,pCubeC,pCubeR};
		
		
		for(PokerCube p:pcubes){
			p.replaceCubeValue();
		}
		return pcubes;
	}
	

}
