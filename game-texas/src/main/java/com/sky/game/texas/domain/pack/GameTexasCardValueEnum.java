/**
 * 
 */
package com.sky.game.texas.domain.pack;

/**
 * 
 * Card value:
 * 
 * 0x0e -  A
 * 0x0d -  K
 * 0x0c -  Q
 * 0x0b -  J
 * 0x0a -  10
 * 0x09 -  9
 * 0x08 -  8
 * ....
 * 0x02 -  2
 * 
 * 
 * 
 * @author sparrow
 *
 */
public enum GameTexasCardValueEnum {
	
	vA((byte)0x0e),
	vK((byte)0x0d),
	vQ((byte)0x0c),
	vJ((byte)0x0b),
	v10((byte)0x0a),
	v9((byte)0x09),
	v8((byte)0x08),
	v7((byte)0x07),
	v6((byte)0x06),
	v5((byte)0x05),
	v4((byte)0x04),
	v3((byte)0x03),
	v2((byte)0x02),
	vUndefined((byte)0x00);
	
	
	
	byte value;

	private GameTexasCardValueEnum(byte value) {
		this.value = value;
	}
	
	static GameTexasCardValueEnum[]  values=new GameTexasCardValueEnum[]{
			vUndefined,
			vUndefined,
			v2,
			v3,
			v4,
			v5,v6,
			v7,
			v8,
			v9,
			v10,
			vJ,vQ,vK,vA
	};
	
	public static GameTexasCardValueEnum getCardValue(byte v){
		GameTexasCardValueEnum cardValue=vUndefined;
		if(v>1&&v<0x0f){
			return values[v];
		}
		return cardValue;
	}
	
	
	public byte getValue(){
		return this.value;
	}

}
