/**
 * @sparrow
 * @Feb 11, 2015   @2:58:57 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.jmx;

import javax.management.JMException;

import com.j256.simplejmx.client.JmxClient;

/**
 * @author sparrow
 *
 */
public class GameJmxObserverClient implements IJmx {

	/**
	 * 
	 */
	public GameJmxObserverClient() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.jmx.IJmx#checkRoom(long, long, boolean)
	 */
	public String checkRoom(long channelId, long roomId, boolean vip) {
		// TODO Auto-generated method stub
		String jmxString=null;
		try {
			JmxClient client=new JmxClient("127.0.0.1", 20000, "admin", "admin");
			Object obj=client.invokeOperation("com.sky.game.landlord.jmx", "gameJmxObserver", "checkRoom", new Object[]{Long.valueOf(channelId),Long.valueOf(roomId),Boolean.valueOf(vip)});
			if(obj!=null){
				jmxString=(String)obj;
			}
			//client.getOperationsInfo("com.sky.game.landlord.jmx", "gameJmxObserver");
		} catch (JMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jmxString;
	}

}
