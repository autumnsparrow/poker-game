package com.sky.poke.ai.handler;   

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.poke.ai.CAIAnalyzer;
import com.sky.poke.ai.CAIAnalyzerFactory;
import com.sky.poke.ai.bean.CAIPoker;
import com.sky.poke.ai.bean.DDZCards;
import com.sky.poke.ai.bean.DDZCards.CARD;
import com.sky.poke.ai.searchhint.CSearchHint;
import com.sky.poke.ai.searchhint.CSearchHintImpl;

/**     
 * Function: AI处理类 
 *
 * ClassName:CAIHandler 
 * Date:     2013-6-22 下午03:29:17    
 * @author   chshyin   
 * @version     
 * @since    JDK 1.6   
 * Copyright (c) 2013, palm-commerce All Rights Reserved.         
 */
public class CAIHandler {
	
	private static Logger logger = LoggerFactory.getLogger("AIanalyzer");
	
	private static String[] cardNumStr = new String[]{"A","2","3","4","5","6","7","8","9","10","J","Q","K","joker","JOKER"};
	
	public static byte[] SearchHint(byte[] holdPokers,byte holdLen,byte[] outPokers,byte outLen,byte[] extandData,byte lastOutIndex){
		
		return SearchHint(holdPokers, holdLen, outPokers, outLen, extandData, lastOutIndex, null, null,null);
	}
	/**
	 * 
	 * function: 
	 *   
	 * @param holdPokers
	 * @param holdLen
	 * @param outPokers
	 * @param outLen
	 * @param extandData
	 * @param lastOutIndex 最后一个出了牌的人index   0 : me    1: right   2:left
	 * @param leftOutPokers 
	 * @param rightOutPokers 
	 * @return    
	 * @since JDK 1.6
	 */
	public static byte[] SearchHint(byte[] holdPokers,byte holdLen,byte[] outPokers,byte outLen,byte[] extandData,byte lastOutIndex, byte[][] rightOutPokers, byte[][] myOutPokers, byte[][] leftOutPokers){
		// 主动出牌
		if(outPokers == null || outLen == 0){
			
			CAIAnalyzer analyzer = CAIAnalyzerFactory.getInstance();
			
			byte[] otherPokers = otherPokers(holdPokers, myOutPokers, rightOutPokers, leftOutPokers);
			
			analyzer.setCardsPattern(holdPokers, 20,extandData,6,otherPokers);
			
			byte[] pokers = new byte[20];
			
			pokers = analyzer.selectFirstOutPoker(pokers, 20);
			
			return pokers;
		}
		// 跟牌
		TRACE("extandData data %d %d %d %d %d %d\n",extandData[0],extandData[1],extandData[2],extandData[3],extandData[4],extandData[5]);
		
		TRACE("手牌：" + formatPoke(holdPokers) + ",上一手出牌:" + formatPoke(outPokers) + ",lastOutIndex:" + lastOutIndex);
		
		CSearchHint m_SearchHint = new CSearchHintImpl();
		
		CAIAnalyzer analyzer = CAIAnalyzerFactory.getInstance();
		
		analyzer.setCardsPattern(holdPokers, 20,extandData,6);
		
		byte beforeMinimumCount = analyzer.getMinimumCalPatternIndex();
		byte resultMinimumCount = 20;
		
		boolean isFromOpponentPart = false;
		
		DDZCards tempCards = null;
		// 手牌
		CAIPoker[] pInHandPoker = initAIPoker(holdPokers);
		// 出的牌
		CAIPoker[] pLastOutPoker = initAIPoker(outPokers);
		
		if ( extandData[0] == 1 ) // i'm lord
		{
			isFromOpponentPart = true;
			if ( extandData[3] <= 2 ) // next player control
			{
				//output card from opponent
				m_SearchHint.SearchHint(pInHandPoker,holdLen,pLastOutPoker,outLen);
				if (m_SearchHint.GetHintCount() == 0)
				{  
					TRACE("extandData[0] == 1 <=2 not out\n");
					/*if ( aIsOut ) 
					{
						PlayerDonotOut();
					}*/
					return new byte[0];
				}
				int m = 0;
				int compareNum = 0;
				int compareIndex = 0;
				for ( m = 0; m < m_SearchHint.GetHintCount(); m++ )
				{
					tempCards = m_SearchHint.GetNextHint();
					int num = 0;
					if ( tempCards.cards[0].number < 3 )
					{
						num = tempCards.cards[0].number + 11;
					}
					else if ( ( tempCards.cards[0].number >= 3 )&&( tempCards.cards[0].number < 14 ) )
					{
						num = tempCards.cards[0].number - 2;
					}
					else
					{
						num = tempCards.cards[0].number;
					}
					
					if ( num >  compareNum )
					{
						compareNum = num;
						compareIndex = m;
					}
				}
				
				for ( m = 0; m <= compareIndex; m++ )
				{
					tempCards = m_SearchHint.GetNextHint();
				}
				TRACE("I am lord ,next user is an opponent\n");
				
			}else if ( lastOutIndex == 2 && extandData[5] <= 2 ) // last output player control
			{
				//output card from opponent
				m_SearchHint.SearchHint(pInHandPoker,holdLen,pLastOutPoker,outLen);
				if (m_SearchHint.GetHintCount() == 0)
				{  
					TRACE("e( pReferUser != pReferRightUser )&&( pReferUser->GetHoldPokerCount() <= 2 ) not out\n");
					/*if ( aIsOut ) 
					{
						PlayerDonotOut();
					}*/
					return new byte[0];
				}
				int m = 0;
				int compareNum = 0;
				int compareIndex = 0;
				for ( m = 0; m < m_SearchHint.GetHintCount(); m++ )
				{
					tempCards = m_SearchHint.GetNextHint();
					int num = 0;
					if ( tempCards.cards[0].number < 3 )
					{
						num = tempCards.cards[0].number + 11;
					}
					else if ( ( tempCards.cards[0].number >= 3 )&&( tempCards.cards[0].number < 14 ) )
					{
						num = tempCards.cards[0].number - 2;
					}
					else
					{
						num = tempCards.cards[0].number;
					}
					
					if ( num >  compareNum )
					{
						compareNum = num;
						compareIndex = m;
					}
				}
				
				for ( m = 0; m <= compareIndex; m++ )
				{
					tempCards = m_SearchHint.GetNextHint();
				}
				TRACE("I am lord ,last output user is an opponent\n");
			}
		}else if ( extandData[0] == 0 ){// i'm farmer
		
			if ( ( extandData[2] == 1 )&&( extandData[3] <= 2 ) ) // next user is an opponent
			{
				//next output card from opponent
				m_SearchHint.SearchHint(pInHandPoker,holdLen,pLastOutPoker,outLen);
				if (m_SearchHint.GetHintCount() == 0)
				{  
					//double no out error
					/*
					 if ( aIsOut ) {
					 PlayerDonotOut();
					 }*/
					//NSLog(@"next output card from opponent   not out");
					TRACE( "next output card from opponent   not out\n" );
					return new byte[0];
				}
				int m = 0;
				int compareNum = 0;
				int compareIndex = 0;
				for ( m = 0; m < m_SearchHint.GetHintCount(); m++ )
				{
					tempCards = m_SearchHint.GetNextHint();
					int num = 0;
					if ( tempCards.cards[0].number < 3 )
					{
						num = tempCards.cards[0].number + 11;
					}
					else if ( ( tempCards.cards[0].number >= 3 )&&( tempCards.cards[0].number < 14 ) )
					{
						num = tempCards.cards[0].number - 2;
					}
					else
					{
						num = tempCards.cards[0].number;
					}
					
					if ( num >  compareNum )
					{
						compareNum = num;
						compareIndex = m;
					}
				}
				
				for ( m = 0; m <= compareIndex; m++ )
				{
					tempCards = m_SearchHint.GetNextHint();
				}
				
				TRACE("I am farmer ,next user is an opponent\n");
				
				
			}else if ( ( (lastOutIndex == 1)&&(extandData[2] == 1) )
					   ||( (lastOutIndex == 2)&&(extandData[4] == 1) ) )
			{
				isFromOpponentPart = true;
				
				if ( ( (lastOutIndex == 1)&&(extandData[2] == 1)&&extandData[3] <= 2)
						|| ((lastOutIndex == 2)&&(extandData[4] == 1)&&extandData[5] <= 2)) 
				{
					//last output card from opponent
					m_SearchHint.SearchHint(pInHandPoker,holdLen,pLastOutPoker,outLen);
					if (m_SearchHint.GetHintCount() == 0)
					{  
						//double no out error
						/*if ( aIsOut ) {
						 PlayerDonotOut();
						 }*/
						
						TRACE(">m_Postion == pOtherUserIndex2)&&(extandData[4] == 1) ) ) not out\n");
						return new byte[0];
					}
					int m = 0;
					int compareNum = 0;
					int compareIndex = 0;
					for ( m = 0; m < m_SearchHint.GetHintCount(); m++ )
					{
						tempCards = m_SearchHint.GetNextHint();
						int num = 0;
						if ( tempCards.cards[0].number < 3 )
						{
							num = tempCards.cards[0].number + 11;
						}
						else if ( ( tempCards.cards[0].number >= 3 )&&( tempCards.cards[0].number < 14 ) )
						{
							num = tempCards.cards[0].number - 2;
						}
						else
						{
							num = tempCards.cards[0].number;
						}
						TRACE("tempCards.cards[0].number %d\n",(byte)tempCards.cards[0].number);
						if ( num >  compareNum )
						{
							compareNum = num;
							compareIndex = m;
						}
					}
					
					for ( m = 0; m <= compareIndex; m++ )
					{
						tempCards = m_SearchHint.GetNextHint();
					}
					TRACE("I am farmer ,last output user is an opponent\n");
				}
			}else if ( ( (lastOutIndex == 1)&&(extandData[2] == 0) && extandData[3] <= 2 )
				||( (lastOutIndex == 2)&&(extandData[4] == 0) && extandData[5] <= 2))
			{
				byte countCal = analyzer.getMinimumCalPatternIndex();
				if ( countCal > 2 )
				{
				//if ( pReferUser->GetHoldPokerCount() <= 2 ) 
				//{					
				TRACE("extandData[0] == 0 ) not out\n");
				TRACE("I am farmer ,last output user is an ally\n");
				return new byte[0];					
				//}
				}
				
			}
			else if ( extandData[4] == 1 ) // next user is an ally
			{
				
				TRACE("I am farmer ,next user is an ally\n");
			}
		}//rule 2.1
		//add by stephenhe
		if ( tempCards == null || tempCards.CurrentLength <= 0 )
		{
			TRACE("normal rule\n");
			
			if (  analyzer.setLatestOutPoker(outPokers,outLen) )
			{
				m_SearchHint.SearchHint(pInHandPoker,holdLen,pLastOutPoker,outLen);
				if (m_SearchHint.GetHintCount() == 0)
				{  
					TRACE("!m_SearchHint.GetHintCount()\n");
					return new byte[0];
				}
				
				
				TRACE("GetHintCount %d\n",m_SearchHint.GetHintCount());
				int resultIndex = 21;
				int m = 0;
				for ( m = 0; m < m_SearchHint.GetHintCount(); m++ )
				{
					tempCards = m_SearchHint.GetNextHint();
					
					analyzer.setCardsPatternWithoutPokers(pInHandPoker, holdLen, extandData, 6, tempCards);
					byte countCal = analyzer.getMinimumCalPatternIndex();
					TRACE("check type problem tempCards.nType = %d\n",tempCards.nType);
					if ( extandData[0] == 0 )
					{						
						if ( ( extandData[2] == 0 )&&(lastOutIndex == 1) )
						{
							if ( ( countCal > 2 )&&( pLastOutPoker[0].getValue() > 8 ) )
							{
								TRACE("not out c\n");
								return new byte[0];
							}
							
						}
						else if ( ( extandData[4] == 0 )&&(lastOutIndex == 2) )
						{
							if ( ( countCal > 2 )&&( pLastOutPoker[0].getValue() > 8 ) )
							{
								TRACE("not out a\n");
								return new byte[0];
							}
						}
					}
															
					//if ( ( tempCards.nType == CARD_TYPE_4_BOMB )||( tempCards.nType == CARD_TYPE_2_JOKER ) )
					if ( ( ( tempCards.CurrentLength == 4 )&&( tempCards.cards[0].number == tempCards.cards[1].number )&&(tempCards.cards[2].number == tempCards.cards[3].number)&&(tempCards.cards[1].number == tempCards.cards[2].number) )
						||( ( tempCards.CurrentLength == 2 )&&( tempCards.cards[0].number == 14||tempCards.cards[0].number == 15) ) )
					{
						
						//BYTE countCal = analyzer.GetMinimumCalPatternIndex();
						TRACE("is bomb or joker, but minimumcalpattern is %d\n", countCal);
						if ( countCal > 0 )
						{
							Random random = new Random();  
							int num = random.nextInt(countCal);
							if ( num < 2 )
							{
								//if bomb, just break ,will be ok
								//break;
							}
							else
							{
								TRACE("not out b\n");
								return new byte[0];
							}
						}
						TRACE("bomb or joker out\n");
					}
					else if ( ( tempCards.cards[0].number > 13 )||(  tempCards.cards[0].number == 2 ) )
					{
						if ( ( pLastOutPoker[0].getPokerValue() <= 52 )
							&&( (pLastOutPoker[0].getPokerValue()+13-2)%13 <= 11 )
							&&( ( pLastOutPoker[0].getPokerValue()+13-2 )%13 > 0 ) )
						{
							countCal = analyzer.getMinimumCalPatternIndex();
							if ( countCal > 0 )
							{
								Random random = new Random();  
								int num = random.nextInt(countCal);
								if ( num >= 2 )
								{
									TRACE("not out d\n");
									return new byte[0];
								}
								else
								{
									//continue;
								}
							}
						}
					}
					
					if ( tempCards.CurrentLength == 6 )
					{
						boolean continueFlag = false;
						for ( int h = 0; h < 3; h++ )
						{
							int countDown = 3;
							for ( int k = h+1; k < 6; k++ )
							{
								if ( tempCards.cards[h].number == tempCards.cards[k].number )
								{
									countDown--;
								}
								if ( countDown <= 0 )
								{
									continueFlag = true;
									break;
								}
							}
							if ( continueFlag )
							{
								break;
							}
						}
						if ( continueFlag )
						{
							continue;
						}
						
					}
					
					byte afterMinimumCount = analyzer.getMinimumCalPatternIndex();
					if ( afterMinimumCount < beforeMinimumCount + 1 )
					{
						TRACE("afterMinimumCount < beforeMinimumCount + 2\n");
						if ( resultMinimumCount > afterMinimumCount )
						{
							TRACE("resultMinimumCount > afterMinimumCount ,afterMinimumCount = %d, resultMinimumCount = %d\n",afterMinimumCount,resultMinimumCount);
							resultMinimumCount = afterMinimumCount;
							resultIndex = m;
							break;
						}
					}
				}
				if ( resultIndex == 21 )
				{
					if ( (m_SearchHint.GetHintCount() == 0)&&(isFromOpponentPart == true)&&((lastOutIndex == 1 && extandData[3] < 6) || (lastOutIndex == 2 && extandData[5] < 6) ))
					{
						tempCards = m_SearchHint.GetNextHint();
						TRACE("resultIndex == 21, (!m_SearchHint.GetHintCount())&&(isFromOpponentPart == TRUE)&&(pReferUser->GetPokerCount() < 6)\n");
					}
					else
					{
					//double no out error
					/*if ( aIsOut ) {
						PlayerDonotOut();
					}*/
					TRACE("not out e\n");
					return new byte[0];
					}
				}
				/*for ( m = 0; m < m_SearchHint.GetHintCount(); m++ )
				{
					tempCards = m_SearchHint.GetNextHint();
				}*/
				
			}else
			{
				if ( !analyzer.selectFollowOutPoker(holdPokers, 20) )
				{
					boolean isPlayOut = false;
					byte countCal = analyzer.getMinimumCalPatternIndex();
					TRACE("single double do not find the right one\n");
					
					TRACE("need to select the bomb\n");
					// if less then 2 pattern ,choose the bomb or rocket
					m_SearchHint.SearchHint(pInHandPoker,holdLen,pLastOutPoker,outLen);
					
					if ( extandData[0] == 0 )
					{						
						if ( ( extandData[2] == 0 )&&(lastOutIndex == 1) )
						{
							if ( ( countCal > 2 )&&( pLastOutPoker[0].getValue() > 8 ) )
							{
								TRACE("not out f\n");
								return new byte[0];
							}
							/*if ( ( countCal > 2 )&&( pLordUser->m_OutPoker[0].GetValue() > 11 ) )
							{
								NSLog(@"( countCal > 2 )&&( pLordUser->m_OutPoker[0].GetValue() > 11 )");
								return 0;
							}*/
						}
						else if ( ( extandData[4] == 0 )&&(lastOutIndex == 2) )
						{
							if ( ( countCal > 2 )&&( pLastOutPoker[0].getValue() > 8 ) )
							{
								TRACE("not out g\n");
								return new byte[0];
							}
							/*if ( ( countCal > 2 )&&( pLordUser->m_OutPoker[0].GetValue() > 11 ) )
							{
								NSLog(@"( countCal > 2 )&&( pLordUser->m_OutPoker[0].GetValue() > 11 )");
								return 0;
							}*/
						}
					}
					
					for ( int m = 0; m < m_SearchHint.GetHintCount(); m++ )
					{
						tempCards = m_SearchHint.GetNextHint();
						for (int aaa = 0; aaa < 20; aaa++)
						{
							TRACE("tempCards.cards[%d].number = %d\n", aaa, tempCards.cards[aaa].number);
						}
						TRACE("tempCards.CurrentLength = %d\n",tempCards.CurrentLength);
						if ( ( ( tempCards.CurrentLength == 1&&tempCards.cards[0].number == 2 )||( tempCards.CurrentLength == 2&&(tempCards.cards[0].number==tempCards.cards[1].number) ) )&&( tempCards.cards[0].number == 2 ) )
						{							
							TRACE("find the 2\n");
							
							if ( ( outLen > 0 )/*&&( pReferUser->m_OutPoker[0].GetValue() > 10 )*/ )
							{
								if ( pLastOutPoker[0].getValue()>= 11 )
								{
									isPlayOut = true;
									break;	
								}
								else
								{
									Random randGen = new Random();
									int numRand = randGen.nextInt( 13 - (pLastOutPoker[0].getPokerValue()+13-2)%13 );
									if ( numRand < 3 )
									{
										isPlayOut = true;
										break;
									}
								}		
							}
						}
						else if ( ( ( tempCards.CurrentLength == 4 )&&( tempCards.cards[0].number == tempCards.cards[1].number && tempCards.cards[2].number == tempCards.cards[3].number && tempCards.cards[1].number == tempCards.cards[2].number) )
								 ||( ( tempCards.CurrentLength == 2 )&&( tempCards.cards[0].number == 14||tempCards.cards[0].number == 15) ) )
							
							//if //( ( tempCards.nType == CARD_TYPE_4_BOMB )||( tempCards.nType == CARD_TYPE_2_JOKER ) )
						{
							TRACE("find the bomb\n");
							if ( countCal < 4 )
							{
								isPlayOut = true;
								break;
							}
							else
							{
								continue;
							}
						}
					}
					
					if ( !isPlayOut )
					{				
						TRACE("not find the 2 or bomb\n");
						//double no out error
						//PlayerDonotOut();
						return new byte[0];
					}
				}
				else
				{
					
					TRACE("not have single or double ,just last check\n");
					byte countCal = analyzer.getMinimumCalPatternIndex();
					if ( extandData[0] == 0 )
					{						
						if ( ( extandData[2] == 0 )&&(lastOutIndex == 1) )
						{
							if ( ( countCal > 2 )&&( pLastOutPoker[0].getValue() > 8 ) )
							{
								analyzer.setPokerClear(pInHandPoker, 20);
								return new byte[0];
							}							
						}
						else if ( ( extandData[4] == 0 )&&(lastOutIndex == 2) )
						{
							if ( ( countCal > 2 )&&( pLastOutPoker[0].getValue() > 8 ) )
							{
								analyzer.setPokerClear(pInHandPoker, 20);
								return new byte[0];
							}
						}
					}
				}
			}
		}		
//		m_bHaveSearched = true;
		if(tempCards == null){
			
			TRACE("tempCards == null\n");
			return new byte[0];
		}
		
		// 选择需要出的牌
		CAIPoker tempPoker;
		
		DDZCards resultCards = new DDZCards();
		
		for (int i=0; i<tempCards.CurrentLength; i++)
		{
			char totalValue = CARD.CARD2char(tempCards.cards[i]);
			tempPoker = new CAIPoker((byte)totalValue);
			for (int j=0; j<holdLen; j++)		
			{
				byte tempPokerValue = tempPoker.getValue();
				byte m_pokerjTotalValue = pInHandPoker[j].getPokerValue();
				byte m_PokerjValue = pInHandPoker[j].getValue();
				boolean bSelect = pInHandPoker[j].isSelect();
				if (tempPoker.getValue() == pInHandPoker[j].getValue()
					&& !pInHandPoker[j].isSelect())
				{
					pInHandPoker[j].setSelect(true);
					resultCards.AddCard(CARD.char2CARD((char)pInHandPoker[j].getPokerValue()));
					break;
				}
			}
		}
		
		TRACE("跟牌成功：" + formatPoke(resultCards));
		
		return convertToByte(resultCards); 
	
	}
	
	private static String formatPoke(DDZCards ddzCards){
		
		StringBuilder builder = new StringBuilder();

		if(ddzCards == null){
			
			return "";
		}
		
		for(int i=0;i<ddzCards.CurrentLength;i++){
			
			builder.append(cardNumStr[ddzCards.cards[i].number - 1]).append(" ");
		}

		return builder.toString();
	}
	
	private static byte[] convertToByte(DDZCards ddzCards){
		
		byte[] pokers = new byte[ddzCards.CurrentLength];
		
		for (int i = 0; i < pokers.length; i++) {
			
			pokers[i] = (byte) CARD.CARD2char(ddzCards.cards[i]);
		}
		
		return pokers;
	}
	
	private static void TRACE(String logMsg,Object ...params){
		
		if(logger.isDebugEnabled()){
			
			logger.debug("CAIHandler:" + logMsg.replaceAll("%d", "{}"),params);
		}
	}
	
	private static String formatPoke(byte[] pokers) {

		StringBuilder builder = new StringBuilder();
		
		if(pokers == null){
			
			return "";
		}

		for (byte b : pokers) {

			if (b > 0) {

				builder.append(CAIPoker.formatPokeValue(b)).append(" ");
			}
		}

		return builder.toString();
	}
	
	private static CAIPoker[] initAIPoker(byte[] pokers){
		
		CAIPoker[] aiPokers = new CAIPoker[pokers.length];
		
		for (int i = 0; i < aiPokers.length; i++) {
			
			aiPokers[i] = new CAIPoker(pokers[i]);
		}
		
		return aiPokers;
	}
	// 计算剩余的牌
	private static byte[] otherPokers(byte[] handPokers,byte[][] myOutPokers, byte[][] rightOutPokers,byte[][] leftOutPokers){
		
		if(myOutPokers == null){
			
			return null;
		}
		
		byte[] pokers = new byte[54];
		// init one poke
		for(int i=0;i<pokers.length;i++){
			
			pokers[i] = (byte) (i + 1);
		}
		
		delOutPokers(pokers, handPokers);// 去除手牌
		delOutPokers(pokers, myOutPokers);// 去除我已经出的牌
		delOutPokers(pokers, rightOutPokers);// 去除下一家出的牌
		delOutPokers(pokers, leftOutPokers);// 去除上一家出的牌
		
		return pokers;
	}
	
	private static void delOutPokers(byte[] pokers,byte[][] outPokers){
		
		if(outPokers == null){
			
			return;
		}
		
		for (byte[] outPoker : outPokers) {
			
			delOutPokers(pokers, outPoker);
		}
	}
	
	private static void delOutPokers(byte[] pokers,byte[] outPokers){
		
		if(outPokers == null){
			
			return;
		}
		
		for (byte oPoker : outPokers) {
			
			if(oPoker > 0 && oPoker <= pokers.length){
				
				pokers[oPoker - 1] = 0;
			}
		}
		
	}
	
}
   
