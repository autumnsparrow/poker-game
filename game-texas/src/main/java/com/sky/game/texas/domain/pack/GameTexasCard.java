/**
 * 
 */
package com.sky.game.texas.domain.pack;

/**
 * 
 * 
 * 
 * @author sparrow
 * 
 */
public class GameTexasCard {

	public GameTexasCardSuitsEnum suitEnum;
	public GameTexasCardValueEnum valueEnum;

	byte value;
	String hex;
	
	



	public GameTexasCard(GameTexasCardSuitsEnum suitEnum,
			GameTexasCardValueEnum valueEnum) {
		super();
		this.suitEnum = suitEnum;
		this.valueEnum = valueEnum;
		this.parse();
	}
	
	

	public GameTexasCard(byte value) {
		super();
		this.value = value;
		this.parse(this.value);
	}



	public GameTexasCard(String hex) {
		super();
		this.hex = hex;
		int i = Integer.parseInt(hex, 16);
		parse((byte) i);
	}

	private void parse(byte value) {
		// get the high 4 bits
		byte h = (byte) ((value) & 0xf0);
		byte l = (byte) (value & 0x0f);

		suitEnum = GameTexasCardSuitsEnum.getTexasCardSuit(h);
		valueEnum = GameTexasCardValueEnum.getCardValue(l);
	}

	
	private void parse(){
		this.value = (byte) (suitEnum.getValue() | valueEnum.getValue());
		this.hex = Integer.toHexString(this.value);
	}
	
	
	@Override
	public String toString() {

		this.parse();
		return this.hex;

	}

	
	public static GameTexasCard obtain(GameTexasCardSuitsEnum suitEnum,
			GameTexasCardValueEnum valueEnum){
		return new GameTexasCard(suitEnum, valueEnum);
	}
}
