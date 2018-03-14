package com.sky.game.poker.robot;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.poker.robot.ai.DeckStates;
import com.sky.game.poker.robot.ai.IDeck;
import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.PokerAnalyzerSchema;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.ai.impl.CardStateDivsion;
import com.sky.game.poker.robot.ai.impl.DefaultDeck;
import com.sky.game.poker.robot.ai.impl.DefaultPokerCubeAnalyzer;
import com.sky.game.poker.robot.poker.ConsecutiveCompare;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * Hello world!
 *
 */
public class App
{
	
	private static final Log logger=LogFactory.getLog(App.class);
	
	private static PokerCube split(PokerCube pCube,PokerCube.Cube cube,int size){
		log(cube.toString()+" "+size);
		//log(pCube.toString());
		PokerCube pokerCube=pCube.subPokerCubeByConsectiveCompare(cube, size);
		if(!pokerCube.isEmpty())
			log(pokerCube.toString());
		return pokerCube;
		
	}
	
	public static class CubeType{
		PokerCube.Cube cube;
		int size;
		public CubeType(PokerCube.Cube cube, int size) {
			super();
			this.cube = cube;
			this.size = size;
		}
		public static CubeType instance(PokerCube.Cube cube,int size){
			return new CubeType(cube, size);
		}
		
	}
    public static void main( String[] args ) throws Exception
    {
      
    	//pokerCubeAnalyzerTest();
//    	for(int i=0;i<1;i++)
//    		deck();
    	
    	//testTripleWithSingles();
    	//TripleWithSingle 200e0000000000 0a0000d8048270
    	//testPattern("200e0000000000",PokerCubeType.TriplesWithSingle,"0a0000d8048270");  -- Fixed
    	// Single  00000000000002  f7014000002000
    	//testPattern("00000000000002",PokerCubeType.Single,"f7014000002000");  -- Fixed
    	
    	// TripleWithPair 0c000d00000000 222a02b0155821
    	//testPattern("0c000d00000000",PokerCubeType.TriplesWithPair,"222a02b0155821");    --fixed
    	
    	// Single 00000000000002 5000032a0ac180
    	//testPattern("00000000000002",PokerCubeType.Single,"5000032a0ac180");  
    	
    	// TripleWithSingle 40000000007000 05010b0ec180d0
    	//testPattern("40000000007000",PokerCubeType.TriplesWithSingle,"05010b0ec180d0"); 
    	
    	//testSingles();
    	///RobotAiAdapter.shuffles();
    //	testTripleWithSingles();
    	
    //	testGt();
    	//testBomb();	
    	
    	
//    	byte[]  l={(byte)6,(byte)8,(byte)11,(byte)12};
//    	byte[]  f1={(byte)6,(byte)9,(byte)11,(byte)12};
//    	byte[]  f2={(byte)11,(byte)14};
//    	
//    	boolean ret=canBeatIt(l, f1, f2);
//    	System.out.println(ret);
    	
    	
    	
    		int loc=0;
    	  for(int i=0;i<1;i++)	{
    		    PokerCube hands=new PokerCube();
    		       log(loc+"\n\n");
    		       if(loc>5)
    		    	   loc=0;
    		       hands.shuffles(5);
    		       log("Shuffles:  "+hands.toString());
    		       log("Shuffles:  "+hands.cubeToHexString());
    		       PokerCube handsR=hands.maskPokerCubeWithValue(PokerCube.Cube.ShufflesRemains.value);
    		       PokerCube handsA=hands.maskPokerCubeWithValue(PokerCube.Cube.ShufflesA.value);
    		       handsA= handsA.addPokerCube(handsR);
    		      
    		       log(handsA.toString());
    		       CardStateDivsion divsion=new CardStateDivsion(handsA);
    		       PokerAnalyzerSchema[] aschema=divsion.getPokerAnalyzerSchemas();
    		       
    		       log("\n\r=====================");
    		       PokerCube handsB=hands.maskPokerCubeWithValue(PokerCube.Cube.ShufflesB.value);
    		       handsB =handsB.addPokerCube(handsR);
    		       handsB.setDeckPoistion(1);
    		       log(handsB.toString());
    		       
    		       divsion=new CardStateDivsion(handsB);
    		     //  divsion.getPokerAnalyzerSchemas();
    		       PokerAnalyzerSchema[] bschema=divsion.getPokerAnalyzerSchemas();
    		       log("\n\r=====================");
    		       PokerCube handsC=hands.maskPokerCubeWithValue(PokerCube.Cube.ShufflesC.value);
    		       handsC =handsC.addPokerCube(handsR);
    		       handsC.setDeckPoistion(2);
    		       log(handsC.toString());
    		       
    		       divsion=new CardStateDivsion(handsC);
    		       PokerAnalyzerSchema[] cschema=divsion.getPokerAnalyzerSchemas();
    		       
    		     
       		      
    		       PokerAnalyzerSchema[] minhands=new PokerAnalyzerSchema[]{aschema[0],bschema[0],cschema[0]};
    		       
    		       Arrays.sort(minhands,new Comparator<PokerAnalyzerSchema>() {

					public int compare(PokerAnalyzerSchema o1,
							PokerAnalyzerSchema o2) {
						// TODO Auto-generated method stub
						return o1.getPokerCubeHands()-o2.getPokerCubeHands();
					}
				});
    		       
    		      PokerCubeType pt=PokerCubeType.Single;
    		     byte[]  avalues= getValues(aschema[0], pt);
    		     byte[]  bvalues=  getValues(bschema[0], pt);
    		     byte[]  cvalues=  getValues(cschema[0], pt);
    		     
    		     // compare the a values to b & c
    		     Arrays.sort(avalues);
    		     
    		     Arrays.sort(bvalues);
    		     
    		     Arrays.sort(cvalues);
    		     System.out.println(pt);
    		     System.out.println(Arrays.toString(avalues));
    		     System.out.println(Arrays.toString(bvalues));
    		     System.out.println(Arrays.toString(cvalues));
    		       
    		     
    		     pt=PokerCubeType.Pairs;
    		     avalues=  getValues(aschema[0], pt);
    		     bvalues=  getValues(bschema[0], pt);
    		     cvalues=  getValues(cschema[0], pt);
    		     
    		     // compare the a values to b & c
    		     Arrays.sort(avalues);
    		     
    		     Arrays.sort(bvalues);
    		     
    		     Arrays.sort(cvalues);
    		     System.out.println(pt);
    		     System.out.println(Arrays.toString(avalues));
    		     System.out.println(Arrays.toString(bvalues));
    		     System.out.println(Arrays.toString(cvalues));
    		       
    		      //logger.info( minhands[0].getPokerCubes().get(0));

    		     pt=PokerCubeType.Triple;
    		     avalues=  getValues(aschema[0], pt);
    		     bvalues=  getValues(bschema[0], pt);
    		     cvalues=  getValues(cschema[0], pt);
    		     
    		     // compare the a values to b & c
    		     Arrays.sort(avalues);
    		     
    		     Arrays.sort(bvalues);
    		     
    		     Arrays.sort(cvalues);
    		     System.out.println(pt);
    		     System.out.println(Arrays.toString(avalues));
    		     System.out.println(Arrays.toString(bvalues));
    		     System.out.println(Arrays.toString(cvalues));
    		       
    		       
    		     
    	  }
    	
    	//deck();
    }
    
    
    private static byte[]  getValues(PokerAnalyzerSchema schema,PokerCubeType pt){
    	byte[] bytes=new byte[0];
    	PokerCube pokerCube=schema.getPokerCube(pt);
    	if(pokerCube!=null){
    		bytes=pokerCube.getValues();
    		Arrays.sort(bytes);
    	}
    	
    	return bytes;
    }
    
    private static void testTripleWithSingles(){
    	PokerCube pokerCubePrevious=new PokerCube();
    	pokerCubePrevious.cubeHextoCube("200e0000000003");
    	pokerCubePrevious.setPokerCubeType(PokerCubeType.TriplesWithSingle);
    	PokerCube owner=new PokerCube();
    	owner.cubeHextoCube("0a0000d8048270");
    	PokerCube pc=owner.getLargerPokerCube(pokerCubePrevious, true);
    	
    	log(pc.toString());
    	//PokerCube.getPokerCubeByType(pokerCubeType, cubes)
    	
    	
    	// single 00000000000002
    	// owner f7014000002000
    }
    
    private static void testBomb(){
    	PokerCube pc=PokerCube.getPokerCubeByHex("f0000000000000");
    	PokerCube rocket=PokerCube.getPokerCubeByHex("00000000000013");
    	rocket.setBelongToLandloard(true);
    	rocket.setDeckPoistion(1);
    	pc.setBelongToLandloard(false);
    	pc.setDeckPoistion(0);
    	DefaultPokerCubeAnalyzer a=new DefaultPokerCubeAnalyzer();
    	PokerCube[][] cubes=new PokerCube[4][];
    	for(int i=0;i<cubes.length;i++){
    		
    		cubes[i]=new PokerCube[]{PokerCube.getPokerCubeByHex(PokerCube.ZERO),PokerCube.getPokerCubeByHex(PokerCube.ZERO)};
    	}
    	cubes[3]=new PokerCube[]{rocket,pc,PokerCube.getPokerCubeByHex(PokerCube.ZERO)};
    	a.analyze(cubes);
    	PokerCube ppp=a.getBestActivePokerCube();
    	System.out.println(ppp);
    }
    
    private static void testGt(){
    	
    	//00000000000092
    	PokerCube pc2=PokerCube.getPokerCubeByHex("00000000000092");
    	DefaultPokerCubeAnalyzer a=new DefaultPokerCubeAnalyzer();
    	PokerCube[][] cubes=new PokerCube[4][];
    	for(int i=0;i<cubes.length;i++){
    		
    		cubes[i]=new PokerCube[]{PokerCube.getPokerCubeByHex(PokerCube.ZERO),PokerCube.getPokerCubeByHex(PokerCube.ZERO)};
    	}
    	a.analyze(cubes);
    	
    	PokerCube ppp=a.getBestActivePokerCube();
    	System.out.println(ppp);
    }
    
    
    private static void testSingles(){
    	PokerCube pCubeOfL=PokerCube.getPokerCubeByHex("00210001010200");
    	pCubeOfL.setBelongToLandloard(true);
    	pCubeOfL.setDeckPoistion(0);
    	PokerCube pCubeOfF1=PokerCube.getPokerCubeByHex("81840004004800");
    	pCubeOfF1.setDeckPoistion(1);
    	PokerCube pCubeOfF2=PokerCube.getPokerCubeByHex("04000000021000");
    	pCubeOfF2.setDeckPoistion(2);
    	
    	log(pCubeOfL.toString());
    	log(pCubeOfF1.toString());
    	log(pCubeOfF2.toString());
    }
    
    private static void testPattern(String previous,PokerCubeType pt,String hand){
    	
    	
    	
    	PokerCube pc=null;
		
		
		
		IPokerCubeAnalyzer pokerCubeAnalyzer = new DefaultPokerCubeAnalyzer();
		
		PokerCube previousPokerCube=new PokerCube();
		previousPokerCube.cubeHextoCube(previous);
		
    	PokerCube owner=new PokerCube();
    	owner.cubeHextoCube(hand);
    	
    	Stack<PokerCube> stack=new Stack<PokerCube>();
    	//PokerCube pokerCubePrevious=new PokerCube();
    	//pokerCubePrevious.cubeHextoCube(previous);
    	stack.push(previousPokerCube);
    	
		//pokerCubeAnalyzer.analyze(owner, null, null, null, null,stack );
		if(previousPokerCube==null||previousPokerCube.isEmpty()){
			// active
			pc = pokerCubeAnalyzer.getBestActivePokerCube();
			
		}else{
			// passive
			pc=pokerCubeAnalyzer.getBestPassivePokerCube();
			
		}
		
		
		
		System.out.println("getOneSendCardBiggerButleast:"+(previousPokerCube!=null?previousPokerCube.toString():"")+owner.toString()+pc.toString());
		
    }
    
    
    
    public static void deck(){
    	logger.debug("============================================================================");
    	logger.debug("============================================================================");
        logger.debug("============================================================================");
    	IDeck deck=new DefaultDeck();
    	while(deck.getDeckState().compareTo(DeckStates.End)!=0){
    		deck.run();
    	}
    	logger.debug("============================================================================");
        logger.debug("============================================================================");
    	logger.debug("============================================================================");
        
    	
    }
    
    public static void log(String msg){
    	System.out.println(msg);
    }
    
    
    
    
    private static void pokerCubeAnalyzerTest(){
    	DefaultPokerCubeAnalyzer  pokerCubeAnalyzer=new DefaultPokerCubeAnalyzer();
    	
    	
    	//pokerCubeAnalyzer.analyze(pokerCube);
    	
    	
    }
    
    private static void pokerCubeTest(){
    	CubeType[][] cubeTypes={
    			{
    				CubeType.instance(PokerCube.Cube.ConsectiveQuplex, 1),
    				CubeType.instance(PokerCube.Cube.ConsectiveTriple, 2),
    				CubeType.instance(PokerCube.Cube.ConsectiveTriple, 1),
    				CubeType.instance(PokerCube.Cube.ConsectivePair, 3),
    				CubeType.instance(PokerCube.Cube.ConsectiveSingle, 5),
    				CubeType.instance(PokerCube.Cube.ConsectivePair, 1),
    				CubeType.instance(PokerCube.Cube.ConsectiveSingle, 1),
    				
    			}	,
    			{
    				CubeType.instance(PokerCube.Cube.ConsectiveQuplex, 1),
    				CubeType.instance(PokerCube.Cube.ConsectivePair, 3),
    				CubeType.instance(PokerCube.Cube.ConsectiveTriple, 2),
    				CubeType.instance(PokerCube.Cube.ConsectiveTriple, 1),
    				CubeType.instance(PokerCube.Cube.ConsectiveSingle, 5),
    				
    				
    				CubeType.instance(PokerCube.Cube.ConsectivePair, 1),
    				CubeType.instance(PokerCube.Cube.ConsectiveSingle, 1),
    			},
    			{
    				CubeType.instance(PokerCube.Cube.ConsectiveQuplex, 1),
    				CubeType.instance(PokerCube.Cube.ConsectiveSingle, 5),
    				CubeType.instance(PokerCube.Cube.ConsectiveTriple, 2),
    				CubeType.instance(PokerCube.Cube.ConsectiveTriple, 1),
    				
    				CubeType.instance(PokerCube.Cube.ConsectivePair, 3),
    				
    				CubeType.instance(PokerCube.Cube.ConsectivePair, 1),
    				CubeType.instance(PokerCube.Cube.ConsectiveSingle, 1),
    			}
    			
    			
    	};
    	
    for(int i=0;i<1;i++)	{
    PokerCube hands=new PokerCube();
       log("\n\n");
       
       hands.shuffles(2);
       log("Shuffles:  "+hands.toString());
       log("Shuffles:  "+hands.cubeToHexString());
       
       PokerCube handsA=hands.maskPokerCubeWithValue(PokerCube.Cube.ShufflesA.value);
       handsA.cubeHextoCube("555000555a5000");
       log(handsA.toString());
       
       PokerCube hand=new PokerCube();
       hand.cubeHextoCube("01110000000000");
       log(hand.toString());
       ConsecutiveCompare compare=handsA.consectivePair();
       boolean larger=  compare.compare(hand.consectiveSolo());
       if(larger){
    	   byte[]  statics=compare.getComparedStatics(PokerCube.Cube.ConsectiveSingle.value);
    	   PokerCube cube=handsA.subPokerCubeByStatics(statics);
    	   log(cube.toString());
    	   log(handsA.toString());
       }else{
    	   log("no larger");
       }
       
      //handsA.cubeHextoCube("2b233580012130");
//       PokerCube [] pokerCubes=new PokerCube[3];
//       
//       for(int j=0;j<3;j++){
//    	   PokerCube cube=handsA.clone();
//    	   CubeType [] cubes=cubeTypes[j];
//    	   
//    	   log("====================================================================================\r\n");
//    	   log(cube.toString());
//    	   for(int k=0;k<cubes.length;k++){
//    		   //handsA.subCubeByConsectiveCompare(cubes[k].cube, cubes[k].size);
//    		   PokerCube pc=split(cube,cubes[k].cube,cubes[k].size);
//    		   if(k==cubes.length-1){
//    			   pokerCubes[j]=pc;
//    		   }
//    	   }
//    	   log(cube.toString());
//    	   
//    	  
//    	   log("====================================================================================\r\n");
//       }
//       
//       log("----------------------------------------------------------------------\r\n");
//       for(int j=0;j<3;j++){
//    	   log(pokerCubes[j].toString());
//       }
//       
//       log("----------------------------------------------------------------------\r\n");
       
//       log("PlayerA: " +handsA.toString());
//       
//      PokerCube pokerCubeAQuplex= handsA.subCubeByConsectiveCompare(PokerCube.Cube.ConsectiveQuplex, 1);
//       log("PlayerA quplex:"+pokerCubeAQuplex.toString());
//       log("PlayerA: " +handsA.toString());
//      PokerCube pokerCubeATriple= handsA.subCubeByConsectiveCompare(PokerCube.Cube.ConsectiveTriple, 2);
//      log("PlayerA triple:"+pokerCubeATriple.toString());
//      log("PlayerA:"+handsA);
//      pokerCubeATriple= handsA.subCubeByConsectiveCompare(PokerCube.Cube.ConsectiveTriple, 1);
//      log("PlayerA triple:"+pokerCubeATriple.toString());
//      
//     
//    
//     
//      log("PlayerA:"+handsA);
//      PokerCube pokerCubeAPair=handsA.subCubeByConsectiveCompare(PokerCube.Cube.ConsectivePair, 3);
//      log("PlayerA pair:"+pokerCubeAPair.toString());
//      log("PlayerA:"+handsA);
//      pokerCubeAPair=handsA.subCubeByConsectiveCompare(PokerCube.Cube.ConsectivePair, 1);
//      log("PlayerA pair:"+pokerCubeAPair.toString());
//      
//      log("PlayerA:"+handsA);
//      PokerCube pokerCubeASingle=handsA.subCubeByConsectiveCompare(PokerCube.Cube.ConsectiveSingle, 5);
//      
//      
//      
//      log("PlayerA single consective:"+pokerCubeASingle.toString());
//      log("PlayerA:"+handsA);
//      pokerCubeASingle=handsA.subCubeByConsectiveCompare(PokerCube.Cube.ConsectiveSingle, 1);
//      log("PlayerA single:"+pokerCubeASingle.toString());
     
//       
//       handsA.reverseCube(PokerCube.Cube.StateValid.value);
//       log("~PlayerA: " +handsA.toString());
//       log("PlayerA:  "+handsA.cubeToHexString());
//       
//       PokerCube handsB=hands.filterPokerHands(PokerCube.Cube.ShufflesB.value);
//       
//       log("PlayerB:  "+handsB.toString());
//       handsB.reverseCube(PokerCube.Cube.StateValid.value);
//       log("~PlayerB:  "+handsB.toString());
//       
//       PokerCube handsC=hands.filterPokerHands(PokerCube.Cube.ShufflesC.value);
//       log("PlayerC:  "+handsC.toString());
//       
//       PokerCube handsR=hands.filterPokerHands(PokerCube.Cube.ShufflesRemains.value);
//       log("PlayerR :"+ handsR.toString());
//       
//       PokerCube handsX= handsC.addPokerHands(handsR,(byte) 0xcf);
////     RoundOfPoker roundOfPoker=new RoundOfPoker(RoundOfPoker.PlayerOwner.Self, 2);
//      log("PlayerX:  "+handsX.toString());
//      handsX.reverseCube(PokerCube.Cube.StateValid.value);
//      log("~PlayerX:  "+handsX.toString());
    }
//       handsR.cubeHextoCube("00000044488000");
       
//       handsC.cubeHextoCube("68198889964a00");
//       log("=============================================");
//       log("PlayerC:  "+handsC.toString());
//       log("PlayerR :"+ handsR.toString());
//       // c - r
//       PokerCube handsT=handsC.subCubeByStatics(handsR);
//       log("PlayerC:  "+handsC.toString());
//       log("PokerHands T:"+handsT.toString());
//     
//       
//      // shuffles();
//      PokerCube handsL= handsC.addPokerHands(handsR,(byte) 0xcf);
////      RoundOfPoker roundOfPoker=new RoundOfPoker(RoundOfPoker.PlayerOwner.Self, 2);
//       log("PlayerL:  "+handsL.toString());
//       log("PlayerL:  "+handsL.cubeToHexString());
//    	//System.out.println( "Hello World!" );
//       handsL.rewriteHexToCubeByValue(handsR.cubeToHexString(),roundOfPoker.getPoker());
//       roundOfPoker=new RoundOfPoker(RoundOfPoker.PlayerOwner.Left, 6);
//       handsL.rewriteHexToCubeByValue(handsA.cubeToHexString(),roundOfPoker.getPoker());
//    	
//       log("PlayerL(HEXtoCube):  "+handsL.toString());
//       handsL.reverseCube((byte)0x01);
//       log("PlayerL(HEXtoCube):  "+handsL.toString());
//       
//    	log("\n"+roundOfPoker.toString());
//       PokerCube handsH=new PokerCube();
//    	handsH.cubeHextoCube("00000044488000");
//    	log("PlayerH:"+handsH.toString());
//    	//handsH.reverseCube((byte)0x01);
//    	//log("PlayerH reverse:"+handsH.toString());
    	
    	
    	
//    	// test consective.
//    	log("PlayerH consecutive Solo:\n"+handsH.consectiveSolo().toString());
//    	log("PlayerH consecutive Pair:\n"+handsH.consectivePair().toString());
//    	log("PlayerH consecutive Triple:\n"+handsH.consectiveTriple().toString());
    	
    }
    
    
    public static void shuffles(){
    	int  numbers[]=new int[56];
    	
    	for(int i=0;i<numbers.length;i++){
    		numbers[i]=i;
    	}
    	log("\n");
    	for(int i=0;i<numbers.length;i++){
    		log("  "+numbers[i]);
    	}
    	log("\n");
    	Random r=new Random();
    	
    	for(int i=0;i<10000;i++){
    		int p=r.nextInt(56);
    		// poistion.
    		int temp=numbers[i%56];
    		numbers[i%56]=numbers[p];
    		numbers[p]=temp;
    		
    	}
    	
    	log("\n");
    	for(int i=0;i<numbers.length;i++){
    		log("  "+numbers[i]);
    	}
    	log("\n");
    	
    }

    
    /**
     * 
     * f1 l f2 (sequences)
     * 
     * landlord first out.
     * @param l  landlord singles.
     * @param f1 farmer1 singles,
     * @param f2 farmer2 single
     * @return
     */
    public static boolean canBeatIt(byte[] l,byte[] f1,byte[]  f2){
    	boolean ret=false;
    	Arrays.sort(l);
    	byte maxL=l[l.length-1];
    	Arrays.sort(f1);
    	Arrays.sort(f2);
    	// check how many number of the f1 and f2 larger than l
    	int i1=0;
    	for(int i=0;i<f1.length;i++){
    		if(f1[i]>maxL)
    			i1++;
    	}
    	int i2=0;
    	for(int i=0;i<f1.length;i++){
    		if(f1[i]>maxL)
    			i2++;
    	}
       
    	ret=(i1+i2)>l.length-1;
    	return ret;
    }


}


