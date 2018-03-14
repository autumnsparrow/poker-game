/**
 * sparrow
 * game-service 
 * Jan 27, 2015- 10:26:29 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;

/**
 * @author sparrow
 *
 */
public enum GameDataCategoryTypes {
	Undefined(0),
	CATEGORY_COINS(1),
	CATEGORY_GOLD(2),
	CATEGORY_GOLD_VIP(3);
	
	public int type;

	/**
	 * @param type
	 */
	private GameDataCategoryTypes(int type) {
		this.type = type;
	};
	
	public static final int CategoryCoins=1;
	public static final int CategoryGold=2;
	public static final int CategoryGoldVip=3;
	
	private static final GameDataCategoryTypes[] types=new GameDataCategoryTypes[]{
		Undefined,
		CATEGORY_COINS,
		CATEGORY_GOLD,
		CATEGORY_GOLD_VIP
	};
	
	
	public static GameDataCategoryTypes getType(int loc){
		return types[loc];
	}
	
	

}
