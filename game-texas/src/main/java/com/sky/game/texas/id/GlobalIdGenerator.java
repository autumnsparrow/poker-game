/**
 * 
 * @Date:Nov 4, 2014 - 10:43:22 AM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.id;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author sparrow
 *
 */
public class GlobalIdGenerator {
	
	
	

	/**
	 * 
	 */
	public GlobalIdGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	private static final ConcurrentHashMap<String,ConcurrentLinkedQueue<Long>> ID_MAPS=new ConcurrentHashMap<String, ConcurrentLinkedQueue<Long>>();
	
	private static final ConcurrentHashMap<String, Long> ID_SEEDS=new ConcurrentHashMap<String, Long>();
	// loading the ID_SEEDS
	static{
		//long seed=System.currentTimeMillis();
		long seed=100000000;
		long step=1;
		for(IdTypesEnum type:IdTypesEnum.values()){
			step++;
			ID_SEEDS.put(type.toString(), Long.valueOf(step*seed));
		}
		
	}
	
	public static synchronized long getId(IdTypesEnum idTypes){
		Queue<Long> queue=ID_MAPS.get(idTypes.toString());
		
		if(queue==null){
			queue=new ConcurrentLinkedQueue<Long>();
			Long seed=ID_SEEDS.get(idTypes.toString());
			long s=seed.longValue();
			for(int i=0;i<1000;i++){
				queue.add(Long.valueOf(s++));
			}
			ID_SEEDS.put(idTypes.toString(), Long.valueOf(s));
		}
		Long l=queue.poll();
		return l.longValue();
		
	}
	
	public static long getTableId(){
		return getId(IdTypesEnum.Table);
	}

	
	public static long getHouseId(){
		return getId(IdTypesEnum.House);
	}
	
	public static long getAreaId(){
		return getId(IdTypesEnum.Area);
	}
}
