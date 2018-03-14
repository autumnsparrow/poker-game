package com.sky.poke.ai;   

import com.sky.poke.ai.bean.CAIPoker;
import com.sky.poke.ai.bean.DDZCards;
import com.sky.poke.ai.searchhint.CSearchHint;
import com.sky.poke.ai.searchhint.CSearchHintImpl;

/**     
 * Function: 天牌帮助类 
 *
 * ClassName:BigestCardUtil 
 * Date:     2013-7-7 下午05:15:23    
 * @author   chshyin   
 * @version     
 * @since    JDK 1.6   
 * Copyright (c) 2013, palm-commerce All Rights Reserved.         
 */
public class BigestCardUtil {
	
	/**
	 * 
	 * function:返回天牌信息 
	 *   
	 * @param pokers 要比较的牌
	 * @param shape	牌型
	 * @param otherPokers 剩余的牌，剩余的牌为其它两家的剩余的牌的和
	 * @param eInfo	附加信息
	 * @return   第一个字节  1 天牌  0 非天牌
	 * @since JDK 1.6
	 */
	public static byte[] isBigestPoker(byte[] pokers,byte[] otherPokers,byte[] eInfo){
		
		byte[] bigestPokerInfo = new byte[1];
		
		CSearchHint searchHint = new CSearchHintImpl();
		
		searchHint.SearchHint(convert(otherPokers), (byte)otherPokers.length, convert(pokers), (byte)pokers.length);
		
		int hintCount = searchHint.GetHintCount();
		// 天牌
		if(hintCount <= 0){
			
			bigestPokerInfo[0] = 1;
		}
		// 其它情况也有可能是天牌
		if(hintCount > 0){
			
			bigestPokerInfo[0] = 0;
			// 有的话，还需要判断是否有炸弹或者火箭，如果没有炸弹，则看敌对方的牌的张数是否大于需要判断的牌的张数
			if(eInfo[0] == 0){// i'm farmer
				// 地主手牌数
				int lordPokersLen = eInfo[2] == 1 ? eInfo[3] : eInfo[5];
				// 如果地主的牌<2张，并且牌数小于要出的牌，则为天牌
				if(lordPokersLen == 1 && lordPokersLen < pokers.length){
					
					bigestPokerInfo[0] = 1;
					
				} else{
					// 其它情况，看比之大的牌是否有炸弹和火箭
					boolean hasBomb = false;
					boolean hasRocket = false;
					
					for(int i = 0 ; i< hintCount ; i++){
						
						DDZCards ddzCards = searchHint.GetNextHint();
						// 判断是否是火箭
						if(ddzCards.CurrentLength == 2){
							
							if((ddzCards.cards[0].number == 14 && ddzCards.cards[1].number == 15)
									|| (ddzCards.cards[0].number == 15 && ddzCards.cards[1].number == 14)){
								
								hasRocket = true;
							}
							// 判断是否是炸弹
						} else if(ddzCards.CurrentLength == 4){
							
							if(ddzCards.cards[0].number == ddzCards.cards[1].number
									&& ddzCards.cards[0].number == ddzCards.cards[2].number
									&& ddzCards.cards[0].number == ddzCards.cards[3].number){
								
								hasBomb = true;
							}
						}
					}
					// 地主牌张数小于要出的牌
					if(lordPokersLen < pokers.length){
						// 如果没有炸弹和火箭，天牌
						if(!hasRocket && !hasBomb){
							
							bigestPokerInfo[0] = 1;
							// 没有火箭
						} else if(!hasRocket){
							// 小于4张，则不可能有炸弹，天牌
							if(lordPokersLen < 4){
								
								bigestPokerInfo[0] = 1;
							}
						}
					}
					
					
				}
				
			}
			
		}
		
		
		return bigestPokerInfo;
		
	}
	
	private static CAIPoker[] convert(byte[] pokers){
		
		CAIPoker aiPokers[] = new CAIPoker[pokers.length];
		
		for (int i = 0; i < aiPokers.length; i++) {
			
			aiPokers[i] = new CAIPoker(pokers[i]);
		}
		
		return aiPokers;
	}
}
   
