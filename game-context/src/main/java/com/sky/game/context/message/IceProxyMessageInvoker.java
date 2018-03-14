/**
 * 
 */
package com.sky.game.context.message;
import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.Message;
import com.sky.game.context.MessageAsyncHandlerPrx;
import com.sky.game.context.MessageException;
import com.sky.game.context.MessageHandlerPrx;
import com.sky.game.context.annotation.introspector.AnnotationIntrospector.ProtocolMapEntry;
import com.sky.game.context.ice.IceProxyManager;

/**
 * @author sparrow
 * 
 */
public class IceProxyMessageInvoker {

	/**
	 * 
	 */
	public IceProxyMessageInvoker() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static boolean sync(Message msg){
		ProtocolMapEntry entry=GameContextGlobals.getAnnotationIntrospector().getProtocolMapEntry(msg.transcode);
		return entry==null?false:entry.sync;
		
	}

	public static Message invoke(Message msg) throws MessageException {
		MessageHandlerPrx prx = null;
		Message resp = null;
		String namespace = GameContextGlobals.getAnnotationIntrospector()
				.getProtocolMapEntry(msg.transcode).namespace;
		if(namespace==null){
			throw new RuntimeException("transcode :"+msg.transcode+" can't find namespace");
		}
		IceProxyManager manager=GameContextGlobals.getIceProxyManager();
		prx =manager.getMessageHandlerProxy(
				namespace);
		if(prx==null)
			throw new RuntimeException("transcode :"+msg.transcode+" can't find proxy"+" ,namesapce -"+namespace);
		resp = prx.invoke(msg);
		//prx.begin_invoke(msg);
		return resp;

	}
	
	public static void onRecieve(Message msg,Object extra) throws MessageException {
		MessageAsyncHandlerPrx prx=null;
		String namespace = GameContextGlobals.getAnnotationIntrospector()
				.getProtocolMapEntry(msg.transcode).namespace;
		if(namespace==null){
			throw new RuntimeException("transcode :"+msg.transcode+" can't find namespace");
		}
		prx = GameContextGlobals.getIceProxyManager().getMessageAsyncHandlerProxy(namespace);
		if(extra instanceof String){
			
			if(prx!=null){
				
				prx.onRecieve(msg, (String)extra);
			}
			else{
				throw new RuntimeException("transcode :"+msg.transcode+" can't find proxy");
			}
		}
		else{
			String ex=GameContextGlobals.getJsonConvertor().format(extra);
			prx.onRecieve(msg, ex);
			
		}
		
	}

}
