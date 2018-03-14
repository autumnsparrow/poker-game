/**
 * 
 */
package com.sky.game.context.service;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;







import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.message.impl.ice.IceServantAsyncMessageHandler;
import com.sky.game.context.message.impl.ice.IceServantMessageHandler;
import com.sky.game.context.message.impl.ice.IceServantMessageInternalHandler;
import com.sky.game.context.util.GameUtil;

/**
 * @author Administrator
 *
 */
@Service
public class ServerStarupService {
	
	//"/META-INF/game-context.conf"
	
	String configuration;
	
	public String getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public ServerStarupService() {
		super();
		// TODO Auto-generated constructor stub
		
	}

	@Autowired
	public ServerStarupService(@Value("${game.context}")String configuration) {
		super();
		this.configuration = configuration;
		//init();
	}
	
	public void loadClient(){
		  new Thread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					  GameContextGlobals.loadClient(ServerStarupService.class.getResource(configuration));// 初始化 MessageHandlerPrx （ice 接口）
				      
				}
			}).start();
	}
		
	public void load(){
		  try {
			GameContextGlobals.init(GameUtil.getInputStream(configuration));
			 List<Ice.Object> servants=new LinkedList<Ice.Object>();
		      servants.add(new IceServantMessageHandler());//添加ice 对象IceServantMessageHandler这里继承了_MessageHandlerDisp实现了它的invoke 放法
		      servants.add(new IceServantAsyncMessageHandler());
		      servants.add(new IceServantMessageInternalHandler());
		      GameContextGlobals.getIceServerManager().setServants(servants);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 初始化 MessageHandlerPrx （ice 接口）
	     
	}
	
	public void start(){
		 GameContextGlobals.getIceServerManager().start();
	}

	public void startup(){
		 load();
		 start();
	}
	
	private Thread t;
	@SuppressWarnings("unused")
	public void init(){

	      t=new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				  try {
					  GameContextGlobals.init(GameUtil.getInputStream(configuration));// 初始化 MessageHandlerPrx （ice 接口）
					  List<Ice.Object> servants=new LinkedList<Ice.Object>();
					  servants.add(new IceServantMessageHandler());//添加ice 对象IceServantMessageHandler这里继承了_MessageHandlerDisp实现了它的invoke 放法
					  servants.add(new IceServantAsyncMessageHandler());
					  servants.add(new IceServantMessageInternalHandler());
					  GameContextGlobals.getIceServerManager().setServants(servants);
					  GameContextGlobals.getIceServerManager().start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	     t.start();
	      
	}
	
	
	

}
