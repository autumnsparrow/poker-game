/**
 * 
 */
package com.sky.game.texas.domain.pack;

/**
 * Card Suit:
 *    0x10 - spade
 *    0x20 - Diamond
 *    0x40 - Heart
 *    0x80 - Club
 * 
 * 
 * @author sparrow
 *
 */
public enum GameTexasCardSuitsEnum {
	
	Spade((byte)0x10),//
	Diamond((byte)0x20),//
	Heart((byte)0x40),//
	Club((byte)0x80);//
	//Undefined((byte)0);
	
	byte value;

	private GameTexasCardSuitsEnum(byte value) {
		this.value = value;
	}
	

	public static GameTexasCardSuitsEnum getTexasCardSuit(byte value){
		GameTexasCardSuitsEnum suit=Spade;
		switch (value) {
		case 0x10:
			suit=Spade;
			break;
		case 0x20:
			suit=Diamond;
			break;
		case 0x40:
			suit=Heart;
			break;
		case (byte) 0x80:
			suit=Club;
			break;

		default:
			
			break;
		}
		return suit;
	}
	
	
	public byte getValue(){
		return this.value;
	}
	
}
