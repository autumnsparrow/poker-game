/**
 * @sparrow
 * @1:27:46 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.game.IGameStage;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.achievement.UserGameEndType;
import com.sky.game.landlord.achievement.UserState;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.service.domain.GameDataCategoryTypes;
import com.sky.game.service.domain.GameDataStaticsTypes;

/**
 * @author sparrow
 *
 */
public class LLGameEnd implements IGameStage {
	private static final Log logger=LogFactory.getLog(LLGameEnd.class);
	
	LLGame game;

	

	public LLGameEnd(LLGame game) {
		super();
		this.game = game;
	}

	
	
	/**
	 * 
	 * @see GameEvent#DECK_GAME_END
	 * 
	 * 
	 */
	
	/* (non-Javadoc)
	 * 
	 * 
	 * @see com.sky.game.context.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		// checking should clear the deck ,or a new game should continue in the deck.
//		if(game.deck.shouldEndGames()){
////			//game.destory();
////			// game deck clear
//			game.deck.reset(true);
////			
//		}else{
////			// continue play the game on the deck.
//			game.deck.reset(false);
//			game.deck.createGame();
//		}
		// increate the player exp.
		//addExp();
		//game.deck.reset(false);
		// tournament game end message
		// task message
		broadcastGameEndTaskMessage();
		// do nothing.
		// leave the seats.
		//		game.deck.reset();
		gameDataStatics();
		
		game.deck.state=LLDeckStates.DeckGameEnd;
		GameEventHandler.handler.broadcast(GameEvent.obtain(GameEvent.GAME_END, game.deck));
		
		
	
		game.close();
		//logger.info("\n"+game.buffer.toString()+"\n");
		
	//	game.deck.room.onGameEnd(game.deck);
		
		
	
		
		logger.info("GameEnd reset the deck.");
		
		

	}
	
	
	 void broadcastGameEndTaskMessage(){
		UserGameEndType gameEndType=LLU.o(UserGameEndType.class);
		List<UserState> states=GameUtil.getList();
		boolean farmerNoPlayer=false;
		for(LLDeckSeat p:game.deck.seats){
			if(p!=null&&p.player!=null&&!p.player.isRobot){
				if(!p.isLandlord()&&p.player.winner&&p.player.pokeCube.remains()==17){
					farmerNoPlayer=true;
					break;
				}
			}
		}
		
		for(LLDeckSeat p:game.deck.seats){
			if(p.player!=null&&!p.player.isRobot){
				
				
				if(p.player.winner){
					p.player.win++;
				}else{
					p.player.lose++;
				}
				
				// 
				if(p.player.winner){
					p.player.alwaysWin=true;
				}else{
					p.player.alwaysWin=false;
				}
				
				UserState e=UserState.obtain(p.id, p.isLandlord(), p.player.winner,p.player.pokeCube);
				e.setLastHand(p.pokerCube);
				e.setMulti(game.score.factor());
				e.setDuration(game.duration);
				e.setWinWithoutPattern(false);
				e.setSpring(game.score.noDealPlayed);
				e.setRoomId(game.deck.room.id.intValue());
				e.setChannelId(p.player.user.getChannelId());
				if(farmerNoPlayer){
					if(!p.isLandlord()&&p.player.winner&&p.player.pokeCube.remains()==0){
						e.setWinWithoutPattern(farmerNoPlayer);
					}
				}
				
				states.add(e);
			}
				
		}
		gameEndType.setUserStates(states);
		
		GameEventHandler.handler.broadcast(GameEvent.obtain(GameEvent.DECK_GAME_END, gameEndType));
	}
	
	private void gameDataStatics(){
		if(game.deck!=null&&game.deck.seats!=null){
			// check the achievement that player always win and sort as first.
			for(LLDeckSeat s:game.deck.seats){
				LLGamePlayer p=s.player;
				if(p!=null&&!p.isRobot){
					GameDataStaticsTypes t=p.winner?GameDataStaticsTypes.RoundWinTimes:GameDataStaticsTypes.RoundLoseTimes;
					if(game.deck.room instanceof LLETRoom){
						LLETRoom etRoom=LLU.asObject(game.deck.room);
						int tt=etRoom.config.getTournamentType();
						GameDataCategoryTypes category=GameDataCategoryTypes.getType(tt);
						
						GSPP.updateGameDataStatics(p.id,category, t);
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameStage#setStage(int, boolean)
	 */
	public void setStage(int stage, boolean resume) {
		// TODO Auto-generated method stub

	}
	
	public static IGameStage obtain(LLGame game){
		return new LLGameEnd(game);
	}

}
