package com.sky.game.protocol;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sky.game.context.util.GameUtil;

/*

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.TaskExecutorFactoryBean;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.scheduling.timer.ScheduledTimerTask;

import IceInternal.Time;

import com.sky.game.context.SpringContext;
import com.sky.game.context.service.ServerStarupService;
import com.sky.game.context.util.GameUtil;
import com.sky.game.service.logic.TaskService;
*/

/**
 * Hello world!
 *
 */
public class App 
{
	public static class Holder{
		int value;
		int rank;
		/**
		 * @param value
		 */
		public Holder(int value) {
			super();
			this.value = value;
		}

		@Override
		public String toString() {
			return "Holder [value=" + value + "]";
		}
		
		
		
	}
	
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
    	List<Holder> holds=GameUtil.getList();
    	for(int i=0;i<20;i++){
    		holds.add(new Holder(i));
    	}
    	Collections.sort(holds, new Comparator<Holder>() {

			public int compare(Holder o1, Holder o2) {
				// TODO Auto-generated method stub
				return o2.value-o1.value;
			}
    		
    		
		});
    	
    	for(int i=0;i<holds.size();i++){
    		holds.get(i).rank=i+1;
    	}
    	
    	System.out.println(Arrays.toString(holds.toArray(new Holder[]{})));
    	
    	
//    	SpringContext.init(new String[]{
//    			"classpath:/META-INF/spring/applicationContext-game-service.xml",
//    			"classpath:/META-INF/spring/applicationContext-game-protocol.xml"
//    			
//    	});
//    	
//    //	MinaEventHandler.getHandler();
//    	ServerStarupService startupService=SpringContext.getBean("serverStarupService");
//    	startupService.startup();

    	
    
//    	final Trigger trigger=new CronTrigger("0 0/1 * * * * ");
//    	final TriggerContext triggerContext=new SimpleTriggerContext();
//    	
//    	
//    	ThreadPoolTaskScheduler scheduler=new ThreadPoolTaskScheduler();
//    	scheduler.initialize();
//    	//final Date c=//trigger.nextExecutionTime(triggerContext);
//    	scheduler.schedule(new Runnable() {
//			Date d;
//			public void run() {
//				// TODO Auto-generated method stub
//				
//				
//		    	//long times=d.getTime();
//		    	if(d==null||GameUtil.expired(d)){
//		    		d=trigger.nextExecutionTime(triggerContext);
//		    		System.out.println(GameUtil.getFormatedDate(d));
//		    	}
//			}
//		}, new CronTrigger("0/1 * * * * * "));
//    	
//    	
//    
    }
}
