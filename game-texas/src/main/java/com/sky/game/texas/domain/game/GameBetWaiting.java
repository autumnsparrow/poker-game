/**
 * 
 * @Date:Nov 13, 2014 - 4:15:07 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import com.sky.game.texas.timer.GameLifeEnum;
import com.sky.game.texas.timer.GameWorldTimer;
import com.sky.game.texas.timer.IGameLife;

/**
 * @author sparrow
 *
 */
public class GameBetWaiting implements IGameLife {
	GameTexasGameBet gameTexasGameBet;
	
	String name;
	long bornTime;
	long lifeTime;
	GameLifeEnum stage;
	

	

	public GameBetWaiting(GameTexasGameBet gameTexasGameBet, long lifeTime) {
		super();
		this.gameTexasGameBet = gameTexasGameBet;
		this.lifeTime = lifeTime;
		this.name=String.valueOf(this.gameTexasGameBet.gameTexasGame.id)+"@"+String.valueOf(System.currentTimeMillis());
		this.bornTime=System.currentTimeMillis();
		this.stage=GameLifeEnum.Born;
		GameWorldTimer.getGameWorldTimer().addGameLifeObject(this);
	}

	/**
	 * 
	 */
	public GameBetWaiting() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.timer.IGameLife#getName()
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.timer.IGameLife#timeout()
	 */
	public void timeout() {
		this.stage=GameLifeEnum.Timeout;
		//GameWorldTimer.getGameWorldTimer().removeGameLifeObject(this);
		// TODO Auto-generated method stub
		this.gameTexasGameBet.betWaitingTimeout();

	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.timer.IGameLife#isTimeout()
	 */
	public boolean isTimeout() {
		// TODO Auto-generated method stub
		return System.currentTimeMillis()-this.bornTime>this.lifeTime;
	}
	
	

	/* (non-Javadoc)
	 * @see com.sky.game.texas.timer.IGameLife#destory()
	 */
	public void destory() {
		this.stage=GameLifeEnum.Destory;
		// TODO Auto-generated method stub
		GameWorldTimer.getGameWorldTimer().removeGameLifeObject(this);
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.timer.IGameLife#getStage()
	 */
	public GameLifeEnum getStage() {
		// TODO Auto-generated method stub
		return this.stage;
	}
	

}
