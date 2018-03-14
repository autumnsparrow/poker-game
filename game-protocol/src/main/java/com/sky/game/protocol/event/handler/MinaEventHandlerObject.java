/**
 * 
 * @Date:Nov 14, 2014 - 9:01:26 AM
 * @Author: sparrow
 * @Project :game-protocol 
 * 
 *
 */
package com.sky.game.protocol.event.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.sky.game.protocol.event.MinaEvent;

/**
 * @author sparrow
 *
 */
public class MinaEventHandlerObject {
	
	Object instance;
	Method method;
	Method filter;
	String transcode;
	
	
	

	private MinaEventHandlerObject(String transcode, Object instance,
			Method method, Method filter) {
		super();
		this.transcode = transcode;
		this.instance = instance;
		this.method = method;
		this.filter = filter;
	}
	
	public static MinaEventHandlerObject obtain(String transcode, Object instance,
			Method method, Method filter){
		return new MinaEventHandlerObject(transcode, instance, method, filter);
	}

	
	
	public static MinaEventHandlerObject obtain(String transcode, Object instance){
		return new MinaEventHandlerObject(transcode, instance);
	}


	private MinaEventHandlerObject(String transcode, Object instance) {
		super();
		this.transcode = transcode;
		this.instance = instance;
	}

	/**
	 * 
	 */
	public MinaEventHandlerObject() {
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean filter(MinaEvent evt){
		boolean ret=false;
		try {
			Object obj=filter.invoke(instance, evt);
			if(obj instanceof Boolean){
				ret=((Boolean)obj).booleanValue();
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public void handle(MinaEvent evt){
		try {
			method.invoke(instance, evt);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
