/**
 * 
 */
package com.sky.game.context.spring.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.MessageInternalBean;
import com.sky.game.context.MessageInternalHandlerPrx;
import com.sky.game.context.handler.ValueHolder;
import com.sky.game.context.ice.IceProxyManager;
import com.sky.game.context.spring.ice.MessageInternalBeanParameterWrapper;


/**
 * @author sparrow
 *
 */

public class RemoteServiceProxy<T> implements InvocationHandler, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7927810381416340002L;
	 private final Class<T> mapperInterface;
	

	/**
	 * @param mapperInterface
	 * @param methodCache
	 */
	public RemoteServiceProxy(Class<T> mapperInterface) {
		super();
		this.mapperInterface = mapperInterface;
		
	}



	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Object o=null;
		try {
			MessageInternalHandlerPrx prx=getProxy();
			MessageInternalBean response= prx.invoke(buildRequest(method, args));
			
			o = parseResponse(method, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   return o; 
	  }
	
	/**
	 * 
	 * get the namespace by the class path.
	 * @return
	 */
	private String getNamespace(){
		return mapperInterface.getPackage().getName();
	}
	
	
	private MessageInternalHandlerPrx getProxy(){
		String  namespace=getNamespace()+"."+MessageInternalHandlerPrx.class.getSimpleName();
		IceProxyManager proxyManager=GameContextGlobals.getIceProxyManager();
		
		MessageInternalHandlerPrx prx=proxyManager.getMessageInternalHandlerPrx(namespace);
		
		return prx;
	}
	
	private MessageInternalBean buildRequest(Method method,Object[] args){
		MessageInternalBean o=new MessageInternalBean();
		o.ns=getNamespace();
		o.operation=mapperInterface.getSimpleName()+"."+method.getName();
		Map<Integer,String> parameters=new HashMap<Integer,String>();
		if(args!=null){
		for(int i=0;i<args.length;i++){
			parameters.put(Integer.valueOf(i), GameContextGlobals.getJsonConvertor().format(args[i]));
		}
		o.parameter=GameContextGlobals.getJsonConvertor().format(MessageInternalBeanParameterWrapper.obtain(parameters));
		}else{
			o.parameter="";
		}
		
		
		return o;
		
	}
	
	private Object parseResponse(Method method,MessageInternalBean response){
		String resp=response.parameter;
		
		Object o=null;
		if(!method.getReturnType().getName().equals("void")){
			o =GameContextGlobals.getJsonConvertor().convert(resp,method.getReturnType());
		}
		//Object o=vh.value;
		return o;
		
		
	}
	

}
