/**
 * 
 */
package com.sky.game.landlord.message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
public class MessageBroker {
	
	private static final Log logger=LogFactory.getLog(MessageBroker.class);
	
	ConcurrentHashMap<String,Map<Long,IIdentifiedObject>> subscriblesMap;
	
	public static final MessageBroker boker=new MessageBroker();
	

	/**
	 * 
	 */
	public MessageBroker() {
		// TODO Auto-generated constructor stub
		subscriblesMap=new ConcurrentHashMap<String,Map<Long,IIdentifiedObject>>();
	}
	
	
	private synchronized Map<Long,IIdentifiedObject> getSubscribles(String uri){
		Map<Long,IIdentifiedObject> subscribles=null;
		if(!subscriblesMap.containsKey(uri)){
			subscribles=GameUtil.getMap();
			subscriblesMap.put(uri, subscribles);
		}else{
			subscribles=subscriblesMap.get(uri);
		}
		return subscribles;
	}
	
	public synchronized void subscrible(String uri,IIdentifiedObject obj){
		Map<Long,IIdentifiedObject> subscribles=getSubscribles(uri);
		if(!subscribles.containsKey(obj.getId())){
			subscribles.put(obj.getId(), obj);
		}
		
		
		
	}
	
	public synchronized void unSubscrible(IIdentifiedObject obj){
		for(String uri:subscriblesMap.keySet()){
			unSubscrible(uri, obj);
		}
	}
	
	
	public synchronized void unSubscrible(String uri,IIdentifiedObject obj){
		Map<Long,IIdentifiedObject> subscribles=getSubscribles(uri);
		if(obj.getId()!=null){
		if(subscribles.containsKey(obj.getId())){
			subscribles.remove(obj.getId());
		}
		}
	}
	
	
	public  void sendBroadcastMessage(String uri,Object obj,boolean request ){
		//logger.debug(GameUtil.formatUri(uri, obj)+" - "+GameContextGlobals.getJsonConvertor().format(obj));
		//logger.info("broadcast["+uri+"] -> "+GameContextGlobals.getJsonConvertor().format(obj));
		Map<Long,IIdentifiedObject> subscribles=getSubscribles(uri);
		for(IIdentifiedObject i:subscribles.values()){
			if(i instanceof IBrokerMessage){
				IBrokerMessage player=(IBrokerMessage)i;
				player.sendBrokerMessage(obj, request);
			}
		}
	}
	
	
	
	
	
	
	
	
	

}
