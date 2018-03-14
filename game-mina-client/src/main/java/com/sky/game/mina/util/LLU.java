/**
 * @sparrow
 * @2:39:45 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.mina.util;

import com.sky.game.context.util.GameUtil;

import com.sky.game.protocol.event.MinaEvent;

/**
 * @author sparrow
 *
 */
public class LLU {

	/**
	 * 
	 */
	public LLU() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static <T> T evtAsObj(MinaEvent evt){
		T t=null;
		if(evt!=null&&evt.obj!=null){
			//Class<?> clz=Class.forName(t.getClass().getName());
			t=(T) evt.obj;
		}
		
		return t;
	}
	
	public static <T> T asObject(Object obj){
		T t=null;
		
		if(obj!=null&&t.getClass().isInstance(obj)){
			t=(T)obj;
		}
		return t;
	}
	

	
	public static <T> T o(Class<?> clz){
		return GameUtil.obtain(clz);
	}
}
