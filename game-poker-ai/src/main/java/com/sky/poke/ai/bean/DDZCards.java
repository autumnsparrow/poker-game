package com.sky.poke.ai.bean;

import java.util.Arrays;

/**
 * Function: 一组牌
 * 
 * ClassName:DDZCards Date: 2013-6-22 下午03:50:37
 * 
 * @author chshyin
 * @version
 * @since JDK 1.6 Copyright (c) 2013, palm-commerce All Rights Reserved.
 */
public class DDZCards {

	public static final int MAX_CARDS_NUMBER = 54;
	// 牌
	public CARD[] cards = new CARD[MAX_CARDS_NUMBER];

	public char CurrentLength;

	public int nType;
	public int nLen;
	public int nLevel;

	public void AddCard(CARD card) {

		AddCard(card.shape, card.number, card.bSelected);
	}

	public void AddCard(int i, int j, boolean sel) {
		  
		AddCard((char)i, (char)j, sel);
		
	}
	
	public void AddCard(char shape, char number, boolean bSel) {
		// ASSERT( CurrentLength <= MAX_CARDS_NUMBER );
		if (CurrentLength > MAX_CARDS_NUMBER) {
			return;
		}

		if ((0 < shape && shape < 5 && 0 < number && number < 14)
				|| (0 == shape && (14 == number || 15 == number))
				|| (0 == shape && 0 == number)) {
			
			if(cards[CurrentLength] == null){
				
				cards[CurrentLength] = new CARD();
			}
			
			cards[CurrentLength].shape = shape;
			cards[CurrentLength].number = number;
			cards[CurrentLength].bSelected = bSel;

			CurrentLength++;
		}
	}

	public void AddCards(DDZCards obCards) {
		
		for (int i = 0; i < obCards.CurrentLength; i++) {
			AddCard(obCards.cards[i]);
		}
	}
	
	public byte[] getCardsNumber(){
		
		byte[] pokers = new byte[CurrentLength];
		
		for(int i =0;i<CurrentLength;i++){
			
			pokers[i] = (byte) CARD.CARD2char(cards[i]);
		}
		
		return pokers;
	}

	public void DelCard(CARD card) {
		DelCard(card.shape, card.number);
	}

	public void DelCard(char shape, char number) {
		for (int i = 0; i < CurrentLength; i++) {
			if (cards[i].shape == shape && cards[i].number == number) {
				DelCard(i);
				return;
			}
		}
	}

	public CARD DelCard(int nPos) {
		
		CARD card = cards[nPos];
		if (0 <= nPos && nPos < CurrentLength) {
			swap(nPos, CurrentLength - 1);
			CurrentLength--;
		}
		return card;
	}

	public void DelCards(DDZCards obCards) {
		for (int i = 0; i < obCards.CurrentLength; i++) {
			DelCard(obCards.cards[i]);
		}
	}

	public int GetTupleInfo(char[] tupleInfo) {

		Arrays.fill(tupleInfo, 0, 16, (char) 0);

		int i;
		
	//	System.out.println("currentLen:" + (byte)CurrentLength);

		for (i = 0; i < CurrentLength; i++) {
			tupleInfo[cards[i].number]++;
		}

		int nCountNoneZero = 0;

		for (i = 0; i < 16; i++) {
			if (tupleInfo[i] != 0) {
				nCountNoneZero++;
			}
		}

		return nCountNoneZero;
	}

	public boolean Contain(DDZCards clDDZCards){
		
		return Contain(clDDZCards, false);
	}
	
	public boolean Contain(DDZCards clDDZCards, boolean bEqual) {
		boolean bContain = false;
		int[] nContain = new int[20];

		if ((!bEqual && clDDZCards.CurrentLength <= CurrentLength)
				|| (bEqual && clDDZCards.CurrentLength == CurrentLength)) {
			for (int i = 0; i < clDDZCards.CurrentLength; i++) {
				bContain = false;
				for (int j = 0; j < CurrentLength; j++) {
					if (nContain[j] == 0
							&& clDDZCards.cards[i].number == cards[j].number) {
						nContain[j] = 1;
						bContain = true;
						break;
					}
				}
				if (!bContain) {
					return false;
				}
			}
		}
		return bContain;
	}

	public CARD[] getCards() {
		return cards;
	}

	public void setCards(CARD[] cards) {
		this.cards = cards;
	}

	public void ReleaseAll() {

		CurrentLength = 0;
	}

	public void SortDetail() {
		if (CurrentLength < 2) {
			return;
		}
		boolean bRun = true;

		int i;

		while (bRun) {
			bRun = false;

			for (i = 1; i < CurrentLength; i++) {
				if (cards[i - 1].number == cards[i].number
						&& cards[i - 1].shape > cards[i].shape) {
					swap(i - 1, i);
					bRun = true;
					break;
				}
			}
		}
	}

	public void swap(int i, int j) {

		if (i < 0 && j < 0 && i >= CurrentLength && j >= CurrentLength) {
			return;
		}

		CARD tempCard = cards[i];
		cards[i] = cards[j];
		cards[j] = tempCard;
	}

	public int getNType() {
		return nType;
	}

	public void setNType(int type) {
		nType = type;
	}

	public int getNLen() {
		return nLen;
	}

	public void setNLen(int len) {
		nLen = len;
	}

	public int getNLevel() {
		return nLevel;
	}

	public void setNLevel(int level) {
		nLevel = level;
	}

	public char getCurrentLength() {
		return CurrentLength;
	}

	public void setCurrentLength(char currentLength) {
		CurrentLength = currentLength;
	}



	public static class CARD {

		public char shape; // 1 = spade, 2 = heart, 3 = club, 4 = diamond
		public char number;
		public boolean bSelected;
		
		private CARD(){}

		public static CARD char2CARD(char c) {
			CARD card = new CARD();

			if (c < 1 || c > 54) {
				card.shape = card.number = 0;
			} else if (c > 52) {
				card.shape = 0;
				card.number = (char) (c - 39);
			} else {
				card.shape = (char) (c / 13);
				card.number = (char) (c % 13);

				if (0 == card.number) {
					card.number = 13;
				} else {
					card.shape += 1;
				}
			}
			card.bSelected = false;
			return card;
		}
		
		public static CARD char2CARD(byte c) {
			CARD card = new CARD();

			if (c < 1 || c > 54) {
				card.shape = card.number = 0;
			} else if (c > 52) {
				card.shape = 0;
				card.number = (char) (c - 39);
			} else {
				card.shape = (char) (c / 13);
				card.number = (char) (c % 13);

				if (0 == card.number) {
					card.number = 13;
				} else {
					card.shape += 1;
				}
			}
			card.bSelected = false;
			return card;
		}

		public static char CARD2char(CARD card) {
			if (0 == card.shape && 0 == card.number) {
				return 0;
			}

			if (0 == card.shape) {
				if (14 == card.number) {
					return 53;
				}

				if (15 == card.number) {
					return 54;
				}
			}

			return (char) ((card.shape - 1) * 13 + card.number);
		}

		public char getShape() {
			return shape;
		}

		public void setShape(char shape) {
			this.shape = shape;
		}

		public char getNumber() {
			return number;
		}

		public void setNumber(char number) {
			this.number = number;
		}

		public boolean isBSelected() {
			return bSelected;
		}

		public void setBSelected(boolean selected) {
			bSelected = selected;
		}

	}
	
	public static void main(String[] args) {
		
		for (int i = 1; i < 55; i++) {
			
			CARD card = CARD.char2CARD((char)i);
			
			System.out.println((byte)CARD.CARD2char(card));
		}
	}

}
