/**
 * 
 * @Date:Nov 4, 2014 - 10:43:47 AM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.id;

/**
 * @author sparrow
 *
 */
public enum IdTypesEnum {
	Area(0),
	House(1),
	Table(2),
	Game(3)
	;
	
	public int value;

	private IdTypesEnum(int value) {
		this.value = value;
	}
	
}
