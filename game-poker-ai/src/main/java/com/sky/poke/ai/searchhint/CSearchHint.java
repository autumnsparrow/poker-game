package com.sky.poke.ai.searchhint;

import com.sky.poke.ai.bean.CAIPoker;
import com.sky.poke.ai.bean.DDZCards;

/**
 * Function: 出牌提示
 * 
 * ClassName:CSearchHint Date: 2013-6-22 下午04:15:28
 * 
 * @author chshyin
 * @version
 * @since JDK 1.6 Copyright (c) 2013, palm-commerce All Rights Reserved.
 */
public interface CSearchHint {

	int CARD_TYPE_SINGLE = 0x01;
	/** ￋﾳￅￆ */
	int CARD_TYPE_CONTINUE_SINGLE = 0x02;
	/** ﾶￔￅￆ */
	int CARD_TYPE_2_TUPLE = 0x04;
	/** ￁ﾬﾶￔ */
	int CARD_TYPE_CONTINUE_2_TUPLE = 0x08;
	/** ￈�ￅￆﾲﾻﾴ￸ */
	int CARD_TYPE_3_TUPLE = 0x10;
	/** ﾷ￉ﾻ￺ */
	int CARD_TYPE_CONTINUE_3_TUPLE = 0x20;
	/** ￈�ￅￆﾴ￸ﾵﾥ */
	int CARD_TYPE_3_TUPLE_PLUS_SINGLE = 0x40;
	/** ￈�ￅￆﾴ￸ￋﾫ */
	int CARD_TYPE_3_TUPLE_PLUS_2_TUPLE = 0x80;
	/** ﾷ￉ﾻ￺ﾴ￸ﾵﾥ */
	int CARD_TYPE_CONTINUE_3_TUPLE_PLUS_SINGLE = 0x100;
	/** ﾷ￉ﾻ￺ﾴ￸ￋﾫ */
	int CARD_TYPE_CONTINUE_3_TUPPLE_PLUS_2_TUPLE = 0x200;
	/** ﾷ￉ﾻ￺ﾴ￸￁ﾬﾵﾥ */
	int CARD_TYPE_CONTINUE_3_TUPLE_PLUS_CONTINUE_SINGLE = 0x400;
	/** ﾷ￉ﾻ￺ﾴ￸￁ﾬￋﾫ */
	int CARD_TYPE_CONTINUE_3_TUPLE_PLUS_CONTINUE_2_TUPLE = 0x800;
	/** ￋￄￅￆﾴ￸￁ﾽﾵﾥ */
	int CARD_TYPE_4_TUPLE_PLUS_2_SINGLE = 0x1000;
	/** ￋￄￅￆﾴ￸￁ﾽￋﾫ */
	int CARD_TYPE_4_TUPLE_PLUS_2_2_TUPLE = 0x2000;
	
	int MAX_INHAND_POKER_NUM = 20;
	// int CARD_TYPE_
	int CARD_TYPE_4_BOMB = 0x200000;
	int CARD_TYPE_5_BOMB = 0x400000;
	int CARD_TYPE_6_BOMB = 0x800000;
	int CARD_TYPE_7_BOMB = 0x1000000;
	int CARD_TYPE_8_BOMB = 0x2000000;
	int CARD_TYPE_2_JOKER = 0x4000000;
	int CARD_TYPE_4_JOKER = 0x8000000;

	void FirstOutSelectAI(CAIPoker[] pInhandPoker, byte InhandPokerNum);

	void SelectAI(CAIPoker[] pInhandPoker, byte InhandPokerNum,
			CAIPoker[] pLastOutPoker, byte LastOutPokerNum);

	boolean FindFitPokers(CAIPoker[] pInhandPoker, byte InhandPokerNum,
			CAIPoker[] pLastOutPoker, byte LastOutPokerNum);

	void SearchHint(CAIPoker[] pInhandPoker, byte InhandPokerNum,
			CAIPoker[] pLastOutPoker, byte LastOutPokerNum);
	// 设置牌型
	boolean SetCardsType(DDZCards ddzCard);
	
	DDZCards GetNextHint();

	DDZCards GetSelectHint();

	int GetHintCount();
}
