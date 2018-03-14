/**
 * @sparrow
 * @Jan 15, 2015   @2:33:57 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sparrow
 *
 */
public class LLGameObjectMap<T> extends ConcurrentHashMap<Long, T> {
	
	public static <T> LLGameObjectMap<T> getMap(){
		return new LLGameObjectMap<T>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3594345843297923568L;

	/**
	 * 
	 */
	public LLGameObjectMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
