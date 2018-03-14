/**
 * 
 * @Date:Nov 4, 2014 - 8:57:21 AM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas;

import java.io.InputStream;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.configuration.GameContxtConfigurationLoader;

/**
 * Configuration of the GameTexas.
 * 
 * @author sparrow
 *
 */
public class GameTexasConfiguration {
	
	
	private int maxExecutorServiceThread;

	/**
	 * 
	 */
	public GameTexasConfiguration() {
		// TODO Auto-generated constructor stub
	}

	public int getMaxExecutorServiceThread() {
		return maxExecutorServiceThread;
	}

	public void setMaxExecutorServiceThread(int maxExecutorServiceThread) {
		this.maxExecutorServiceThread = maxExecutorServiceThread;
	}

	
	public static void main(String args[]){
		
		GameTexasConfiguration configuration=new GameTexasConfiguration();
		configuration.maxExecutorServiceThread=5;
		String conf=GameContextGlobals.getJsonConvertor().format(configuration);
		System.out.println(conf);
		InputStream url=GameTexasConfiguration.class.getResourceAsStream("game-texas.conf");
		GameContxtConfigurationLoader.loadConfiguration(url, GameTexasConfiguration.class);
		
	}
	
	
}
