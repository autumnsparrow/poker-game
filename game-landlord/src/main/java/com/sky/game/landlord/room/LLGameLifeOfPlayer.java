/**
 * @sparrow
 * @8:43:53 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.context.game.AbstractGameLife;

/**
 * @author sparrow
 *
 */
public class LLGameLifeOfPlayer  extends AbstractGameLife{
	
	LLRoom room;
	LLGamePlayer player;
	

	public LLGameLifeOfPlayer(LLRoom room, LLGamePlayer player,long life) {
		super(life);
		this.room = room;
		this.player = player;
		
		
		// attach the game life to player
		this.player.setGameLifeOfPlayer(this);
		
		init();
	}


	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.AbstractGameLife#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.room.getUri()+"/player/"+player.getId().longValue();
	}


	@Override
	public void timeout() {
		// TODO Auto-generated method stub
		super.timeout();
		room.unObserve(player);
		room.setState(LLRoomStates.UserLeave);
	}

	
	
}
