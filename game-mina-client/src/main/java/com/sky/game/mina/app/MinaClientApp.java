package com.sky.game.mina.app;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javassist.bytecode.analysis.Executor;

import org.apache.mina.core.session.IoSession;


import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.Message;
import com.sky.game.context.SpringContext;
import com.sky.game.context.handler.ValueHolder;
import com.sky.game.context.service.ServerStarupService;
import com.sky.game.mina.MinaConnector;
import com.sky.game.mina.client.ProxyMinaMessageInvoker;
import com.sky.game.mina.context.SessionContext;
import com.sky.game.mina.packet.Packet;
import com.sky.game.mina.packet.PacketException;
import com.sky.game.mina.packet.PacketTypes;

import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.commons.GT0001Beans;
import com.sky.game.protocol.commons.GU0001Beans;
import com.sky.game.protocol.commons.GU0002Beans;
import com.sky.game.protocol.commons.GU0003Beans;
import com.sky.game.protocol.commons.GU0003Beans.Request;
import com.sky.game.protocol.commons.GU0007Beans;


/**
 * Hello world!
 * 
 */
public class MinaClientApp {

	public static void log(String msg) {
		System.out.append(msg);
	}

	


	
	static int currentLoc=4759;
	public static void main(String[] args) throws PacketException {
		System.out.println("Hello World!");
		GameContextGlobals.init(ServerStarupService.class
				.getResourceAsStream("/META-INF/game-context.conf"));
		SpringContext.init(new String[] {

		"/META-INF/spring/applicationContext.xml" });

		MinaConnector connector = new MinaConnector();
		final ExecutorService service = Executors.newFixedThreadPool(100);

		final MobileUser[] mobileUsers = new MobileUser[10000];
		for (int i = currentLoc; i < mobileUsers.length; i++) {
			mobileUsers[i] = MobileUser.obtain(connector, "Robot" + ( i),
					"111111", "0123456789-" + (i));
		}
		
		
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				service.execute(new Runnable() {

					public void run() {
						
						// TODO Auto-generated method stub
						if(currentLoc<mobileUsers.length){
							mobileUsers[currentLoc].connect();
							currentLoc++;
						}
					}
				});
				
			}
			
			
		}, 1000L, 10*1);
		
		
		

		

	}
	

}
