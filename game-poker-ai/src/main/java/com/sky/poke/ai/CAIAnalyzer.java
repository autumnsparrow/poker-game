package com.sky.poke.ai;

import com.sky.poke.ai.bean.CAIPoker;
import com.sky.poke.ai.bean.DDZCards;

/**
 * Function: 牌型分析类
 * 
 * ClassName:CAIAnalyzer Date: 2013-6-18 下午05:55:22
 * 
 * @author chshyin
 * @version
 * @since JDK 1.6 Copyright (c) 2013, palm-commerce All Rights Reserved.
 */
public interface CAIAnalyzer {
	// 用户最大牌数
	int PLAYERCARDS_MAXNUM = 20;
	// 一共进行几种模式分析
	int PATTERN_NUM1 = 6;
	// 获取最小的牌型
	byte getMinimumCalPatternIndex();
	// 跟牌
	boolean selectFollowOutPoker(byte[] pokers, int length);
	// 设置最后一手牌
	boolean setLatestOutPoker(byte[] pokers, int length);
	// 主动出牌
	byte[] selectFirstOutPoker(byte[] pokers, int length);
	// 设置牌，进行牌型分析
	void setCardsPattern(CAIPoker[] pokers, int length, byte[] eInfo, int eILen);
	// 设置牌，进行牌型分析
	void setCardsPattern(byte[] pokers, int length, byte[] eInfo, int eILen);
	// 设置牌，进行牌型分析，新增剩余的其它的牌，剩余其它的牌 = 整副牌 - 所有人已出的牌 - 自己现有的手牌
	void setCardsPattern(byte[] pokers, int length, byte[] eInfo, int eILen, byte[] otherPokers);
	
	void setPatternIndex(int patternType);

	void setCardsPatternWithoutPokers(CAIPoker[] pokers, int length,
			byte[] eInfo, int eILen, DDZCards withoutCards);

	boolean setPokerClear(CAIPoker[] pokers, int length);
	
	// 获取最优牌型的牌组合
	DDZCards[] getDDZCardsArray();
	
//	void processing();
	// 计算各种牌型的手数
	void CalculatePatternCount();

}
