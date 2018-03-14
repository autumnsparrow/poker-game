/**
 * 
 */
package com.sky.game.texas.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author sparrow
 * 
 * 
 * 
 * 
 *
 */

@Service
public class GameTexasService {
	
	
	private int numberOfThreads;
	
	ExecutorService executorService;

	/**
	 * 
	 */
	public GameTexasService() {
		
		// TODO Auto-generated constructor stub
		executorService=Executors.newFixedThreadPool(numberOfThreads);
	}
	
	
	
	
	
	@Scheduled(fixedRate=500)
	public void schedule(){
		
	}

}
