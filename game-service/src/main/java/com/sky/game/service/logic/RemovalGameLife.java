/**
 * 
 */
package com.sky.game.service.logic;

import com.sky.game.context.event.IGameSession;
import com.sky.game.context.game.GameLifeEnum;
import com.sky.game.context.game.IGameLife;

/**
 * @author Administrator
 *
 */
public class RemovalGameLife implements IGameLife {
	
	long id;
	
	public static RemovalGameLife obtain(long id){
		return new RemovalGameLife(id);
	}

	public RemovalGameLife(long id) {
		super();
		this.id = id;
	}

	/**
	 * 
	 */
	public RemovalGameLife() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameLife#getName()
	 */
	
	public String getName() {
		// TODO Auto-generated method stub
		return String.valueOf(id);
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameLife#timeout()
	 */
	
	public void timeout() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameLife#isTimeout()
	 */
	
	public boolean isTimeout() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameLife#destory()
	 */
	
	public void destory() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameLife#getStage()
	 */
	
	public GameLifeEnum getStage() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameLife#refresh()
	 */
	
	public void refresh() {
		// TODO Auto-generated method stub

	}

	public IGameLife start() {
		// TODO Auto-generated method stub
		return this;
	}

	public void setGameSession(IGameSession session) {
		// TODO Auto-generated method stub
		
	}

	public IGameSession getGameSession() {
		// TODO Auto-generated method stub
		return null;
	}

}
