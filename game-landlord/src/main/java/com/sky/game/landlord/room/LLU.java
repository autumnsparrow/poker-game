package com.sky.game.landlord.room;


/**
 * @sparrow
 * @2:39:45 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */

import java.util.concurrent.atomic.AtomicLong;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.id.LLGlobalIdGenerator;
import com.sky.game.context.id.LLIdTypes;
import com.sky.game.context.util.BeanUtil;
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
		
		if(obj!=null){
			t=(T)obj;
		}
		return t;
	}
	
	public static Long getId(LLIdTypes types){
		return LLGlobalIdGenerator.g.getId(types.type);
	}

	
	public static <T> T o(Class<?> clz){
		return GameUtil.obtain(clz);
	}
	
	
	public static <T> T exp(Object o,String e){
		return BeanUtil.v(o, e);
	}

	public static <T> LLGameObjectMap<T> getGameObjectMap(){
		return LLGameObjectMap.getMap();
	}
	
	/**
	 * {@link GameContextGlobals#registerEventHandler(String, IIdentifiedObject)}
	 * @param obj
	 * @param transcode
	 */
	public static void regeisterHandler(IIdentifiedObject obj,String ...strings ){
		for(String s:strings){
			GameContextGlobals.registerEventHandler(s,obj);
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @param strings
	 */
	public static void removeHandler(IIdentifiedObject obj,String ...strings ){
		for(String s:strings){
			GameContextGlobals.unregisterEventHandler(s,obj);
		}
	}
	
	
	public static void clearAllHandlers(IIdentifiedObject obj){
		GameContextGlobals.removeObjectEventHandler(obj);
	}
	
	//static long robotUserId = 1;
	static AtomicLong robotUserId=new AtomicLong(1);
	public static long getRobotId(){
		if(robotUserId.longValue()>9999){
			robotUserId=new AtomicLong(1);
		}
		return robotUserId.getAndIncrement();
	}
}

