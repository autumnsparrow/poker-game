package com.sky.poke.ai.searchhint;

import com.sky.poke.ai.bean.CAIPoker;
import com.sky.poke.ai.bean.DDZCards;
import com.sky.poke.ai.bean.DDZCards.CARD;

/**
 * Function: 搜索提示类
 * 
 * ClassName:CSearchHint Date: 2013-6-22 下午03:34:33
 * 
 * @author chshyin
 * @version
 * @since JDK 1.6 Copyright (c) 2013, palm-commerce All Rights Reserved.
 */
public class CSearchHintImpl implements CSearchHint {

	private int m_nHintCount;

	private int m_nHintPos;

	private DDZCards[] m_DDZCardsFirstOut;
	private DDZCards[] m_DDZCardsHint;
	private DDZCards[] m_DDZCardsAIMain;
	private DDZCards[] m_DDZCardsAISub;
	private DDZCards[] m_DDZCardsSrc;
	private DDZCards m_DDZCardsAim;

	private int m_nFirstOutCount;
	private int m_nSrcCount;
	private int m_nAimCount;

	private int m_nAIMainCount;
	private int m_nAISubCount;

	private int m_nMainCount;
	private int m_nSubCount;
	private int m_nSubNum;
	
	public CSearchHintImpl(){
		
		m_DDZCardsFirstOut = new DDZCards[50];
		m_DDZCardsHint = new DDZCards[30];
		m_DDZCardsAIMain = new DDZCards[30];
		m_DDZCardsAISub = new DDZCards[50];
		m_DDZCardsSrc = new DDZCards[30];
		m_DDZCardsAim = new DDZCards();
		
		init(m_DDZCardsFirstOut);
		init(m_DDZCardsHint);
		init(m_DDZCardsAIMain);
		init(m_DDZCardsAISub);
		init(m_DDZCardsSrc);
	}
	
	private void init(DDZCards[] ddzCards){
		
		for(int i=0;i<ddzCards.length;i++){
			
			ddzCards[i] = new DDZCards();
		}
	}

	public void SearchHint(byte[] pInhandPoker, byte InhandPokerNum,
			byte[] pLastOutPoker, byte LastOutPokerNum) {

		if (pInhandPoker == null || pLastOutPoker == null) {
			return;
		}

		m_nHintCount = 0;
		m_nHintPos = 0;

	}

	public boolean SetCardsType(DDZCards obCards) {
		if (obCards.CurrentLength == 0) {
			return false;
		}

		obCards.nLen = 0;
		obCards.nLevel = 0;
		obCards.nType = 0;

		if (Check_Card_Type_CARD_TYPE_SINGLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_CONTINUE_SINGLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_2_TUPLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_CONTINUE_2_TUPLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_3_TUPLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_CONTINUE_3_TUPLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_3_TUPLE_PLUS_SINGLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_3_TUPLE_PLUS_2_TUPLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_CONTINUE_3_TUPLE_PLUS_SINGLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_CONTINUE_3_TUPPLE_PLUS_2_TUPLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_CONTINUE_3_TUPLE_PLUS_CONTINUE_SINGLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_CONTINUE_3_TUPLE_PLUS_CONTINUE_2_TUPLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_4_TUPLE_PLUS_2_SINGLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_4_TUPLE_PLUS_2_2_TUPLE(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_4_BOMB(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_5_BOMB(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_6_BOMB(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_7_BOMB(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_8_BOMB(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_2_JOKER(obCards)) {
			return true;
		}

		if (Check_Card_Type_CARD_TYPE_4_JOKER(obCards)) {
			return true;
		}

		return false;
	}

	
	public boolean FindFitPokers(CAIPoker[] pInhandPoker, byte InhandPokerNum,
			CAIPoker[] pLastOutPoker, byte LastOutPokerNum) {

		if (pInhandPoker == null || pLastOutPoker == null) {
			return false;
		}

		if (m_nFirstOutCount > 0) {
			m_DDZCardsAim.ReleaseAll();
			if (m_nFirstOutCount == 1) {
				m_DDZCardsAim.AddCards(m_DDZCardsFirstOut[0]);
			} else {
				GetSamePokers(m_DDZCardsAim);
			}
			return true;
		}
		return false;
	}

	private void GetSamePokers(DDZCards SameDDZCards) {
		if (m_nFirstOutCount <= 0) {
			return;
		}
		DDZCards TempDDZCards = new DDZCards();
		int i, j, k;
		int[] nFlag = new int[20];
		boolean bContain = false;

		SameDDZCards.ReleaseAll();
		SameDDZCards.AddCards(m_DDZCardsFirstOut[0]);
		// TBuf8<64> cardbuf;
		for (i = 1; i < m_nFirstOutCount; i++) {
			// cardbuf.Zero();
			// for (j=0;j<m_DDZCardsFirstOut[i].CurrentLength;j++)
			// {
			// cardbuf.AppendNum(m_DDZCardsFirstOut[i].cards[j].number);
			// cardbuf.Append(_L8(", "));
			// }

			for (j = 0; j < 20; j++) {
				nFlag[j] = 0;
			}
			TempDDZCards.ReleaseAll();
			TempDDZCards.AddCards(SameDDZCards);
			SameDDZCards.ReleaseAll();
			for (j = 0; j < TempDDZCards.getCurrentLength(); j++) {
				bContain = false;
				for (k = 0; k < m_DDZCardsFirstOut[i].getCurrentLength(); k++) {
					CARD[] tempCards = TempDDZCards.getCards();
					CARD[] outCards = m_DDZCardsFirstOut[i].getCards();

					if (nFlag[k] == 0
							&& tempCards[j].getNumber() == outCards[k]
									.getNumber()) {
						nFlag[k] = 1;
						bContain = true;
						break;
					}
				}
				if (bContain) {

					SameDDZCards.AddCard(TempDDZCards.cards[j]);
				}
			}
		}
	}

	
	public void FirstOutSelectAI(CAIPoker[] pInhandPoker, byte InhandPokerNum) {

		if (pInhandPoker == null || InhandPokerNum <= 0)
		{
			return;
		}
		
		m_nAIMainCount = 0;
		m_nAISubCount = 0;
		m_nFirstOutCount = 0;
		
		int i;//,j,k,l;    
		DDZCards PlayerCards = new DDZCards();
		GetCardsFromPokers(pInhandPoker,InhandPokerNum,PlayerCards);
		
		CAIPoker[] m_SelectPoker = new CAIPoker[MAX_INHAND_POKER_NUM];
		int nSelectPokerNum = 0;
		for (i=0; i< InhandPokerNum; i++){
			if (pInhandPoker[i].isSelect())
			{
				m_SelectPoker[nSelectPokerNum].SetPoker(pInhandPoker[i].getPokerValue());
				nSelectPokerNum ++;
			}
		}
		DDZCards SelectCards = new DDZCards();
		GetCardsFromPokers(m_SelectPoker,(byte)nSelectPokerNum,SelectCards);
		
		DDZCards LastPlayerCards = new DDZCards();
		LastPlayerCards.nLen = 0;
		LastPlayerCards.nLevel = 0;
		LastPlayerCards.CurrentLength = 0;
		
		if (nSelectPokerNum <= 1)
		{
			return;
		}
		boolean bTuple = true;
		for (i=0;i<nSelectPokerNum-1;i++)
		{
			if (SelectCards.cards[i].number != SelectCards.cards[i+1].number)
			{
				bTuple = false;
				break;
			}
		}
		if (bTuple)
		{
			return;
		}
		
		//œ÷‘⁄“—æ≠µ√µΩ…œ“ª¬÷µƒ≈∆∫ÕÕÊº“ ÷÷–µƒ≈∆¡À£¨ø…“‘º∆À„¡À
		DDZCards[] m_DDZCards = new DDZCards[100];
		char[] tupleInfo = new char[16];
		int nTempCount = 0;
		int nCardsCount;
		nCardsCount = PlayerCards.GetTupleInfo(tupleInfo);
		
		nTempCount = FirstOutContinue(m_DDZCards,SelectCards,tupleInfo,7,1);
		for (i=0; i<nTempCount; i++)
		{
			if (m_DDZCards[i].Contain(SelectCards,false))
			{
				if (m_nFirstOutCount>=50)
				{
					return;
				}
				m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
				m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(m_DDZCards[i]);
				m_nFirstOutCount++;
			}
		}
		//CWindowBase::WriteLogL(_L8("FirstOutSelectAI0"));
		nTempCount = FirstOutContinue(m_DDZCards,SelectCards,tupleInfo,5,2);
		for (i=0; i<nTempCount; i++)
		{
			if (m_DDZCards[i].Contain(SelectCards,false))
			{
				if (m_nFirstOutCount>=50)
				{
					return;
				}
				m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
				m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(m_DDZCards[i]);
				m_nFirstOutCount++;
			}
		}
		//CWindowBase::WriteLogL(_L8("FirstOutSelectAI1"));
		nTempCount = FirstOutContinue(m_DDZCards,SelectCards,tupleInfo,4,3);
		for (i=0; i<nTempCount; i++)
		{
			if (m_DDZCards[i].Contain(SelectCards,false))
			{
				if (m_nFirstOutCount>=50)
				{
					return;
				}
				m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
				m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(m_DDZCards[i]);
				m_nFirstOutCount++;
			}
		}
		//CWindowBase::WriteLogL(_L8("FirstOutSelectAI2"));
		m_nAIMainCount = Search3TuplePlusSingle(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
		SearchPlusSingle(PlayerCards);
		FirstOut3TuplePlus(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
		//CWindowBase::WriteLogL(_L8("FirstOutSelectAI3"));
		m_nAIMainCount = Search3TuplePlus2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
		SearchPlus2Tuple(PlayerCards);
		FirstOut3TuplePlus(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
		//CWindowBase::WriteLogL(_L8("FirstOutSelectAI4"));
		m_nAIMainCount = FirstOutContinue(m_DDZCardsAIMain,SelectCards,tupleInfo,4,3);
		//m_nAIMainCount = SearchContinue3TuplePlusSingle(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
		//CWindowBase::WriteLogL(_L8("0"));
		SearchPlusSingle(PlayerCards);
		//CWindowBase::WriteLogL(_L8("1"));
		FirstOutSubDDZCards(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
		//CWindowBase::WriteLogL(_L8("FirstOutSelectAI5"));
		m_nAIMainCount = FirstOutContinue(m_DDZCardsAIMain,SelectCards,tupleInfo,4,3);
		//m_nAIMainCount = SearchContinue3TuplePlus2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
		SearchPlus2Tuple(PlayerCards);
		FirstOutSubDDZCards(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
		//CWindowBase::WriteLogL(_L8("FirstOutSelectAI6"));
		m_nAIMainCount = Search4TuplePlusSingle(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
		SearchPlusSingle(PlayerCards);
		FirstOutSubDDZCards(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
		//CWindowBase::WriteLogL(_L8("FirstOutSelectAI7"));
		m_nAIMainCount = Search4TuplePlus2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
		SearchPlus2Tuple(PlayerCards);
		FirstOutSubDDZCards(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);	
		//CWindowBase::WriteLogL(_L8("FirstOutSelectAI8"));

	}
	
	private int FirstOutContinue(DDZCards[] m_DDZCards,DDZCards SelectCards,char[] tupleInfo,int nStartLevel,int nNum){
		int i,j,k;
		int nLen = 0;
		int nLevel = 0;
		boolean bBig = true;
		int nTempCount = 0;
		//DDZCards m_DDZCards[30];
		
		for(nLevel=nStartLevel;nLevel<14;nLevel++)
		{
			nLen = nLevel-2;
			//µ⁄“ª’≈≈∆¥”±»…œº“≥ˆµƒ¥Û“ª µΩK£¨A‘⁄œ¬√Ê¥¶¿Ì
			for(i=nLevel; i<14; i++)
			{
				if(nTempCount >= 100)
				{
					return 0; // modify by lucas
				}
				bBig = true;
				for(j=0; j<nLen; j++)
				{
					if(tupleInfo[i-j] < nNum)
					{
						bBig = false;
						break;
					}
				}
				if(bBig)
				{
					m_DDZCards[nTempCount].ReleaseAll();
					for(j=0; j<nLen; j++)
					{
						for (k=0;k<nNum;k++)
						{
							m_DDZCards[nTempCount].AddCard((char)1, (char)(i-j), false);
						}
					}
					nTempCount ++;
				}
			}
			
			//≤‚ ‘AœÚœ¬µƒÀ≥◊”
			if(nTempCount < 100 && tupleInfo[1] > nNum-1)
			{
				bBig = true;
				for(i=1; i<nLen; i++)
				{
					if(tupleInfo[14-i] < nNum)
					{
						bBig = false;
						break;
					}
				}
				if(bBig)
				{
					m_DDZCards[nTempCount].ReleaseAll();
					//º”A
					for (k=0;k<nNum;k++)
					{
						m_DDZCards[nTempCount].AddCard(1, 1, false);
					}
					//º”∆‰À˚≈∆
					for(i=0; i<nLen-1; i++)
					{
						for (k=0;k<nNum;k++)
						{
							m_DDZCards[nTempCount].AddCard(1, 13-i, false);
						}
					}
					nTempCount ++;
				}
			}
		}
		//≤‚ ‘AœÚœ¬µƒÀ≥◊”
		if(nTempCount < 100 && tupleInfo[1] > nNum-1)
		{
			bBig = true;
			for(i=1; i<12; i++)
			{
				if(tupleInfo[14-i] < nNum)
				{
					bBig = false;
					break;
				}
			}
			if(bBig)
			{
				m_DDZCards[nTempCount].ReleaseAll();
				//º”A
				for (k=0;k<nNum;k++)
				{
					m_DDZCards[nTempCount].AddCard(1, 1, false);
				}
				//º”∆‰À˚≈∆
				for(i=0; i<12-1; i++)
				{
					for (k=0;k<nNum;k++)
					{
						m_DDZCards[nTempCount].AddCard(1, 13-i, false);
					}
				}
				nTempCount ++;
			}
		}
		return nTempCount;
	}
	
	private void FirstOut3TuplePlus(DDZCards[] DDZCardsMain,int nMainCount,DDZCards[] DDZCardsSub,int nSubCount,DDZCards SelectCards)
	{
		int i,j;
		DDZCards MainDDZCards = new DDZCards();
		DDZCards TotalDDZCards = new DDZCards();
		
		for (i=0;i<nMainCount;i++)
		{
			MainDDZCards.ReleaseAll();
			MainDDZCards.AddCards(DDZCardsMain[i]);
			for (j=0;j<nSubCount;j++)
			{
				TotalDDZCards.ReleaseAll();
				TotalDDZCards.AddCards(MainDDZCards);
				TotalDDZCards.AddCards(DDZCardsSub[j]);
				if (TotalDDZCards.Contain(SelectCards))
				{
					if (m_nFirstOutCount>=50)
					{
						return;
					}
					m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
					m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(TotalDDZCards);
					m_nFirstOutCount++;
				}
			}
		}
	}

	private void FirstOutSubDDZCards(DDZCards[] DDZCardsMain,int nMainCount,DDZCards[] DDZCardsSub,int nSubCount,DDZCards SelectCards)
	{
		int i,j;
		int nNum = 0;
		DDZCards MainDDZCards = new DDZCards();
		int[] pSrc = new int[nSubCount];
		for (i=0;i<nMainCount;i++)
		{
			if (DDZCardsMain[i].CurrentLength == 4)
			{
				nNum = 2;
			}
			else
			{
				nNum = DDZCardsMain[i].CurrentLength/3;
			}
			MainDDZCards.ReleaseAll();
			MainDDZCards.AddCards(DDZCardsMain[i]);
			for (j=0;j<nSubCount;j++)
			{
				if(j<nNum)
					pSrc[j] = 1;
				else
					pSrc[j] = 0;
			}		
			
			GetCombinationDDZCards(MainDDZCards,DDZCardsSub,SelectCards,pSrc,nSubCount);
			while (Combination(pSrc,nSubCount))
			{
				GetCombinationDDZCards(MainDDZCards,DDZCardsSub,SelectCards,pSrc,nSubCount);
			}
		}
//		DELETE(pSrc) ;// add by lucas
	}
	
	private boolean Combination(int[] pSrc,int nSubCount)
	{
		int nCount = 0;
		int i,j;
		for(i=0;i<nSubCount-1;i++)
		{
			if(pSrc[i]==1 && pSrc[i+1]==0)
			{
				nCount = 0;
				pSrc[i]=0;
				pSrc[i+1]=1;
				
				for(j=0;j<i;j++)
				{
					if (pSrc[j]==1)
					{
						nCount++;
					}
				}
				for(j=0;j<i;j++)
				{
					if (j<nCount)
					{
						pSrc[j]=1;
					}
					else
					{
						pSrc[j]=0;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	private void GetCombinationDDZCards(DDZCards MainDDZCards,DDZCards[] DDZCardsSub,DDZCards SelectCards,int[] pSrc,int nSubCount)// «Û¥” ˝◊Èaµƒn∏ˆ‘™Àÿ÷–»°m∏ˆ‘™Àÿµƒ◊È∫œ
	{
		int i;
		boolean bContain = false;
		DDZCards TotalDDZCards = new DDZCards();
		
		TotalDDZCards.ReleaseAll();
		TotalDDZCards.AddCards(MainDDZCards);
		for(i=0;i<nSubCount;i++)
		{
			if (pSrc[i]==1)
			{
				if (MainDDZCards.Contain(DDZCardsSub[i]))
				{
					bContain = true;
					break;
				}
				else
				{
					TotalDDZCards.AddCards(DDZCardsSub[i]);
				}
			}
		}
		if (!bContain && TotalDDZCards.Contain(SelectCards))
		{
			if (m_nFirstOutCount>=50)
			{
				return;
			}
			//≈–∂œ «∑Ò“—æ≠∞¸∫¨”–ÕÍ»´“ª—˘µƒ◊È∫œ
			bContain = false;
			for (i=0;i<m_nFirstOutCount;i++)
			{
				if (m_DDZCardsFirstOut[i].Contain(TotalDDZCards,true))
				{
					bContain = true;
					break;
				}
			}
			if (!bContain)
			{
				m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
				m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(TotalDDZCards);
				m_nFirstOutCount++;
			}
		}
	}
	
	private void SearchPlusSingle(DDZCards PlayerCards){
		
		SearchPlusSingle(PlayerCards, false);
	}
	
	private void SearchPlusSingle(DDZCards PlayerCards,boolean bSame)
	{
		SortByTuple(PlayerCards);
		boolean bContain = false;
		int nPlayerLevel;
		int nLastLevel = 0;
		int[] nTempLevel = new int[MAX_INHAND_POKER_NUM];
		int nLevelNum = 0;
		int[] nSelect = new int[MAX_INHAND_POKER_NUM];
		int i,j,k;
		
		for (i=0;i<MAX_INHAND_POKER_NUM;i++)
		{
			nSelect[i] = 0;
		}
		
		nLevelNum = m_DDZCardsAIMain[0].CurrentLength/3;
		m_nAISubCount = 0;
		for (i=0; i<m_nAIMainCount; i++)
		{
			for( j=0; j< nLevelNum; j++)
			{
				nTempLevel[j] = GetLevel(m_DDZCardsAIMain[i].cards[j*3]);
			}
			for(j=PlayerCards.CurrentLength-1; j>=0; j--)
			{
				if(nSelect[j] == 0 && m_nAISubCount < 50)
				{
					bContain = false;
					nPlayerLevel = GetLevel(PlayerCards.cards[j]);
					if (nPlayerLevel < 14)
					{
						for(k=0; k<nLevelNum; k++)
						{
							if(nTempLevel[k] == nPlayerLevel)
							{
								bContain = true;
								break;
							}
						}
						if(!bContain)
						{
							nSelect[j] = 1;
							if (bSame)
							{
								nLastLevel = nPlayerLevel;
								m_DDZCardsAISub[m_nAISubCount].ReleaseAll();
								m_DDZCardsAISub[m_nAISubCount].AddCard(PlayerCards.cards[j]);
								m_nAISubCount ++;
							}
							else
							{
								if (nLastLevel != nPlayerLevel)
								{
									nLastLevel = nPlayerLevel;
									m_DDZCardsAISub[m_nAISubCount].ReleaseAll();
									m_DDZCardsAISub[m_nAISubCount].AddCard(PlayerCards.cards[j]);
									m_nAISubCount ++;
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void SearchPlus2Tuple(DDZCards PlayerCards)
	{
		//œ÷‘⁄“—æ≠µ√µΩ…œ“ª¬÷µƒ≈∆∫ÕÕÊº“ ÷÷–µƒ≈∆¡À£¨ø…“‘º∆À„¡À
		char tupleInfo[] = new char[16];
		int nCardsCount;
		nCardsCount = PlayerCards.GetTupleInfo(tupleInfo);
		
		DDZCards m_DDZCardsTemp[] = new DDZCards[30];
		int	nTempCount = 0;
		
		DDZCards cardsTemp = new DDZCards();
		int i,j,k;
		
		//±È¿˙’≈ ˝
		for(i=2; i<9; i++)
		{
			//3-K
			for(j=3; j<14; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					SetCardsType(cardsTemp);
					if(nTempCount < 30)
					{
						m_DDZCardsTemp[nTempCount].ReleaseAll();
						m_DDZCardsTemp[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
			//A-2
			for(j=1; j<3; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					SetCardsType(cardsTemp);
					if(nTempCount < 30)
					{
						m_DDZCardsTemp[nTempCount].ReleaseAll();
						m_DDZCardsTemp[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
		}
		
		boolean bContain = false;
		int nPlayerLevel;
		int nTempLevel[] = new int[MAX_INHAND_POKER_NUM];
		int nLevelNum = 0;
		int nSelect[] = new int[MAX_INHAND_POKER_NUM];
		for (i=0;i<MAX_INHAND_POKER_NUM;i++)
		{
			nSelect[i] = 0;
		}
		
		nLevelNum = m_DDZCardsAIMain[0].CurrentLength/3;
		m_nAISubCount = 0;
		for (i=0; i<m_nAIMainCount; i++)
		{
			for( j=0; j< nLevelNum; j++)
			{
				nTempLevel[j] = GetLevel(m_DDZCardsAIMain[i].cards[j*3]);
			}
			for(j=nTempCount-1; j>=0; j--)
			{
				if(nSelect[j] == 0 && m_nAISubCount < 50)
				{
					bContain = false;
					nPlayerLevel = GetLevel(m_DDZCardsTemp[j].cards[0]);
					for(k=0; k<nLevelNum; k++)
					{
						if(nTempLevel[k] == nPlayerLevel)
						{
						 	bContain = true;
						 	break;
						}
					}
					if(!bContain)
					{
						nSelect[j] = 1;
						m_DDZCardsAISub[m_nAISubCount].ReleaseAll();
						m_DDZCardsAISub[m_nAISubCount].AddCards(m_DDZCardsTemp[j]);
						m_nAISubCount ++;
					}
				}
			}
		}
	}
	
	private int Search3TuplePlusSingle(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char[] tupleInfo,boolean bPlus)
	{
		int i,j,k;
		int nTempCount = 0;
		DDZCards cardsTemp = new DDZCards();
		DDZCards cardsTempNoPlus = new DDZCards();
		//±È¿˙’≈ ˝
		for(i=3; i<9; i++)
		{
			for(j=3; j<14; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTempNoPlus.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1,j,false);
					cardsTempNoPlus.AddCard(1,j,false);
					cardsTempNoPlus.AddCard(1,j,false);
					
					//’““ª∏ˆµ•’≈µƒ
					SortByTuple(PlayerCards);
					for(k=PlayerCards.CurrentLength-1; k>=0; k--)
					{
						if(PlayerCards.cards[k].number != j)
						{
							cardsTemp.AddCard(PlayerCards.cards[k]);
							break;
						}
					}
					
					if(cardsTemp.CurrentLength == 4)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
			for(j=1; j<3; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTempNoPlus.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1,j,false);
					cardsTempNoPlus.AddCard(1,j,false);
					cardsTempNoPlus.AddCard(1,j,false);
					
					//’““ª∏ˆµ•’≈µƒ
					SortByTuple(PlayerCards);
					for(k=PlayerCards.CurrentLength-1; k>=0; k--)
					{
						if(PlayerCards.cards[k].number != j)
						{
							cardsTemp.AddCard(PlayerCards.cards[k]);
							break;
						}
					}
					
					if(cardsTemp.CurrentLength == 4)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
		}
		return nTempCount;
	}
	
	private int Search3TuplePlus2Tuple(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{
		int i,j,k,l;
		int nTempCount = 0;
		DDZCards cardsTemp = new DDZCards();
		DDZCards cardsTempNoPlus = new DDZCards();
		//±È¿˙’≈ ˝
		for(i=3; i<9; i++)
		{
			for(j=3; j<14; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTempNoPlus.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1,j,false);
					cardsTempNoPlus.AddCard(1,j,false);
					cardsTempNoPlus.AddCard(1,j,false);
					
					//’““ª∂‘µƒ
					for(k=2; k<9; k++)
					{
						for(l=3; l<14; l++)
						{
							if(tupleInfo[l] == k && l != j)
							{
								cardsTemp.AddCard(1, l, false);
								cardsTemp.AddCard(1, l, false);
								
								l=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
								k=100;
							}
						}
						if(k != 100)
						{
							for(l=1; l<3; l++)
							{
								if(tupleInfo[l] == k && l != j)
								{
									cardsTemp.AddCard(1, l, false);
									cardsTemp.AddCard(1, l, false);
									
									l=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
									k=100;
								}
							}
						}
						
					}
					if(cardsTemp.CurrentLength == 5)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
			for(j=1; j<3; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTempNoPlus.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1,j,false);
					cardsTempNoPlus.AddCard(1,j,false);
					cardsTempNoPlus.AddCard(1,j,false);
					
					//’““ª∂‘µƒ
					for(k=2; k<9; k++)
					{
						for(l=3; l<14; l++)
						{
							if(tupleInfo[l] == k && l != j)
							{
								cardsTemp.AddCard(1, l, false);
								cardsTemp.AddCard(1, l, false);
								
								l=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
								k=100;
							}
						}
						if(k != 100)
						{
							for(l=1; l<3; l++)
							{
								if(tupleInfo[l] == k && l != j)
								{
									cardsTemp.AddCard(1, l, false);
									cardsTemp.AddCard(1, l, false);
									
									l=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
									k=100;
								}
							}
						}
						
					}
					if(cardsTemp.CurrentLength == 5)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
		}
		return nTempCount;
	}

	private int SearchContinue3TuplePlusSingle(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{
		int i,j,k,l;
		int nLen = LastPlayerCards.nLen;
		int nLevel = LastPlayerCards.nLevel;
		int nTempCount = 0;
		DDZCards cardsTemp = new DDZCards();
		DDZCards cardsTempNoPlus = new DDZCards();
		
		//µ⁄“ª’≈–°”⁄A£¨÷¡…ŸŒ™333444,≥§∂»÷¡…ŸŒ™2£¨◊Ó∂‡Œ™12
		if(nLevel<12 && nLevel>1 && nLen>1 && nLen<13)
		{
			//µ⁄“ª’≈≈∆¥”±»…œº“≥ˆµƒ¥Û“ª µΩK£¨A‘⁄œ¬√Ê¥¶¿Ì
			for(i=nLevel+2+1; i<14; i++)
			{
				cardsTemp.ReleaseAll();
				cardsTempNoPlus.ReleaseAll();
				if(nTempCount >= 30)
				{
					break;
				}
				
				boolean bBig = true;
				for(j=0; j<nLen; j++)
				{
					if(tupleInfo[i-j] < 3)
					{
						bBig = false;
						break;
					}
				}
				if(bBig)
				{
					for(j=0; j<nLen; j++)
					{
						cardsTemp.AddCard(1, i-j, false);
						cardsTemp.AddCard(1, i-j, false);
						cardsTemp.AddCard(1, i-j, false);
						cardsTempNoPlus.AddCard(1, i-j, false);
						cardsTempNoPlus.AddCard(1, i-j, false);
						cardsTempNoPlus.AddCard(1, i-j, false);
					}
					
					//’“µ•’≈µƒ
					SortByTuple(PlayerCards);
					
					for(l=0; l<nLen; l++)
					{
						for(k=PlayerCards.CurrentLength-1; k>=0; k--)
						{
							boolean bDifference = true;
							for(int m=0; m<cardsTemp.CurrentLength; m++)
							{
								if(cardsTemp.cards[m].number == PlayerCards.cards[k].number)
								{
									bDifference = false;
								}
							}
							
							if(bDifference)
							{
								cardsTemp.AddCard(PlayerCards.cards[k]);
								break;
							}
						}
					}						
					if(cardsTemp.CurrentLength == nLen*4)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
			
			
			cardsTemp.ReleaseAll();
			cardsTempNoPlus.ReleaseAll();
			//≤‚ ‘AœÚœ¬µƒÀ≥◊”
			if(nTempCount < 30 && tupleInfo[1] > 0)
			{
				boolean bBig = true;
				for(i=1; i<nLen; i++)
				{
					if(tupleInfo[14-i] < 3)
					{
						bBig = false;
						break;
					}
				}
				if(tupleInfo[1] < 3)
				{
					bBig = false;
				}
				
				if(bBig)
				{
					//º”A
					cardsTemp.AddCard(1, 1, false);
					cardsTemp.AddCard(1, 1, false);
					cardsTemp.AddCard(1, 1, false);
					cardsTempNoPlus.AddCard(1, 1, false);
					cardsTempNoPlus.AddCard(1, 1, false);
					cardsTempNoPlus.AddCard(1, 1, false);
					//º”∆‰À˚≈∆
					for(i=0; i<nLen-1; i++)
					{
						cardsTemp.AddCard(1, 13-i, false);
						cardsTemp.AddCard(1, 13-i, false);
						cardsTemp.AddCard(1, 13-i, false);
						cardsTempNoPlus.AddCard(1, 13-i, false);
						cardsTempNoPlus.AddCard(1, 13-i, false);
						cardsTempNoPlus.AddCard(1, 13-i, false);
					}
					
					//’“µ•’≈µƒ
					SortByTuple(PlayerCards);
					
					for(l=0; l<nLen; l++)
					{
						for(k=PlayerCards.CurrentLength-1; k>=0; k--)
						{
							boolean bDifference = true;
							for(int m=0; m<cardsTemp.CurrentLength; m++)
							{
								if(cardsTemp.cards[m].number == PlayerCards.cards[k].number)
								{
									bDifference = false;
								}
							}
							
							if(bDifference)
							{
								cardsTemp.AddCard(PlayerCards.cards[k]);
								break;
							}
						}
					}						
					if(cardsTemp.CurrentLength == nLen*4)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
					
				}
			}
		}
		return nTempCount;
	}

	private int SearchContinue3TuplePlus2Tuple(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{
		int i,j,k,l;
		int nLen = LastPlayerCards.nLen;
		int nLevel = LastPlayerCards.nLevel;
		int nTempCount = 0;
		DDZCards cardsTemp = new DDZCards();
		DDZCards cardsTempNoPlus = new DDZCards();
		
		//µ⁄“ª’≈–°”⁄A£¨÷¡…ŸŒ™333444,≥§∂»÷¡…ŸŒ™2£¨◊Ó∂‡Œ™12
		if(nLevel<12 && nLevel>1 && nLen>1 && nLen<13)
		{
			//µ⁄“ª’≈≈∆¥”±»…œº“≥ˆµƒ¥Û“ª µΩK£¨A‘⁄œ¬√Ê¥¶¿Ì
			for(i=nLevel+2+1; i<14; i++)
			{
				cardsTemp.ReleaseAll();
				cardsTempNoPlus.ReleaseAll();
				if(nTempCount >= 30)
				{
					break;
				}
				
				boolean bBig = true;
				for(j=0; j<nLen; j++)
				{
					if(tupleInfo[i-j] < 3)
					{
						bBig = false;
						break;
					}
				}
				if(bBig)
				{
					for(j=0; j<nLen; j++)
					{
						cardsTemp.AddCard(1, i-j, false);
						cardsTemp.AddCard(1, i-j, false);
						cardsTemp.AddCard(1, i-j, false);
						cardsTempNoPlus.AddCard(1, i-j, false);
						cardsTempNoPlus.AddCard(1, i-j, false);
						cardsTempNoPlus.AddCard(1, i-j, false);
					}
					
					//’“∂‘						
					for(l=0; l<nLen; l++)
					{
						for(int x=2; x<9; x++)
						{
							for(int y=1; y<14; y++)
							{
								if(tupleInfo[y] == x)
								{
									boolean bDifference = true;
									for(int m=0; m<cardsTemp.CurrentLength; m++)
									{
										if(cardsTemp.cards[m].number == y)
										{
											bDifference = false;
										}
									}
									
									if(bDifference)
									{
										cardsTemp.AddCard(1, y, false);
										cardsTemp.AddCard(1, y, false);
										
										x=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
										y=100;
									}
								}
							}
						}
					}	
					if(cardsTemp.CurrentLength == nLen*5)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
			
			
			cardsTemp.ReleaseAll();
			cardsTempNoPlus.ReleaseAll();
			//≤‚ ‘AœÚœ¬µƒÀ≥◊”
			if(nTempCount < 30 && tupleInfo[1] > 0)
			{
				boolean bBig = true;
				for(i=1; i<nLen; i++)
				{
					if(tupleInfo[14-i] < 3)
					{
						bBig = false;
						break;
					}
				}
				if(tupleInfo[1] < 3)
				{
					bBig = false;
				}
				if(bBig)
				{
					//º”A
					cardsTemp.AddCard(1, 1, false);
					cardsTemp.AddCard(1, 1, false);
					cardsTemp.AddCard(1, 1, false);
					cardsTempNoPlus.AddCard(1, 1, false);
					cardsTempNoPlus.AddCard(1, 1, false);
					cardsTempNoPlus.AddCard(1, 1, false);
					//º”∆‰À˚≈∆
					for(i=0; i<nLen-1; i++)
					{
						cardsTemp.AddCard(1, 13-i, false);
						cardsTemp.AddCard(1, 13-i, false);
						cardsTemp.AddCard(1, 13-i, false);
						cardsTempNoPlus.AddCard(1, 13-i, false);
						cardsTempNoPlus.AddCard(1, 13-i, false);
						cardsTempNoPlus.AddCard(1, 13-i, false);
					}
					//’“∂‘						
					for(l=0; l<nLen; l++)
					{
						for(int x=2; x<9; x++)
						{
							for(int y=1; y<14; y++)
							{
								if(tupleInfo[y] == x)
								{
									boolean bDifference = true;
									for(int m=0; m<cardsTemp.CurrentLength; m++)
									{
										if(cardsTemp.cards[m].number == y)
										{
											bDifference = false;
										}
									}
									if(bDifference)
									{
										cardsTemp.AddCard(1, y, false);
										cardsTemp.AddCard(1, y, false);
										
										x=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
										y=100;
									}
								}
							}
						}
					}							
					
					if(cardsTemp.CurrentLength == nLen*5)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
		}
		return nTempCount;
	}

	private int Search4TuplePlusSingle(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{
		int i,j,k,l;
		int nTempCount = 0;
		DDZCards cardsTemp = new DDZCards();
		DDZCards cardsTempNoPlus = new DDZCards();
		//±È¿˙’≈ ˝
		for(i=4; i<9; i++)
		{
			for(j=3; j<14; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTempNoPlus.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					
					//’“¡Ω∏ˆµ•’≈µƒ
					SortByTuple(PlayerCards);
					for(k=PlayerCards.CurrentLength-1; k>=0; k--)
					{
						if(PlayerCards.cards[k].number != j)
						{
							cardsTemp.AddCard(PlayerCards.cards[k]);
							break;
						}
					}
					//						SortByTuple(PlayerCards);
					for(k=PlayerCards.CurrentLength-1; k>=0; k--)
					{
						if(PlayerCards.cards[k].number != j
						   && PlayerCards.cards[k].number != cardsTemp.cards[4].number)
						{
							cardsTemp.AddCard(PlayerCards.cards[k]);
							break;
						}
					}
					
					if(cardsTemp.CurrentLength == 6)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
			for(j=1; j<3; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTempNoPlus.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					
					//’“¡Ω∏ˆµ•’≈µƒ
					SortByTuple(PlayerCards);
					for(k=PlayerCards.CurrentLength-1; k>=0; k--)
					{
						if(PlayerCards.cards[k].number != j)
						{
							cardsTemp.AddCard(PlayerCards.cards[k]);
							break;
						}
					}
					//				SortByTuple(PlayerCards);
					for(k=PlayerCards.CurrentLength-1; k>=0; k--)
					{
						if(PlayerCards.cards[k].number != j
						   && PlayerCards.cards[k].number != cardsTemp.cards[4].number)
						{
							cardsTemp.AddCard(PlayerCards.cards[k]);
							break;
						}
					}
					
					if(cardsTemp.CurrentLength == 6)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
		}
		return nTempCount;
	}

	private int Search4TuplePlus2Tuple(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{			
		int i,j,k,l;
		int nTempCount = 0;
		DDZCards cardsTemp = new DDZCards();
		DDZCards cardsTempNoPlus = new DDZCards();
		//±È¿˙’≈ ˝
		for(i=4; i<9; i++)
		{
			for(j=3; j<14; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTempNoPlus.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					
					//’““ª∂‘µƒ
					for(k=2; k<9; k++)
					{
						for(l=3; l<14; l++)
						{
							if(tupleInfo[l] == k && l != j)
							{
								cardsTemp.AddCard(1, l, false);
								cardsTemp.AddCard(1, l, false);
								
								l=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
								k=100;
							}
						}
						if(k != 100)	//»Áπ˚…œ√Ê√ª’“µΩ£¨‘Ú’“A∫Õ2
						{
							for(l=1; l<3; l++)
							{
								if(tupleInfo[l] == k && l != j)
								{
									cardsTemp.AddCard(1, l, false);
									cardsTemp.AddCard(1, l, false);
									
									l=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
									k=100;
								}
							}
						}
					}
					
					//’“¡Ì“ª∂‘µƒ
					for(k=2; k<9; k++)
					{
						for(l=1; l<14; l++)
						{
							if(tupleInfo[l] == k && l != j
							   && l != cardsTemp.cards[cardsTemp.CurrentLength-1].number)
							{
								cardsTemp.AddCard(1, l, false);
								cardsTemp.AddCard(1, l, false);
								
								l=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
								k=100;
							}
						}
						if(k != 100)
						{
							for(l=1; l<14; l++)
							{
								if(tupleInfo[l] == k && l != j
								   && l != cardsTemp.cards[cardsTemp.CurrentLength-1].number)
								{
									cardsTemp.AddCard(1, l, false);
									cardsTemp.AddCard(1, l, false);
									
									l=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
									k=100;
								}
							}
						}
					}
					
					if(cardsTemp.CurrentLength == 8)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
			//ºÏ≤ÈA,2
			for(j=1; j<3; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTempNoPlus.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					cardsTempNoPlus.AddCard(1, j, false);
					
					//’““ª∂‘µƒ
					for(k=2; k<9; k++)
					{
						for(l=1; l<14; l++)
						{
							if(tupleInfo[l] == k && l != j)
							{
								cardsTemp.AddCard(1, l, false);
								cardsTemp.AddCard(1, l, false);
								
								l=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
								k=100;
							}
						}
					}
					
					//’“¡Ì“ª∂‘µƒ
					for(k=2; k<9; k++)
					{
						for(l=1; l<14; l++)
						{
							if(tupleInfo[l] == k && l != j
							   && l != cardsTemp.cards[cardsTemp.CurrentLength-1].number)
							{
								cardsTemp.AddCard(1, l, false);
								cardsTemp.AddCard(1, l, false);
								
								l=100;	//ÕÀ≥ˆ¡Ω÷ÿ—≠ª∑£¨≤ªœÎ”√goto
								k=100;
							}
						}
					}
					
					
					if(cardsTemp.CurrentLength == 8)
					{
						SetCardsType(cardsTemp);
						if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
						{
							m_DDZCards[nTempCount].ReleaseAll();
							if(bPlus)
								m_DDZCards[nTempCount].AddCards(cardsTemp);
							else
								m_DDZCards[nTempCount].AddCards(cardsTempNoPlus);
							nTempCount ++;
						}
					}
				}
			}
		}
		return nTempCount;
	}

	private void GetCardsFromPokers(CAIPoker[] pPoker, byte pokerNum,
			DDZCards pCards) {
		
		CARD tempCard;

		for (int i = 0; i < pokerNum; i++) {
			tempCard = CARD.char2CARD((char) pPoker[i].getPokerValue());
			pCards.AddCard(tempCard);
		}
	}

	
	public int GetHintCount() {

		return m_nHintCount;
	}

	
	public DDZCards GetNextHint() {

		if (m_nHintPos >= m_nHintCount)
			m_nHintPos = 0;

		return m_DDZCardsHint[m_nHintPos++];
	}

	
	public DDZCards GetSelectHint() {

		return m_DDZCardsAim;
	}
	
	private int GetLevel( CARD card)
	{
		return GetLevel( card.number );
	}
	
	private int GetLevel(int number){
		
		return GetLevel((char)number);
	}
	
	private int GetLevel(char number)
	{
		if( number >= 0 && number >= 16 )
		{
			return 0;
		}
		
		switch( number )
		{
			case 3:	case 4:case 5: case 6:case 7:case 8:
			case 9: case 10:case 11:case 12:case 13:
				return number - 2;
			case 1:case 2:
				return 11 + number;
			case 14:case 15:
				return number;
		}
		
		return 0;
	}
	
	private void SortByLevel( DDZCards obCards )
	{
		if( 2 > obCards.CurrentLength )
		{
			return;
		}
		
		int i = 0;
		
		while( i < obCards.CurrentLength )
		{
			if( GetLevel( obCards.cards[i] ) < GetLevel( obCards.cards[i + 1] ) )
			{
				obCards.swap( i , i + 1 );
				i = 0;
			}
			else
			{
				i ++;
				
				if( i == obCards.CurrentLength - 1 )
				{
					i ++;
				}
			}
		}
	}
	
	private void SortByTuple( DDZCards obCards)
	{
		if( obCards.CurrentLength < 2 )
		{
			return;
		}
		
		DDZCards dupcards = new DDZCards();
		dupcards.ReleaseAll();
		dupcards.AddCards( obCards );
		obCards.ReleaseAll();
		
		DDZCards tempcards = new DDZCards();
		char[] tupleInfo = new char[16];
		
		dupcards.GetTupleInfo( tupleInfo );
		
		int i , j , k;
		for( i = 8 ; i > 0 ; i -- )
		{
			tempcards.ReleaseAll();
			
			for( j = 0 ; j < 16 ; j ++ )
			{
				if( tupleInfo[j] == i )
				{
					for( k = 0 ; k < dupcards.CurrentLength ; k ++ )
					{
						if( dupcards.cards[k].number == j )
						{
							tempcards.AddCard( dupcards.cards[k] );
						}
					}
				}
			}
			
			if( tempcards.CurrentLength != 0)
			{
				SortByLevel( tempcards );
				obCards.AddCards( tempcards );
			}
		}
	}

	
	public void SearchHint(CAIPoker[] pInhandPoker, byte InhandPokerNum,
			CAIPoker[] pLastOutPoker, byte LastOutPokerNum) {

		if (pInhandPoker == null || pLastOutPoker == null)
		{
			return;
		}
		
		m_nHintCount = 0;
		m_nHintPos = 0;
		   
		DDZCards PlayerCards = new DDZCards();
		GetCardsFromPokers(pInhandPoker,InhandPokerNum,PlayerCards);
		
		DDZCards LastPlayerCards = new DDZCards();
		GetCardsFromPokers(pLastOutPoker,LastOutPokerNum,LastPlayerCards);
		
		if (!SetCardsType( LastPlayerCards))
		{
			return;
		}
		
		char tupleInfo[] = new char[16];
		int nCardsCount;
		nCardsCount = PlayerCards.GetTupleInfo(tupleInfo);
		
		switch(LastPlayerCards.nType) 
		{
			case CARD_TYPE_SINGLE:
			{
				int nLastLevel = 0;
				int nLastPlayerCardsLevel = LastPlayerCards.nLevel;
				SortByTuple(PlayerCards);
				for(int i=PlayerCards.CurrentLength-1; i>=0; i--)
				{
					if(GetLevel(PlayerCards.cards[i]) > nLastPlayerCardsLevel
					   && nLastLevel != GetLevel(PlayerCards.cards[i])
					   && m_nHintCount < 30)
					{
						nLastLevel = GetLevel(PlayerCards.cards[i]);
						m_DDZCardsHint[m_nHintCount].ReleaseAll();
						m_DDZCardsHint[m_nHintCount].AddCard(PlayerCards.cards[i]);
						m_nHintCount ++;
					}
				}
			}
				break;
			case CARD_TYPE_CONTINUE_SINGLE:
			{
				m_nHintCount = SearchContinueSingle(PlayerCards,LastPlayerCards,m_DDZCardsHint,tupleInfo,true);
				
			}
				break;
			case CARD_TYPE_2_TUPLE:
			{
				m_nHintCount = Search2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsHint,tupleInfo,true);
				
				
			}
				break;
			case CARD_TYPE_CONTINUE_2_TUPLE:
			{
				m_nHintCount = SearchContinue2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsHint,tupleInfo,true);
				
				
			}
				break;
			case CARD_TYPE_3_TUPLE:
			{
				m_nHintCount = Search3Tuple(PlayerCards,LastPlayerCards,m_DDZCardsHint,tupleInfo,true);
				
				
			}
				break;
			case CARD_TYPE_CONTINUE_3_TUPLE:
			{
				m_nHintCount = SearchContinue3Tuple(PlayerCards, LastPlayerCards, m_DDZCardsHint,tupleInfo,true);
				
				
			}
				break;
			case CARD_TYPE_3_TUPLE_PLUS_SINGLE:
			{
				m_nHintCount = Search3TuplePlusSingle(PlayerCards,LastPlayerCards,m_DDZCardsHint,tupleInfo,true);

			}
				break;
			case CARD_TYPE_3_TUPLE_PLUS_2_TUPLE:
			{
				m_nHintCount = Search3TuplePlus2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsHint,tupleInfo,true);

			}
				break;
			case CARD_TYPE_CONTINUE_3_TUPLE_PLUS_SINGLE:
			{
				m_nHintCount = SearchContinue3TuplePlusSingle(PlayerCards,LastPlayerCards,m_DDZCardsHint,tupleInfo,true);

			}
				break;
			case CARD_TYPE_CONTINUE_3_TUPPLE_PLUS_2_TUPLE:
			{
				m_nHintCount = SearchContinue3TuplePlus2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsHint,tupleInfo,true);

			}
				break;
			case CARD_TYPE_4_TUPLE_PLUS_2_SINGLE:
			{
				m_nHintCount = Search4TuplePlusSingle(PlayerCards,LastPlayerCards,m_DDZCardsHint,tupleInfo,true);

			}
				break;
			case CARD_TYPE_4_TUPLE_PLUS_2_2_TUPLE:
			{
				m_nHintCount = Search4TuplePlus2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsHint,tupleInfo,true);

			}
				break;
				
			case CARD_TYPE_4_BOMB:
				break;
			case CARD_TYPE_5_BOMB:
				break;
			case CARD_TYPE_6_BOMB:
				break;
			case CARD_TYPE_7_BOMB:
				break;
			case CARD_TYPE_8_BOMB:
				break;
			case CARD_TYPE_2_JOKER:
				break;
			case CARD_TYPE_4_JOKER:
				break;
			default:
				break;
		}
		m_nHintCount = SearchBomb(LastPlayerCards, PlayerCards,m_DDZCardsHint,m_nHintCount);

	}

	
	public void SelectAI(CAIPoker[] pInhandPoker, byte InhandPokerNum,
			CAIPoker[] pLastOutPoker, byte LastOutPokerNum) {

		m_nFirstOutCount = 0;
		m_nMainCount = 0;
		m_nSubCount = 0;
		
		if (pInhandPoker == null || pLastOutPoker == null)
		{
			return;
		}
		
		{
			int i;//j,k,l; 
			
			m_nAIMainCount = 0;
			m_nAISubCount = 0;
			
			DDZCards PlayerCards = new DDZCards();
			GetCardsFromPokers(pInhandPoker,InhandPokerNum,PlayerCards);
			
			CAIPoker[] m_SelectPoker = new CAIPoker[MAX_INHAND_POKER_NUM];
			int nSelectPokerNum = 0;
			for (i=0; i< InhandPokerNum; i++)
			{
				if (pInhandPoker[i].isSelect())
				{
					m_SelectPoker[nSelectPokerNum].SetPoker(pInhandPoker[i].getPokerValue());
					nSelectPokerNum ++;
				}
			}
			DDZCards SelectCards = new DDZCards();
			GetCardsFromPokers(m_SelectPoker,(byte) nSelectPokerNum,SelectCards);
			
			DDZCards LastPlayerCards = new DDZCards();
			GetCardsFromPokers(pLastOutPoker,LastOutPokerNum,LastPlayerCards);

			if (!SetCardsType( LastPlayerCards) || nSelectPokerNum == 0)
			{
				return;
			}
			
			DDZCards pDDZCardsBomb[] = new DDZCards[5];
			int nBomb = 0;
			nBomb = SearchBomb(LastPlayerCards, PlayerCards,pDDZCardsBomb,nBomb);
			for (i=0;i<nBomb;i++)
			{
				if(pDDZCardsBomb[i].Contain(SelectCards))
				{
					if (m_nFirstOutCount>=50)
					{
						return;
					}
					m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
					m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(pDDZCardsBomb[i]);
					m_nFirstOutCount++;
				}
			}
			
			char tupleInfo[] = new char[16];
			int nCardsCount;
			nCardsCount = PlayerCards.GetTupleInfo(tupleInfo);
			
			switch(LastPlayerCards.nType) 
			{
				case CARD_TYPE_SINGLE:
					if (nSelectPokerNum==1)
					{
						int nLastPlayerCardsLevel = LastPlayerCards.nLevel;
						if(GetLevel(SelectCards.cards[0]) > nLastPlayerCardsLevel)
						{
							if (m_nFirstOutCount>=50)
							{
								return;
							}
							m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
							m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(SelectCards);
							m_nFirstOutCount++;
						}
					}
					break;
				case CARD_TYPE_CONTINUE_SINGLE:
					m_nAIMainCount = SearchContinueSingle(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					for (i=0; i<m_nAIMainCount; i++)
					{
						if (m_DDZCardsAIMain[i].Contain(SelectCards))
						{
							if (m_nFirstOutCount>=50)
							{
								return;
							}
							m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
							m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(m_DDZCardsAIMain[i]);
							m_nFirstOutCount++;
						}
					}
					break;
				case CARD_TYPE_2_TUPLE:
					m_nAIMainCount = Search2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					for (i=0; i<m_nAIMainCount; i++)
					{
						if (m_DDZCardsAIMain[i].Contain(SelectCards))
						{
							if (m_nFirstOutCount>=50)
							{
								return;
							}
							m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
							m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(m_DDZCardsAIMain[i]);
							m_nFirstOutCount++;
						}
					}
					break;
				case CARD_TYPE_CONTINUE_2_TUPLE:
					m_nAIMainCount = SearchContinue2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					for (i=0; i<m_nAIMainCount; i++)
					{
						if (m_DDZCardsAIMain[i].Contain(SelectCards))
						{
							if (m_nFirstOutCount>=50)
							{
								return;
							}
							m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
							m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(m_DDZCardsAIMain[i]);
							m_nFirstOutCount++;
						}
					}
					break;
				case CARD_TYPE_3_TUPLE:
					m_nAIMainCount = Search3Tuple(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					for (i=0; i<m_nAIMainCount; i++)
					{
						if (m_DDZCardsAIMain[i].Contain(SelectCards))
						{
							if (m_nFirstOutCount>=50)
							{
								return;
							}
							m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
							m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(m_DDZCardsAIMain[i]);
							m_nFirstOutCount++;
						}
					}
					break;
				case CARD_TYPE_CONTINUE_3_TUPLE:
					m_nAIMainCount = SearchContinue3Tuple(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					for (i=0; i<m_nAIMainCount; i++)
					{
						if (m_DDZCardsAIMain[i].Contain(SelectCards))
						{
							if (m_nFirstOutCount>=50)
							{
								return;
							}
							m_DDZCardsFirstOut[m_nFirstOutCount].ReleaseAll();
							m_DDZCardsFirstOut[m_nFirstOutCount].AddCards(m_DDZCardsAIMain[i]);
							m_nFirstOutCount++;
						}
					}
					break;
				case CARD_TYPE_3_TUPLE_PLUS_SINGLE:
					m_nAIMainCount = Search3TuplePlusSingle(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					SearchPlusSingle(PlayerCards);
					FirstOut3TuplePlus(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
					break;
				case CARD_TYPE_3_TUPLE_PLUS_2_TUPLE:
					m_nAIMainCount = Search3TuplePlus2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					SearchPlus2Tuple(PlayerCards);
					FirstOut3TuplePlus(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
					break;
				case CARD_TYPE_CONTINUE_3_TUPLE_PLUS_SINGLE:
					m_nAIMainCount = SearchContinue3TuplePlusSingle(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					SearchPlusSingle(PlayerCards);
					FirstOutSubDDZCards(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
					break;
				case CARD_TYPE_CONTINUE_3_TUPPLE_PLUS_2_TUPLE:
					m_nAIMainCount = SearchContinue3TuplePlus2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					SearchPlus2Tuple(PlayerCards);
					FirstOutSubDDZCards(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
					break;
				case CARD_TYPE_4_TUPLE_PLUS_2_SINGLE:
					m_nAIMainCount = Search4TuplePlusSingle(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					SearchPlusSingle(PlayerCards);
					FirstOutSubDDZCards(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
					break;
				case CARD_TYPE_4_TUPLE_PLUS_2_2_TUPLE:
					m_nAIMainCount = Search4TuplePlus2Tuple(PlayerCards,LastPlayerCards,m_DDZCardsAIMain,tupleInfo,false);
					SearchPlus2Tuple(PlayerCards);
					FirstOutSubDDZCards(m_DDZCardsAIMain,m_nAIMainCount,m_DDZCardsAISub,m_nAISubCount,SelectCards);
					break;
				default:
					break;
			}
		}

	}
	
	private int SearchBomb(DDZCards LastPlayerCards, DDZCards PlayerCards, DDZCards[] m_DDZCards, int nCount)
	{
		//4Bomb
		char tupleInfo[] = new char[16];
		int nCardsCount;
		int nTempCount = nCount;
		
		nCardsCount = PlayerCards.GetTupleInfo(tupleInfo);
		DDZCards cardsTemp = new DDZCards();
		
		for(int i=0; i<16; i++)
		{
			if(nTempCount < 30)
			{
				if(tupleInfo[i] >= 4)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					SetCardsType(cardsTemp);
					if(IsFirstCardsBig(cardsTemp, LastPlayerCards))
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						cardsTemp.ReleaseAll();
						nTempCount ++;
					}
				}
			}
			
			
			if(nTempCount < 30)
			{
				if(tupleInfo[i] >= 5)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					SetCardsType(cardsTemp);
					if(IsFirstCardsBig(cardsTemp, LastPlayerCards))
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
			
			if(nTempCount < 30)
			{
				if(tupleInfo[i] >= 6)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					SetCardsType(cardsTemp);
					if(IsFirstCardsBig(cardsTemp, LastPlayerCards))
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
			
			if(nTempCount < 30)
			{
				if(tupleInfo[i] >= 7)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					SetCardsType(cardsTemp);
					if(IsFirstCardsBig(cardsTemp, LastPlayerCards))
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
			
			if(nTempCount < 30)
			{
				if(tupleInfo[i] == 8)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					cardsTemp.AddCard(1, i, false);
					SetCardsType(cardsTemp);
					if(IsFirstCardsBig(cardsTemp, LastPlayerCards))
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
		}
		
		if(nTempCount < 30 && PlayerCards.CurrentLength >=2)
		{
			SortByLevel(PlayerCards);
			if(PlayerCards.cards[0].number >= 14 && PlayerCards.cards[1].number >= 14)
			{
				m_DDZCards[nTempCount].ReleaseAll();
				m_DDZCards[nTempCount].AddCard(0, PlayerCards.cards[0].number, false);
				m_DDZCards[nTempCount].AddCard(0, PlayerCards.cards[1].number, false);
				nTempCount ++;
			}
		}
		
		if(nTempCount < 30 && PlayerCards.CurrentLength >=4)
		{
			SortByLevel(PlayerCards);
			if(PlayerCards.cards[0].number >= 14 && PlayerCards.cards[1].number >= 14
			   && PlayerCards.cards[2].number >= 14 && PlayerCards.cards[3].number >= 14)
			{
				m_DDZCards[nTempCount].ReleaseAll();
				m_DDZCards[nTempCount].AddCard(0, PlayerCards.cards[0].number, false);
				m_DDZCards[nTempCount].AddCard(0, PlayerCards.cards[1].number, false);
				m_DDZCards[nTempCount].AddCard(0, PlayerCards.cards[2].number, false);
				m_DDZCards[nTempCount].AddCard(0, PlayerCards.cards[3].number, false);
				nTempCount ++;
			}
		}
		return nTempCount;
	}
	
	private int CSearchHint(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{
		int i,j,k;
		int nTempCount = 0;
		DDZCards cardsTemp = new DDZCards();
		//±È¿˙’≈ ˝
		for(i=2; i<9; i++)
		{
			for(j=3; j<14; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					SetCardsType(cardsTemp);
					if((LastPlayerCards.CurrentLength==0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)//add by lucas
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
			for(j=1; j<3; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					SetCardsType(cardsTemp);
					if((LastPlayerCards.CurrentLength==0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
		}
		return nTempCount;
	}
	
	private int SearchContinueSingle(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{
		int i,j,k;
		int nTempCount = 0;
		
		int nLen = LastPlayerCards.nLen;
		int nLevel = LastPlayerCards.nLevel;
		int nStartLevel = nLevel+2+1;
		
		//µ⁄“ª’≈–°”⁄A£¨÷¡…ŸŒ™7,≥§∂»÷¡…ŸŒ™5£¨◊Ó∂‡Œ™12
		if(nLevel<12 && nLevel>4 && nLen>4 && nLen<13)
		{
			//µ⁄“ª’≈≈∆¥”±»…œº“≥ˆµƒ¥Û“ª µΩK£¨A‘⁄œ¬√Ê¥¶¿Ì
			for(i=nStartLevel/*nLevel+2+1*/; i<14; i++)
			{
				if(nTempCount >= 30)
				{
					break;
				}
				
				boolean bBig = true;
				for(j=0; j<nLen; j++)
				{
					if(tupleInfo[i-j] == 0)
					{
						bBig = false;
						break;
					}
				}
				if(bBig)
				{
					m_DDZCards[nTempCount].ReleaseAll();
					for(j=0; j<nLen; j++)
					{
						m_DDZCards[nTempCount].AddCard(1, i-j, false);
					}
					nTempCount ++;
				}
			}
			
			//≤‚ ‘AœÚœ¬µƒÀ≥◊”
			if(nTempCount < 30 && tupleInfo[1] > 0)
			{
				boolean bBig = true;
				for(i=1; i<nLen; i++)
				{
					if(tupleInfo[14-i] == 0)
					{
						bBig = false;
						break;
					}
				}
				if(tupleInfo[1] < 1)
				{
					bBig = false;
				}
				
				if(bBig)
				{
					m_DDZCards[nTempCount].ReleaseAll();
					//º”A
					m_DDZCards[nTempCount].AddCard(1, 1, false);
					//º”∆‰À˚≈∆
					for(i=0; i<nLen-1; i++)
					{
						m_DDZCards[nTempCount].AddCard(1, 13-i, false);
					}
					nTempCount ++;
				}
			}
		}
		return nTempCount;
	}

	
	private int Search2Tuple(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{
		int i,j,k;
		int nTempCount = 0;
		DDZCards cardsTemp = new DDZCards();
		//±È¿˙’≈ ˝
		for(i=2; i<9; i++)
		{
			for(j=3; j<14; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					SetCardsType(cardsTemp);
					if((LastPlayerCards.CurrentLength==0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)//add by lucas
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
			for(j=1; j<3; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					SetCardsType(cardsTemp);
					if((LastPlayerCards.CurrentLength==0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
		}
		return nTempCount;
	}


	private int SearchContinue2Tuple(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{
		int i,j,k;
		int nTempCount = 0;
		
		int nLen = LastPlayerCards.nLen;
		int nLevel = LastPlayerCards.nLevel;
		int nStartLevel = nLevel+2+1;
		
		//µ⁄“ª’≈–°”⁄A£¨÷¡…ŸŒ™5,≥§∂»÷¡…ŸŒ™3£¨◊Ó∂‡Œ™12
		if(nLevel<12 && nLevel>2 && nLen>2 && nLen<13)
		{
			//µ⁄“ª’≈≈∆¥”±»…œº“≥ˆµƒ¥Û“ª µΩK£¨A‘⁄œ¬√Ê¥¶¿Ì
			for(i=nStartLevel/*nLevel+2+1*/; i<14; i++)
			{
				if(nTempCount >= 30)
				{
					break;
				}
				
				boolean bBig = true;
				for(j=0; j<nLen; j++)
				{
					if(tupleInfo[i-j] < 2)
					{
						bBig = false;
						break;
					}
				}
				if(bBig)
				{
					m_DDZCards[nTempCount].ReleaseAll();
					for(j=0; j<nLen; j++)
					{
						m_DDZCards[nTempCount].AddCard(1, i-j, false);
						m_DDZCards[nTempCount].AddCard(1, i-j, false);
					}
					nTempCount ++;
				}
			}
			
			//≤‚ ‘AœÚœ¬µƒÀ≥◊”
			if(nTempCount < 30 && tupleInfo[1] > 1)
			{
				boolean bBig = true;
				for(i=1; i<nLen; i++)
				{
					if(tupleInfo[14-i] < 2)
					{
						bBig = false;
						break;
					}
				}
				if(tupleInfo[1] < 2)
				{
					bBig = false;
				}
				
				if(bBig)
				{
					m_DDZCards[nTempCount].ReleaseAll();
					//º”A
					m_DDZCards[nTempCount].AddCard(1, 1, false);
					m_DDZCards[nTempCount].AddCard(1, 1, false);
					//º”∆‰À˚≈∆
					for(i=0; i<nLen-1; i++)
					{
						m_DDZCards[nTempCount].AddCard(1, 13-i, false);
						m_DDZCards[nTempCount].AddCard(1, 13-i, false);
					}
					nTempCount ++;
				}
			}
		}
		return nTempCount;
	}
	
	private int Search3Tuple(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{
		int i,j,k;
		int nTempCount = 0;
		DDZCards cardsTemp = new DDZCards();
		//±È¿˙’≈ ˝
		for(i=3; i<9; i++)
		{
			for(j=3; j<14; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					SetCardsType(cardsTemp);
					if((LastPlayerCards.CurrentLength == 0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
			for(j=1; j<3; j++)
			{
				if(tupleInfo[j] == i)
				{
					cardsTemp.ReleaseAll();
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					cardsTemp.AddCard(1, j, false);
					SetCardsType(cardsTemp);
					if((LastPlayerCards.CurrentLength==0 || IsFirstCardsBig(cardsTemp, LastPlayerCards)) && nTempCount < 30)
					{
						m_DDZCards[nTempCount].ReleaseAll();
						m_DDZCards[nTempCount].AddCards(cardsTemp);
						nTempCount ++;
					}
				}
			}
		}
		return nTempCount;
	}

	private int SearchContinue3Tuple(DDZCards PlayerCards,DDZCards LastPlayerCards,DDZCards[] m_DDZCards,char tupleInfo[],boolean bPlus)
	{
		int i,j,k;
		int nTempCount = 0;
		
		int nLen = LastPlayerCards.nLen;
		int nLevel = LastPlayerCards.nLevel;
		int nStartLevel = nLevel+2+1;
		
		//µ⁄“ª’≈–°”⁄A£¨÷¡…ŸŒ™333444,≥§∂»÷¡…ŸŒ™2£¨◊Ó∂‡Œ™12
		if(nLevel<12 && nLevel>1 && nLen>1 && nLen<13)
		{
			//µ⁄“ª’≈≈∆¥”±»…œº“≥ˆµƒ¥Û“ª µΩK£¨A‘⁄œ¬√Ê¥¶¿Ì
			for(i=nStartLevel/*nLevel+2+1*/; i<14; i++)
			{
				if(nTempCount >= 30)
				{
					break;
				}
				
				boolean bBig = true;
				for(j=0; j<nLen; j++)
				{
					if(tupleInfo[i-j] < 3)
					{
						bBig = false;
						break;
					}
				}
				if(bBig)
				{
					m_DDZCards[nTempCount].ReleaseAll();
					for(j=0; j<nLen; j++)
					{
						m_DDZCards[nTempCount].AddCard(1, i-j, false);
						m_DDZCards[nTempCount].AddCard(1, i-j, false);
						m_DDZCards[nTempCount].AddCard(1, i-j, false);
					}
					nTempCount ++;
				}
			}
			
			//≤‚ ‘AœÚœ¬µƒÀ≥◊”
			if(nTempCount < 30 && tupleInfo[1] > 2)
			{
				boolean bBig = true;
				for(i=1; i<nLen; i++)
				{
					if(tupleInfo[14-i] < 3)
					{
						bBig = false;
						break;
					}
				}
				if(tupleInfo[1] < 3)
				{
					bBig = false;
				}
				if(bBig)
				{
					m_DDZCards[nTempCount].ReleaseAll();
					//º”A
					m_DDZCards[nTempCount].AddCard(1, 1, false);
					m_DDZCards[nTempCount].AddCard(1, 1, false);
					m_DDZCards[nTempCount].AddCard(1, 1, false);
					//º”∆‰À˚≈∆
					for(i=0; i<nLen-1; i++)
					{
						m_DDZCards[nTempCount].AddCard(1, 13-i, false);
						m_DDZCards[nTempCount].AddCard(1, 13-i, false);
						m_DDZCards[nTempCount].AddCard(1, 13-i, false);
					}
					nTempCount ++;
				}
			}
		}
		return nTempCount;
	}
	
	private boolean IsFirstCardsBig(DDZCards firstCards , DDZCards secondCards)
	{
		if( firstCards.nType == secondCards.nType )
		{
			if( firstCards.nLen == secondCards.nLen && firstCards.nLevel > secondCards.nLevel )
			{
				return true;
			}
			
			return false;
		}
		
		if( firstCards.nType >= CARD_TYPE_4_BOMB && firstCards.nType > secondCards.nType )
		{
			return true;
		}
		
		return false;
	}
	
	private boolean Check_Card_Type_CARD_TYPE_SINGLE( DDZCards obCards)
	{
		if( 1 == obCards.CurrentLength )
		{
			obCards.nLen	= 1;
			obCards.nLevel	= GetLevel( obCards.cards[0] );
			obCards.nType	= CARD_TYPE_SINGLE;
			
			return true;
		}
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_CONTINUE_SINGLE( DDZCards obCards)
	{
		if( 4 < obCards.CurrentLength )
		{
			SortByLevel( obCards );
			
			if( GetLevel( obCards.cards[0] ) > GetLevel( 1 ) )
			{
				return false;
			}
			
			for( int i = 1 ; i < obCards.CurrentLength ; i ++ )
			{
				if( GetLevel( obCards.cards[i] ) + i != GetLevel( obCards.cards[0] ) )
				{
					return false;
				}
			}
			
			obCards.nLen	= obCards.CurrentLength;
			obCards.nLevel	= GetLevel( obCards.cards[0] );
			obCards.nType	= CARD_TYPE_CONTINUE_SINGLE;
			
			return true;
		}
		
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_2_TUPLE( DDZCards obCards)
	{
		if( 2 == obCards.CurrentLength )
		{
			if( obCards.cards[0].number == obCards.cards[1].number )
			{
				obCards.nLen	= 1;
				obCards.nLevel	= GetLevel( obCards.cards[0] );
				obCards.nType	= CARD_TYPE_2_TUPLE;
				
				return true;
				
			}
		}
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_CONTINUE_2_TUPLE( DDZCards obCards)
	{
		if( 0 == obCards.CurrentLength % 2 )
		{
			if( obCards.CurrentLength > 5 )//÷¡…Ÿ3¡¨
			{
				SortByLevel( obCards );
				
				if( GetLevel( obCards.cards[0] ) > GetLevel( 1 ) )
				{
					return false;
				}
				
				int i;
				
				for( i = 0 ; i < obCards.CurrentLength / 2 ; i ++ )
				{
					if( obCards.cards[2 * i].number != obCards.cards[2 * i + 1].number )
					{
						return false;
					}
				}
				
				for( i = 0 ; i < obCards.CurrentLength / 2 - 1 ; i ++ )
				{
					if( GetLevel( obCards.cards[2 * i] ) != GetLevel( obCards.cards[2 * i + 2] ) + 1 )
					{
						return false;
					}
				}
				
				obCards.nLen	= obCards.CurrentLength / 2;
				obCards.nLevel	= GetLevel( obCards.cards[0] );
				obCards.nType	= CARD_TYPE_CONTINUE_2_TUPLE;
				
				return true;	
			}
		}
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_3_TUPLE( DDZCards obCards)
	{
		if( 3 == obCards.CurrentLength )
		{
			if( obCards.cards[0].number == obCards.cards[1].number && obCards.cards[0].number == obCards.cards[2].number )
			{
				obCards.nLen	= 1;
				obCards.nLevel	= GetLevel( obCards.cards[0] );
				obCards.nType	= CARD_TYPE_3_TUPLE;
				
				return true;
			}
		}
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_CONTINUE_3_TUPLE( DDZCards obCards)
	{
		if( 0 == obCards.CurrentLength % 3 )
		{
			if( obCards.CurrentLength > 5 )//÷¡…Ÿ2¡¨
			{
				SortByLevel( obCards );
				
				if( GetLevel( obCards.cards[0] ) > GetLevel( 1 ) )
				{
					return false;
				}
				
				int i;
				
				for( i = 0 ; i < obCards.CurrentLength / 3 ; i ++ )
				{
					if( obCards.cards[3 * i].number != obCards.cards[3 * i + 1].number || obCards.cards[3 * i].number != obCards.cards[3 * i + 2].number )
					{
						return false;
					}
				}
				
				for( i = 0 ; i < obCards.CurrentLength / 3 - 1 ; i ++ )
				{
					if( GetLevel( obCards.cards[3 * i] ) != 1 + GetLevel( obCards.cards[3 * i + 3] ) )
					{
						return false;
					}
				}
				
				obCards.nLen	= obCards.CurrentLength / 3;
				obCards.nLevel	= GetLevel( obCards.cards[0] );
				obCards.nType	= CARD_TYPE_CONTINUE_3_TUPLE;
				
				return true;
			}
		}
		
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_3_TUPLE_PLUS_SINGLE( DDZCards obCards)
	{
		if( 4 == obCards.CurrentLength )
		{
			SortByLevel( obCards );
			
			if( obCards.cards[2].number != obCards.cards[1].number )
			{
				return false;
			}
			
			if(		( obCards.cards[0].number != obCards.cards[1].number && obCards.cards[2].number == obCards.cards[3].number ) 
			   ||  ( obCards.cards[0].number == obCards.cards[1].number && obCards.cards[2].number != obCards.cards[3].number ) )
			{
				obCards.nLen	= 1;
				obCards.nLevel	= GetLevel( obCards.cards[1] );
				obCards.nType	= CARD_TYPE_3_TUPLE_PLUS_SINGLE;
				
				return true;
			}
		}
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_3_TUPLE_PLUS_2_TUPLE( DDZCards obCards)
	{
		if( 5 == obCards.CurrentLength )
		{
			SortByLevel( obCards );
			
			if( obCards.cards[0].number != obCards.cards[1].number )
			{
				return false;
			}
			
			if( obCards.cards[3].number != obCards.cards[4].number )
			{
				return false;
			}
			
			if( obCards.cards[0].number == obCards.cards[4].number )
			{
				return false;
			}
			
			if( obCards.cards[0].number != obCards.cards[2].number && obCards.cards[4].number != obCards.cards[2].number )
			{
				return false;
			}
			
			obCards.nLen	= 1;
			obCards.nLevel	= GetLevel( obCards.cards[2] );
			obCards.nType	= CARD_TYPE_3_TUPLE_PLUS_2_TUPLE;
			
			return true;
		}
		
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_CONTINUE_3_TUPLE_PLUS_SINGLE( DDZCards obCards)
	{
		if( 0 == obCards.CurrentLength % 4 && obCards.CurrentLength > 7 )
		{
			char tupleInfo[] = new char[16];
			
			obCards.GetTupleInfo( tupleInfo ); // modify by lucas 
//			if( obCards.CurrentLength / 2 != obCards.GetTupleInfo( tupleInfo ) )
//			{
//				return false;
//			}
			
			int nCount_1 = 0;
			int nCount_3 = 0;
			
			char level_3[] = new char[16];
			
			int i;
			for(i = 0 ; i < obCards.CurrentLength ; i ++ )
			{
				//if( 1 == tupleInfo[obCards.cards[i].number] )
				if ( 1 == tupleInfo[obCards.cards[i].number] || 2 == tupleInfo[obCards.cards[i].number] )
				{
					nCount_1 ++;
				}
				else if( 3 == tupleInfo[obCards.cards[i].number] )
				{
					nCount_3 ++;
					
					level_3[GetLevel( obCards.cards[i] )] = 1;
				}
				else
				{
					return false;
				}
			}
			
			nCount_3 /= 3;
			
			if( nCount_1 != nCount_3 )
			{
				return false;
			}
			
			if( nCount_3 * 4 != obCards.CurrentLength )
			{
				return false;
			}
			
			int nMark_3 = 0;
			
			for( i = 1 ; i < 16 ; i ++ )
			{			
				if( level_3[i - 1] != 0 && level_3[i] != 0 )
				{
					level_3[i] += level_3[i - 1];
					
					if( level_3[i] == nCount_3 )
					{
						nMark_3 = i;
					}
				}
			}
			
			if( nMark_3 == 0 || nMark_3 > GetLevel( 1 ) )
			{
				return false;
			}
			
			obCards.nLen	= level_3[nMark_3];
			obCards.nLevel	= nMark_3;
			obCards.nType	= CARD_TYPE_CONTINUE_3_TUPLE_PLUS_SINGLE;
			
			return true;
		}
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_CONTINUE_3_TUPPLE_PLUS_2_TUPLE( DDZCards obCards)
	{
		if( 0 == obCards.CurrentLength % 5 && obCards.CurrentLength > 9 )
		{
			char tupleInfo[] = new char[16];
			
			if( 2 * obCards.CurrentLength / 5 != obCards.GetTupleInfo( tupleInfo ) )
			{
				return false;
			}
			
			int nCount_2 = 0;
			int nCount_3 = 0;
			
			char level_3[] = new char[16];
			
			int i;
			for(i = 0 ; i < obCards.CurrentLength ; i ++ )
			{
				if( 2 == tupleInfo[obCards.cards[i].number] )
				{
					nCount_2 ++;
				}
				else if( 3 == tupleInfo[obCards.cards[i].number] )
				{
					nCount_3 ++;
					
					level_3[GetLevel( obCards.cards[i] )] = 1;
				}
				else
				{
					return false;
				}
			}
			
			nCount_2 /= 2;
			nCount_3 /= 3;
			
			if( nCount_2 != nCount_3 )
			{
				return false;
			}
			
			if( nCount_3 * 5 != obCards.CurrentLength )
			{
				return false;
			}
			
			int nMark_3 = 0;
			
			for( i = 1 ; i < 16 ; i ++ )
			{			
				if( level_3[i - 1] != 0 && level_3[i] != 0 )
				{
					level_3[i] += level_3[i - 1];
					
					if( level_3[i] == nCount_3 )
					{
						nMark_3 = i;
					}
				}
			}
			
			if( nMark_3 == 0 || nMark_3 > GetLevel( 1 ) )
			{
				return false;
			}
			
			obCards.nLen	= level_3[nMark_3];
			obCards.nLevel	= nMark_3;
			obCards.nType	= CARD_TYPE_CONTINUE_3_TUPPLE_PLUS_2_TUPLE;
			
			return true;
		}
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_CONTINUE_3_TUPLE_PLUS_CONTINUE_SINGLE( DDZCards obCards)
	{
		//√ªºÏ≤Èπ˝
		if( 0 == obCards.CurrentLength % 4 && obCards.CurrentLength > 7 )
		{
			char tupleInfo[] = new char[16];
			
			if( obCards.CurrentLength / 2 != obCards.GetTupleInfo( tupleInfo ) )
			{
				return false;
			}
			
			int nCount_1 = 0;
			int nCount_3 = 0;
			
			char level_1[] = new char[16];
			char level_3[] = new char[16];
			
			int i;
			for(i = 0 ; i < obCards.CurrentLength ; i ++ )
			{
				if( 1 == tupleInfo[obCards.cards[i].number] )
				{
					nCount_1 ++;
					
					level_1[GetLevel( obCards.cards[i] )] = 1;
				}
				else if( 3 == tupleInfo[obCards.cards[i].number] )
				{
					nCount_3 ++;
					
					level_3[GetLevel( obCards.cards[i] )] = 1;
				}
				else
				{
					return false;
				}
			}
			
			if( nCount_1 != nCount_3 )
			{
				return false;
			}
			
			if( nCount_3 * 4 != obCards.CurrentLength )
			{
				return false;
			}
			
			int nMark_1 = 0;
			int nMark_3 = 0;
			
			for( i = 1 ; i < 16 ; i ++ )
			{
				if( level_1[i - 1] != 0 && level_1[i] != 0 )
				{
					level_1[i] += level_1[i - 1];
					
					if( level_1[i] == nCount_1 )
					{
						nMark_1 = i;
					}
				}
				
				if( level_3[i - 1] != 0 && level_3[i] != 0 )
				{
					level_3[i] += level_3[i - 1];
					
					if( level_3[i] == nCount_3 )
					{
						nMark_3 = i;
					}
				}
			}
			
			if( nMark_1 == 0 || nMark_1 > GetLevel( 1 ) )
			{
				return false;
			}
			
			if( nMark_3 == 0 || nMark_3 > GetLevel( 1 ) )
			{
				return false;
			}
			
			obCards.nLen	= level_3[nMark_3];
			obCards.nLevel	= nMark_3;
			obCards.nType	= CARD_TYPE_CONTINUE_3_TUPLE_PLUS_CONTINUE_SINGLE;
			
			return true;
		}
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_CONTINUE_3_TUPLE_PLUS_CONTINUE_2_TUPLE( DDZCards obCards)
	{
		//√ªºÏ≤Èπ˝
		if( 0 == obCards.CurrentLength % 5 && obCards.CurrentLength > 9 )
		{
			char tupleInfo[] = new char[16];
			
			if( 2 * obCards.CurrentLength / 5 != obCards.GetTupleInfo( tupleInfo ) )
			{
				return false;
			}
			
			int nCount_2 = 0;
			int nCount_3 = 0;
			
			char level_2[] = new char[16];
			char level_3[] = new char[16];
			
			
			int i;
			for( i = 0 ; i < obCards.CurrentLength ; i ++ )
			{
				if( 2 == tupleInfo[obCards.cards[i].number] )
				{
					nCount_2 ++;
					
					level_2[GetLevel( obCards.cards[i] )] = 1;
				}
				else if( 3 == tupleInfo[obCards.cards[i].number] )
				{
					nCount_3 ++;
					
					level_3[GetLevel( obCards.cards[i] )] = 1;
				}
				else
				{
					return false;
				}
			}
			
			nCount_2 /= 2;
			nCount_3 /= 3;
			
			if( nCount_2 != nCount_3 )
			{
				return false;
			}
			
			if( nCount_3 * 5 != obCards.CurrentLength )
			{
				return false;
			}
			
			int nMark_2 = 0;
			int nMark_3 = 0;
			
			for( i = 1 ; i < 16 ; i ++ )
			{
				if( level_2[i - 1]!= 0 && level_2[i]!= 0 )
				{
					level_2[i] += level_2[i - 1];
					
					if( level_2[i] == nCount_2 )
					{
						nMark_2 = i;
					}
				}
				
				if( level_3[i - 1]!= 0 && level_3[i]!= 0 )
				{
					level_3[i] += level_3[i - 1];
					
					if( level_3[i] == nCount_3 )
					{
						nMark_3 = i;
					}
				}
			}
			
			if( nMark_2 ==0 || nMark_2 > GetLevel( 1 ) )
			{
				return false;
			}
			
			if( nMark_3==0 || nMark_3 > GetLevel( 1 ) )
			{
				return false;
			}
			
			obCards.nLen	= level_3[nMark_3];
			obCards.nLevel	= nMark_3;
			obCards.nType	= CARD_TYPE_CONTINUE_3_TUPLE_PLUS_CONTINUE_2_TUPLE;
			
			return true;
		}
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_4_TUPLE_PLUS_2_SINGLE( DDZCards obCards)
	{
		if( 6 == obCards.CurrentLength )
		{
			char tupleInfo[] = new char[16];
			
			//if( 3 != obCards.GetTupleInfo( tupleInfo ) )
			if( 3 != obCards.GetTupleInfo( tupleInfo )  && 2  != obCards.GetTupleInfo( tupleInfo ) )  
			{
				// 4 with 2    modify by lucas
				return false;
			}
			
			for( int i = 0 ; i < obCards.CurrentLength ; i ++ )
			{
				if( 4 == tupleInfo[obCards.cards[i].number] )
				{
					obCards.nLen	= 1;
					obCards.nLevel	= GetLevel( obCards.cards[i] );
					obCards.nType	= CARD_TYPE_4_TUPLE_PLUS_2_SINGLE;
					
					return true;
				}
			}
		}
		
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_4_TUPLE_PLUS_2_2_TUPLE( DDZCards obCards)
	{
		if( 8 == obCards.CurrentLength )
		{
			char tupleInfo[] = new char[16];
			
			if( 3 != obCards.GetTupleInfo( tupleInfo ) )
			{
				return false;
			}
			
			int i;
			
			for( i = 0 ; i < obCards.CurrentLength ; i ++ )
			{
				if( tupleInfo[obCards.cards[i].number] % 2 != 0)
				{
					return false;
				}
			}
			
			for( i = 0 ; i < obCards.CurrentLength ; i ++ )
			{
				if( 4 == tupleInfo[obCards.cards[i].number] )
				{
					obCards.nLen	= 1;
					obCards.nLevel	= GetLevel( obCards.cards[i] );
					obCards.nType	= CARD_TYPE_4_TUPLE_PLUS_2_2_TUPLE;
					
					return true;
				}
			}
		}
		
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_4_BOMB( DDZCards obCards)
	{
		if( 4 == obCards.CurrentLength )
		{
			for( int i = 1 ; i < 4 ; i ++ )
			{
				if( obCards.cards[0].number != obCards.cards[i].number )
				{
					return false;
				}
			}
			
			obCards.nLen	= 1;
			obCards.nLevel	= GetLevel( obCards.cards[0] );
			obCards.nType	= CARD_TYPE_4_BOMB;
			//TRACE("Check_Card_Type_CARD_TYPE_4_BOMB\n");
			return true;
		}
		
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_5_BOMB( DDZCards obCards)
	{
		if( 5 == obCards.CurrentLength )
		{
			for( int i = 1 ; i < 5 ; i ++ )
			{
				if( obCards.cards[0].number != obCards.cards[i].number )
				{
					return false;
				}
			}
			
			obCards.nLen	= 1;
			obCards.nLevel	= GetLevel( obCards.cards[0] );
			obCards.nType	= CARD_TYPE_5_BOMB;
			
			return true;
		}
		
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_6_BOMB( DDZCards obCards)
	{
		if( 6 == obCards.CurrentLength )
		{
			for( int i = 1 ; i < 6 ; i ++ )
			{
				if( obCards.cards[0].number != obCards.cards[i].number )
				{
					return false;
				}
			}
			
			obCards.nLen	= 1;
			obCards.nLevel	= GetLevel( obCards.cards[0] );
			obCards.nType	= CARD_TYPE_6_BOMB;
			
			return true;
		}
		
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_7_BOMB( DDZCards obCards)
	{
		if( 7 == obCards.CurrentLength )
		{
			for( int i = 1 ; i < 7 ; i ++ )
			{
				if( obCards.cards[0].number != obCards.cards[i].number )
				{
					return false;
				}
			}
			
			obCards.nLen	= 1;
			obCards.nLevel	= GetLevel( obCards.cards[0] );
			obCards.nType	= CARD_TYPE_7_BOMB;
			
			return true;
		}
		
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_8_BOMB( DDZCards obCards)
	{
		if( 8 == obCards.CurrentLength )
		{
			for( int i = 1 ; i < 8 ; i ++ )
			{
				if( obCards.cards[0].number != obCards.cards[i].number )
				{
					return false;
				}
			}
			
			obCards.nLen	= 1;
			obCards.nLevel	= GetLevel( obCards.cards[0] );
			obCards.nType	= CARD_TYPE_8_BOMB;
			
			return true;
		}
		
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_2_JOKER( DDZCards obCards)
	{
		if( 2 == obCards.CurrentLength )
		{
			SortByLevel( obCards );
			
			if( obCards.cards[0].number == 15 && obCards.cards[1].number == 14 ) 
			{
				obCards.nLen	= 1;
				obCards.nLevel	= GetLevel( obCards.cards[0] );
				obCards.nType	= CARD_TYPE_2_JOKER;
				//TRACE("Check_Card_Type_CARD_TYPE_2_JOKER\n");
				return true;
			}
		}
		return false;
	}

	private boolean Check_Card_Type_CARD_TYPE_4_JOKER( DDZCards obCards)
	{
		if( 4 == obCards.CurrentLength )
		{
			SortByLevel( obCards );
			
			if( obCards.cards[0].number == 15 && obCards.cards[1].number == 15
			   && obCards.cards[2].number == 14 &&obCards.cards[3].number == 14) 
			{
				obCards.nLen	= 1;
				obCards.nLevel	= GetLevel( obCards.cards[0] );
				obCards.nType	= CARD_TYPE_4_JOKER;
				
				return true;
			}
		}
		return false;
	}

}
