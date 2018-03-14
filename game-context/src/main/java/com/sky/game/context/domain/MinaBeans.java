/**
 * 
 */
package com.sky.game.context.domain;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.message.MessageBean;
import com.sky.game.context.message.ProxyMessageInvoker;

/**
 * @author sparrow
 *
 */
public class MinaBeans {
	
	@HandlerRequestType(transcode="SM0001")
	public static class Request{
		String content;
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public Request(String content) {
			super();
			this.content = content;
			
		}
		
		
		public Request(MessageBean message){
			super();
			String m = message.transcode + "&&" + message.content;
			this.content=m;
		}
		
		
		@Override
		public String toString() {
			return  content;
		}
		
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

		public static Request getRequest(String transcode,Object request,String token){
			MessageBean message=new MessageBean();
			message.transcode=transcode;
			message.content=GameContextGlobals.getJsonConvertor().format(request);
			message.token=token;//GameContextGlobals.getToken();
			return new Request(message);
		}
		
	}
	
	
	@HandlerNamespaceExtraType(namespace="GameMina")
	public static class Extra{
		String deviceId;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public Extra(String deviceId) {
			super();
			this.deviceId = deviceId;
		}
		
		public static Extra getExtra(String deviceId){
			return new Extra(deviceId);
		}

		public Extra() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	@HandlerAsyncType(namespace="GameMina",transcode="SM0001",enable=true,enableFilter=false)
	public static class Handler{
		
	}
	
	
	// add
	// don't allow the 
	
	public static void sendMinaMessage(String token,String deviceId,String transcode,Object obj){
		Request req=Request.getRequest(transcode,obj,token);
		
		ProxyMessageInvoker.onRecieve("SM0001", req, Extra.getExtra(deviceId));
	}

	
	
}
