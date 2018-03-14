/**
 * 
 */
package com.sky.game.context.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

/**
 * @author sparrow
 *
 */
public class CronUtil {

	/**
	 * 
	 */
	public CronUtil() {
		// TODO Auto-generated constructor stub
	}

	private static final String DATE_FORMT="yyyy-MM-dd HH:mm:ss";
	
	
	public static String getFormatedDateNow(){
		return getFormatedDate(new Date());
	}
	/**
	 * next schedule time format.
	 * @param date
	 * @return
	 */
	public static String getFormatedDate(Date date){
		SimpleDateFormat format=new SimpleDateFormat(DATE_FORMT);
		String d=format.format(date);
		return d;
	}
	
	/**
	 * scheduled time expired the current time.
	 * 
	 * 
	 * @param d
	 * @return
	 */
	public static boolean expired(Date d){
		Date c=new Date();
		
		//logger.info("current:"+c.getTime()+" d:"+d.getTime()+ " duration="+(c.getTime()-d.getTime()));
		return c.getTime()>=d.getTime();
	}
	
	/**
	 * get next scheduled time from the crontab expression.
	 * @param cronexp
	 * @return
	 */
	public static Date getScheduledTime(String cronexp){
		Trigger trigger=new CronTrigger(cronexp);
    	TriggerContext triggerContext=new SimpleTriggerContext();
    	return trigger.nextExecutionTime(triggerContext);
	}
	
	public static long timeLeft(String cronexp){
		Date d=getScheduledTime(cronexp);
		Date c=new Date();
		return c.getTime()-d.getTime();
	}
	
	
	public static boolean expired(String cronexp){
		Date d=getScheduledTime(cronexp);
		Date c=new Date();
		return c.getTime()>=d.getTime();
	}
}
