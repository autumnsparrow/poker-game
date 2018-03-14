/**
 * sparrow
 * game-service 
 * Feb 11, 2015- 9:38:52 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.logic;

import org.springframework.stereotype.Service;

import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.service.domain.GameSystemMessage;

/**
 * @author sparrow
 *
 */
@Service
public class GameSystemMessageService {

	/**
	 * 
	 */
	public GameSystemMessageService() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void broadcastMessage(GameSystemMessage message){
		GameEventHandler.handler.broadcast(new GameEvent(GameEvent.GAME_SYSTEM_MESSAGE,message));
	}
	
	
	

}
