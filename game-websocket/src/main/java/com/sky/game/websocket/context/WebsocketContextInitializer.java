/**
 * 
 */
package com.sky.game.websocket.context;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.SpringContext;
import com.sky.game.context.message.impl.ice.IceServantAsyncMessageHandler;
import com.sky.game.context.message.impl.ice.IceServantMessageHandler;
import com.sky.game.websocket.util.WebsocketConfiguration;

/**
 * @author Administrator
 *
 */
@Deprecated
public class WebsocketContextInitializer implements ServletContextListener {
	private static final Log logger=LogFactory.getLog(WebsocketContextInitializer.class);
	/**
	 * 
	 */
	public WebsocketContextInitializer() {
		// TODO Auto-generated constructor stub
	}
	Thread daemon;
	
	private static final Object lock=new Object();
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		synchronized (lock) {
			
		logger.info("contextInitialized starting....");
		String file = sce.getServletContext().getInitParameter("websocket-context");
		
		SpringContext.init("classpath:META-INF/spring/applicationContext.xml");
		InputStream url = null, gameContextIs = null;
		try {
			configuration=WebsocketConfiguration.load(file);
			String gameContexConf = configuration.getContextConf();
			gameContextIs = WebsocketConfiguration.class
					.getResourceAsStream(gameContexConf);
			GameContextGlobals.init(gameContextIs);

			List<Ice.Object> servants = new LinkedList<Ice.Object>();
			servants.add(new IceServantMessageHandler());
			servants.add(new IceServantAsyncMessageHandler());
			GameContextGlobals.getIceServerManager().setServants(servants);
			
			
			
			
			daemon=new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					GameContextGlobals.getIceServerManager().start();
				}
				
				
			});
			daemon.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("contextInitialized error"+e.getMessage());
		}
		}

	}

	WebsocketConfiguration configuration;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
		
		try {
			daemon.join(10L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GameContextGlobals.getIceServerManager().cleanup();
	}

}
