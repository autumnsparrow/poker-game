/**
 * @sparrow
 * @Jan 23, 2015   @1:08:55 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.achievement;

import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.IGameEventObserver;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.landlord.room.LLGameEnd;
import com.sky.game.landlord.room.LLGaming;
import com.sky.game.landlord.room.LLU;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.game.service.domain.UserAchievementStates;
import com.sky.game.service.domain.UserLandlordStates;

/**
 * <p>
 * task
 * @see LLGameEnd
 * @see GameEvent#DECK_GAME_END
 * <p>
 * achivements
 * @see LLGaming
 * @see GameEvent#POKER_TYPE_EVENT
 * 
 * <p>
 * winner 1 in elimination tournament.
 * @author sparrow
 *
 */
public class AchievementObserver implements IGameEventObserver {

	/**
	 * 
	 */
	public AchievementObserver() {
		// TODO Auto-generated constructor stub
		GameEventHandler.handler.registerObserver(GameEvent.POKER_TYPE_EVENT,
				this);
		GameEventHandler.handler.registerObserver(GameEvent.DECK_GAME_END,
				this);
		
		GameEventHandler.handler.registerObserver(GameEvent.WINNER_IN_ET, this);
		GameEventHandler.handler.registerObserver(GameEvent.HALF_FINAL_IN_ET, this);
		GameEventHandler.handler.registerObserver(GameEvent.WINNER_IN_BT, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.event.IGameEventObserver#getUri()
	 */
	public String getUri() {
		// TODO Auto-generated method stub
		return "AchievementObserver";
	}

	/**
	 * 
	 * 
	ZD(1,"炸弹"),
	TTS(2,"通天顺"),
	CT(3,"春天"),
	HJ(4,"火箭"),
	LD(5,"连对"),
	SDY(6,"三带一"),
	SDE(7,"三带二"),
	SDS(8,"四带三"),
	FJ(9,"飞机"),
	
	
	SYZDZDFZD(12,"使用炸弹炸对方炸弹"),
	
	
	DCSZYSDP(18,"打出十张以上的牌"),
	DC12C(25,"一手打出十二张牌"),
	
	
		SZZDMC(26,"手中有炸弹或火箭，但直到游戏结束没有出"),
	S(10,"胜"),

	KSJS15(11,"游戏开始到结束少于15秒"),
	DZD(13,"身为地主在没出牌时使用炸弹"),
	
	NWDHS(14,"身为农民在队友没出牌的情况下获胜"),
	NBHS(15,"身为农民在不出牌的情况下获胜"),
	//
	ZHC3(16,"最后出3胜"),
	HSZHSZD(17,"获胜最后一手是炸弹"),
	SCFBS(19,"比赛中出现四次翻倍胜"),
	
	
	
	
	
	
	
	
	BSQSDG(20,"比赛中全胜夺冠"),
	TTSDG(21,"淘汰赛中夺冠"),
	
	NUM1(22,"第一名"),
	NUM2(23,"第二名"),
	NUM3(24,"第三名"),
	
	
	
	LIANSU5(27,"连输5场"),
	LS3(28,"连赢3场"),
	LS6(29,"连赢6场"),
	LS10(30,"连赢10场"),	
	LS15(31,"连赢15场");	
	
	 * @param evt
	 */
	public void onPokerTypeEvent(GameEvent evt) {
		UserPokerType pokerCubeType = LLU.asObject(evt.obj);
		PokerCube pCube=pokerCubeType.pokerCube;
		UserLandlordStates state=pokerCubeType.landlord?UserLandlordStates.Landlord:UserLandlordStates.Farmer;
		if (pCube!= null) {
			PokerCubeType pCubeType=pCube.getPokerCubeType();
			if (pCube.isPokerCubeRocket()) {
				GSPP.updateGameAchievement(pokerCubeType.userId,
						UserAchievementStates.HJ, state);
			} else if (pCube.isPokerCubeBomb()) {
				GSPP.updateGameAchievement(pokerCubeType.userId,
						UserAchievementStates.ZD, state);
			}
			
			if(pCube.isPokerCubeBomb()||pCube.isPokerCubeRocket()){
				if(pokerCubeType.firstHand&&pokerCubeType.landlord){
					GSPP.updateGameAchievement(pokerCubeType.userId,
							UserAchievementStates.DZD, state);
				}
				if(pokerCubeType.previosBombOrRocket){
					GSPP.updateGameAchievement(pokerCubeType.userId,
							UserAchievementStates.SYZDZDFZD, state);
				}
			}
			
			
			if(pCubeType==null){
				pCube.judgePokerCubeType();
				pCubeType=pCube.getPokerCubeType();
			}
			
			if(pCubeType!=null){
				
			if(pCubeType.isPokerCubeType(PokerCubeType.TriplesWithPair)){
				GSPP.updateGameAchievement(pokerCubeType.userId,
						UserAchievementStates.SDE, state);
			}else if(pCubeType.isPokerCubeType(PokerCubeType.TriplesWithSingle)){
				GSPP.updateGameAchievement(pokerCubeType.userId,
						UserAchievementStates.SDY, state);
			}else if(pCubeType.isPokerCubeType(PokerCubeType.QuplexWithPair)){
				GSPP.updateGameAchievement(pokerCubeType.userId,
						UserAchievementStates.SDS, state);
			}else if(pCubeType.isPokerCubeType(PokerCubeType.ConsecutiveTriplesWithPairs)||
					pCubeType.isPokerCubeType(PokerCubeType.ConsecutiveTriplesWithSingles)){
				GSPP.updateGameAchievement(pokerCubeType.userId,
						UserAchievementStates.FJ, state);
			}else if(pCubeType.isPokerCubeType(PokerCubeType.ConsecutivePairs)){
				GSPP.updateGameAchievement(pokerCubeType.userId,
					UserAchievementStates.LD, state);
				
			}else if(pCubeType.isPokerCubeType(PokerCubeType.ConsecutiveSingle)){
				if(pCube.remains()==12)
					GSPP.updateGameAchievement(pokerCubeType.userId,
						UserAchievementStates.TTS, state);
				 
			}
			
			if(pCube.remains()>=10){
				GSPP.updateGameAchievement(pokerCubeType.userId,
						UserAchievementStates.DCSZYSDP, state);
			}
			if(pCube.remains()>=12){
				GSPP.updateGameAchievement(pokerCubeType.userId,
						UserAchievementStates.DC12C, state);
			}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.event.IGameEventObserver#observer(com.sky.game.
	 * landlord.event.GameEvent)
	 */
	public void observer(GameEvent evt) {
		// TODO Auto-generated method stub
		if (evt.isEvent(GameEvent.POKER_TYPE_EVENT)) {
			onPokerTypeEvent(evt);
		}else if(evt.isEvent(GameEvent.DECK_GAME_END)){
			onGameEnd(evt);
		}else if(evt.isEvent(GameEvent.WINNER_IN_ET)){
			onWinnerET(evt);
		}else if(evt.isEvent(GameEvent.HALF_FINAL_IN_ET)){
			onHalfFinal(evt);
		}else if(evt.isEvent(GameEvent.WINNER_IN_BT)){
			onWinnerBT(evt);
		}

	}
	
	
	
	private void onHalfFinal(GameEvent evt){
		UserWinnerType obj=LLU.asObject(evt.obj);
		//Integer.valueOf(obj.getRoomId());
		Integer[] taskIds=TaskIdAndRoomIdMapper.getGameHalfFinalByRoomId((int)obj.getRoomId());
		for(Integer t:taskIds){
			GSPP.updateTaskRate(obj.getUserId().longValue(), t.intValue(), 1);
		}
	}
	
	/**
	 * 
	 * 
	NUM1(22,"第一名"),
	NUM2(23,"第二名"),
	NUM3(24,"第三名"),
	 * @param evt
	 */
	private void onWinnerET(GameEvent evt) {
		// TODO Auto-generated method stub
		UserWinnerType obj=LLU.asObject(evt.obj);
		rankWinner(obj);
		
	}
	
	private void onWinnerBT(GameEvent evt){
		UserWinnerType obj=LLU.asObject(evt.obj);
		// task
		GSPP.updateTaskRate(obj.getUserId().longValue(), 30, 1);
	}
	
	private void rankWinner(UserWinnerType obj){
		if(obj.rank==1){
			if(obj.alwaysWin){
				GSPP.updateGameAchievement(obj.userId,
						UserAchievementStates.BSQSDG, UserLandlordStates.Unkown);
			}
			GSPP.updateGameAchievement(obj.userId,
					UserAchievementStates.NUM1, UserLandlordStates.Unkown);
			GSPP.updateGameAchievement(obj.userId,
					UserAchievementStates.TTSDG, UserLandlordStates.Unkown);
			/*GSPP.updateGameAchievement(obj.userId,
					UserAchievementStates.BSQSDG, UserLandlordStates.Unkown);*/
			
			//GameDataCategoryTypes category = GameDataCategoryTypes
			//		.getType(TournamentTypes.Tournament.category);
			
			
			// task
			//TotalTask5(1,"任意赛场获得一次冠军",0)
			// task
			GSPP.updateTaskRate(obj.userId.longValue(), 1, 1);
			
			//
		
			
		}else if(obj.rank==2){
			GSPP.updateGameAchievement(obj.userId,
					UserAchievementStates.NUM2, UserLandlordStates.Unkown);
		}else if(obj.rank==3){
			GSPP.updateGameAchievement(obj.userId,
					UserAchievementStates.NUM3, UserLandlordStates.Unkown);
		}
		
		int fen=UserExtraPropertyIdMap.getFen(obj.tournamentType, obj.rank);
		if(fen>0){
			GSPP.updateUserProperty(obj.userId.longValue(), 14, fen);
			
		}
	}

	private void onGameEnd(GameEvent evt){
		UserGameEndType obj=LLU.asObject(evt.obj);
		
		for(UserState state:obj.getUserStates()){
			try {
				if(state.winner){
					winner(state);
					taskGameEnd(state);
				}
				// check if the current pokercube contains bomb or rock
				pokerCubeContainsBombOrRocket(state);
				taskGameEndNoMaterWinner(state);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	
	private void taskGameEndNoMaterWinner(UserState state) {
		Integer[] taskIds = TaskIdAndRoomIdMapper.anyTournamentsGameEnd;
		Integer[] taskIds1 = TaskIdAndRoomIdMapper.anyTournamentsGameEnd1;
 		long channelId = state.getChannelId();
		if(channelId==2000){
			for (Integer id : taskIds1) {
				GSPP.updateTaskRate(state.getUserId(), id.intValue(), 1);
			}
		}else{
			for (Integer id : taskIds) {
				GSPP.updateTaskRate(state.getUserId(), id.intValue(), 1);
			}
		}
	}
	
	// task  
	private void taskGameEnd(UserState state){
		Integer[]  taskIds=TaskIdAndRoomIdMapper.getGameEndTaskIdByRoomId(state.roomId);
		if(taskIds!=null)
		for(Integer id:taskIds){

			GSPP.updateTaskRate(state.getUserId(), id.intValue(), 1);
		}
		
	}
	
	private void winner(UserState state){
		UserLandlordStates s=state.landlord?UserLandlordStates.Landlord:UserLandlordStates.Farmer;
		GSPP.updateGameAchievement(state.userId,
				UserAchievementStates.S, s);
		
	
		if(state.pokerCube.remains()==17&&!state.landlord){
			GSPP.updateGameAchievement(state.userId,
					UserAchievementStates.NBHS, s);
		}
		if(state.winWithoutPattern){
			GSPP.updateGameAchievement(state.userId,
					UserAchievementStates.NWDHS, s);
		}
		if(state.isSpring()){
			GSPP.updateGameAchievement(state.userId,
					UserAchievementStates.CT, s);
		}
		
		if(state.multi>=4){
			GSPP.updateGameAchievement(state.userId,
					UserAchievementStates.SCFBS, s);
		}
		if(state.lastHand!=null){
		if(state.lastHand.isPokerCubeBomb()||state.lastHand.isPokerCubeRocket()){
			GSPP.updateGameAchievement(state.userId,
					UserAchievementStates.HSZHSZD, s);
		}
		
		if(state.lastHand.isSmallestSingle()){
			GSPP.updateGameAchievement(state.userId,
					UserAchievementStates.ZHC3, s);
		}
	
		if(state.duration<=15000){
			GSPP.updateGameAchievement(state.userId,
					UserAchievementStates.KSJS15, s);
		}
		}
	}
	
	
	private void pokerCubeContainsBombOrRocket(UserState state){
		UserLandlordStates s=state.landlord?UserLandlordStates.Landlord:UserLandlordStates.Farmer;
		if(state.pokerCube.containsBombOrRocket()){
			GSPP.updateGameAchievement(state.userId,
				UserAchievementStates.SZZDMC, s);
		}
//		PokerCube bom=state.pokerCube.subPokerCubeByPokerCubeType(PokerCubeType.Bomb);
//		UserLandlordStates s=state.landlord?UserLandlordStates.Landlord:UserLandlordStates.Farmer;
//		if(bom!=null&&bom.isPokerCubeBomb()){
//			GSPP.updateGameAchievement(state.userId,
//					UserAchievementStates.SZZDMC, s);
//		}
//		PokerCube rocket=state.pokerCube.subPokerCubeByPokerCubeType(PokerCubeType.Rocket);
//		if(rocket!=null&&rocket.isPokerCubeBomb()){
//			GSPP.updateGameAchievement(state.userId,
//					UserAchievementStates.SZZDMC, s);
//		}
	}
	
	
	public static void observerHalfFinalEvent(int roomId,long userId,int rank) {
		//if (!player.isRobot()) {
			UserWinnerType obj = LLU.o(UserWinnerType.class);
			obj.setRank(rank);
			obj.setUserId(userId);
			obj.setRoomId(roomId);
			//obj.setLandlord(player);
			GameEventHandler.handler.broadcast(GameEvent.obtain(
					GameEvent.HALF_FINAL_IN_ET, obj));
		//}

	}
	
	
	public static void observerWinnerET(int roomId,long userId,int rank,boolean alwaysWin,int tournamentType) {
		//if (!player.isRobot()) {
			UserWinnerType obj = LLU.o(UserWinnerType.class);
			obj.setRank(rank);
			obj.setUserId(userId);
			obj.setRoomId(roomId);
			obj.setAlwaysWin(alwaysWin);
			obj.tournamentType=tournamentType;
			
			//obj.setLandlord(player);
			GameEventHandler.handler.broadcast(GameEvent.obtain(
					GameEvent.WINNER_IN_ET, obj));
		//}

	}
	
	public static void observerWinnerBT(int roomId,long userId,int rank) {
		//if (!player.isRobot()) {
			UserWinnerType obj = LLU.o(UserWinnerType.class);
			obj.setRank(rank);
			obj.setUserId(userId);
			obj.setRoomId(roomId);
			//obj.setLandlord(player);
			GameEventHandler.handler.broadcast(GameEvent.obtain(
					GameEvent.WINNER_IN_BT, obj));
		//}

	}
	
	public static void observerPokerType(long userId,PokerCube lastHand,boolean landlord,boolean firstHand,boolean previosBombOrRocket){
		UserPokerType obj= UserPokerType.obtain(userId, lastHand,landlord,firstHand);
		obj.setPreviosBombOrRocket(previosBombOrRocket);
		GameEventHandler.handler.broadcast(GameEvent.obtain(GameEvent.POKER_TYPE_EVENT,obj));
	}

}
