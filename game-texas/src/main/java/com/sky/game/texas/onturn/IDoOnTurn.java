/**
 * 
 * @Date:Nov 21, 2014 - 10:51:51 AM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.onturn;

import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.game.GameTexasGame;
import com.sky.game.texas.domain.table.GameTexasSeat;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * @author sparrow
 *
 */
public interface IDoOnTurn {
	
	public GameTexasGame getGame();
	public CircleOrderArray<GameTexasSeat> getSeats();
	public boolean onTurn(GameTexasSeat seat)
			throws GameTexasException;

}
