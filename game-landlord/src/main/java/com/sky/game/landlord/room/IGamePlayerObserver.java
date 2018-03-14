/**
 * @sparrow
 * @Jan 30, 2015   @11:28:58 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

/**
 * @author sparrow
 *
 */
public interface IGamePlayerObserver {
	
	public void enter(LLGamePlayer player);
	public void leave(LLGamePlayer player);
	public LLGameObjectMap<LLGamePlayer> getGameObjectMap();
	public IGamePlayerObserver getGamePlayerObserver();

}
