package com.sky.poke.ai;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.poke.ai.bean.CAIPoker;
import com.sky.poke.ai.bean.DDZCards;
import com.sky.poke.ai.bean.DDZCards.CARD;
import com.sky.poke.ai.searchhint.CSearchHint;
import com.sky.poke.ai.searchhint.CSearchHintImpl;

/**
 * Function: 牌型分析实现类
 * 
 * ClassName:CAIAnalyzerImpl Date: 2013-6-18 下午06:09:43
 * 
 * @author chshyin
 * @version
 * @since JDK 1.6 Copyright (c) 2013, palm-commerce All Rights Reserved.
 */
public class CAIAnalyzerImpl implements CAIAnalyzer {

	private static Logger log = LoggerFactory.getLogger("AIanalyzer");
	// 各种牌型
	private CardsPattern pattern[];
	// 当前的模式
	private int patternType;
	// 用于记录原始的牌
	private byte[] originalCards;
	// 用于记录当前正在处理的牌
	private byte[] currentCards;
	// 用于记录没有炸弹的牌
	private byte[] noBombCards;
	// 用于记录最后一手出的牌
	private byte[] lastOutCards;
	// 记录其它玩家剩余的牌
	private byte[] otherPokers;
	
	
	public void CalculatePatternCount() {

		pattern[patternType].basicPatternNum = 0;
		pattern[patternType].calcuPatternNum = 0;
		int i = 0;
		if ( pattern[patternType].rocket != 0 )
		{
			pattern[patternType].basicPatternNum++;
			pattern[patternType].calcuPatternNum++;
		}
		//bomb
		for ( i = 0; i < 5; i++ )
		{
			if ( pattern[patternType].bomb[i] == 0 )
			{
				continue;
			}
			else
			{
				pattern[patternType].basicPatternNum++;
				pattern[patternType].calcuPatternNum++;
			}
		}
		//singlestraight
		for ( i = 0; i < 4; i++ )
		{
			if ( pattern[patternType].singleStraight[i][0] == 0 )
			{
				continue;
			}
			else
			{
				pattern[patternType].basicPatternNum++;
				pattern[patternType].calcuPatternNum++;
			}
		}
		//double straight
		for ( i = 0; i < 3; i++ )
		{
			if ( pattern[patternType].doubleStraight[i][0] == 0 )
			{
				continue;
			}
			else
			{
				pattern[patternType].basicPatternNum++;
				pattern[patternType].calcuPatternNum++;
			}
		}
		TRACE("CalculatePatternCount basicPatternNum = %d, calcuPatternNum = %d\n",false, pattern[patternType].basicPatternNum, pattern[patternType].calcuPatternNum);
		// single card
		byte singleCardNum = 0;
		for ( i = 0; i < 20; i++ )
		{
			if ( pattern[patternType].singleCard[i] == 0 )
			{
				continue;
			}
			else
			{
				pattern[patternType].basicPatternNum++;
				singleCardNum++;
			}
		}
		// double card
		byte doubleCardNum = 0;
		for ( i = 0; i < 10; i++ )
		{
			if ( pattern[patternType].doubleCard[i][0] == 0 )
			{
				break;
			}
			else
			{
				pattern[patternType].basicPatternNum++;
				doubleCardNum++;
			}
		}
		// triple straight
		for ( i = 0; i < 3; i++ )
		{
			if ( pattern[patternType].tripleStraight[i][0] == 0 )
			{
				break;
			}
			else
			{
				pattern[patternType].basicPatternNum++;
				pattern[patternType].calcuPatternNum++;
				byte pokerNum = 0;
				for ( int j = 0; j < 18; j++ )
				{
					if ( pattern[patternType].tripleStraight[i][j] == 0 )
					{
						break;
					}
					else
					{
						pokerNum++;
					}
				}
				if ( singleCardNum >= pokerNum/3 )
				{
					singleCardNum -= pokerNum/3;
				}
				else if ( doubleCardNum >= pokerNum/3 )
				{
					doubleCardNum -= pokerNum/3;
				}
				else if ( ( doubleCardNum >= pokerNum/6 )&&( pokerNum%6 == 0 ) )
				{
					doubleCardNum -= pokerNum/6;
				}
				else if ( ( doubleCardNum >= pokerNum/6 )&&( pokerNum%6 == 3 ) )
				{
					if ( singleCardNum >= 1 )
					{
						doubleCardNum -= pokerNum/6;
						singleCardNum -= 1;
					}
					else if ( doubleCardNum > pokerNum/6 )
					{
						doubleCardNum -= pokerNum/6;
					}
				}
			}
		}
		TRACE("CalculatePatternCount basicPatternNum = %d, calcuPatternNum = %d\n",false, pattern[patternType].basicPatternNum, pattern[patternType].calcuPatternNum);
		// triple card
		for ( i = 0; i < 6; i++ )
		{
			if ( pattern[patternType].tripleCard[i][0] == 0 )
			{
				break;
			}
			else
			{
				pattern[patternType].basicPatternNum++;
				pattern[patternType].calcuPatternNum++;
				if ( singleCardNum > 0 )
				{
					singleCardNum--;
				}
				else 
				{
					if ( doubleCardNum > 0 )
					{
						doubleCardNum--;
					}
				}
			}
		}
		TRACE("CalculatePatternCount basicPatternNum = %d, calcuPatternNum = %d\n",false, pattern[patternType].basicPatternNum, pattern[patternType].calcuPatternNum);
		pattern[patternType].calcuPatternNum += (singleCardNum+doubleCardNum);
		//s;TRACE("patternType = %d, CalculatePatternCount basicPatternNum = %d, calcuPatternNum = %d\n", patternType, pattern[patternType].basicPatternNum, pattern[patternType].calcuPatternNum);

	}

	
	public byte getMinimumCalPatternIndex() {

		TRACE("GetMinimumCalPatternIndex\n");
		byte mininumCount = 20;
		byte result = 0;
		for ( int i = 0; i < 6; i++ )
		{
			if ( pattern[i].calcuPatternNum < mininumCount )
			{
				
				mininumCount = pattern[i].calcuPatternNum;
				TRACE("%d\n",false,mininumCount);
				result = mininumCount;
			}
		}
		return result;
	}

	
	public byte[] selectFirstOutPoker(byte[] pokers, int length) {

		byte[] tmpBuf = new byte[PLAYERCARDS_MAXNUM];
		MEMSET(tmpBuf,0,PLAYERCARDS_MAXNUM);
		processingFirstOutPoker(tmpBuf, length);
		
		return tmpBuf;

	}
	
	/**
	 * 处理主动出牌
	 * function: 
	 *   
	 * @param pokers 手牌  最终将需要出的牌放到pokers中
	 * @param length 牌长度
	 * @since JDK 1.6
	 */
	private void processingFirstOutPoker(byte[] pokers, int length){
		byte type = 0;
		byte[] exInfo = new byte[6];
		MEMCPY(exInfo,pattern[type].extandInfo,6);
		
		int i;
		for ( i = 0; i < 6; i++ )
		{
			if ( pattern[i].calcuPatternNum < pattern[type].calcuPatternNum )
			{
				type = (byte) i;
			}
		}
		
		if ( pattern[type].calcuPatternNum == 1 )				// 1 pattern , out directly
		{
			TRACE("first out special situation 1\n");
			MEMCPY( pokers, originalCards, PLAYERCARDS_MAXNUM ); 
			return;
		}
		
		DDZCards[] myPokers = null;
		// 新增天牌的处理
		if(otherPokers != null){
			
			myPokers = getDDZCardsArray();
		}
		
		if ( pattern[type].calcuPatternNum == 2 )			// 2 pattern, ...
		{
			// 新增对于天牌的判断，两手牌，有一手天牌，出天牌
			if(myPokers != null){
				// 如果有天牌，则出天牌
				for(int x=0;x<myPokers.length;x++){
					
					byte[] bigestInfo = BigestCardUtil.isBigestPoker(myPokers[x].getCardsNumber(), otherPokers, exInfo);
					// 是天牌
					if(bigestInfo[0] == 1){
						
						MEMCPY(pokers, myPokers[x].getCardsNumber(),myPokers[x].CurrentLength);
						
						TRACE("first out 2 bigest card situation 2\n");
						return;
					}
				}
			}
			
			if ( pattern[type].rocket != 0 )
			{
				MEMSET(pokers,0,20);
				pokers[0] = 53;
				pokers[1] = 54;
				TRACE("first out special situation 2\n");
				return;
			
			} else{
				// 有炸弹，并且炸弹不是天牌
				if ( pattern[type].bomb[0] != 0 )
				{	// 直接出四带二，最保险
					if ( pattern[type].doubleCard[0][0] != 0 )
					{
						MEMSET(pokers,0,20);
						pokers[0] = pattern[type].bomb[0];
						pokers[1] = (byte) (pattern[type].bomb[0]+13);
						pokers[2] = (byte) (pattern[type].bomb[0]+13*2);
						pokers[3] = (byte) (pattern[type].bomb[0]+13*3);
						pokers[4] = pattern[type].doubleCard[0][0];
						pokers[5] = pattern[type].doubleCard[0][1];
						TRACE("first out special situation 3\n");
						return;
					}
					// 炸弹和另一手牌
					else
					{	// 天牌判断，至此没有天牌，这是特殊情况
						if(myPokers != null){
							
							DDZCards unBombCard = myPokers[0];
							
							for(int x=0;x<myPokers.length;x++){
								
								if(myPokers[x].nType != CSearchHint.CARD_TYPE_4_BOMB){
									
									unBombCard = myPokers[x];
									break;
								}
							}
							// 非特殊情况，即另一手牌不是单张或者对子，出另一手牌
							if(unBombCard.CurrentLength > 2){
								
								MEMCPY(pokers, unBombCard.getCardsNumber(),unBombCard.CurrentLength);
								TRACE("first out un bomb card situation 2\n");
								return;
							}
							// 非特殊情况，另一手牌是单张或者对子，敌方牌数不相同
							if(unBombCard.CurrentLength <= 2){
																
								if(exInfo[0] == 0){
									
									int enemyIndex = exInfo[2] == 1 ? 2 : 4;
									
									if(exInfo[enemyIndex + 1] != unBombCard.CurrentLength){
										
										MEMCPY(pokers, unBombCard.getCardsNumber(),unBombCard.CurrentLength);
										TRACE("first out un bomb card situation 3\n");
										return;
									}
								}else{
									
									if(exInfo[3] != unBombCard.CurrentLength && exInfo[5] != unBombCard.CurrentLength){
										
										MEMCPY(pokers, unBombCard.getCardsNumber(),unBombCard.CurrentLength);
										TRACE("first out un bomb card situation 4\n");
										return;
									}
								}
							}
						}
						
						MEMSET(pokers,0,20);
						pokers[0] = pattern[type].bomb[0];
						pokers[1] = (byte) (pattern[type].bomb[0]+13);
						pokers[2] = (byte) (pattern[type].bomb[0]+13*2);
						pokers[3] = (byte) (pattern[type].bomb[0]+13*3);
						TRACE("first out special situation 4\n");
						return;
					}
				}
			}
			
		}
		TRACE(">>>>>>>>>>>>>>>>>>>>extandInfo %d %d %d %d %d %d\n",false,pattern[type].extandInfo[0],pattern[type].extandInfo[1],pattern[type].extandInfo[2],pattern[type].extandInfo[3],pattern[type].extandInfo[4],pattern[type].extandInfo[5]);
		if (pattern[type].extandInfo[0] == 0){ //	i am farmer
		
			byte smallestNum = 55;		
			if (pattern[type].extandInfo[2] == 0)
			{
				if ( pattern[type].extandInfo[3] == 1 )
				{
					smallestNum = getSmallestNumInBytes( /*pokers*/noBombCards, 20 );
					if ( smallestNum < 55 )
					{
						MEMSET( pokers, 0, 20 );
						pokers[0] = smallestNum;
						TRACE("next also farmer ,and give one card\n");
						return;
					}				
				}else if ( pattern[type].extandInfo[3] == 2 ){		
					
					byte[] doubleCardss = new byte[2];
					smallestNum = getSmallestNumInBytes( noBombCards, 20, doubleCardss, 2 );
					if ( smallestNum < 55 )
					{
						MEMSET( pokers, 0, 20 );
						MEMCPY( pokers, doubleCardss, 2);
						TRACE("next also farmer ,and give two card\n");
						return;
					}
				}
				
			}else if (pattern[type].extandInfo[2] == 1){
				
				if ( pattern[type].extandInfo[3] == 1 )
				{
					processingFirstOutLastOne(pokers, length, type);
					return;
				}
				else if ( pattern[type].extandInfo[3] == 2 )
				{								
					processingFirstOutLastTwo(pokers, length, type);
					return;
				}
				else if (pattern[type].extandInfo[4] == 0)
				{
					if ( pattern[type].extandInfo[5] == 1 )
					{
						smallestNum = getSmallestNumInBytes( noBombCards, 20 );
						if ( smallestNum < 55 )
						{
							MEMSET( pokers, 0, 20 );
							pokers[0] = smallestNum;
							TRACE("next also farmer ,and give one card\n");
							return;
						}
					}
					else if ( pattern[type].extandInfo[5] == 2 )
					{									
						byte[] doubleCardss = new byte[2];
						smallestNum = getSmallestNumInBytes( noBombCards, 20, doubleCardss, 2 );
						if ( smallestNum < 55 )
						{
							MEMSET( pokers, 0, 20 );
							MEMCPY( pokers, doubleCardss, 2);
							TRACE("next also farmer ,and give two card\n");
							return;
						}
					}				
				}						
			}
		}
		if (pattern[type].extandInfo[0] == 1){ //	i am lord
		
			if ( pattern[type].extandInfo[3] == 1 )
			{
				processingFirstOutLastOne(pokers, length, type);
				return;
			}
			else if ( pattern[type].extandInfo[3] == 2 )
			{				
				processingFirstOutLastTwo(pokers, length, type);
				return;
			}
			else if ( pattern[type].extandInfo[5] == 1 )
			{
				processingFirstOutLastOne(pokers, length, type);
				return;
			}
			else if ( pattern[type].extandInfo[5] == 2 )
			{				
				processingFirstOutLastTwo(pokers, length, type);
				return;
			}
		}
		if ( pattern[type].calcuPatternNum == 2 )			// 2 pattern, ...
		{
			if ( ( pattern[type].doubleStraight[0][0] != 0 )||( pattern[type].singleStraight[0][0] != 0 )||( pattern[type].tripleStraight[0][0] != 0 )||( pattern[type].tripleCard[0][0] != 0 )||( pattern[type].doubleCard[0][0] != 0 ) )
			{
				byte smallestNum1 = 55;
				if ( pattern[type].singleStraight[0][0] != 0 )
				{
					if ( getSmallestNumInBytes( pattern[type].singleStraight[0], 12 ) < smallestNum1 )
					{
						smallestNum1 = getSmallestNumInBytes( pattern[type].singleStraight[0], 12 );
						MEMSET( pokers, 0, 20 );
						MEMCPY( pokers, pattern[type].singleStraight[0], 12 );
					}
					if ( pattern[type].singleStraight[1][0] != 0 )
					{
						if ( getSmallestNumInBytes( pattern[type].singleStraight[1], 12 ) < smallestNum1 )
						{
							smallestNum1 = getSmallestNumInBytes( pattern[type].singleStraight[0], 12 );
							MEMSET( pokers, 0, 20 );
							MEMCPY( pokers, pattern[type].singleStraight[1], 12 );
						}
					}
				}
				else if ( pattern[type].doubleStraight[0][0] != 0 )
				{
					if ( getSmallestNumInBytes( pattern[type].doubleStraight[0], 20 ) < smallestNum1 )
					{
						smallestNum1 = getSmallestNumInBytes( pattern[type].doubleStraight[0], 20 );
						MEMSET( pokers, 0, 20 );
						//TODO 有修改
						MEMCPY( pokers, pattern[type].doubleStraight[0],0, 20 );
					}
					if ( pattern[type].doubleStraight[1][0] != 0 )
					{
						if ( getSmallestNumInBytes( pattern[type].doubleStraight[1], 20 ) < smallestNum1 )
						{
							smallestNum1 = getSmallestNumInBytes( pattern[type].doubleStraight[0], 20 );
							MEMSET( pokers, 0, 20 );
							//TODO 有修改
							MEMCPY( pokers, pattern[type].doubleStraight[1],0, 20 );
						}
					}
				}
				else if ( pattern[type].tripleStraight[0][0] != 0 )
				{
					//check if XXX XXX OO, out poker directly
					if ( ( pattern[type].tripleStraight[0][6] == 0 )&&( pattern[type].doubleCard[0][0] != 0 ) )
					{
						TRACE("first out special situation 1--\n");
						ProcessingFirstOutLastTwoOnlyTripleStraightOrTripleCard(pokers, length, type);
						//MEMCPY( pokers, originalCards, PLAYERCARDS_MAXNUM ); 
						return;
					}
						
					// triplestraight
					byte singleCardIndex = 20;
					byte doubleCardIndex = 10;
					ShapeData shapeData = new ShapeData(smallestNum1,singleCardIndex,doubleCardIndex);
					SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLESTRAIGHT, true, shapeData );
					smallestNum1 = shapeData.getMinimun();
				}
				else if ( pattern[type].tripleCard[0][0] != 0 )
				{
					// triplecard
					byte singleCardIndex = 20;
					byte doubleCardIndex = 10;
					ShapeData shapeData = new ShapeData(smallestNum1,singleCardIndex,doubleCardIndex);
					SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLECARD, true, shapeData );
					smallestNum1 = shapeData.getMinimun();
				}
				else if ( pattern[type].doubleCard[0][0] != 0 )
				{
					byte[] doubleCardss = new byte[2];
					smallestNum1 = getSmallestNumInBytes( noBombCards, 20, doubleCardss, 2 );
					if ( smallestNum1 < 55 )
					{
						MEMSET( pokers, 0, 20 );
						MEMCPY( pokers, doubleCardss, 2);
						TRACE("next also farmer ,and give two card\n");
						return;
					}
					
//					smallestNum = GetSmallestNumInBytes( &pattern[type].doubleCard[0][0], 2 );
//					MEMSET( pokers, 0, 20 );
//					MEMCPY( pokers, pattern[type].doubleCard[0], 2 );
				}
				if ( smallestNum1 < 55 )
				{
					TRACE("first out special situation 8\n");
					return;
				}
			}
			else if (((pattern[type].extandInfo[0] == 0)&&(((pattern[type].extandInfo[2] == 1)&&(pattern[type].extandInfo[3] < 4))||((pattern[type].extandInfo[4] == 1)&&(pattern[type].extandInfo[5] < 4))) )
					 ||((pattern[type].extandInfo[0] == 1)&&(((pattern[type].extandInfo[2] == 0)&&(pattern[type].extandInfo[3] < 4))||((pattern[type].extandInfo[4] == 0)&&(pattern[type].extandInfo[5] < 4)))))
			{
				ProcessingFirstOutBigger(pokers, length, type);
				TRACE("first out special situation 9\n");
				return;
			}
		}
		if (((pattern[type].extandInfo[0] == 0)&&(((pattern[type].extandInfo[2] == 1)&&(pattern[type].extandInfo[3] <= 1))||((pattern[type].extandInfo[4] == 1)&&(pattern[type].extandInfo[5] <= 1))) )
			||((pattern[type].extandInfo[0] == 1)&&(((pattern[type].extandInfo[2] == 0)&&(pattern[type].extandInfo[3] <= 1))||((pattern[type].extandInfo[4] == 0)&&(pattern[type].extandInfo[5] <= 1)))))
		{
			processingFirstOutLastOne(pokers, length, type);
			TRACE("first out special situation 10\n");
			return;
		}
		if (((pattern[type].extandInfo[0] == 0)&&(((pattern[type].extandInfo[2] == 1)&&(pattern[type].extandInfo[3] < 3))||((pattern[type].extandInfo[4] == 1)&&(pattern[type].extandInfo[5] < 3))) )
			||((pattern[type].extandInfo[0] == 1)&&(((pattern[type].extandInfo[2] == 0)&&(pattern[type].extandInfo[3] < 3))||((pattern[type].extandInfo[4] == 0)&&(pattern[type].extandInfo[5] < 3)))))
		{
			processingFirstOutLastTwo(pokers, length, type);
			TRACE("first out special situation 11\n");
			return;
		}
		//TODO 新增全部是炸弹的情况
		if(pattern[type].bomb[0] != 0 && pattern[type].doubleCard[0][0] == 0 && pattern[type].doubleStraight[0][0] == 0
				&& pattern[type].singleCard[0] == 0 && pattern[type].singleStraight[0][0] == 0 && pattern[type].tripleCard[0][0] == 0
				&& pattern[type].tripleStraight[0][0] == 0){
			
			MEMSET(pokers,0,20);
			pokers[0] = pattern[type].bomb[0];
			pokers[1] = (byte) (pattern[type].bomb[0]+13);
			pokers[2] = (byte) (pattern[type].bomb[0]+13*2);
			pokers[3] = (byte) (pattern[type].bomb[0]+13*3);
			TRACE("first out special situation 12,is all bomb\n");
			return;
		}
		
		processingFirstOutNormal(pokers, length, type);
	}

	/**
	 * 
	 * function:主动出牌时敌方只有2张牌 
	 *   
	 * @param pokers
	 * @param length
	 * @param type   
	 * @since JDK 1.6
	 */
	private void processingFirstOutLastTwo(byte[] pokers, int length, int type){
		byte minimum1 = 55;
		byte compareN = 55;
		// single card
		byte singleCardNum = 0;
		int i = 0;
		for ( i = 0; i < 20; i++ )
		{
			if ( pattern[type].singleCard[i] == 0 )
			{
				break;
			}
			else
			{
				singleCardNum++;
			}
		}
		byte compareSNum1 = singleCardNum;
		// double card
		byte doubleCardNum = 0;
		for ( i = 0; i < 10; i++ )
		{
			if ( pattern[type].doubleCard[i][0] == 0 )
			{
				break;
			}
			else
			{
				doubleCardNum++;
			}
		}
		byte compareDNum1 = doubleCardNum;
		
		ShapeData shapeData = new ShapeData(minimum1,compareSNum1,compareDNum1);
		// triplestraight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLESTRAIGHT, true, shapeData );
		// singlestraight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.SINGLESTRAIGHT, true, shapeData );
		//double straight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.DOUBLESTRAIGHT, true, shapeData );
		//triple card
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLECARD, true, shapeData );
		
		if ( shapeData.getMinimun() == 55 )
		{
			//CRandGen randGen;
			//int randSeek = randGen.GetRand( 3 );
			// 随机猜是单张还是对子
			int randSeek = new Random().nextBoolean() ? 1 : 0;
			//TRACE("randSeek = %d\n", randSeek);
			if ( pattern[type].singleCard[0] > 52 ) // if have joker ,need to out single card
			{
				randSeek = 1;
			}
			if ( randSeek == 0 ) // guess left singlecard
			{
				TRACE("Guess last two is single \n");
				//double card
				shapeData.setMinimun((byte) 3);
				SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.DOUBLECARD, false, shapeData );	
				if ( shapeData.getMinimun() == 3 )
				{
					TRACE("choose not single fail ,get largest single\n");
					shapeData.setMinimun((byte) 55);
					SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.SINGLECARD, true, shapeData );					
				}
			}
			else // guess left doublecard
			{
				TRACE("Guess last two is double \n");
				SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.SINGLECARD, true, shapeData );	
				if ( shapeData.getMinimun() == 55 )
				{
					TRACE("choose single fail ,get smallest double\n");
					//double card
					SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.DOUBLECARD, true, shapeData );	
					//divided the doublecard
					if ( pokers[1] != 0 )
					{
						pokers[1] = 0;
					}
				}
			}
		}
	} 
	
	
	
	/**
	 * 
	 * function: 主动出牌时敌方只有一张牌 
	 *      
	 * @since JDK 1.6
	 */
	private void processingFirstOutLastOne(byte[] pokers, int length, int type)
	{
		byte minimum1 = 55;
		// single card
		byte singleCardNum = 0;
		int i = 0;
		for ( i = 0; i < 20; i++ )
		{
			if ( pattern[type].singleCard[i] == 0 )
			{
				break;
			}
			else
			{
				singleCardNum++;
			}
		}
		byte compareSNum1 = singleCardNum;
		// double card
		byte doubleCardNum = 0;
		
		for ( i = 0; i < 10; i++ )
		{
			if ( pattern[type].doubleCard[i][0] == 0 )
			{
				break;
			}
			else
			{
				doubleCardNum++;
			}
		}
		byte compareDNum1 = doubleCardNum;
		
		ShapeData shapeData = new ShapeData(minimum1,compareSNum1,compareDNum1);
		// triplestraight
		// TODO 可以做特殊处理，如果能带对子和个子
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLESTRAIGHT, true, shapeData ,true);
		// singlestraight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.SINGLESTRAIGHT, true, shapeData );
		//double straight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.DOUBLESTRAIGHT, true, shapeData );
		//triple card
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLECARD, true, shapeData );
		//double card
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.DOUBLECARD, true, shapeData );	
		//single card
		if ( shapeData.getMinimun() == 55 )
		{
			shapeData.setMinimun((byte) 3);
			SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.SINGLECARD, false, shapeData );	
		}
	}
	
	/**
	 * 一般情况下的主动出牌
	 * function: 
	 *   
	 * @param pokers
	 * @param length
	 * @param type   
	 * @since JDK 1.6
	 */
	private void processingFirstOutNormal(byte[] pokers, int length, int type){
		byte minimum1 = 55;
		byte compareN = 55;
		// single card
		byte singleCardNum = 0;
		int i = 0;
		for ( i = 0; i < 20; i++ )
		{
			if ( pattern[type].singleCard[i] == 0 )
			{
				break;
			}
			else
			{
				singleCardNum++;
			}
		}
		byte compareSNum1 = singleCardNum;
		// double card
		byte doubleCardNum = 0;
		
		for ( i = 0; i < 10; i++ )
		{
			if ( pattern[type].doubleCard[i][0] == 0 )
			{
				break;
			}
			else
			{
				doubleCardNum++;
			}
		}
		byte compareDNum1 = doubleCardNum;
		
		ShapeData shapeData = new ShapeData(minimum1,compareSNum1,compareDNum1);
		// triplestraight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLESTRAIGHT, true, shapeData );
		// singlestraight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.SINGLESTRAIGHT, true, shapeData );
		//double straight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.DOUBLESTRAIGHT, true, shapeData );
		//triple card
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLECARD, true, shapeData );
		//double card
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.DOUBLECARD, true, shapeData );	
		//single card
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.SINGLECARD, true, shapeData );	
		return;
		
	}
	
	/**
	 * 出最大的牌
	 * function: 
	 *   
	 * @param pokers
	 * @param length
	 * @param type   
	 * @since JDK 1.6
	 */
	private void ProcessingFirstOutBigger(byte[] pokers, int length, int type){
		byte singleCardIndex = 20;
		byte doubleCardIndex = 10;
		ShapeData shapeData = new ShapeData((byte)55,singleCardIndex,doubleCardIndex);
		// triplestraight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLESTRAIGHT, true, shapeData );
		// singlestraight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.SINGLESTRAIGHT, true, shapeData );
		//double straight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.DOUBLESTRAIGHT, true, shapeData );
		//triple card
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLECARD, true, shapeData );
		//double card
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.DOUBLECARD, true, shapeData );	
		//single card
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.SINGLECARD, true, shapeData );	
	}
	
	private void ProcessingFirstOutLastTwoOnlyTripleStraightOrTripleCard(byte[] pokers, int length, int type)
	{
		byte minimum = 55;
		byte compareN = 55;
		// single card
		byte singleCardNum = 0;
		int i = 0;
		for ( i = 0; i < 20; i++ )
		{
			if ( pattern[type].singleCard[i] == 0 )
			{
				break;
			}
			else
			{
				singleCardNum++;
			}
		}
		byte compareSNum = singleCardNum;
		// double card
		byte doubleCardNum = 0;
		for ( i = 0; i < 10; i++ )
		{
			if ( pattern[type].doubleCard[i][0] == 0 )
			{
				break;
			}
			else
			{
				doubleCardNum++;
			}
		}
		byte compareDNum = doubleCardNum;
		
		ShapeData shapeData = new ShapeData(minimum,compareSNum,compareDNum);
		// triplestraight
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLESTRAIGHT, true, shapeData );
		//triple card
		SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.TRIPLECARD, true, shapeData );
	}
	
	
	/**
	 * 跟牌 如果要跟牌，放入pokers中
	 */
	public boolean selectFollowOutPoker(byte[] pokers, int length) {

		byte[] tmpBuf = new byte[PLAYERCARDS_MAXNUM];
		MEMSET(tmpBuf,0,PLAYERCARDS_MAXNUM);
		ProcessingFollowOutPoker(tmpBuf, length);
		//TODO test
		boolean result = false;
		
		for ( int i = 0; i <  tmpBuf.length; i++)
		{
			if ( tmpBuf[i] == 0 ){
				
				break;
			} else{
				
				result = true;
			}
		}
		return result;
		
	}
	
	private void ProcessingFollowOutPoker(byte[] pokers, int length){
		
		byte type = 0;
		byte[] exInfo = new byte[6];
		MEMCPY(exInfo,pattern[type].extandInfo,6);
		
		int i;
		for ( i = 0; i < 6; i++ )
		{
			if ( pattern[i].calcuPatternNum < pattern[type].calcuPatternNum )
			{
				type = (byte) i;
			}
		}
		
		ProcessingFollowOutNormal(pokers, length, type);
	}
		
	
	
	private void ProcessingFollowOutNormal(byte[] pokers, int length, int type){
		
		byte minimum = 55;
		byte compareN = 55;
		// single card
		byte singleCardNum = 0;
		int i = 0;
		for ( i = 0; i < 20; i++ )
		{
			if ( pattern[type].singleCard[i] == 0 )
			{
				break;
			}
			else
			{
				singleCardNum++;
			}
		}
		byte compareSNum = singleCardNum;
		// double card
		byte doubleCardNum = 0;
		
		for ( i = 0; i < 10; i++ )
		{
			if ( pattern[type].doubleCard[i][0] == 0 )
			{
				break;
			}
			else
			{
				doubleCardNum++;
			}
		}
		byte compareDNum = doubleCardNum;
		// triplestraight
		//SetOneShapeBySmallestOrLargest( pokers, length, type, TRIPLESTRAIGHT, true, minimum, compareSNum, compareDNum );
		// singlestraight
		//SetOneShapeBySmallestOrLargest( pokers, length, type, SINGLESTRAIGHT, true, minimum, compareSNum, compareDNum );
		//double straight
		//SetOneShapeBySmallestOrLargest( pokers, length, type, DOUBLESTRAIGHT, true, minimum, compareSNum, compareDNum );
		//triple card
		//SetOneShapeBySmallestOrLargest( pokers, length, type, TRIPLECARD, true, minimum, compareSNum, compareDNum );
		
		ShapeData shapeData = new ShapeData(minimum,compareSNum,compareDNum);
		
		if ( lastOutCards[1] != 0 ){
			//double card
			SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.DOUBLECARD, true, shapeData, lastOutCards[0]);	
		}else{
			//single card
			SetOneShapeBySmallestOrLargest( pokers, length, type, TShape.SINGLECARD, true, shapeData, lastOutCards[0]);	
		}
	}

	
	public void setCardsPattern(CAIPoker[] pokers, int length, byte[] eInfo,
			int eILen) {
		// 用于存放牌的数值
		byte[] tmpBuf = new byte[PLAYERCARDS_MAXNUM];

		int index = 0;

		for (int i = 0; i < length && i < PLAYERCARDS_MAXNUM; i++) {
			if ((pokers[i].getPokerValue() > 0)
					&& (pokers[i].getPokerValue() < 55)) {
				if (pokers[i].isOut() == false) {
					tmpBuf[index] = pokers[i].getPokerValue();
					index++;
				}
			}
		}

		setCardsPattern(tmpBuf, PLAYERCARDS_MAXNUM, eInfo, eILen);
	}

	
	public void setCardsPattern(byte[] pokers, int length, byte[] eInfo, int eLen) {

		pattern = new CardsPattern[PATTERN_NUM1];
		
		originalCards = new byte[PLAYERCARDS_MAXNUM];
		// copy pokers内容
		MEMCPY(originalCards, pokers, length);
		// 存储pattern的附加信息
		for (int i = 0; i < PATTERN_NUM1; i++) {
			
			pattern[i] = new CardsPattern();
			pattern[i].extandInfo = new byte[6];
			// copy 玩家类型和牌数信息
			MEMCPY(pattern[i].extandInfo, eInfo, eLen);
		}
		// 检查是否排序，进行排序
		if(!checkIsInOrder()){
			
			TRACE("sort");
			sort(true);
		}
		currentCards = new byte[PLAYERCARDS_MAXNUM];
		
		MEMCPY(currentCards, originalCards, PLAYERCARDS_MAXNUM);
		// 找出牌中的火箭，并在模式中设置有火箭牌
		if ( getRocket(currentCards, PLAYERCARDS_MAXNUM))
		{
			pattern[0].rocket = 1;	
			pattern[1].rocket = 1;	
			pattern[2].rocket = 1;	
			pattern[3].rocket = 1;	
			pattern[4].rocket = 1;	
			pattern[5].rocket = 1;	
		}
		// 找出牌中的炸弹，并在模式中设置炸弹牌
		TRACE("after get rocket\n");
		
		while( getBomb(currentCards, PLAYERCARDS_MAXNUM) );
		// 记录没有炸弹的牌
		noBombCards = new byte[PLAYERCARDS_MAXNUM];
		
		MEMCPY(noBombCards,currentCards,PLAYERCARDS_MAXNUM);
	
		int patternNum = PATTERN_NUM1;
		for ( int j = 0; j < patternNum; j++ )
		{
			MEMCPY(currentCards,noBombCards,PLAYERCARDS_MAXNUM);
			setOnePattern(j);
		}
	}
	
	/**
	 * 对一种模式进行牌分析
	 * function: 
	 *   
	 * @param j   
	 * @since JDK 1.6
	 */
	private void setOnePattern(int type) {
		  
		patternType = type;
		switch (type) {
			case 0:
				TRACE("type = 3 2 1\n");
				TRACE("OnePattern = %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d\n",
					  currentCards[0],currentCards[1],currentCards[2],currentCards[3],currentCards[4],
					  currentCards[5],currentCards[6],currentCards[7],currentCards[8],currentCards[9],
					  currentCards[10],currentCards[11],currentCards[12],currentCards[13],currentCards[14],
					  currentCards[15],currentCards[16],currentCards[17],currentCards[18],currentCards[19]);
				
				tripleFetching(type);//查找三顺
				doubleFetching(type);//查找双顺
				singleFetching(type);//查找单顺
				while ( getTripleCard(currentCards, 20) );
				TRACE("getTripleCard = ");
				for ( int i = 0; i < 6; i++ )
				{
					if ( pattern[type].tripleCard[i][0] != 0 )
						TRACE("%d %d %d    ",pattern[type].tripleCard[i][0],pattern[type].tripleCard[i][1],pattern[type].tripleCard[i][2]);
				}
				TRACE("\n");
				while ( getDoubleCard(currentCards, 20) );
				TRACE("getDoubleCard = ");
				for ( int i = 0; i < 10; i++ )
				{
					if ( pattern[type].doubleCard[i][0] != 0 )
						TRACE("%d %d    ",pattern[type].doubleCard[i][0],pattern[type].doubleCard[i][1]);
				}
				TRACE("\n");
				while ( getSingleCard(currentCards, 20) );
				for ( int i = 0; i < 20; i++ )
				{
					if ( pattern[type].singleCard[i] != 0 )
						TRACE("getSingleCard = %d \n",pattern[type].singleCard[i]);
				}
				CalculatePatternCount();
				TRACE("basic count = %d, calculate count = %d \n",false,pattern[type].basicPatternNum,pattern[type].calcuPatternNum);
				break;
			case 1:
				TRACE("type = 3 1 2\n");
				TRACE("OnePattern = %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d\n",
					  currentCards[0],currentCards[1],currentCards[2],currentCards[3],currentCards[4],
					  currentCards[5],currentCards[6],currentCards[7],currentCards[8],currentCards[9],
					  currentCards[10],currentCards[11],currentCards[12],currentCards[13],currentCards[14],
					  currentCards[15],currentCards[16],currentCards[17],currentCards[18],currentCards[19]);
				
				tripleFetching(type);//查找三顺
				singleFetching(type);//查找单顺
				doubleFetching(type);//查找双顺
				while ( getTripleCard(currentCards, 20) );
				TRACE("getTripleCard = ");
				for ( int i = 0; i < 6; i++ )
				{
					if ( pattern[type].tripleCard[i][0] != 0 )
						TRACE("%d %d %d    ",pattern[type].tripleCard[i][0],pattern[type].tripleCard[i][1],pattern[type].tripleCard[i][2]);
				}
				TRACE("\n");
				while ( getDoubleCard(currentCards, 20) );
				TRACE("getDoubleCard = ");
				for ( int i = 0; i < 10; i++ )
				{
					if ( pattern[type].doubleCard[i][0] != 0 )
						TRACE("%d %d    ",pattern[type].doubleCard[i][0],pattern[type].doubleCard[i][1]);
				}
				TRACE("\n");
				while ( getSingleCard(currentCards, 20) );
				for ( int i = 0; i < 20; i++ )
				{
					if ( pattern[type].singleCard[i] != 0 )
						TRACE("getSingleCard = %d \n",pattern[type].singleCard[i]);
				}
				CalculatePatternCount();
				TRACE("basic count = %d, calculate count = %d \n",false,pattern[type].basicPatternNum,pattern[type].calcuPatternNum);
				break;
			case 2:
				TRACE("type = 2 3 1\n");
				TRACE("OnePattern = %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d\n",
					  currentCards[0],currentCards[1],currentCards[2],currentCards[3],currentCards[4],
					  currentCards[5],currentCards[6],currentCards[7],currentCards[8],currentCards[9],
					  currentCards[10],currentCards[11],currentCards[12],currentCards[13],currentCards[14],
					  currentCards[15],currentCards[16],currentCards[17],currentCards[18],currentCards[19]);
				
				doubleFetching(type);//查找双顺
				tripleFetching(type);//查找三顺
				singleFetching(type);//查找单顺
				while ( getTripleCard(currentCards, 20) );
				TRACE("getTripleCard = ");
				for ( int i = 0; i < 6; i++ )
				{
					if ( pattern[type].tripleCard[i][0] != 0 )
						TRACE("%d %d %d    ",pattern[type].tripleCard[i][0],pattern[type].tripleCard[i][1],pattern[type].tripleCard[i][2]);
				}
				TRACE("\n");
				while ( getDoubleCard(currentCards, 20) );
				TRACE("getDoubleCard = ");
				for ( int i = 0; i < 10; i++ )
				{
					if ( pattern[type].doubleCard[i][0] != 0 )
						TRACE("%d %d    ",pattern[type].doubleCard[i][0],pattern[type].doubleCard[i][1]);
				}
				TRACE("\n");
				while ( getSingleCard(currentCards, 20) );
				for ( int i = 0; i < 20; i++ )
				{
					if ( pattern[type].singleCard[i] != 0 )
						TRACE("getSingleCard = %d \n",pattern[type].singleCard[i]);
				}
				CalculatePatternCount();
				TRACE("basic count = %d, calculate count = %d \n",false,pattern[type].basicPatternNum,pattern[type].calcuPatternNum);
				break;
			case 3:
				TRACE("type = 2 1 3\n");
				TRACE("OnePattern = %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d\n",
					  currentCards[0],currentCards[1],currentCards[2],currentCards[3],currentCards[4],
					  currentCards[5],currentCards[6],currentCards[7],currentCards[8],currentCards[9],
					  currentCards[10],currentCards[11],currentCards[12],currentCards[13],currentCards[14],
					  currentCards[15],currentCards[16],currentCards[17],currentCards[18],currentCards[19]);
				
				doubleFetching(type);//查找双顺
				singleFetching(type);//查找单顺
				tripleFetching(type);//查找三顺
				while ( getTripleCard(currentCards, 20) );
				TRACE("getTripleCard = ");
				for ( int i = 0; i < 6; i++ )
				{
					if ( pattern[type].tripleCard[i][0] != 0 )
						TRACE("%d %d %d    ",pattern[type].tripleCard[i][0],pattern[type].tripleCard[i][1],pattern[type].tripleCard[i][2]);
				}
				TRACE("\n");
				while ( getDoubleCard(currentCards, 20) );
				TRACE("getDoubleCard = ");
				for ( int i = 0; i < 10; i++ )
				{
					if ( pattern[type].doubleCard[i][0] != 0 )
						TRACE("%d %d    ",pattern[type].doubleCard[i][0],pattern[type].doubleCard[i][1]);
				}
				TRACE("\n");
				while ( getSingleCard(currentCards, 20) );
				for ( int i = 0; i < 20; i++ )
				{
					if ( pattern[type].singleCard[i] != 0 )
						TRACE("getSingleCard = %d \n",pattern[type].singleCard[i]);
				}
				CalculatePatternCount();
				TRACE("basic count = %d, calculate count = %d \n",false,pattern[type].basicPatternNum,pattern[type].calcuPatternNum);
				break;
			case 4:
				TRACE("type = 1 2 3\n");
				TRACE("OnePattern = %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d\n",
					  currentCards[0],currentCards[1],currentCards[2],currentCards[3],currentCards[4],
					  currentCards[5],currentCards[6],currentCards[7],currentCards[8],currentCards[9],
					  currentCards[10],currentCards[11],currentCards[12],currentCards[13],currentCards[14],
					  currentCards[15],currentCards[16],currentCards[17],currentCards[18],currentCards[19]);
				
				singleFetching(type);//查找单顺
				doubleFetching(type);//查找双顺
				tripleFetching(type);//查找三顺
				while ( getTripleCard(currentCards, 20) );
				TRACE("getTripleCard = ");
				for ( int i = 0; i < 6; i++ )
				{
					if ( pattern[type].tripleCard[i][0] != 0 )
						TRACE("%d %d %d    ",pattern[type].tripleCard[i][0],pattern[type].tripleCard[i][1],pattern[type].tripleCard[i][2]);
				}
				TRACE("\n");
				while ( getDoubleCard(currentCards, 20) );
				TRACE("getDoubleCard = ");
				for ( int i = 0; i < 10; i++ )
				{
					if ( pattern[type].doubleCard[i][0] != 0 )
						TRACE("%d %d    ",pattern[type].doubleCard[i][0],pattern[type].doubleCard[i][1]);
				}
				TRACE("\n");
				while ( getSingleCard(currentCards, 20) );
				for ( int i = 0; i < 20; i++ )
				{
					if ( pattern[type].singleCard[i] != 0 )
						TRACE("getSingleCard = %d \n",pattern[type].singleCard[i]);
				}
				CalculatePatternCount();
				TRACE("basic count = %d, calculate count = %d \n",false,pattern[type].basicPatternNum,pattern[type].calcuPatternNum);
				break;
			case 5:
				TRACE("type = 1 3 2\n");
				TRACE("OnePattern = %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d\n",
					  currentCards[0],currentCards[1],currentCards[2],currentCards[3],currentCards[4],
					  currentCards[5],currentCards[6],currentCards[7],currentCards[8],currentCards[9],
					  currentCards[10],currentCards[11],currentCards[12],currentCards[13],currentCards[14],
					  currentCards[15],currentCards[16],currentCards[17],currentCards[18],currentCards[19]);
				
				singleFetching(type);//查找单顺
				tripleFetching(type);//查找三顺
				doubleFetching(type);//查找双顺
				while ( getTripleCard(currentCards, 20) );
				TRACE("getTripleCard = ");
				for ( int i = 0; i < 6; i++ )
				{
					if ( pattern[type].tripleCard[i][0] != 0 )
						TRACE("%d %d %d    ",pattern[type].tripleCard[i][0],pattern[type].tripleCard[i][1],pattern[type].tripleCard[i][2]);
				}
				TRACE("\n");
				while ( getDoubleCard(currentCards, 20) );
				TRACE("getDoubleCard = ");
				for ( int i = 0; i < 10; i++ )
				{
					if ( pattern[type].doubleCard[i][0] != 0 )
						TRACE("%d %d    ",pattern[type].doubleCard[i][0],pattern[type].doubleCard[i][1]);
				}
				TRACE("\n");
				while ( getSingleCard(currentCards, 20) );
				for ( int i = 0; i < 20; i++ )
				{
					if ( pattern[type].singleCard[i] != 0 )
						TRACE("getSingleCard = %d \n",pattern[type].singleCard[i]);
				}
				CalculatePatternCount();
				TRACE("basic count = %d, calculate count = %d \n",false,pattern[type].basicPatternNum,pattern[type].calcuPatternNum);
				break;
			default:
				break;
		}  
		
	}
	
	

	// 查找牌中的火箭，并将火箭牌去掉
	private boolean getRocket(byte[] pokers, int length) {
		  
		byte rocket[] = new byte[2];
		
		int countDown = 2;
		
		for ( int i = 0; i < length&& i <PLAYERCARDS_MAXNUM; i++ )
		{
			if ( ( pokers[i] == 53 )||( pokers[i] == 54 ) )
			{
				
				countDown--;
				rocket[countDown] = (byte)i;
			}
			if ( !( countDown > 0 ) )
			{
				for ( int j = 0; j < PATTERN_NUM1; j++ )
				{
					pattern[j].rocket = 1;
				}
				pokers[rocket[0]] = 0;
				pokers[rocket[1]] = 0;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取牌中的炸弹并将炸弹排除
	 * function: 
	 *   
	 * @param pokers
	 * @param length
	 * @return   
	 * @since JDK 1.6
	 */
	private boolean getBomb(byte[] pokers,int length){
		
		
		byte bombPos[] = new byte[4];
		
		int countDown = 4;
		int compareNum = 0;	
		for (int i = 0; i < length && i < PLAYERCARDS_MAXNUM; i++ )
		{
			if ( ( pokers[i] == 0 )||( pokers[i] == 53 )||( pokers[i] == 54 ) )
			{
				//if (( i+1 < PLAYERCARDS_MAXNUM )&&( compareNum == 0 ))
				//	compareNum = pokers[i+1]%13; 
				continue;
			}
			if ( compareNum == 0 )
			{
				countDown = 4;
				compareNum = pokers[i];
				countDown--;
				bombPos[countDown] = (byte)i;
				continue;
			}
			if ( ( pokers[i] > 0 ) && ( pokers[i]%13 == compareNum%13 ) )
			{
				countDown--;
				bombPos[countDown] = (byte)i;			
			}
			else
			{
				countDown = 4;
				compareNum = pokers[i];
				countDown--;
				bombPos[countDown] = (byte)i;
			}
			if ( !( countDown > 0 ) )
			{			
				TRACE("after get bomb true\n");
				for ( int n = 0; n < PATTERN_NUM1; n++ )
				{
					for ( int j = 0 ; j < 5; j++ )
					{
						if ( pattern[n].bomb[j] == 0 )
						{
							
							pattern[n].bomb[j] = (byte) (pokers[bombPos[0]]%13==0?13:pokers[bombPos[0]]%13);
							break;
						}
					}			
					//TRACE("%d pattern %d %d %d %d\n", n, pattern[n].bomb[0], pattern[n].bomb[1], pattern[n].bomb[2], pattern[n].bomb[3]);
				}
				pokers[bombPos[0]] = 0;
				pokers[bombPos[1]] = 0;
				pokers[bombPos[2]] = 0;
				pokers[bombPos[3]] = 0;
				return true;
			}		
		}
		return false;
	}

	// 校验是否排序
	private boolean checkIsInOrder(){
		
		int i = 0;
		if ( originalCards[0] == 53 ) 
		{
			i++;
		}
		if ( originalCards[0] == 54 ) 
		{
			i++;
			if ( originalCards[1] == 53 ) 
			{
				i++;
			}
		}
		for ( ; i < PLAYERCARDS_MAXNUM-1 ; i++ )
		{
			if ( ( originalCards[i] == 53 ) || ( originalCards[i] == 54 ) )
			{
				return false;
			}
			
			if ( ( originalCards[i]+13-3 )%13 < ( originalCards[i+1]+13-3 )%13 )
			{
				return false;
			}
		}
		return true;
	}
	
	// 排序一副牌,传入是否需要改变牌
	private void sort(boolean isProperOrder){
		
		if ( isProperOrder )
		{
			/**
			 updown first ,leftright second, count the poker value
			 -----------------------
			 1    1    0    1     1   
			 ....
			 
			 1    1    0    0     0
			 -----------------------
			 */
			for ( int i = 1; i < PLAYERCARDS_MAXNUM; i++ )
			{
				for ( int j = 0; j < PLAYERCARDS_MAXNUM - i; j++ )
				{
					if ( ( originalCards[j] == 0 )||( originalCards[j+1] == 0 ) )
					{
						if ( ( originalCards[j] == 0 )&&(originalCards[j+1] != 0) )
						{
							byte tempValue = originalCards[j];
							originalCards[j] = originalCards[j+1];
							originalCards[j+1] = tempValue;
						}
					}
					else if ( ( originalCards[j+1] == 54 ) || ( originalCards[j+1] == 53 ) || ( originalCards[j] == 54 ) || ( originalCards[j] == 53 ) )
					{
						if ( ( originalCards[j+1] == 54 )||( ( originalCards[j+1] == 53 )&&( originalCards[j] != 54 ) ) )
						{
							byte tempValue = originalCards[j];
							originalCards[j] = originalCards[j+1];
							originalCards[j+1] = tempValue;
						}
					}
					else
					{
						if ( ( (originalCards[j]+13-3)%13 < (originalCards[j+1]+13-3)%13 )||( ( (originalCards[j]+13-3)%13 == (originalCards[j+1]+13-3)%13 )&&( (originalCards[j]+13-3)/13 > (originalCards[j+1]+13-3)/13 ) ) )
						{
							byte tempValue = originalCards[j];
							originalCards[j] = originalCards[j+1];
							originalCards[j+1] = tempValue;
						}
					}
					
				}
			}
			
			TRACE("Sort = %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d\n",
				  originalCards[0],originalCards[1],originalCards[2],originalCards[3],originalCards[4],
				  originalCards[5],originalCards[6],originalCards[7],originalCards[8],originalCards[9],
				  originalCards[10],originalCards[11],originalCards[12],originalCards[13],originalCards[14],
				  originalCards[15],originalCards[16],originalCards[17],originalCards[18],originalCards[19]);
		}
	}
	
	// need to be extended again
	/**
	 * 查找牌中的长度为5的单顺，并从牌中去除掉顺子，从大的往小的找
	 */
	private boolean getSingleStraight(byte[] pokers, int length){
		
		byte straight[] = new byte[5];
		
		int countDown = 0;
		byte compareNum = 0;
		for ( int i = length-1; i >= 0; i--)
		{
			if ( ( pokers[i] == 0 )||( pokers[i] == 53 )||( pokers[i] == 54 )||( pokers[i]%13 == 2 ) )
			{
				continue;
			}
			
			MEMSET(straight,0,5);
			countDown = 5;
			compareNum = (byte) (pokers[i]%13==0?13:pokers[i]%13);
			countDown--;
			straight[countDown] = (byte) i; 
			if ( ( compareNum > 10 )||( compareNum < 3 )||( i < 4 ) ) // the first poker position is larger than 10 or less than 3;
			{
				return false;
			}
			
			byte nextNum = (byte) (++compareNum%13==0?13:compareNum%13);
			
			for ( int j = i-1; j >= 0; j-- )
			{
				if  ( pokers[j] == 0 )
				{
					continue;
				}
				if ( ( pokers[j] == 53 )||( pokers[j] == 54 )||( pokers[j]%13 == 2 ) )
				{
					break;
				}
				if ( nextNum == 2 )
				{
					return false;
				}
				if ( ( (pokers[j]%13 > 0)&&( pokers[j]%13 == nextNum ) )||( ( pokers[j]%13 == 0 )&&( nextNum == 13 ) ) )
				{
					nextNum = (byte) (++nextNum%13==0?13:nextNum%13);
					countDown--;
					straight[countDown] = (byte) j;
					//MEMSET(straight,0,5);
					//countDown = 5;
					
				}
				else if ( ( pokers[i]+13-2 )%13 > (nextNum+13-2 )%13 )
				{
					break;
				}
				
				if ( !( countDown > 0 ) )
				{
					for ( int n = 0; n < 4; n++ )
					{
						if ( pattern[patternType].singleStraight[n][0] == 0 )
						{
							pattern[patternType].singleStraight[n][0] = pokers[straight[0]];
							pattern[patternType].singleStraight[n][1] = pokers[straight[1]];
							pattern[patternType].singleStraight[n][2] = pokers[straight[2]];
							pattern[patternType].singleStraight[n][3] = pokers[straight[3]];
							pattern[patternType].singleStraight[n][4] = pokers[straight[4]];
							break;
						}
					}
					pokers[straight[0]] = 0;
					pokers[straight[1]] = 0;
					pokers[straight[2]] = 0;
					pokers[straight[3]] = 0;
					pokers[straight[4]] = 0;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 查找牌中的最大的双顺,不需要进行扩展，应为已经排除了炸弹牌
	 * function: 
	 *   
	 * @param pokers
	 * @param length
	 * @return   
	 * @since JDK 1.6
	 */
	private boolean getDoubleStraight(byte[] pokers, int length){
		
		byte straight[] = new byte[20];
		
		MEMSET(straight,0,20);
		int countDown = 6;
		byte compareNum = 0; //pokers[length-1]%13==0?13:pokers[length-1]%13;
		byte nextNum = 3;
		for ( int i = length-1; i >= 0; i-- )
		{
			if ( ( pokers[i] == 0 )||( pokers[i] == 53 )||( pokers[i] == 54 )||( pokers[i]%13 == 2 ) )
			{
				continue;
			}
			
			/*countDown = 6;
			 compareNum = pokers[i]%13==0?13:pokers[i]%13;
			 countDown--;
			 straight[5-countDown] = i; */
			
			if ( countDown%2 == 0 )
			{
				if ( ( pokers[i]+13-2 )%13 > (nextNum+13-2 )%13 )
				{
					if ( countDown <= 0 )
						break;
					countDown = 6;
					compareNum = (byte) (pokers[i]%13==0?13:pokers[i]%13);
					countDown--;
					MEMSET(straight,0,20);
					straight[5-countDown] = (byte) i;
					nextNum = (byte) (++compareNum%13==0?13:compareNum%13);
				}
				else if ( ( pokers[i]+13-2 )%13 == (nextNum+13-2 )%13 )
				{
					compareNum = (byte) (pokers[i]%13==0?13:pokers[i]%13);
					countDown--;
					straight[5-countDown] = (byte) i;
					nextNum = (byte) (++compareNum%13==0?13:compareNum%13);
				}
				else
				{
					//ASSERT(0);
					continue;
				}
			}
			else //==1
			{
				if ( ( pokers[i]+13-2 )%13 > (pokers[straight[5-countDown]]+13-2 )%13 )
				{
					if ( countDown <= 0 )
						break;
					countDown = 6;
					compareNum = (byte) (pokers[i]%13==0?13:pokers[i]%13);
					countDown--;
					MEMSET(straight,0,20);
					straight[5-countDown] = (byte) i;
					nextNum = (byte) (++compareNum%13==0?13:compareNum%13);
				}
				else if ( ( pokers[i]+13-2 )%13 == (pokers[straight[5-countDown]]+13-2 )%13 )
				{
					countDown--;
					straight[5-countDown] = (byte) i;
				}
				else
				{
					//ASSERT(0);
					continue;
				}
			}
			
		}
		if ( countDown <= 0 )
		{
			int even = (6-countDown)/2*2;
			for ( int n = 0; n < 3; n++ )
			{
				if ( pattern[patternType].doubleStraight[n][0] == 0 )
				{
					
					for ( int m = even-1; m >= 0; m-- )
					{
						pattern[patternType].doubleStraight[n][even-m-1] = pokers[straight[m]];
					}
					break;
				}
			}
			for ( int m = 0; m < even; m++ )
			{
				pokers[straight[m]] = 0;
			}
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * 查找并排除牌中的三顺
	 * function: 
	 *   
	 * @param pokers
	 * @param length
	 * @return   
	 * @since JDK 1.6
	 */
	private boolean getTripleStraight(byte[] pokers, int length){
		
		byte[] straight = new byte[18];
		
		MEMSET(straight,0,18);
		int countDown = 6;
		byte compareNum = 0; //pokers[length-1]%13==0?13:pokers[length-1]%13;
		byte nextNum = 3;
		for ( int i = length-1; i >= 0; i-- )
		{
			if ( ( pokers[i] == 0 )||( pokers[i] == 53 )||( pokers[i] == 54 )||( pokers[i]%13  == 2 ) )
			{
				continue;
			}
			
			/*countDown = 6;
			 compareNum = pokers[i]%13==0?13:pokers[i]%13;
			 countDown--;
			 straight[5-countDown] = i; */
			
			if ( countDown%3 == 0 )
			{
				if ( ( pokers[i]+13-2 )%13 > (nextNum+13-2 )%13 )
				{
					if ( countDown <= 0 )
						break;
					countDown = 6;
					compareNum = (byte) (pokers[i]%13==0?13:pokers[i]%13);
					countDown--;
					MEMSET(straight,0,18);
					straight[5-countDown] = (byte) i;
					nextNum = (byte) (++compareNum%13==0?13:compareNum%13);
				}
				else if ( ( pokers[i]+13-2 )%13 == (nextNum+13-2 )%13 )
				{
					compareNum = (byte) (pokers[i]%13==0?13:pokers[i]%13);
					countDown--;
					straight[5-countDown] = (byte) i;
					nextNum = (byte) (++compareNum%13==0?13:compareNum%13);
				}
				else
				{
					//ASSERT(0);
					continue;
				}
			}
			else //==1
			{
				if ( ( pokers[i]+13-2 )%13 > (pokers[straight[5-countDown]]+13-2 )%13 )
				{
					if ( countDown <= 0 )
						break;
					countDown = 6;
					compareNum = (byte) (pokers[i]%13==0?13:pokers[i]%13);
					countDown--;
					MEMSET(straight,0,18);
					straight[5-countDown] = (byte) i;
					nextNum = (byte) (++compareNum%13==0?13:compareNum%13);
				}
				else if ( ( pokers[i]+13-2 )%13 == (pokers[straight[5-countDown]]+13-2 )%13 )
				{
					countDown--;
					straight[5-countDown] = (byte) i;
				}
				else
				{
					//ASSERT(0);
					continue;
				}
			}
			
		}
		if ( countDown <= 0 )
		{
			int tripleFormat = (6-countDown)/3*3;
			for ( int n = 0; n < 3; n++ )
			{
				if ( pattern[patternType].tripleStraight[n][0] == 0 )
				{
					
					for ( int m = tripleFormat-1; m >= 0; m-- )
					{
						pattern[patternType].tripleStraight[n][tripleFormat-m-1] = pokers[straight[m]];
					}
					break;
				}
			}
			for ( int m = 0; m < tripleFormat; m++ )
			{
				pokers[straight[m]] = 0;
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * 查找牌中的单牌并排除
	 * function: 
	 *   
	 * @param pokers
	 * @param length
	 * @return   
	 * @since JDK 1.6
	 */
	private boolean getSingleCard(byte[] pokers, int length){
		
		int pokerPos = 20;
		for ( int i = 0; i < length; i++ )
		{
			if ( pokers[i] != 0 )
			{
				
				pokerPos = i;
				break;
			}
		}
		if ( pokerPos >= 20 )
			return false;
		
		for ( int j = 0; j < PLAYERCARDS_MAXNUM; j++ )
		{
			if ( pattern[patternType].singleCard[j] == 0 )
			{
				pattern[patternType].singleCard[j] = pokers[pokerPos];
				pokers[pokerPos] = 0;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查找对子并排除
	 * function: 
	 *   
	 * @param pokers
	 * @param length   
	 * @since JDK 1.6
	 */
	private boolean getDoubleCard(byte[] pokers, int length){
		
		byte[] doubleCard = new byte[2];
		MEMSET(doubleCard,0,2);
		byte doubleNum = pokers[0],countDown = 2;
		for ( int i = 0; i < length; i++ )
		{
			if ( ( pokers[i] == 0 )||( pokers[i] == 53 )||( pokers[i] == 54 ) )
			{
				if (( i+1 < PLAYERCARDS_MAXNUM )&&( doubleNum == 0 )&&(pokers[i+1] != 0))
					doubleNum = pokers[i+1]; 
				continue;
			}
			
			if ( ( doubleNum+13-2 )%13 == ( pokers[i]+13-2 )%13 )
			{
				countDown--;
				doubleCard[countDown] = (byte) i;
			}
			else
			{
				countDown = 2;
				doubleNum = pokers[i];
				countDown--;
				doubleCard[countDown] = (byte) i;
				continue;
			}
			
			if ( !( countDown > 0 ) )
			{
				for ( int n = 0; n < 10; n++ )
				{
					if ( pattern[patternType].doubleCard[n][0] == 0 )
					{
						pattern[patternType].doubleCard[n][0] = pokers[doubleCard[1]];
						pattern[patternType].doubleCard[n][1] = pokers[doubleCard[0]];
						break;
					}
				}
				pokers[doubleCard[0]] = 0;
				pokers[doubleCard[1]] = 0;
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * 查找牌中的三张并排除
	 * function: 
	 *   
	 * @param pokers
	 * @param length
	 * @return   
	 * @since JDK 1.6
	 */
	private boolean getTripleCard(byte[] pokers, int length){
		
		byte[] tripleCard = new byte[3];
		MEMSET(tripleCard,0,3);
		byte tripleNum = 3,countDown = 3;
		for ( int i = 0; i < length; i++ )
		{
			if ( ( pokers[i] == 0 )||( pokers[i] == 53 )||( pokers[i] == 54 ) )
			{
				if (( i+1 < PLAYERCARDS_MAXNUM )&&( tripleNum == 0 )&&(pokers[i+1] != 0))
					tripleNum = (byte) (pokers[i+1]%13==0?13:pokers[i+1]%13); 
				continue;
			}
			
			if ( ( tripleNum+13-2 )%13 == ( pokers[i]+13-2 )%13 )
			{
				countDown--;
				tripleCard[countDown] = (byte) i;
			}
			else
			{
				countDown = 3;
				tripleNum = (byte) (pokers[i]%13==0?13:pokers[i]%13);
				countDown--;
				tripleCard[countDown] = (byte) i;
				continue;
			}
			if ( !( countDown > 0 ) )
			{
				for ( int n = 0; n < 6; n++ )
				{
					if ( pattern[patternType].tripleCard[n][0] == 0 )
					{
						pattern[patternType].tripleCard[n][0] = pokers[tripleCard[2]];
						pattern[patternType].tripleCard[n][1] = pokers[tripleCard[1]];
						pattern[patternType].tripleCard[n][2] = pokers[tripleCard[0]];
						break;
					}
				}
				pokers[tripleCard[0]] = 0;
				pokers[tripleCard[1]] = 0;
				pokers[tripleCard[2]] = 0;
				return true;
			}
		}	
		return false;
	}
	
	private void TRACE(String logInfo){
		
		if(log.isDebugEnabled()){
			
			log.debug(logInfo.replaceAll("%d", "{}"));
		}
	}
	
//	private void TRACE(String logMsg,Object ...params){
//		
//		if(log.isDebugEnabled()){
//			
//			log.debug(logMsg.replaceAll("%d", "{}"),params);
//		}
//	}
	
	private void TRACE(String logMsg,boolean isPokerValue,byte ...pokers){
		
		if(isPokerValue){
			
			TRACE(logMsg, pokers);
		} else{
			
			if(log.isDebugEnabled()){
				
				String[] strArray = new String[pokers.length];
				
				for(int i=0;i<pokers.length;i++){
					
					strArray[i] = String.valueOf(pokers[i]);
				}
				
				log.debug(logMsg.replaceAll("%d", "{}"),strArray);
			}
		}
	}
	
	private void TRACE(String logMsg,byte ...pokers){
		
		if(log.isDebugEnabled()){
			
			String[] pokerStrs = new String[pokers.length];
			
			for (int i = 0; i < pokerStrs.length; i++) {
				
				if(pokers[i] > 0 && pokers[i] < 55){
					
					pokerStrs[i] = CAIPoker.formatPokeValue(pokers[i]);
				} else{
					
					pokerStrs[i] = "";
				}
				
				
			}
			
			log.debug(logMsg.replaceAll("%d", "{}"),pokerStrs);
		}
	}
	
	
	/**
	 * 
	 * function: 字节数组cpyt
	 *   
	 * @param originalCards
	 * @param pokers   
	 * @since JDK 1.6
	 */
	private void MEMCPY(byte[] originalCards,byte[] pokers,int length){
		
		for(int i=0;i<pokers.length && i < length; i++){
			
			originalCards[i] = pokers[i];
		}
	}
	
	private void MEMCPY(byte[] originalCards,int destFrom,byte[] pokers,int srcFrom,int length){
		
		for(int i=0;i<pokers.length && i < length; i++){
			
			originalCards[i + destFrom] = pokers[i + srcFrom];
		}
	}
	
	/**
	 * 
	 * function: 字节数组cpyt
	 *   
	 * @param originalCards
	 * @param pokers   
	 * @since JDK 1.6
	 */
	private void MEMCPY(byte[] originalCards,byte[] pokers,int destFrom,int length){
		
		for(int i=0;i<pokers.length && i < length; i++){
			
			originalCards[i + destFrom] = pokers[i];
		}
	}
	
	/**
	 * 重置字节数组
	 * function: 
	 *   
	 * @param cards
	 * @param from
	 * @param len   
	 * @since JDK 1.6
	 */
	private void MEMSET(byte[] cards,int from,int len){
		
		int length = 0;
		
		for (int i = from; i < cards.length && length++ < len; i++) {
			
			cards[i] = 0;
		}
	}
	
	/**
	 * 对单顺进行扩展
	 * function: 
	 *   
	 * @param pokers
	 * @param length
	 * @return   
	 * @since JDK 1.6
	 */
	private boolean extandPossibleSS(byte[] pokers, int length){
		
		byte[] tmpStraight = new byte[7];
		
		boolean result = false;
		for ( int i = 0; i < 4; i++ )
		{
			MEMSET(tmpStraight,0,7);
			byte extandValue = pattern[patternType].singleStraight[i][0];
			if ( extandValue == 0 )
				continue;
			byte extandLength = 0;
			for ( int j = 0; j < length; j++ )
			{
				if ( ( pokers[j] == 0 )||( pokers[j] == 53 )||( pokers[j] == 54 )||( pokers[j]%13 == 2) )
					continue;
				
				if ( (extandValue+13-2+1)%13 == (pokers[j]+13-2)%13 )
				{
					tmpStraight[extandLength] = pokers[j];
					extandLength++;
					pokers[j] = 0;
					j = -1;
					if ( extandValue%13 == 0 )
					{
						break;
					}
					else
					{
						extandValue = (byte) ((extandValue+1)%13==0?13:(extandValue+1)%13);
					}
					
				}
			}
			
			if ( extandLength > 0 )
			{
				byte[] exchangeS = new byte[12-extandLength];
				
				MEMCPY(exchangeS,pattern[patternType].singleStraight[i],12-extandLength);
				MEMSET(pattern[patternType].singleStraight[i],0,12);
				for ( int m = 0 ; m < extandLength; m++ )
				{
					pattern[patternType].singleStraight[i][m] = tmpStraight[extandLength-m-1];
				}
				// 有变动
				MEMCPY(pattern[patternType].singleStraight[i],exchangeS,extandLength,12-extandLength);
				result = true;
				for (int x = 0; x < 4; x ++ )
				{
					if ( pattern[patternType].singleStraight[x][0] != 0 )
						TRACE("extandPossibleSS = %d %d %d %d %d %d %d %d %d %d %d %d\n",
							  pattern[patternType].singleStraight[x][0],pattern[patternType].singleStraight[x][1],pattern[patternType].singleStraight[x][2],pattern[patternType].singleStraight[x][3],pattern[patternType].singleStraight[x][4],
							  pattern[patternType].singleStraight[x][5],pattern[patternType].singleStraight[x][6],pattern[patternType].singleStraight[x][7],pattern[patternType].singleStraight[x][8],pattern[patternType].singleStraight[x][9],
							  pattern[patternType].singleStraight[x][10],pattern[patternType].singleStraight[x][11]);
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * function: 对单顺进行合并
	 *   
	 * @param pokers
	 * @param length
	 * @return   
	 * @since JDK 1.6
	 */
	private boolean truncatePossibleSS(byte[] pokers, int length){
		
		boolean result = false;
		for ( int i = 0; i < 4; i++ )
		{
			byte lastElementValue = 0;
			for ( int m = 0; m < 8; m++ )
			{
				if ( pattern[patternType].singleStraight[i][m] == 0 )
				{
					
					if(m == 0){
						
						lastElementValue = 0;
					} else{
						
						lastElementValue = pattern[patternType].singleStraight[i][m-1];
					}
					break;
				}
				
			}
			TRACE("truncatePossibleSS lastElementValue = %d\n",lastElementValue);
			if ( lastElementValue == 0 )
				continue;
			for ( int j = 0; j < 4; j++ )
			{
				if ( ( i == j )||( pattern[patternType].singleStraight[j][0] == 0 ) )
					continue;
				
				TRACE("truncatePossibleSS singleStraight[j][0] = %d\n",pattern[patternType].singleStraight[j][0]);
				if ( ( pattern[patternType].singleStraight[j][0] + 13 -2 )%13 == ( lastElementValue + 13 -2 -1 )%13 )
				{
					if ( i > j )
					{
						byte[] exchangeS = new byte[12];
						MEMCPY(exchangeS,pattern[patternType].singleStraight[j],12);
						int metaIndex = 0;
						while ( ( pattern[patternType].singleStraight[i][metaIndex] > 0 )&&( metaIndex < 7 ) )
						{
							metaIndex++;	
						}
						MEMSET(pattern[patternType].singleStraight[j],0,12);
						
						MEMCPY(pattern[patternType].singleStraight[j],pattern[patternType].singleStraight[i],metaIndex);
						// 有变动
						MEMCPY(pattern[patternType].singleStraight[j],exchangeS,metaIndex,12-metaIndex);
						MEMSET(pattern[patternType].singleStraight[i],0,12);
					}
					else
					{
						int metaIndex = 0;
						while ( ( pattern[patternType].singleStraight[i][metaIndex] > 0 )&&( metaIndex < 7 ) )
						{
							metaIndex++;	
						}
						// 有变动
						MEMCPY(pattern[patternType].singleStraight[i],pattern[patternType].singleStraight[j],metaIndex,12-metaIndex);
						MEMSET(pattern[patternType].singleStraight[j],0,12);
					}
					
					TRACE("truncatePossibleSS true\n");
					result = true;
					for ( int x = 0; x < 4; x++ )
					{
						if ( pattern[patternType].singleStraight[x][0] != 0 )
							TRACE("truncatePossibleSS = %d %d %d %d %d %d %d %d %d %d %d %d\n",
								  pattern[patternType].singleStraight[x][0],pattern[patternType].singleStraight[x][1],pattern[patternType].singleStraight[x][2],pattern[patternType].singleStraight[x][3],pattern[patternType].singleStraight[x][4],
								  pattern[patternType].singleStraight[x][5],pattern[patternType].singleStraight[x][6],pattern[patternType].singleStraight[x][7],pattern[patternType].singleStraight[x][8],pattern[patternType].singleStraight[x][9],
								  pattern[patternType].singleStraight[x][10],pattern[patternType].singleStraight[x][11]);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * function: 抓取顺子，并记录该模式的数据
	 *   
	 * @param type   
	 * @since JDK 1.6
	 */
	private void singleFetching(int type){
		
		while ( getSingleStraight(currentCards, 20) );
		for ( int i = 0; i < 4; i++ )
		{
			if ( pattern[type].singleStraight[i][0] != 0 )
				TRACE("getSingleStraight = %d %d %d %d %d %d %d %d %d %d %d %d\n",
					  pattern[type].singleStraight[i][0],pattern[type].singleStraight[i][1],pattern[type].singleStraight[i][2],pattern[type].singleStraight[i][3],pattern[type].singleStraight[i][4],
					  pattern[type].singleStraight[i][5],pattern[type].singleStraight[i][6],pattern[type].singleStraight[i][7],pattern[type].singleStraight[i][8],pattern[type].singleStraight[i][9],
					  pattern[type].singleStraight[i][10],pattern[type].singleStraight[i][11]);
		}
		extandPossibleSS(currentCards, 20);
		truncatePossibleSS(currentCards, 20);
		
	}
	
	/**
	 * 抓取双顺，并对相应模式做记录
	 * function: 
	 *   
	 * @param type   
	 * @since JDK 1.6
	 */
	private void doubleFetching(int type){
		
		while ( getDoubleStraight(currentCards, 20) );
		for ( int i = 0; i < 3; i++ )
		{
			if ( pattern[type].doubleStraight[i][0] != 0 )
				TRACE("getDoubleStraight = %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d\n",
					  pattern[type].doubleStraight[i][0],pattern[type].doubleStraight[i][1],pattern[type].doubleStraight[i][2],pattern[type].doubleStraight[i][3],pattern[type].doubleStraight[i][4],
					  pattern[type].doubleStraight[i][5],pattern[type].doubleStraight[i][6],pattern[type].doubleStraight[i][7],pattern[type].doubleStraight[i][8],pattern[type].doubleStraight[i][9],
					  pattern[type].doubleStraight[i][10],pattern[type].doubleStraight[i][11],pattern[type].doubleStraight[i][12],pattern[type].doubleStraight[i][13],pattern[type].doubleStraight[i][14],
					  pattern[type].doubleStraight[i][15],pattern[type].doubleStraight[i][16],pattern[type].doubleStraight[i][17],pattern[type].doubleStraight[i][18],pattern[type].doubleStraight[i][19]);
		}
		
	}
	
	/**
	 * 抓取三顺，并对相应模式做记录
	 * function: 
	 *   
	 * @param type   
	 * @since JDK 1.6
	 */
	private void tripleFetching(int type){
		
		while ( getTripleStraight(currentCards, 20) );
		for ( int i = 0; i < 3; i++ )
		{
			if ( pattern[type].tripleStraight[i][0] != 0 )
				TRACE("getTripleStraight = %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d\n",
					  pattern[type].tripleStraight[i][0],pattern[type].tripleStraight[i][1],pattern[type].tripleStraight[i][2],pattern[type].tripleStraight[i][3],pattern[type].tripleStraight[i][4],
					  pattern[type].tripleStraight[i][5],pattern[type].tripleStraight[i][6],pattern[type].tripleStraight[i][7],pattern[type].tripleStraight[i][8],pattern[type].tripleStraight[i][9],
					  pattern[type].tripleStraight[i][10],pattern[type].tripleStraight[i][11],pattern[type].tripleStraight[i][12],pattern[type].tripleStraight[i][13],pattern[type].tripleStraight[i][14],
					  pattern[type].tripleStraight[i][15],pattern[type].tripleStraight[i][16],pattern[type].tripleStraight[i][17]);
		}
	}
	
	/**
	 * 
	 * function: 获取指定张数的最小的牌
	 *   
	 * @param pokers 手牌
	 * @param length 牌长度
	 * @param resultPokers 结果集
	 * @param reLen 查找几张牌
	 * @return   找到的最小的牌value
	 * @since JDK 1.6
	 */
	private byte getSmallestNumInBytes(byte[] pokers, int length, byte[] resultPokers, int reLen){
		int compareN = 55;
		int realLen = reLen;
		boolean isFind = false;
		for ( int i = length-1; i >= 0; i-- )
		{
			if ( ( pokers[i] == 0 )||( pokers[i] == 53 )||( pokers[i] == 54 )/*||( pokers[i]%13 == 2 )*/ )
				continue;
			if ( compareN > 52 )
			{
				compareN = pokers[i];
				realLen = reLen;
				realLen--;
				resultPokers[realLen] = pokers[i];
			}
			else if ( (pokers[i]+13-2)%13 == (compareN+13-2)%13 )
			{
				realLen--;
				resultPokers[realLen] = pokers[i];
			}
			else if ( (pokers[i]+13-2)%13 != (compareN+13-2)%13 )
			{
				compareN = pokers[i];
				realLen = reLen;
				realLen--;
				resultPokers[realLen] = pokers[i];
			}
			if ( realLen <= 0 )
			{
				isFind = true;
				break;
			}
		}
		if ( isFind )
			return (byte) compareN;
		else
			return 55;
	}
	
	/**
	 * 获取最小的一张手牌
	 * function: 
	 *   
	 * @return   
	 * @since JDK 1.6
	 */
	private byte getSmallestNumInBytes(byte[] pokers, int length){
		int compareN = 55;
		for ( int i = 0; i < length; i++ )
		{
			if ( pokers[i] == 0 )
				break;
			if ( compareN > 52 )
			{
				if ( pokers[i] < compareN )
				{
					compareN = pokers[i];
				}
			}
			else
			{
				if ( pokers[i] <= 52 )
				{
					if ( ( ( pokers[i]+13-2)%13 != 0 )&&( (pokers[i]+13-2)%13 < ((compareN+13-2)%13==0?13:(compareN+13-2)%13 )) )
					{
						compareN = pokers[i];
					}
				}
			}
		}
		return (byte) compareN;
	}
	
	private boolean  SetOneShapeBySmallestOrLargest(byte[] pokers, int length, int type, TShape aShape, boolean aIsSmallest, ShapeData shapeData){
		
		return SetOneShapeBySmallestOrLargest(pokers, length, type, aShape, aIsSmallest, shapeData, (byte)0,false);
	}
	
	private boolean  SetOneShapeBySmallestOrLargest(byte[] pokers, int length, int type, TShape aShape, boolean aIsSmallest, ShapeData shapeData,boolean useSingleFirst){
		
		return SetOneShapeBySmallestOrLargest(pokers, length, type, aShape, aIsSmallest, shapeData, (byte)0,useSingleFirst);
	}
	
	private boolean SetOneShapeBySmallestOrLargest(byte[] pokers, int length, int type, TShape aShape, boolean aIsSmallest, ShapeData shapeData, byte aLimitNum){
		
		return SetOneShapeBySmallestOrLargest(pokers, length, type, aShape, aIsSmallest, shapeData, aLimitNum,false);
	}
	
	private boolean SetOneShapeBySmallestOrLargest(byte[] pokers, int length, int type, TShape aShape, boolean aIsSmallest, ShapeData shapeData, byte aLimitNum ,boolean useSingleFirst){
		
		TRACE("SetOneShapeBySmallestOrLargest Minimum %d\n", false,shapeData.getMinimun());
		int i = 0;
		boolean isExchange = false;
		byte exchangeIndex = 0;
		byte compareN = 55;
		switch (aShape) 
		{
			case SINGLECARD:		
				for ( i = shapeData.getCompareSNum() - 1; i >= 0; i-- )
				{
					if ( pattern[type].singleCard[i] == 0 )
					{
						continue;
					}
					compareN = pattern[type].singleCard[i];
				//	TRACE("singlecard compareN %d, aIsSmallest = %d\n", compareN, aIsSmallest);
					isExchange = CompareIsExchange(shapeData.getMinimun(), compareN, aIsSmallest, aLimitNum);
					if ( isExchange )
					{
						//aMinimum = compareN;
						TRACE("exchange single once\n");
						exchangeIndex = (byte) i;
						break;
					}
					TRACE("singlecard aMinimum %d\n", shapeData.getMinimun());
				}			
				if ( isExchange )
				{
					if ( ( aLimitNum > 0 )&&( aLimitNum < 55 ) )
					{
						
					}
					else
					{
						if ( aIsSmallest )
						{
							for ( i = 19; i >= 0; i-- )
							{
								if ( pattern[type].singleCard[i] == 0 )
								{
									continue;
								}
								exchangeIndex = (byte) i;
								break;
							}
						}
						else
						{
							for ( i = 0; i < 20; i++ )
							{
								if ( pattern[type].singleCard[i] == 0 )
								{
									continue;
								}
								exchangeIndex = (byte) i;
								break;
							}
						}
					}
					shapeData.setMinimun(compareN);
					MEMSET(pokers,0,20);
					//TODO 有变化
					MEMCPY(pokers,0,pattern[type].singleCard,exchangeIndex,1);
					TRACE("singlecard exchange poker = %d\n", pattern[type].singleCard[exchangeIndex]);
				}
				break;
			case DOUBLECARD:
				for ( i = shapeData.getCompareDNum() - 1; i >= 0; i-- )
				{
					if ( pattern[type].doubleCard[i][0] == 0 )
					{
						continue;
					}
					compareN = pattern[type].doubleCard[i][0];
					TRACE("doublecard compareN %d\n", compareN);
					isExchange = CompareIsExchange(shapeData.getMinimun(), compareN, aIsSmallest, aLimitNum);
					if ( isExchange )
					{
						//aMinimum = compareN;
						TRACE("exchange double once\n");
						exchangeIndex = (byte) i;
						break;
					}
					TRACE("doublecard aMinimum %d, aLimitNum %d\n", shapeData.getMinimun(), aLimitNum);
				}
				if ( isExchange )
				{
					if ( ( aLimitNum > 0 )&&( aLimitNum < 55 ) )
					{
						
					}
					else
					{
						if ( aIsSmallest )
						{
							for ( i = 9; i >= 0; i-- )
							{
								if ( pattern[type].doubleCard[i][0] == 0 )
								{
									continue;
								}
								exchangeIndex = (byte) i;
								break;
							}						
						}
						else
						{
							for ( i = 0; i < 10; i++ )
							{
								if ( pattern[type].doubleCard[i][0] == 0 )
								{
									continue;
								}
								exchangeIndex = (byte) i;
								break;
							}
						}
					}
					shapeData.setMinimun(compareN);
					MEMSET(pokers,0,20);					
					MEMCPY(pokers,pattern[type].doubleCard[exchangeIndex],2);
					TRACE("doublecard exchange poker = %d %d\n", pattern[type].doubleCard[exchangeIndex][0], pattern[type].doubleCard[exchangeIndex][1]);					
				}								
				break;
			case TRIPLECARD:
				for ( i = 5; i >= 0; i-- )
				{
					if ( pattern[type].tripleCard[i][0] != 0 )
					{					
						compareN = pattern[type].tripleCard[i][0];
						
						isExchange = CompareIsExchange(shapeData.getMinimun(), compareN, aIsSmallest);
						TRACE("TRIPLECARD CompareIsExchange\n");
						if ( isExchange )
						{
							TRACE("Exchange\n");
							exchangeIndex = (byte) i;
							break;
						}										
					}
				}
				if ( isExchange )
				{
					shapeData.setMinimun(compareN);
					MEMSET(pokers,0,20);
					//TODO 有变化
					MEMCPY(pokers,0,pattern[type].tripleCard[exchangeIndex],0,3);
					boolean isUseDoubleCard = GetDoubleOrSingleCard(pokers, (byte)1, (byte)type, 3, useSingleFirst);
					//GetDoubleOrSingleCard(pokers+3, 1, type);
					
					if ( shapeData.getCompareSNum() > 1 )
					{
						shapeData.setCompareSNum((byte) (shapeData.getCompareSNum() - 1));
					}
					else if ( shapeData.getCompareDNum() > 1 )
					{
						shapeData.setCompareDNum((byte) (shapeData.getCompareDNum() - 1));
					}
					/*if ( ( aCompareSNum > 0 )&&( GetDoubleOrSingleCard(pokers+3, 1, aCompareSNum, aCompareDNum) ) )
					{
						aCompareSNum--;
					}*/
					TRACE("aCompareSNum = %d, aCompareDNum = %d\n",shapeData.getCompareSNum(),shapeData.getCompareDNum());
				}
				break;
			case SINGLESTRAIGHT:
				for ( i = 0; i < 4; i++ )
				{
					if ( pattern[type].singleStraight[i][0] == 0 )
					{
						break;
					}
					else
					{
						for ( int j = 0; j < 12; j++ )
						{
							if ( pattern[type].singleStraight[i][j] == 0 )
							{
								break;
							}
							else
							{
								compareN = pattern[type].singleStraight[i][j];
							}
						}
						isExchange = CompareIsExchange(shapeData.getMinimun(), compareN, aIsSmallest);
						if ( isExchange )
						{
							exchangeIndex = (byte) i;
						}
						break;
					}
				}
				if ( isExchange )
				{
					shapeData.setMinimun(compareN);
					MEMSET(pokers,0,20);
					MEMCPY(pokers,pattern[type].singleStraight[i],12);
				}
				break;
			case DOUBLESTRAIGHT:
				for ( i = 0; i < 3; i++ )
				{
					if ( pattern[type].doubleStraight[i][0] == 0 )
					{
						break;
					}
					else
					{
						for ( int j = 0; j < 20; j++ )
						{
							if ( pattern[type].doubleStraight[i][j] == 0 )
							{
								break;
							}
							else
							{
								compareN = pattern[type].doubleStraight[i][j];
							}
						}
						isExchange = CompareIsExchange(shapeData.getMinimun(), compareN, aIsSmallest);
						if ( isExchange )
						{
							exchangeIndex = (byte) i;
						}
						break;
					}
				}
				if ( isExchange )
				{
					shapeData.setMinimun(compareN);
					MEMSET(pokers,0,20);
					MEMCPY(pokers,pattern[type].doubleStraight[i],20);
				}
				break;
			case TRIPLESTRAIGHT:
			{
				byte pokerNum = 0;
				for ( i = 0; i < 3; i++ )
				{
					if ( pattern[type].tripleStraight[i][0] == 0 )
					{
						break;
					}
					else
					{
						pokerNum = 0;
						for ( int j = 0; j < 18; j++ )
						{
							if ( pattern[type].tripleStraight[i][j] == 0 )
							{
								break;
							}
							else
							{
								compareN = pattern[type].tripleStraight[i][j];
								pokerNum++;
							}
						}
						if ( shapeData.getCompareSNum() > pokerNum/3 )
						{
							shapeData.setCompareSNum((byte) (shapeData.getCompareSNum() - pokerNum/3));
						}
						else if ( shapeData.getCompareDNum() > pokerNum/3 )
						{
							shapeData.setCompareDNum((byte) (shapeData.getCompareDNum() - pokerNum/3));
						}
						isExchange = CompareIsExchange(shapeData.getMinimun(), compareN, aIsSmallest);
						TRACE("TRIPLESTRAIGHT CompareIsExchange\n");
						if ( isExchange )
						{
							TRACE("Exchange\n");
							exchangeIndex = (byte) i;
						}
						break;
					}
				}
				if ( isExchange )
				{
					shapeData.setMinimun(compareN);
					MEMSET(pokers,0,20);
					MEMCPY(pokers,pattern[type].tripleStraight[i],18);
					GetDoubleOrSingleCard(pokers, (byte)(pokerNum/3), (byte)type, pokerNum, useSingleFirst);
					/*if ( (aCompareDNum > pokerNum/3-1)&&( GetDoubleOrSingleCard(pokers+pokerNum, pokerNum/3, aCompareSNum, aCompareDNum) ) )
					{
						aCompareDNum--;
					}*/
					TRACE("aCompareSNum = %d, aCompareDNum = %d\n",false,shapeData.getCompareSNum(),shapeData.getCompareDNum());
				}
			}
				break;
			case ROCKET:
				break;
			case BOMBS:
				break;
			default:
				break;
		}
		return isExchange;
	}
	
	private boolean CompareIsExchange(byte aMinimum, byte aCompareN, boolean aIsSmallest, byte aBottomLimit){
		
		return CompareIsExchange(aMinimum, aCompareN, aIsSmallest, aBottomLimit, (byte)0);
	}
	
	private boolean  CompareIsExchange(byte aMinimum, byte aCompareN, boolean aIsSmallest){
		
		return CompareIsExchange(aMinimum, aCompareN, aIsSmallest, (byte)0, (byte)0);
	}
	
	// 比较是否有变化
	private boolean CompareIsExchange(byte aMinimum, byte aCompareN, boolean aIsSmallest, byte aBottomLimit, byte aUpLimit)
	{
		if ( (aBottomLimit > 0)&&(aBottomLimit < 55)&&IsLarger(aBottomLimit, aCompareN, true) )
		{
			TRACE("CompareIsExchange - 1\n");
			return false;
		}
		if ( (aUpLimit > 0)&&(aUpLimit < 55)&&IsLarger(aCompareN, aUpLimit, true) )
		{
			TRACE("CompareIsExchange - 2\n");
			return false;
		}	
		if ( aIsSmallest )
		{
			TRACE("CompareIsExchange - 3\n");
			return IsLarger(aMinimum, aCompareN, true);		
		}
		else
		{
			TRACE("CompareIsExchange - 4\n");
			return IsLarger(aCompareN, aMinimum, true);		
		}
	}
	
	// 比较2张牌大小
	private boolean IsLarger(byte aNumber1, byte aNumber2, boolean isEqualInclude)
	{
		
		if ( aNumber2 > 52 )
		{
			if ( isEqualInclude )
			{
				if ( aNumber1 >= aNumber2 )
				{
					return true;
				}
			}
			else
			{
				if ( aNumber1 > aNumber2 )
				{
					return true;
				}
			}
		}
		else	if ( aNumber2%13 == 2 )
		{
			if ( isEqualInclude )
			{
				if ( ( aNumber1 >= 52 )||( aNumber1%13 == 2 ) )
				{
					return true;
				}
			}
			else
			{
				if ( aNumber1 > 52 )
				{
					return true;
				}
			}		
		}
		else
		{
			if ( ( aNumber1%13 == 2 )||( aNumber1 > 52 ) )
			{
				return true;
			}
			if ( isEqualInclude )
			{
				if ( ( aNumber1+13-2 )%13 >= ( aNumber2+13-2 )%13 )
				{
					return true;
				}
			}
			else
			{
				if ( ( aNumber1+13-2 )%13 > ( aNumber2+13-2 )%13 )
				{
					return true;
				}
			}
		}		
		return false;
	}

	private boolean GetDoubleOrSingleCard(byte[] pokers, byte count, byte type,int startIndex,boolean singleFirst)
	{
		
		byte smallestNum = 0;
		int index = count;
		int singleCount = 0;
		byte[] buf = new byte[8];
		MEMSET(buf,0,8);
		for ( int i = 19; i >= 0; i-- )
		{
			if ( ( pattern[type].singleCard[i] == 0 )||( pattern[type].singleCard[i] == 53 )||( pattern[type].singleCard[i] == 54 )||( pattern[type].singleCard[i]%13 == 2 ) )
			{
				continue;
			}
			index--;
			singleCount++;
			buf[index] = pattern[type].singleCard[i];
			
			//TRACE("buf[%d] = %d\n",index,buf[index]);
			if ( index <= 0 )
			{
				smallestNum = pattern[type].singleCard[i];
				TRACE("singlecard select success\n");
				MEMCPY(pokers, buf, startIndex,8);
				if(singleFirst){
					
					return true;
				}
				break;
			}
		}
		index = count;
		MEMSET(buf,0,8);
		
		int doubleCount = 0;
		for ( int i = 9; i >= 0; i-- )
		{
			if ( ( pattern[type].doubleCard[i][0] == 0 )||( pattern[type].doubleCard[i][0]%13 == 2 ) )
			{
				continue;
			}
			index--;
			doubleCount ++;
			buf[index*2] = pattern[type].doubleCard[i][0];
			buf[index*2+1] = pattern[type].doubleCard[i][1];
			
		//	TRACE("buf[%d] = %d, buf[%d] = %d\n",index*2,buf[index*2],index*2+1,buf[index*2+1]);
			if ( index <= 0 )
			{			
				if ( (smallestNum != 0)&&( (smallestNum+13-2)%13 < (pattern[type].doubleCard[i][0]+13-2)%13 ) )
					break;
				TRACE("doublecard select success\n");
				
				MEMCPY(pokers, buf,startIndex, 8);
				
				break;
			}
		}
		
		if(doubleCount >= count){
			
			return true;
		}
		// 单张不够，用对子代替单张
		if(count % 2 == 0 && doubleCount * 2 >= count ){
			
			TRACE("try truncated double card to single card use\n");
			MEMCPY(pokers,startIndex, buf,count, buf.length -count);
			
			//TODO 用单张 + 对子 ，(总数 - 单张个数 ) % 2 ==0 并且个数要大于需要的数量
		} else if(singleCount + doubleCount * 2 >= count && (count - singleCount) %2 == 0){
			
//			TRACE("try truncated double card to single card and add single card use\n");
//			
//			MEMSET(buf, 0, 8);
//			
//			for ( int i = 19; i >= 0; i-- )
//			{
//				if ( ( pattern[type].singleCard[i] == 0 )||( pattern[type].singleCard[i] == 53 )||( pattern[type].singleCard[i] == 54 )||( pattern[type].singleCard[i]%13 == 2 ) )
//				{
//					continue;
//				}
//				index--;
//				singleCount++;
//				buf[index] = pattern[type].singleCard[i];
//				
//				TRACE("buf[%d] = %d\n",index,buf[index]);
//				if ( index <= 0 )
//				{
//					smallestNum = pattern[type].singleCard[i];
//					TRACE("singlecard select success\n");
//					MEMCPY(pokers, buf, startIndex,8);
//					
//					break;
//				}
//			}
		}
		
		return true;
	}
	
	
	public boolean setLatestOutPoker(byte[] pokers, int length) {

		for ( int i = 0; i < length&& i < PLAYERCARDS_MAXNUM; i++ )
		{
			if ( pokers[i] == 0 )
			{
				if ( i < 3 && pokers[1] != 53 && pokers[1] != 54 )
				{
					return false;
				}
			}
		}
		return true;
	}

	
	public boolean setPokerClear(CAIPoker[] pokers, int length) {

		for ( int j = 0; j < length; j++ )
		{
			pokers[j].setSelect(false);
		}
		return true;
	}

	
	
	
	public void setPatternIndex(int patternType) {
		  
		this.patternType = patternType; 
		
	}

	
	
	public void setCardsPatternWithoutPokers(CAIPoker[] pokers, int length,
			byte[] eInfo, int eILen, DDZCards withoutCards) {
		  
		byte tmpBuf[] = new byte[PLAYERCARDS_MAXNUM];
		MEMSET(tmpBuf,0,PLAYERCARDS_MAXNUM);
		
		for (int i = 0; i < PATTERN_NUM1; i++) {
			
			pattern[i] = new CardsPattern();
		}
		
		int index = 0;
		for ( int i = 0; i < length&& i < PLAYERCARDS_MAXNUM && i < pokers.length; i++ )
		{
			if ( ( pokers[i].getPokerValue() > 0 ) && ( pokers[i].getPokerValue() < 55 ) )
			{
				if ( pokers[i].isOut() == false )
				{
					boolean flag = true;
					for ( int j = 0; j < withoutCards.CurrentLength; j++ )
					{	
						if ( withoutCards.cards[j].number == pokers[i].getValue() )
						{
							flag = false;
							break;
						}
					}
					if ( flag )
					{
						tmpBuf[index] = pokers[i].getPokerValue();
						index++;
					}
				}
			}
		}
		
		setCardsPattern(tmpBuf, PLAYERCARDS_MAXNUM, eInfo, eILen);  
		
	}
	
	/**
	 * 获取计算出来的牌型
	 * function: 
	 *   
	 * @return   
	 * @since JDK 1.6
	 */
	public DDZCards[] getDDZCardsArray(){
		
		byte type = 0;
		byte[] exInfo = new byte[6];
		MEMCPY(exInfo,pattern[type].extandInfo,6);
		
		int i;
		for ( i = 0; i < 6; i++ )
		{
			if ( pattern[i].calcuPatternNum < pattern[type].calcuPatternNum )
			{
				type = (byte) i;
			}
		}
		
		DDZCards cardsArray[] = new DDZCards[pattern[type].calcuPatternNum];
		
		int cardsIndex = 0;
		// rocket
		if ( pattern[type].rocket != 0 )
		{
			
			DDZCards rocket = new DDZCards();
			
			rocket.AddCard(CARD.char2CARD((char)53));
			rocket.AddCard(CARD.char2CARD((char)54));
			cardsArray[cardsIndex++] = rocket;
		}
		//bomb
		for ( i = 0; i < 5; i++ )
		{
			if ( pattern[type].bomb[i] == 0 )
			{
				continue;
			}
			else
			{
				
				DDZCards bomb = new DDZCards();
				bomb.AddCard(CARD.char2CARD(pattern[type].bomb[i]));
				bomb.AddCard(CARD.char2CARD((byte)(pattern[type].bomb[i]+13)));
				bomb.AddCard(CARD.char2CARD((byte)(pattern[type].bomb[i]+13*2)));
				bomb.AddCard(CARD.char2CARD((byte)(pattern[type].bomb[i]+13*3)));
				cardsArray[cardsIndex++] = bomb;
			}
		}
		//singlestraight
		for ( i = 0; i < 4; i++ )
		{
			if ( pattern[type].singleStraight[i][0] == 0 )
			{
				continue;
			}
			else
			{
				int index = 0;
					
				DDZCards singlestraight = new DDZCards();
				
				while(index < pattern[type].singleStraight[i].length && pattern[type].singleStraight[i][index] != 0){
					
					singlestraight.AddCard(CARD.char2CARD(pattern[type].singleStraight[i][index]));
					index ++;
				}
				
				cardsArray[cardsIndex++] = singlestraight;
			}
		}
		//double straight
		for ( i = 0; i < 3; i++ )
		{
			if ( pattern[type].doubleStraight[i][0] == 0 )
			{
				continue;
			}
			else
			{
				int index = 0;
				
				DDZCards doubleStraight = new DDZCards();
				
				while(index < pattern[type].doubleStraight[i].length && pattern[type].doubleStraight[i][index] != 0){
					
					doubleStraight.AddCard(CARD.char2CARD(pattern[type].doubleStraight[i][index]));
					index ++;
				}
				
				cardsArray[cardsIndex++] = doubleStraight;
			}
		}
		TRACE("CalculatePatternCount basicPatternNum = %d, calcuPatternNum = %d\n",false, pattern[patternType].basicPatternNum, pattern[patternType].calcuPatternNum);
		// all single
		DDZCards singleCards = new DDZCards();
		// single card
		for ( i = 0; i < 20; i++ )
		{
			if ( pattern[type].singleCard[i] == 0 )
			{
				continue;
			}
			else
			{
				singleCards.AddCard(CARD.char2CARD(pattern[type].singleCard[i]));
			}
		}
		
		DDZCards doubleCards = new DDZCards();
		// double card
		for ( i = 0; i < 10; i++ )
		{
			if ( pattern[type].doubleCard[i][0] == 0 )
			{
				break;
			}
			else
			{
				doubleCards.AddCard(CARD.char2CARD(pattern[type].doubleCard[i][0]));
				doubleCards.AddCard(CARD.char2CARD(pattern[type].doubleCard[i][1]));
			}
		}
		// triple straight
		for ( i = 0; i < 3; i++ )
		{
			if ( pattern[type].tripleStraight[i][0] == 0 )
			{
				break;
			}
			else
			{
				
				DDZCards tripleStraight = new DDZCards();
				cardsArray[cardsIndex++] = tripleStraight;
				
				for ( int j = 0; j < 18; j++ )
				{
					if ( pattern[type].tripleStraight[i][j] == 0 )
					{
						break;
					}
					else
					{
						tripleStraight.AddCard(CARD.char2CARD(pattern[type].tripleStraight[i][j]));
					}
				}
				int pokerNum = tripleStraight.CurrentLength;
				// 拿出飞机带的单张
				if ( singleCards.CurrentLength >= pokerNum/3 )
				{
					
					for(int x=0;x<pokerNum/3;x++){
						
						tripleStraight.AddCard(singleCards.DelCard(singleCards.CurrentLength - 1));
					}
				}
				// 拿出飞机带的对子
				else if ( doubleCards.CurrentLength/2 >= pokerNum/3 )
				{
					for(int x=0;x<pokerNum/3;x++){
						
						tripleStraight.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
						tripleStraight.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
					}
				}
				// 对子当飞机
				else if ( ( doubleCards.CurrentLength/2 >= pokerNum/6 )&&( pokerNum%6 == 0 ) )
				{
					for(int x=0;x<pokerNum/6;x++){
						
						tripleStraight.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
						tripleStraight.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
					}
				}
				else if ( ( doubleCards.CurrentLength/2 >= pokerNum/6 )&&( pokerNum%6 == 3 ) )
				{
					if ( singleCards.CurrentLength >= 1 )
					{
						tripleStraight.AddCard(singleCards.DelCard(singleCards.CurrentLength - 1));
						
						for(int x=0;x<pokerNum/6;x++){
							
							tripleStraight.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
							tripleStraight.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
						}
					}
					else if ( doubleCards.CurrentLength/2 > pokerNum/6 )
					{
						for(int x=0;x<pokerNum/6;x++){
							
							tripleStraight.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
							tripleStraight.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
						}
						tripleStraight.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
						singleCards.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
					}
				}
			}
		}
		TRACE("CalculatePatternCount basicPatternNum = %d, calcuPatternNum = %d\n",false, pattern[patternType].basicPatternNum, pattern[patternType].calcuPatternNum);
		// triple card
		for ( i = 0; i < 6; i++ )
		{
			if ( pattern[type].tripleCard[i][0] == 0 )
			{
				break;
				
			}else
			{
				DDZCards tripleCard = new DDZCards();
				
				tripleCard.AddCard(CARD.char2CARD(pattern[type].tripleCard[i][0]));
				tripleCard.AddCard(CARD.char2CARD(pattern[type].tripleCard[i][1]));
				tripleCard.AddCard(CARD.char2CARD(pattern[type].tripleCard[i][2]));
				
				if ( singleCards.CurrentLength > 0 ){
					
					tripleCard.AddCard(singleCards.DelCard(singleCards.CurrentLength - 1));
				}
				else 
				{
					if ( doubleCards.CurrentLength > 0 )
					{
						tripleCard.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
						tripleCard.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
					}
				}
				
				cardsArray[cardsIndex++] = tripleCard;
			}
		}
		
		while(singleCards.CurrentLength > 0){
			
			DDZCards singleCard = new DDZCards();
			
			singleCard.AddCard(singleCards.DelCard(0));
			
			cardsArray[cardsIndex++] = singleCard;
		}
		
		while(doubleCards.CurrentLength > 0){
			
			byte[] numbers = doubleCards.getCardsNumber();
			
			DDZCards doubleCard = new DDZCards();
			
			doubleCard.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
			doubleCard.AddCard(doubleCards.DelCard(doubleCards.CurrentLength - 1));
			
			cardsArray[cardsIndex++] = doubleCard;
		}
		
		CSearchHint m_SearchHint = new CSearchHintImpl();
		// 设置牌型
		for (DDZCards ddzCard : cardsArray) {
			
			m_SearchHint.SetCardsType(ddzCard);
		}
		
		return cardsArray;
	}
	
	
	public void setCardsPattern(byte[] pokers, int length, byte[] info,
			int len, byte[] otherPokers) {
		  
		setCardsPattern(pokers, length, info, len);
		this.otherPokers = otherPokers;
		
	}

	/**
	 * 
	 * fuction
	 **/

	public static void main(String[] args) {

		CAIAnalyzer analyzer = CAIAnalyzerFactory.getInstance();

		CAIPoker[] pokers = null;

		analyzer.setCardsPattern(pokers, 20, null, 1);
	}
}
