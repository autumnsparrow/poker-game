/**
 * 
 */
package com.sky.game.context.configuration.ice;

import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import Ice.InitializationData;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
@JsonRootName(value="Configuration")
public class IceServiceConfig {
	private static Log logger=LogFactory.getLog(IceServiceConfig.class);
	String name;
	
	String ip;
	int port;
	long timeout;
	ServiceLocators sync;
	ServiceLocators async;
	ServiceLocators remote;

	/**
	 * 
	 */
	public IceServiceConfig() {
		// TODO Auto-generated constructor stub
		this.timeout=30000;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	//GameBlockadeObjectAdapter.Endpoints=tcp -h 127.0.0.1  -p 12000
	private Properties getServerProperties(){
		Properties o=new Properties();
		String k=String.format("%sObjectAdapter.Endpoints", name );
		String v=String.format("tcp -h %s -p %d", ip,port);
		o.put(k, v);
		
		return o;
	}
	
	public  InitializationData getIceServerConfig(boolean client){
		InitializationData config=new InitializationData();
		config.properties=Ice.Util.createProperties();
		Properties pros=client?getClientProperties():getServerProperties();
		for(Object k:pros.keySet()){
			
			Object v=pros.get(k);
			if(v!=null){
				String key=(String)k;
				String value=(String)v;
				config.properties.setProperty(key, value);
				logger.info("IceProperties:"+String.format("[key=%s,value=%s]", key,value));
			}
			
		}
		return config;
	}
	
	
	
	
	private Properties getClientProperties(){
		Properties o=new Properties();
		if(sync.getLocator()!=null&&sync!=null)
		for(ServiceLocator locator:sync.getLocator()){
			IceServiceConfig config=IceServiceConfigRegstry.getServiceConfig(locator.getService());
			if(config!=null)
			o.put(String.format("%s.MessageHandlerPrx", locator.getNs()), String.format("IceServantMessageHandler:tcp -h %s -p %d -t %d", config.getIp(),config.getPort(),config.getTimeout()));
		}
		
		if(async.getLocator()!=null&&async!=null)
		for(ServiceLocator locator:async.getLocator()){
			IceServiceConfig config=IceServiceConfigRegstry.getServiceConfig(locator.getService());
			if(config!=null)
			o.put(String.format("%s.MessageAsyncHandlerPrx", locator.getNs()), String.format("IceServantAsyncMessageHandler:tcp -h %s -p %d -t %d", config.getIp(),config.getPort(),config.getTimeout()));
		}
		
		if(remote.getLocator()!=null&&remote!=null)
		for(ServiceLocator locator:remote.getLocator()){
			IceServiceConfig config=IceServiceConfigRegstry.getServiceConfig(locator.getService());
			if(config!=null)
			o.put(String.format("%s.MessageInternalHandlerPrx", locator.getNs()), String.format("IceServantMessageInternalHandler:tcp:tcp -h %s -p %d -t %d", config.getIp(),config.getPort(),config.getTimeout()));
		}
		
		
		return o;
	}
	
	
	/**
	 * 
	 MessageHandlerPrx.Servers=GameUser,UserCenter,IndianaStar,GameSystem,UserMessage,AuctionCompany,GameMessage

GameUser.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
UserCenter.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
IndianaStar.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
GameSystem.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
UserMessage.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
AuctionCompany.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
GameMessage.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000

MessageAsyncHandlerPrx.Servers=LLGame,MinaServer
MinaServer.MessageAsyncHandlerPrx=IceServantAsyncMessageHandler:tcp  -h 127.0.0.1 -p 18900 -t 30000
LLGame.MessageAsyncHandlerPrx=IceServantAsyncMessageHandler:tcp  -h 127.0.0.1 -p 12000 -t 30000

GameBlockadeObjectAdapter.Endpoints=tcp -h 127.0.0.1  -p 12000

#
	 * @param args
	 * 
	 * 
	 
#MessageHandlerPrx.Servers=GameUser,UserCenter,AuctionCompany

MessageHandlerPrx.Servers=GameUser,UserCenter,GameSystem,IndianaStar,UserMessage,AuctionCompany
GameUser.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
UserCenter.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
UserMessage.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
IndianaStar.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
AuctionCompany.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
GameSystem.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 12000 -t 30000
#
#game system


MessageAsyncHandlerPrx.Servers=LLGame
LLGame.MessageAsyncHandlerPrx=IceServantAsyncMessageHandler:tcp  -h 127.0.0.1 -p 12000 -t 30000

#
#upmp invoke the remote service
com.sky.game.internal.service.payment.upmp.MessageInternalHandlerPrx=IceServantMessageInternalHandler:tcp -h 127.0.0.1 -p 18000 -t 30000

#
# demo invoke the remote service
com.sky.game.service.internal.MessageInternalHandlerPrx=IceServantMessageInternalHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
#
	
	 *
	 *
	
MessageHandlerPrx.Servers=GameUser,GameSystem

GameUser.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000
#configuration the protocol client
GameSystem.MessageHandlerPrx=IceServantMessageHandler:tcp -h 127.0.0.1 -p 18900 -t 30000



MessageAsyncHandlerPrx.Servers=GameMina,LLGame
GameMina.MessageAsyncHandlerPrx=IceServantAsyncMessageHandler:tcp  -h 127.0.0.1 -p 20001 -t 30000
LLGame.MessageAsyncHandlerPrx=IceServantAsyncMessageHandler:tcp  -h 127.0.0.1 -p 12000 -t 30000




com.sky.game.internal.service.payment.upmp.MessageInternalHandlerPrx=IceServantMessageInternalHandler:tcp -h 127.0.0.1 -p 18900 


	 *
	 */
	public static void main(String args[]){
		IceServiceConfig o=GameUtil.obtain(IceServiceConfig.class);
		o.setName("Payment");
		o.setIp("127.0.0.1");
		o.setPort(18000);
		o.sync=new ServiceLocators();
		o.async=new ServiceLocators();
		o.remote=new ServiceLocators();
		
		
		
		o.remote.locator.add(ServiceLocator.obtain("Protocol", "com.sky.game.internal.service.payment.upmp"));
		
		
//		String x=GameContextGlobals.getXmlJsonConvertor().format(o);
//		System.out.println(x);
//		
		
		IceServiceConfigIndex i=GameUtil.obtain(IceServiceConfigIndex.class);
		i.index.add(IceServiceConfigInfo.obtain("Landlord", "/META-INF/ice/Landlord.xml"));
		i.index.add(IceServiceConfigInfo.obtain("Payment", "/META-INF/ice/Payment.xml"));
		i.index.add(IceServiceConfigInfo.obtain("Protocol", "/META-INF/ice/Protocol.xml"));
		i.index.add(IceServiceConfigInfo.obtain("Websocket", "/META-INF/ice/Websocket.xml"));
	String	 x=GameContextGlobals.getXmlJsonConvertor().format(i);
		System.out.println(x);
		
	}

	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ServiceLocators getSync() {
		return sync;
	}

	public void setSync(ServiceLocators sync) {
		this.sync = sync;
	}

	public ServiceLocators getAsync() {
		return async;
	}

	public void setAsync(ServiceLocators async) {
		this.async = async;
	}

	public ServiceLocators getRemote() {
		return remote;
	}

	public void setRemote(ServiceLocators remote) {
		this.remote = remote;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	

}
