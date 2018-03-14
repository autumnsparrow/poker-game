/**
 * 
 */
package com.sky.game.protocol.event.handler;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.EventHandlerClass;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.lock.IObtain;
import com.sky.game.context.lock.VolatileLocking;
import com.sky.game.protocol.event.MinaEvent;

/**
 * @author Administrator
 *
 */

public class MinaEventHandler {
	private static Log logger=LogFactory.getLog(MinaEventHandler.class);
	
	
	private static MinaEventHandler handler=new MinaEventHandler();
	
	public static MinaEventHandler getHandler(){
		return handler;
	}
	
	
	
	public static void addRecieveMinEvent(MinaEvent evt){
		handler.addRecievedEvent(evt);
	}

	//static LinkedBlockingQueue<MinaEvent> minaEventQueue;
	
	//static LinkedBlockingQueue<MinaEvent> minaReceivedQueue;
	
	//static LinkedList<IMinaRecieveObserver> minaRecivedObservers;
	/**
	 * 
	 * TODO:
	 *  handler map is key performance code, it need to optimize.
	 *  
	 * 
	 * 
	 * 
	 * 
	 */
	static ConcurrentHashMap<String, LinkedList<MinaEventHandlerObject>> HANDLER_MAPS;
 //	private final static int MAX_THREADS=5;	
	/**
	 * 
	 */
	public MinaEventHandler() {
		// TODO Auto-generated constructor stub
		super();
		init();
		
	}
	
	
	VolatileLocking<ExecutorService> vlExecutorService;
	
	public void init(){
		//minaEventQueue=new LinkedBlockingQueue<MinaEvent>();
		
		//executorService=GameContextGlobals.getExecutorUnOrdered();
		//Executors.newFixedThreadPool(MAX_THREADS);
		
	//	minaReceivedQueue=new LinkedBlockingQueue<MinaEvent>();
		//minaRecivedObservers=new LinkedList<IMinaRecieveObserver>();
		HANDLER_MAPS=new ConcurrentHashMap<String, LinkedList<MinaEventHandlerObject>>();
		vlExecutorService=new VolatileLocking<ExecutorService>();
	//	new Thread(new SendingThread()).start();
		//new Thread(new RecievingThread()).start();
		
	}
	
	
	private ExecutorService getExecutorService(){
		return vlExecutorService.getHelper(new IObtain<ExecutorService, Object>() {

			public ExecutorService obtain(Object o) {
				// TODO Auto-generated method stub
				
				return GameContextGlobals.getExecutorUnOrdered();
			}
		}, null);
	}
	
	
	//static ExecutorService  executorService;
	
	
	
	private static class MinaEventRecievedTask implements Runnable{
		
		
		MinaEvent minaEvent;
		

		public MinaEventRecievedTask(MinaEvent minaEvent) {
			super();
			
			this.minaEvent=minaEvent;
		}


		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			// TODO Auto-generated method stub
			try {
				
				
				// don't need the register event handler annotation anymore.
				EventHandlerClass eventHandleClass=GameContextGlobals.getEventHandlerClass(this.minaEvent.transcode);
				if(eventHandleClass==null||this.minaEvent==null)
					return;
				IIdentifiedObject obj=eventHandleClass.getObject(this.minaEvent.getId());
				
				if(obj!=null){
				if(obj instanceof IIdentifiedObject){
					
						eventHandleClass.handle(obj, this.minaEvent);
					}
					else{
						logger.info("ERRROR -object not instance of IIdentifiedObject :"+obj.getClass());
					}
				}else{
					throw new RuntimeException("transcode:"+this.minaEvent.transcode+ " obj:"+minaEvent.getId()+" userId:"+this.minaEvent.userId+" deviceId:"+this.minaEvent.deviceId+" can't found the event handler instance.");
				}
				

				//this.observer.onRecievedMinaEvent(minaEvent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	

	
	public void addRecievedEvent(MinaEvent evt){
		try {
		
			
			if(evt!=null){
				getExecutorService().execute(new MinaEventRecievedTask(evt));
			}
			//minaReceivedQueue.put(evt);
			logger.debug("recieved:"+evt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	

}
