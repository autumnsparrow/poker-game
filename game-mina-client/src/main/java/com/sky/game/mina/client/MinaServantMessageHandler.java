/**
 * 
 */
package com.sky.game.mina.client;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.SpringContext;
import com.sky.game.context.annotation.introspector.AnnotationIntrospector;
import com.sky.game.context.annotation.introspector.AnnotationIntrospector.ProtocolMapEntry;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.BaseResponse;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.json.HandlerObjectType;
import com.sky.game.context.json.IJsonConvertor;
import com.sky.game.context.message.IMessageHandler;
import com.sky.game.context.message.MessageBean;
import com.sky.game.context.message.MessageException;

/**
 * 
 * 
 * server side
 * 
 * @author sparrow
 *
 */
public class MinaServantMessageHandler implements IClientMessage {
	
	

	/**
	 * 
	 */
	public MinaServantMessageHandler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.message.IMessageHandler#send(com.sky.game.context.message.MessageBean)
	 */
	public MessageBean invoke(MessageBean message) throws MessageException {
		// TODO Auto-generated method stub
		MessageBean reponseBean=new MessageBean();
		String transcode=message.transcode;
		String content=message.content;
		String token=message.token;
		
		IJsonConvertor convertor=GameContextGlobals.getJsonConvertor();
		
		Object request=convertor.convert(transcode, content, HandlerObjectType.Request);
		
		if(request!=null&&(request instanceof BaseRequest)){
			BaseRequest baseRequest=(BaseRequest)request;
			baseRequest.setToken(token);
		}
		
		// find the handlers.
		AnnotationIntrospector introspector=GameContextGlobals.getAnnotationIntrospector();
		ProtocolMapEntry entry=introspector.getProtocolMapEntry(transcode);
		
		if(entry.enable&&entry.handlerClazz!=null){
			try {
				Object handler= SpringContext.getBean(entry.transcode);//entry.handlerClazz.newInstance();
				Object response= entry.responseClazz.newInstance();
				if(handler!=null){
					
					try {
						entry.method.invoke(handler,request, response);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						if(e.getTargetException() instanceof ProtocolException){
							ProtocolException pEx=(ProtocolException)e.getTargetException();
							throw new MessageException(pEx.getState(), pEx.getMessage());
						}
					} catch (Exception ex){
						if(ex instanceof ProtocolException){
							ProtocolException pEx=(ProtocolException)ex;
							throw new MessageException(pEx.getState(), pEx.getMessage());
						}
						
					}
					
				}
				
				
				
				if(response!=null){
					
					reponseBean.content=convertor.format(response);
					reponseBean.transcode=entry.responsecode;
					if(response instanceof BaseResponse){
						BaseResponse baseResponse=(BaseResponse)response;
						reponseBean.token=baseResponse.getToken();
					}else{
						reponseBean.token=token;
					}
					
				}
				
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			
			throw new MessageException(-1, "transcode disable");
			
			
		}
		
		
		
		return reponseBean;
	}

	
	/**
	 * 
	 * 
	 * 
	 */
	public void onRecieve(MessageBean message, Object extra)
			throws MessageException {
		// TODO Auto-generated method stub
		
		
		String transcode=message.transcode;
		String content=message.content;
		String token=message.token;
		if(transcode==null)
			throw new MessageException("transcode can't be noting");
		
		IJsonConvertor convertor=GameContextGlobals.getJsonConvertor();
		// this is the response code,let's get the transcode
		
		// find the handlers.
		AnnotationIntrospector introspector=GameContextGlobals.getAnnotationIntrospector();
				
				
		
		
		String requestCode=GameContextGlobals.getTranscodeByResponse(transcode);
		Object request=convertor.convert(requestCode, content, HandlerObjectType.Response);
		
		// using the method extra or the namespace extra.
		
		
//		Object extraParam=null;
//		if(extra!=null)
//			extraParam=convertor.convert(requestCode, extra, HandlerObjectType.Extra);
		
		ProtocolMapEntry entry=introspector.getProtocolMapEntry(transcode);
		if(entry==null){
			entry=introspector.getProtocolManEntryPattern(transcode);
		}
		
		
		if(entry.enable&&entry.handlerClazz!=null){
			try {
				
				Object handler=null;
				try {
					if(entry.enableFilter){
						handler=SpringContext.getBean(entry.handlerClazz.getSimpleName());
					}else{
						handler = SpringContext.getBean(transcode);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(handler!=null){
					
					if(request instanceof BaseResponse){
						BaseResponse response=	(BaseResponse)request;
						response.setToken(token);
					}
					
					try {
						entry.method.invoke(handler,request, extra);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						if(e.getTargetException() instanceof ProtocolException){
							ProtocolException pEx=(ProtocolException)e.getTargetException();
							throw new MessageException(pEx.getState(), pEx.getMessage());
						}
					} catch (Exception ex){
						if(ex instanceof ProtocolException){
							ProtocolException pEx=(ProtocolException)ex;
							throw new MessageException(pEx.getState(), pEx.getMessage());
						}
						
					}
					
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}else{
			
			throw new MessageException(-1, "transcode disable");
			
			
		}
		
		
	}
  

	
}
