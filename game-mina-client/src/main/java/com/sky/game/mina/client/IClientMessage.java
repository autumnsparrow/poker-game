/**
 * 
 * @Date:Nov 19, 2014 - 5:57:40 PM
 * @Author: sparrow
 * @Project :game-mina-client 
 * 
 *
 */
package com.sky.game.mina.client;

import com.sky.game.context.message.MessageBean;
import com.sky.game.context.message.MessageException;

/**
 * @author sparrow
 *
 */
public interface IClientMessage {
	
	/**
	 * interface for the ice 
	 * 
	 * @param message
	 * @return
	 * @throws MessageException
	 */
	public MessageBean invoke(MessageBean message) throws MessageException;
	
	
	public void onRecieve(MessageBean message,Object extra) throws MessageException;

}
