package com.sky.poke.ai;

/**
 * Function: 选牌模式类
 * 
 * ClassName:CardsPattern Date: 2013-6-18 下午05:49:04
 * 
 * @author chshyin
 * @version
 * @since JDK 1.6 Copyright (c) 2013, palm-commerce All Rights Reserved.
 */
public class CardsPattern {
	// rocket 0--have not, 1--own rocket
	public byte rocket;
	// bomb card value 0--have not, else--card value; if 4444, the first bomb
	// should be assigned as follow. bomb[0] = 4;
	public byte bomb[] = new byte[5];
	// one cards pattern, if 3, assigned as follow. singleCard[0] = 3;
	public byte singleCard[] = new byte[20];
	// two cards pattern, if 33, assigned as follow. doubleCard[0][0] = 3;
	// doubleCard[0][0] = 16;
	public byte doubleCard[][] = new byte[10][2];
	// three cards pattern, if 333, assigned as follow. tripleCard[0][0] = 3;
	// tripleCard[0][0] = 16; tripleCard[0][0] = 29;
	public byte tripleCard[][] = new byte[6][3];
	// single straight, if 34567 of pink, singleStraight[0][0] = 3;
	// singleStraight[0][1] = 4;...singleStraight[0][4] = 7;
	public byte singleStraight[][] = new byte[4][12];
	// double straight, if 334455, doubleStraight[0][0] = 3;
	// doubleStraight[0][1] = 16;...doubleStraight[0][5] = 18;
	public byte doubleStraight[][] = new byte[3][20];
	// triple straight, if 333444, tripleStraight[0][0] = 3;
	// tripleStraight[0][1] = 16;...tripleStraight[0][5] = 30;
	public byte tripleStraight[][] = new byte[3][18];
	public byte basicPatternNum;
	public byte calcuPatternNum;
	// bottom 1 -- actor 2 -- pokerNum, right 1 -- actor 2 -- pokerNum ,left 1
	// -- actor 2 -- pokerNum
	public byte extandInfo[];
}
