/**
 * 
 */
package com.sky.game.protocol;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.input.ProxyInputStream;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.SpringContext;
import com.sky.game.context.handler.ValueHolder;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.context.service.ServerStarupService;
import com.sky.game.context.util.GameUtil;
import com.sky.game.protocol.commons.GU0001Beans;
import com.sky.game.protocol.commons.GU0002Beans;
import com.sky.game.protocol.commons.GU0007Beans;
import com.sky.game.protocol.commons.GU0030Beans;
import com.sky.game.protocol.commons.WX0001Beans;
import com.sky.game.protocol.handler.WX0001Handler;
import com.sky.game.service.domain.Item;
import com.sky.game.service.internal.ItemsWrapper;
import com.sky.game.service.remote.InternalServiceInvoker;
import com.sky.game.service.util.GameServiceUtil;
import com.sky.game.service.util.GameServiceUtil.SexTypes;

/**
 * @author Administrator
 *
 */
public class AppClient {

	/**
	 * 
	 */
	public AppClient() {
		// TODO Auto-generated constructor stub
	}

	
	private static void testGU0007(){

		GU0007Beans.Request request=new GU0007Beans.Request(Long.valueOf(4));
		
		ValueHolder<GU0007Beans.Response> holder=new ValueHolder<GU0007Beans.Response>();
		//request.setToken(token);
		ProxyMessageInvoker.invoke("GU0007", request, holder);
		
		if(holder.enableExtra){
			System.out.println(holder.extra);
		}else{
			System.out.println(holder.value.toString());
		}		
	}
	//private static String token;
	@SuppressWarnings("unused")
	private static void testGU0002(){
		ValueHolder<GU0002Beans.Response> holder=new ValueHolder<GU0002Beans.Response>();
		GU0002Beans.Request request=new GU0002Beans.Request();
		request.setAccount("zhanghao");
		request.setPassword("mimamima");
		request.setDeviceId("2222");
		ProxyMessageInvoker.invoke("GU0002", request, holder);
		
		if(holder.enableExtra){
			System.out.println(holder.extra);
		}else{
			System.out.println(holder.value.toString());
			//token=holder.value.getToken();
		}
		
		
	}
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		SpringContext.init(new String[]{
				"classpath:/META-INF/spring/applicationContext-game-service.xml",
    			"classpath:/META-INF/spring/applicationContext-game-protocol.xml"
    			
    	});
		
		
		ServerStarupService startupService=SpringContext.getBean("serverStarupService");
    	startupService.load();
		
    	Thread.sleep(1000);
    	
    	WX0001Beans.Request request=GameUtil.obtain(WX0001Beans.Request.class);
    	request.setId(1);
    	request.setIp("0.0.0.0");
    	WX0001Beans.Response response=ProxyMessageInvoker.invoke(request);
//    	
//		InternalServiceInvoker invoker=SpringContext.getBean("internalServiceInvoker");
//		
//		ItemsWrapper items=invoker.getItems();
//		System.out.println(Arrays.toString(items.getItems().toArray(new Item[]{})));
//		Item item=invoker.getItemBytId(1001);
//		
//		System.out.println(item);
		
//		URL url=AppClient.class.getResource("/META-INF/game-context.conf");
//		GameContextGlobals.init(url);
		//for(int );
		/*GU0032Beans.Request req=GameUtil.obtain(GU0032Beans.Request.class);
		String[] icon=new String []{"head_1.png","head_2.png","head_3.png","head_4.png","head_5.png","head_6.png","head_7.png","head_8.png","head_11.png","head_12.png","head_13.png","head_14.png","head_15.png","head_16.png"};
		
		for(int i=20987;i<=30986;i++){
			Random r=new Random();
			int a=r.nextInt(14);
			System.out.println(icon[a]);
		System.out.println(icon[a]);
		Thread t=new Thread();
		try {
			t.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setUserId(i);
		req.setValue(icon[a]);
		GU0032Beans.Response resp=ProxyMessageInvoker.invoke(req);
		if(resp!=null){
			System.out.println(1);
			
		}else{
			System.out.println(0);
		}
		}*/
//		
//		/**/
//		
//		//testGU0002();
//		testGU0007();
		
		/**
		 * mysqlbzwt.mysql.rds.aliyuncs.com
//		 */
//		 for(int i=3664;i<=10000;i++){
//			//System.out.println("---------------------");
//			Thread t=new Thread();
//			try {
//				t.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		GU0030Beans.Request req=GameUtil.obtain(GU0030Beans.Request.class);
//	
//		req.setChanelId(99999999);
//		req.setDeviceId("R01234567890-"+i);
//		GU0030Beans.Response resp=ProxyMessageInvoker.invoke(req);
//		if(resp!=null){
//			System.out.println(1);
//			
//		}else{
//			System.out.println(0);
//		}
//		}
//		}
	}

}
