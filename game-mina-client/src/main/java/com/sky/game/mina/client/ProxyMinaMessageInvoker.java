/**
 * 
 */
package com.sky.game.mina.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.AnnotationIntrospector.ProtocolMapEntry;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.handler.ValueHolder;
import com.sky.game.context.json.HandlerObjectType;
import com.sky.game.context.message.IMessageHandler;
import com.sky.game.context.message.MessageBean;
import com.sky.game.mina.context.SessionContext;


/**
 * 
 * 
 * client invoker 
 * @author sparrow
 *
 */
public class ProxyMinaMessageInvoker {
	private static final Log logger=LogFactory.getLog(ProxyMinaMessageInvoker.class);

	
	/**
	 * system internal can ignore the token value.
	 * 
	 * 
	 * @param transcode
	 * @param request
	 * @param holder
	 * @return
	 */
	public static <Req,Resp> boolean invoke(SessionContext context,String transcode,Req request,ValueHolder<Resp> holder){
		MessageBean bean=new MessageBean();
		bean.transcode=transcode;
		bean.content=GameContextGlobals.getJsonConvertor().format(request);
		if(request instanceof BaseRequest){
			bean.token=((BaseRequest)request).getToken();//GameContextGlobals.getToken();
		}
		IClientMessage proxyHandler=context.getHandler();//GameContextGlobals.getProxyMessageHandler();
		boolean ret=true;
		try {
			MessageBean resp=proxyHandler.invoke(bean);
			// when resp update the token.
			//GameContextGlobals.updateToken(resp.token);
			String responsecode=GameContextGlobals.getAnnotationIntrospector().getProtocolMapEntry(transcode).responsecode;
			if(responsecode.equals(resp.transcode)){
				holder.value=GameContextGlobals.getJsonConvertor().convert(transcode, resp.content, HandlerObjectType.Response);
				holder.enableExtra=false;
			}
			else{
				holder.extra=GameContextGlobals.getJsonConvertor().convert(resp.transcode, resp.content, HandlerObjectType.Response);
				holder.enableExtra=true;
			}
			
		} catch (Exception e){
			e.printStackTrace();
			ret=false;
		}
		return ret;
	}
	
	
	public static <Request,Extra> void asyncInvoke(SessionContext context,Request request){
		ProtocolMapEntry entry=GameContextGlobals.getAnnotationIntrospector().getProtocolMapEntryByRequestClass(request);
		
		if(entry!=null){
			asyncInvoke(context,entry.transcode, request);
		}else{
			logger.error("Can't find the protocolMapEntry by request:"+request.toString());
		}
	
	}
	

	
	/**
	 * 
	 * 
	 * 
	 * @param transcode
	 * @param request
	 * @param extra
	 */
	public static <Request,Extra> void asyncInvoke(SessionContext context,String transcode,Request request) {
		MessageBean bean=new MessageBean();
		bean.transcode=transcode;
		bean.content=GameContextGlobals.getJsonConvertor().format(request);
		
		if(request instanceof BaseRequest){
			bean.token=((BaseRequest)request).getToken();//GameContextGlobals.getToken();
		}else {
			bean.token="System Token";
		}
		//bean.token=GameContextGlobals.getToken();
		
		IClientMessage proxyHandler=context.getHandler();//GameContextGlobals.getProxyMessageHandler();
	//	String extraContent=extra==null?"":GameContextGlobals.getJsonConvertor().format(extra);
		try {
			
			proxyHandler.onRecieve(bean, context);
			
		} catch (Exception e){
			e.printStackTrace();
			
		}
		
		
	}

}
