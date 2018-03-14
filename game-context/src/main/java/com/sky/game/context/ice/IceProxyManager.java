/**
 * @company Palm Lottery Information&Technology Co.,Ltd.
 *
 * @author  sparrow
 *
 * @date    Sep 7, 2013-6:14:52 PM
 *
 */
package com.sky.game.context.ice;

import java.util.HashMap;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.MessageAsyncHandlerPrx;
import com.sky.game.context.MessageAsyncHandlerPrxHelper;
import com.sky.game.context.MessageHandlerPrx;
import com.sky.game.context.MessageHandlerPrxHelper;
import com.sky.game.context.MessageInternalHandlerHolder;
import com.sky.game.context.MessageInternalHandlerPrx;
import com.sky.game.context.MessageInternalHandlerPrxHelper;
import com.sky.game.context.configuration.ice.IceServiceConfig;
import com.sky.game.context.configuration.ice.IceServiceConfigRegstry;
import com.sky.game.context.util.GameUtil;











import Ice.InitializationData;
import Ice.ObjectPrx;

/**
 * 
 * Ice Proxy manager.
 * 
 * @author sparrow
 *
 */
public class IceProxyManager {
	
	
	private static final Log logger=LogFactory.getLog(IceProxyManager.class);
	Ice.Communicator communictor;
	
	String serviceName;
	
	

	
	

	/**
	 * 
	 */
	public IceProxyManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void init(){
		IceServiceConfig iceServiceConfig=IceServiceConfigRegstry.getServiceConfig(serviceName);
		if(iceServiceConfig==null)
			throw new RuntimeException("Can't load ice config service :"+serviceName);
		InitializationData config= iceServiceConfig.getIceServerConfig(true);
		//config.properties.
		
		
		
		
		
		
		
		//logger.info("loading proxy:"+ProtocolStoragePrx.class.getSimpleName()+ "- "+config.properties.getProperty(ProtocolStoragePrx.class.getSimpleName()));
		//loadProxy(ProtocolStoragePrx.class,config.properties);
		//loadProxy(ProtocolProcessorPrx.class,config.properties);
		
		
		communictor=Ice.Util.initialize(config);
	}
	
	HashMap<String,String> handlerProxies=new HashMap<String,String>();
	
	private void loadProxy(Class<?> clazz ,Ice.Properties pros){
		String name=clazz.getSimpleName();
		String proxies=pros.getProperty(name+".Servers");
		String[]  proxyiesNames=proxies.split(",");
		for(String proxy:proxyiesNames){
			String k=proxy+"."+name;
			String value=pros.getProperty(k);
			if(value==null){
				value="<not setting>";
			}else{
				handlerProxies.put(proxy, k);
			}
			
			logger.info("loading proxy:"+k+ "- "+value);
		}
		
		
		
	}
	
	public synchronized Set<String> getProxyNames(){
		return handlerProxies.keySet();
	}
	
	
	
	public MessageInternalHandlerPrx getMessageInternalHandlerPrx(String name){
		MessageInternalHandlerPrx handlerPrx=null;
		try {
		
		ObjectPrx prx=communictor.propertyToProxy(name);//communictor.stringToProxy(ProtocolStoragePrx);
		
		if(prx!=null){
			
				handlerPrx=MessageInternalHandlerPrxHelper.checkedCast (prx);
				//handlerPrx.ice_ping();
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return handlerPrx;
	}
	//private static final String ProtocolStoragePrx="ProtocolStorage.Proxy";
	
	public  MessageHandlerPrx getMessageHandlerProxy(String name){
		MessageHandlerPrx handlerPrx=null;
		try {
		
		String k=String.format("%s.MessageHandlerPrx", name);
	
		ObjectPrx prx=communictor.propertyToProxy(k);//communictor.stringToProxy(ProtocolStoragePrx);
		
		if(prx!=null){
			
				handlerPrx=MessageHandlerPrxHelper.uncheckedCast(prx);
				handlerPrx.ice_ping();
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return handlerPrx;
	}
	
	
	public synchronized MessageAsyncHandlerPrx getMessageAsyncHandlerProxy(String name){
		
		String k=String.format("%s.MessageAsyncHandlerPrx", name);
		ObjectPrx prx=communictor.propertyToProxy(k);//communictor.stringToProxy(ProtocolStoragePrx);
		MessageAsyncHandlerPrx handlerPrx=null;
		if(prx==null){
			throw new RuntimeException("Can't cast the property :"+k+" into ObjectPrx");
		}
		if(prx!=null){
			try {
				
				handlerPrx=MessageAsyncHandlerPrxHelper.uncheckedCast(prx);
				handlerPrx.ice_ping();
				//handlerPrx=handlerPrx.ice_oneway();
				//handlerPrx.ice_oneway();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return handlerPrx;
	}
	
//	public synchronized ProtocolStoragePrx getProxy(){
//		
//		ObjectPrx prx=communictor.propertyToProxy(ProtocolStoragePrx.class.getSimpleName());//communictor.stringToProxy(ProtocolStoragePrx);
//		ProtocolStoragePrx protocolStoragePrx=null;
//		if(prx!=null){
//			try {
//				protocolStoragePrx=ProtocolStoragePrxHelper.checkedCast(prx);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return protocolStoragePrx;
//		
//	}
	

	

	
	public void cleanup(){
		if(communictor!=null)
			communictor.destroy();
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	

}
