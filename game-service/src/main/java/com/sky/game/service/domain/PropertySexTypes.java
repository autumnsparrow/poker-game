/**
 * sparrow
 * game-service 
 * Jan 10, 2015- 10:08:04 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;

/**
 * @author sparrow
 *
 */
public enum PropertySexTypes {
	MALE(1),
	FEMALE(0);
	public int value;

	/**
	 * @param value
	 */
	private PropertySexTypes(int value) {
		this.value = value;
	}
	
	
	
}
