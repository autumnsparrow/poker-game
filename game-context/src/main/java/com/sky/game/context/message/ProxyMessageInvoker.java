/**
 * 
 */
package com.sky.game.context.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.AnnotationIntrospector.ProtocolMapEntry;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.handler.ValueHolder;
import com.sky.game.context.json.HandlerObjectType;
import com.sky.game.context.util.GameUtil;


/**
 * 
 * 
 * client invoker 
 * @author sparrow
 *
 */
public class ProxyMessageInvoker {
	
	private static final Log logger=LogFactory.getLog(ProxyMessageInvoker.class);
	public static <Req extends Object,Resp extends Object> Resp invoke(Req request){
		ProtocolMapEntry entry=GameContextGlobals.getAnnotationIntrospector().getProtocolMapEntryByRequestClass(request);
		Resp resp=null;
		if(entry!=null){
			resp=GameUtil.obtain(entry.responseClazz);
			ValueHolder<Resp> holder=new ValueHolder<Resp>(resp);
			invoke(entry.transcode, request, holder);
			if(holder.enableExtra){
				logger.warn("invoke:"+request+" - "+holder.extra);
			}else{
				resp=holder.value;
			}
		}
		return resp;
	}

	
	/**
	 * system internal can ignore the token value.
	 * 
	 * 
	 * @param transcode
	 * @param request
	 * @param holder
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <Req extends Object,Resp extends Object> boolean invoke(String transcode,Req request,ValueHolder<Resp> holder){
		MessageBean bean=new MessageBean();
		bean.transcode=transcode;
		bean.content=GameContextGlobals.getJsonConvertor().format(request);
		if(request instanceof BaseRequest){
			bean.token=((BaseRequest)request).getToken();//GameContextGlobals.getToken();
		}
		IMessageHandler proxyHandler=GameContextGlobals.getProxyMessageHandler();
		boolean ret=true;
		try {
			MessageBean resp=proxyHandler.invoke(bean);
			// when resp update the token.
			//GameContextGlobals.updateToken(resp.token);
			String responsecode=GameContextGlobals.getAnnotationIntrospector().getProtocolMapEntry(transcode).responsecode;
			if(responsecode.equals(resp.transcode)){
				holder.value=(Resp)GameContextGlobals.getJsonConvertor().convert(transcode, resp.content, HandlerObjectType.Response);
				holder.enableExtra=false;
			}
			else{
				holder.extra=GameContextGlobals.getJsonConvertor().convert(resp.transcode, resp.content, HandlerObjectType.Response);
				holder.enableExtra=true;
			}
			
		} catch (MessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret=false;
		}catch (Exception e){
			e.printStackTrace();
			ret=false;
		}
		return ret;
	}
	
	
	public static <Request,Extra> void onRecieve(Request request,Extra extra){
		ProtocolMapEntry entry=GameContextGlobals.getAnnotationIntrospector().getProtocolMapEntryByRequestClass(request);
		if(entry!=null){
			onRecieve(entry.transcode, request, extra);
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
	public static <Request,Extra> void onRecieve(String transcode,Request request,Extra extra) {
		MessageBean bean=new MessageBean();
		bean.transcode=transcode;
		bean.content=GameContextGlobals.getJsonConvertor().format(request);
		
		if(request instanceof BaseRequest){
			bean.token=((BaseRequest)request).getToken();//GameContextGlobals.getToken();
		}else {
			bean.token="System Token";
		}
		//bean.token=GameContextGlobals.getToken();
		
		IMessageHandler proxyHandler=GameContextGlobals.getProxyMessageHandler();
		String extraContent=extra==null?"":GameContextGlobals.getJsonConvertor().format(extra);
		try {
			
			proxyHandler.onRecieve(bean, extraContent);
			
		} catch (MessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}catch (Exception e){
			e.printStackTrace();
			
		}
		
		
	}

}
