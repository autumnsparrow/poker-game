package com.sky.poke.ai.bean;

/**
 * Function: 基本扑克类
 * 	整副牌的从1-54 顺序
 *  QKA23456789(10)...(小王)(大王)
 * 
 * ClassName:CAIPoker Date: 2013-6-18 下午05:58:23
 * 
 * @author chshyin
 * @version
 * @since JDK 1.6 Copyright (c) 2013, palm-commerce All Rights Reserved.
 */
public class CAIPoker {

	private byte pokerValue; // 牌的总值

	private byte value; // 牌点 //1-15 顺序3456789(10)JQKA2(小王)(大王)

	private boolean isOut; // 当前牌是否已经出了

	private boolean isSelect; // 是否被选择

	public CAIPoker(byte bPoker) {

		pokerValue = bPoker;
		value = this.getValue(pokerValue);
		isOut = false;
		isSelect = false;
	}
	
	public void SetPoker(byte bPoker){
		
		pokerValue = bPoker;
		value = this.getValue(pokerValue);
		isOut = false;
		isSelect = false;
	}

	// 根据牌值获取牌点数
	protected byte getValue(byte aTotalValue) {
		if (aTotalValue == 54)
			return 15;
		else if (aTotalValue == 53)
			return 14;
		else {
			byte bValue = (byte) ((aTotalValue - 1) % 13 + 1);
			if (bValue < 3)
				bValue += 11;
			else
				bValue -= 2;
			return bValue;
		}
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	
	public byte getPokerValue() {
		return pokerValue;
	}

	public void setPokerValue(byte pokerValue) {
		this.pokerValue = pokerValue;
	}

	public boolean isOut() {
		return isOut;
	}

	public void setOut(boolean isOut) {
		this.isOut = isOut;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	
	public static String formatPokeValue(byte aTotalValue) {

		String colorStr = "♣♦♥♠";
		String[] valueArray = {"3", "4", "5", "6", "7", "8", "9",
				"10", "J", "Q", "K", "A", "2", "joker", "JOKER" };

		int colorIndex = 0;
		int value = 0;
		// A23456789(10)JQK .... (小王)(大王)
		if (aTotalValue == 54) {
			value = 15;
			colorIndex = -1;
		} else if (aTotalValue == 53) {
			value = 14;
			colorIndex = -1;
		} else {
			byte bValue = (byte) ((aTotalValue - 1) % 13 + 1);
			if (bValue < 3) {
				bValue += 11;
			} else {
				bValue -= 2;
			}
			
			colorIndex = (aTotalValue - 1) / 13;
			value = bValue;
		}

		String valueStr = valueArray[value - 1];
		
		String color = colorIndex < 0 ? "" : "" + colorStr.charAt(colorIndex);
		
		return color + valueStr;
	}

	public static void main(String[] args) {

		for (int i = 1; i <= 54; i++) {

			CAIPoker poker = new CAIPoker((byte) i);
			
			System.out.print(i+" "+poker.getValue()+" ");

			System.out.println(formatPokeValue((byte) i));
		}
		
		System.out.println(formatPokeValue((byte) 15));
	}

}
