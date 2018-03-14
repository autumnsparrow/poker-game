/**
 * 
 * @Date: Nov 4, 2014
 * @Author: sparrow
 * @Project :game-texas 
 *
 */
package com.sky.game.texas;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sky.game.context.configuration.GameContextConfiguration;
import com.sky.game.context.configuration.GameContxtConfigurationLoader;
import com.sky.game.texas.area.GameTexasArea;
import com.sky.game.texas.domain.game.GameTexasGame;
import com.sky.game.texas.domain.player.GameTexasPlayer;
import com.sky.game.texas.domain.table.GameTexasTable;

/**
 * 
 * 
 * GameTexas GlobalContext
 * 
 * 1. ExecutorService
 * 
 * @author sparrow
 *
 */
public class GameTexasGlobalContext {

	/**
	 * 
	 */
	public GameTexasGlobalContext() {
		// TODO Auto-generated constructor stub
	}
	
	//private static ExecutorService executorService=null;
	private static GameTexasConfiguration configuration=null;
	
	public static GameTexasConfiguration getConfiguration(){
		return configuration;
	}
	
	
	/**
	 * Game Base structure.
	 * 
	 */
	private static GameTexasArea  gameTexasArea;
	
	public static GameTexasArea getGameTexasArea(){
		return gameTexasArea;
	}
	
	private static ConcurrentHashMap<Long, GameTexasTable> PLAYER_TABLE_MAP=new ConcurrentHashMap<Long, GameTexasTable>();

	public static final String THREAD_POOL_GAME_TEXAS_CONTEXT="GameTexasGlobalContext";
	
	/**
	 * 
	 * @param url
	 */
	public static void init(InputStream url){
		configuration=GameContxtConfigurationLoader.loadConfiguration(url, GameTexasConfiguration.class);
		executorService=Executors.newFixedThreadPool(configuration.getMaxExecutorServiceThread());
		
		gameTexasArea=new GameTexasArea();
		gameTexasArea=new GameTexasArea();
		gameTexasArea=new GameTexasArea();
		
		// register the 
	
	}
	
//	public static EventHandler<StateChangedEvent> getGameEventHander(){
//		return GAME_EVENT_HANDLER;
//	}
	
	
	public static void mapPlayerAndTable(long userId,GameTexasTable table){
		PLAYER_TABLE_MAP.put(Long.valueOf(userId), table);
	}
	
	
	public static void umapPlayerAndTable(long userId){
		PLAYER_TABLE_MAP.remove(Long.valueOf(userId));
	}
	
	public static GameTexasTable getTable(long userId){
		return PLAYER_TABLE_MAP.get(Long.valueOf(userId));
	}
	
	public static GameTexasPlayer getPlayer(long userId){
		GameTexasTable table=getTable(userId);
		return table==null?null:table.getPlayer(userId);
	}
	public static GameTexasGame getTexasGame(long userId){
		GameTexasTable table=getTable(userId);
		return table==null?null:table.getGameTexasGame();
	}
	
	
	private static ExecutorService executorService=null;
	
	public static ExecutorService getExecutor(){
		return executorService;
	}
	
	/**
	 * 
	 * 
	 * @param task
	 */
	public static void postTask(Runnable  task){
		
		executorService.execute(task);
	}
	
	
	
	
	
	
	
	

}
