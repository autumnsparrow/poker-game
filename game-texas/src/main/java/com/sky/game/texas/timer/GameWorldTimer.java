/**
 * 
 * @Date:Nov 13, 2014 - 1:58:27 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.timer;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.sky.game.texas.GameTexasGlobalContext;

/**
 * @author sparrow
 *
 */

public class GameWorldTimer extends TimerTask {
	
	private static final GameWorldTimer timer=new GameWorldTimer();
	
	/**
	 * 
	 * @return
	 */
	public static GameWorldTimer getGameWorldTimer(){
		return timer;
	}
	
	
	
	ConcurrentHashMap<String, IGameLife> gameLives;

	/**
	 * 
	 */
	public GameWorldTimer() {
		// TODO Auto-generated constructor stub
		gameLives=new ConcurrentHashMap<String,IGameLife>();
		Timer t=new Timer();
		
		t.scheduleAtFixedRate(this, 1000L, 500L);
	}
	
	
	
	public void addGameLifeObject(IGameLife gameLifeObject){
		if(!gameLives.containsKey(gameLifeObject.getName())){
			gameLives.put(gameLifeObject.getName(), gameLifeObject);
		}
	}
	
	
	
public void removeGameLifeObject(IGameLife gameLife){
	if(gameLives.containsKey(gameLife.getName())){
			gameLives.remove(gameLife.getName());
	}
}

	


	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Iterator<IGameLife> it=gameLives.values().iterator();
		while(it.hasNext()){
			final IGameLife gameLife=it.next();
			if(gameLife.isTimeout()){
				removeGameLifeObject(gameLife);
				//gameLives.remove(gameLife.getName());
				// 
				GameTexasGlobalContext.postTask(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						gameLife.timeout();
						
					}
				});
			}
			
		}
		
	}
	
	public static boolean gameLifeStage(IGameLife obj,GameLifeEnum stage){
		
		return obj!=null?(obj.getStage().equals(stage)):false;
	}

}
