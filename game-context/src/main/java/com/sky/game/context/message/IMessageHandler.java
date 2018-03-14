/**
 * 
 */
package com.sky.game.context.message;


/**
 * @author sparrow
 *
 */
public interface IMessageHandler {
	
	/**
	 * interface for the ice 
	 * 
	 * @param message
	 * @return
	 * @throws MessageException
	 */
	public MessageBean invoke(MessageBean message) throws MessageException;
	
	
	public void onRecieve(MessageBean message,String extra) throws MessageException;

}
