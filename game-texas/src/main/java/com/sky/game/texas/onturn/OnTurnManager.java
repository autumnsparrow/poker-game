/**
 * 
 * @Date:Nov 21, 2014 - 10:50:28 AM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.onturn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.table.GameTexasSeat;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * @author sparrow
 *
 */
public class OnTurnManager {
	
	private static final Log logger=LogFactory.getLog(OnTurnManager.class);
	private static final OnTurnManager ON_TURN_MANAGER=new OnTurnManager();
	
	public static OnTurnManager obtain(){
		return ON_TURN_MANAGER;
	}

	/**
	 * 
	 */
	public OnTurnManager() {
		// TODO Auto-generated constructor stub
	}
	
	public  void turn(IDoOnTurn doOnTurn,boolean resume){
		if(resume)
			doOnTurnResume(doOnTurn);
		else
			doOnTurn(doOnTurn);
	}
	
	/**
	 * begin on turn 
	 * 
	 * 
	 * @param doOnTurn
	 */
	public synchronized void doOnTurn(IDoOnTurn doOnTurn){
		CircleOrderArray<GameTexasSeat> seats = doOnTurn.getSeats();
		// GameTexasPlayer player=null;
		if(seats==null)
			return;
		GameTexasSeat seat=seats.currentHolder();
		logger.debug("doOnTurn:"+seats.toString());
		do {
			// player=seat.getPlayer();
			try {
				if (!doOnTurn.onTurn(seat))
					break;
			} catch (GameTexasException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seat=seats.moveNext();
		} while (!seats.isMoveAroud());
	}
	
	/**
	 * continue on turn.
	 * 
	 * @param doOnTurn
	 */
	public  synchronized void doOnTurnResume(IDoOnTurn doOnTurn){
		
		CircleOrderArray<GameTexasSeat> seats = doOnTurn.getSeats();
		if(seats==null)
			return;
		// GameTexasPlayer player=null;
		GameTexasSeat seat=seats.currentMover();
		logger.debug("doOnTurnResume:"+seats.toString());
		do {
			// player=seat.getPlayer();
			try {
				if (!doOnTurn.onTurn(seat)){
					// the next time should be next elements.
					seat=seats.moveNext();
					break;
				}
			} catch (GameTexasException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seat=seats.moveNext();
		} while (!seats.isMoveAroud());
		
	}

}
