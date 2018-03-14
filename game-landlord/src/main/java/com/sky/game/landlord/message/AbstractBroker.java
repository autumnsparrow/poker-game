/**
 * 
 */
package com.sky.game.landlord.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.room.LLGamePlayer;

/**
 * @author sparrow
 *
 */
public abstract class AbstractBroker implements IBrokerObserver, IBrokerUri ,IBrokerMessage{
	private static final Log logger=LogFactory.getLog(AbstractBroker.class);
	/**
	 * 
	 */
	public AbstractBroker() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.IUri#getUri()
	 */
	public abstract String getUri() ;
	
	

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.IJoinOrLeave#join(com.sky.game.landlord.player.GamePlayer)
	 */
	public void observe(LLGamePlayer player) {
		// TODO Auto-generated method stub
		logger.debug(GameUtil.formatId(player.getId(),player)+" - observer("+getUri()+")");
		//logger.info("player["+player.getId()+"] -  observe "+getUri());
		MessageBroker.boker.subscrible(getUri(), player);

	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.IJoinOrLeave#leave(com.sky.game.landlord.player.GamePlayer)
	 */
	public void unObserve(LLGamePlayer player) {
		// TODO Auto-generated method stub
		logger.debug(GameUtil.formatId(player.getId(),player)+" - unobserve("+getUri()+")");
		//logger.info("player["+player.getId()+"] -  unobserve "+getUri());
		MessageBroker.boker.unSubscrible(getUri(), player);
	}

	public void sendBrokerMessage(Object obj, boolean request) {
		// TODO Auto-generated method stub
		MessageBroker.boker.sendBroadcastMessage(getUri(), obj, request);
		
	}

}
