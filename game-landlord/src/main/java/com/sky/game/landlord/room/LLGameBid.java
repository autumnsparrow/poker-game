/**
 * @sparrow
 * @11:27:22 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.game.GameDoOnTurnException;
import com.sky.game.context.game.IGameStage;
import com.sky.game.context.util.GameUtil;
import com.sky.game.protocol.commons.GL0000Beans.LG0002Response;

/**
 * procedure:
 * 1. determine who has right first call (random ,or one by one)
 * 2. move the holder to the first call.
 * 3. give the one who can call a gamelife. if time up, that one give up the chance of bid.
 * 
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class LLGameBid extends AbstractGameStage  {
	
	
	private static final Log logger=LogFactory.getLog(LLGameBid.class);
	
	
	
	public LLGameBid(LLGame game) {
		super(game);
	
	}

	LLDeckSeat maxBidSeat;
	int maxBidScore;
	int continuePass;
	boolean isBidRoundFinished;
	boolean noLandlord;
	int shouldEnd;
	
	private final static int DETERMIN_FACE_UP=0;
	private final static int MOVE_FIRST_CALL=1;
	public final static int BID_ROUND=2;


	public static final int CHECK_BID_ROUND_END = 3;
	
	private static final int RESET_SEATS=4;
	private static final int RESET_TO_PREV_STATE = 5;
	private static final int FIND_MAX_BID = 6;
	
	
	
	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameDoOnTurn#onTurn(java.lang.Object)
	 */
	public boolean onTurn(LLDeckSeat seat) throws GameDoOnTurnException {
		// TODO Auto-generated method stub
		boolean ret=true;
		switch (stage) {
		case DETERMIN_FACE_UP:{
			// don't matter the loop.
			if(seat.deck.conf.firstCallRandom){
				int faceUp=(int)(Math.random()*51);
				int location=faceUp%2;
				seat.deck.seats[location].actionState=LLDeckSeatActionTypes.FirstCall;
				game.append(seat,"firstcall");
				ret=false;
			}else{
				// find who is the first call
				if(seat.player!=null){
					if(seat.actionState.equals(LLDeckSeatActionTypes.FirstCall)){
						// now can say the next player is the first call.
						seats.moveNext().actionState=LLDeckSeatActionTypes.FirstCall;
						//seat.deck.sendBrokerMessage(seat, false);
						ret=false;
					}
				}
			}
			
			// next need to move the holder as the first call.
			
			
		}
			
			break;
		case MOVE_FIRST_CALL:
		{
			if(seat.player!=null){
				if(seat.actionState.equals(LLDeckSeatActionTypes.FirstCall)){
					seats.holderMoveTo(seat);
					seat.deck.sendBrokerMessage(seat.wrapActionState(), false);
					// current holder is the first one call.
					
					ret=false;
				}
			}
		}
			break;
		
		case BID_ROUND:
		{
			
			// only once time can call score
			// the call bid must on turn
			if(seat!=null&&seat.bidScore==-1){
				// let's bid.
				
				//seat.onBid();
				onBid(seat);
			
				
			}
			ret=false;
			
		}
			break;
			
		
		case CHECK_BID_ROUND_END:
		{
			//should the bid round continue
			// when stop the bid.
			// from the holder find point 3
			ret=true;
			
			// 
			if(seat.bidScore>maxBidScore){
				maxBidScore=seat.bidScore;
				game.score.bid=maxBidScore;
				
				//seats.holderMoveTo(seat);
				// record the bidscore
				maxBidSeat=seat;//seats.currentHolder();
				
			}
			
			
			// should stop.
			// when a player call 3.
			
			
			if(seat.bidScore==0){
				continuePass++;
			}else{
				continuePass=0;
			}
			
			if(seat.bidScore>-1){
				shouldEnd++;
			}
			
			
			// three player all pass 
			// jump to the state shuffles.
			
			
		}
			break;
		case RESET_TO_PREV_STATE:
		{
			if(seat!=null){
				seat.actionState=LLDeckSeatActionTypes.Undefined;
				ret=true;
			}
			
			
		}
			break;
			
		case RESET_SEATS:
		{
			if(seat!=null){
				//seat.reset();
				seat.bidScore=-1;
				seat.actionState=LLDeckSeatActionTypes.Undefined;
				ret=true;
			}
		}
			break;
		

		default:
			break;
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.AbstractGameStage#begin()
	 */
	@Override
	public void begin() {
		// TODO Auto-generated method stub
		// determine who should first call.
		
		reset();
		// 
		// the seat no take any action yet.
		setStage(RESET_SEATS, false);
		// find who first call.
		setStage(DETERMIN_FACE_UP, false);
		// move the holder to the first call.
		setStage(MOVE_FIRST_CALL,false);
		
		
	//	GameUtil.gameLife("bidground//:"+game.id, 3000L, this, "setStage", Integer.valueOf(BID_ROUND),Boolean.valueOf(true));
		// call bid.
		// each player got time gamelife to determine call bid.
		// when the time up, default as pass will 
		setStage(BID_ROUND,true);
		
		//game.setNextState();

	}
	
	
	public static IGameStage obtain(LLGame game){
		return new LLGameBid(game);
	}
	
	
	private void reset(){
		maxBidScore=0;
		shouldEnd=0;
		isBidRoundFinished=false;
		noLandlord=false;
		continuePass=0;
		maxBidSeat=null;
		
		
	}
	
	private void check(){
		reset();
		setStage(LLGameBid.CHECK_BID_ROUND_END, false);
		// first check if some player call 3.
		isBidRoundFinished=(maxBidScore==3);
		// first check if all player called.
		// no 3 score caller,then check the shouled end
		if(!isBidRoundFinished)
			isBidRoundFinished=(shouldEnd==3)?true:false;
		noLandlord=(continuePass==3);
		
	}
	
	/**
	 * 
	 * check should end
	 * 
	 */
	public void shouldEnd(){
		check();
		
		// have landlord
		if(noLandlord){
			setStage(LLGameBid.RESET_SEATS, false);
			if(game.state.eq(LLGameStates.Bid))
				game.setState(LLGameStates.Born);
		}else{
			// call bid finished.
			if(isBidRoundFinished){
				//
				//seats.holderMoveTo(maxBidSeat);
				maxBidSeat.positionState=LLDeckSeatPositionTypes.Landlord;
				//
				// to next state.
				if(game.state.eq(LLGameStates.Bid))
					game.setNextState();
			}else{
				// continue bid.
				setStage(LLGameBid.BID_ROUND, true);
			}
		}
		
	}
	
	
	
	
	

	
	private long getLives(boolean isRobot){
		return isRobot?game.deck.conf.robotBidWaitingTime:game.deck.conf.bidWaitingTime;
	}
	
	
	public synchronized void onBid(final LLDeckSeat seat) {
		seat.actionState = LLDeckSeatActionTypes.Turn;
		
		game.append(seat,"onBid");
		long lives = getLives(seat.player!=null&&seat.player.enableRobot);
		GameUtil.gameLife(game.getId()+"/bid", lives, this, "onBidTimeout", seat).setGameSession(game.gameSession);
	}
	
	
	
	
	
	public void bid(LLDeckSeat seat,int bid){
		// clear the game life
		GameUtil.clearGameLife(game.getId()+"/bid");
		game.append( seat,"bid:"+bid);
				
		int score = bid;//bid.getBid();
		// TODO:make sure the score is high than maxBidScore
	
		// TODO: user can't call lower score.
		if (score <= maxBidScore) {
			score=0;
		}

		// user bid ,clear the game life.
		seat.setBidScore(score);
		// send the bid message.
		bidMessage(seat);
	}
	
	
	private void onBidTimeout(LLDeckSeat seat) {
		int score=0;
		game.append(seat,"onBidTimeout");
		if (seat.player!=null&&seat.player.enableRobot) {
			if(maxBidScore<3){
				score= maxBidScore+1;
			}else{
				score=1;
			}
			//score=0;
		} else {

			// pass
			score= 0;
		}
		
		seat.setBidScore(score);
		
		bidMessage(seat);
		// should continue the bid if not finished.

	}

	private void bidMessage(final LLDeckSeat seat) {
		check();
		// bid score must larger than the previous
		LG0002Response obj = LLU.o(LG0002Response.class);
	
		obj.setBid(seat.bidScore);
		obj.setPosition(seat.location);
		obj.setId(seat.deck.id);
		
			
		seat.deck.sendBrokerMessage(obj, false);
		
		//seat.deck.sendBrokerMessage(obj, false);
			
		shouldEnd();

	}

	@Override
	public String toString() {
		return "LLGameBid [maxBidSeat=" + maxBidSeat + ", maxBidScore="
				+ maxBidScore + ", continuePass=" + continuePass
				+ ", isBidRoundFinished=" + isBidRoundFinished
				+ ", noLandlord=" + noLandlord + ", shouldEnd=" + shouldEnd
				+ "]";
	}
	
	
	

}
