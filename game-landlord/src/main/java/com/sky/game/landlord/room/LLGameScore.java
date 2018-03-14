/**
 * @sparrow
 * @1:06:11 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 * 
 * 
 * 
 * 
 */
package com.sky.game.landlord.room;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.game.GameDoOnTurnException;
import com.sky.game.context.game.IGameStage;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.service.domain.PropertyTypes;

/**
 * @author sparrow
 *
 */
public class LLGameScore extends AbstractGameStage {
	private static final Log logger=LogFactory.getLog(LLGameScore.class);
	private static final int CACULATE_SCORES = 1;
	private static final int CHECK_WINNER = 0;
	private static final int CHANGE_SCORES = 2;
	private static final int CHANGE_SPRING = 3;
	private static final int CALCULATE_SPRING=4;

	public static IGameStage obtain(LLGame game){
		
		return new LLGameScore(game);
	}
	
	
	
	boolean isWinnerLL;
	boolean isPull;
	// the landlord can really get score.
	int llScore;
	// the farm should win score
	int fScore;
	
	
	LLDeckSeat landlord;
	
	LLDeckSeat farmers[];
	int locationOfFarmer;
	
	int landlordAddition;
	
	int farmerNoplayed;

	/**
	 * @param game
	 */
	public LLGameScore(LLGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameDoOnTurn#onTurn(java.lang.Object)
	 */
	public boolean onTurn(LLDeckSeat seat) throws GameDoOnTurnException {
		// TODO Auto-generated method stub
		
		boolean ret=true;
		switch (stage) {
		case CHECK_WINNER:
		{
			if(seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)||
					seat.positionState.equals(LLDeckSeatPositionTypes.Pull)){
				
				isPull=seat.positionState.equals(LLDeckSeatPositionTypes.Pull);
				
				
			}
			
			if(seat.player!=null&&seat.player.getPokeCube()!=null&&seat.player.getPokeCube().isEmpty()){
				
				if(seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)||
						seat.positionState.equals(LLDeckSeatPositionTypes.Pull)){
					isWinnerLL=true;
				}else if(seat.positionState.equals(LLDeckSeatPositionTypes.Farmer)||
						seat.positionState.equals(LLDeckSeatPositionTypes.Kicker)){
					isWinnerLL=false;
				}
				ret=false;
			}
		}
			break;
		case CACULATE_SCORES:{
			
				// just calculate the farmers score.
				if(seat.positionState.equals(LLDeckSeatPositionTypes.Farmer)||
						seat.positionState.equals(LLDeckSeatPositionTypes.Kicker)){
					// the loser.
					boolean isKicker=seat.positionState.equals(LLDeckSeatPositionTypes.Kicker);
					int addition=0;
					
					addition=isKicker?(addition+1):addition;
					if(isKicker)
						addition=isPull?(addition+1):addition;
						
					
						
					// calculate the player should lose 
					seat.scoreBroad.oldScore=seat.player.getChips();
					seat.scoreBroad.score=game.score.calculateWithoutKickerOrPull(addition)*game.deck.conf.baseChips;
					seat.scoreBroad.addition=addition;
					// the winner should win
					fScore=fScore+seat.scoreBroad.score;
					
					
					if(isWinnerLL){
						boolean isScoreEnough=seat.player.getChips()>seat.scoreBroad.score;
						seat.scoreBroad.score=isScoreEnough?seat.scoreBroad.score:seat.player.getChips();
						// should lose
						llScore=llScore+seat.scoreBroad.score;
					}
					
					//landlordAddition=landlordAddition+seat.scoreBroad.addition;
					
						
					logger.info(" farmer["+locationOfFarmer+ "] - "+seat.player.getId()+seat.scoreBroad.toString());
					farmers[locationOfFarmer]=seat;
					locationOfFarmer++;
					//seat.scoreBroad.score=-1*seat.scoreBroad.score*game.deck.conf.baseChips;
					// can collected score.
					
					
					
				}
				
			
		}
			
			break;
			
		case CHANGE_SPRING:
		{
			if(isWinnerLL){
				if(seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)||
						seat.positionState.equals(LLDeckSeatPositionTypes.Pull)){
					//int chips=seat.player.getChips()+collectedScore;
					
					// the landlord win,but the farmers don't play any cards.
					// that's a spring.
					if(farmerNoplayed==2){
						game.score.noDealPlayed=true;
					}
					
					
					ret=false;
				}
				
				
				
				
			}else{
				
				if(seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)||
						seat.positionState.equals(LLDeckSeatPositionTypes.Pull)){
					// when the landlord lose
						
						// one farmer don't play,the landlord only one hand ,one farmer wins,
						// that a spring.
						//
						if(farmerNoplayed==1){
							int lastHand=seat.pokerCubes.size()>0?seat.pokerCubes.get(0).remains():0;
							
							if(seat.player.pokeCube.remains()+lastHand==20){
								game.score.noDealPlayed=true;
							}
						}
				
					
						ret=false;
					}
					
					
					
				
				
			}
		}
			break;
			
		case CALCULATE_SPRING:
		{
			if(seat.positionState.equals(LLDeckSeatPositionTypes.Farmer)||
					seat.positionState.equals(LLDeckSeatPositionTypes.Kicker)){
				// the loser.
				
					
				if(seat.player.pokeCube.remains()==17){
					farmerNoplayed=farmerNoplayed+1;
				}
				
			}
		
			
		}
		
		break;
			
		case CHANGE_SCORES:
		{
			// change the scores.
			// land lord win
			if(isWinnerLL){
				if(seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)||
						seat.positionState.equals(LLDeckSeatPositionTypes.Pull)){
					//int chips=seat.player.getChips()+collectedScore;
					
					
					
					seat.scoreBroad.oldScore=seat.player.getChips();
					seat.scoreBroad.score=llScore;// win the farmers.
					seat.scoreBroad.currentScore=seat.scoreBroad.oldScore+seat.scoreBroad.score;
					//seat.scoreBroad.addition=landlordAddition;
					
					
					seat.player.setChips(seat.scoreBroad.currentScore);
					
					for(int i=0;i<2;i++){
						if(farmers[i]==null)
							continue;
						farmers[i].scoreBroad.currentScore=farmers[i].scoreBroad.oldScore-farmers[i].scoreBroad.score;
						farmers[i].player.setChips(farmers[i].scoreBroad.currentScore);
						farmers[i].player.winner=false;
					}
					
					landlord=seat;
					landlord.player.winner=true;
					ret=false;
				}
				
				
				
				
			}else{
				
				if(seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)||
						seat.positionState.equals(LLDeckSeatPositionTypes.Pull)){
					// when the landlord lose
						
						// one farmer don't play,the landlord only one hand ,one farmer wins,
						// that a spring.
						//
						
				
						seat.scoreBroad.oldScore=seat.player.getChips();
						//seat.scoreBroad.addition=landlordAddition;
						boolean isScoreEnough=seat.scoreBroad.oldScore>fScore;
						seat.scoreBroad.score=(isScoreEnough?fScore:seat.scoreBroad.oldScore);
						seat.scoreBroad.currentScore=seat.scoreBroad.oldScore-seat.scoreBroad.score;
						
						if(!isScoreEnough){
							// we should split the 
							long scores=(farmers[0].scoreBroad.score+farmers[1].scoreBroad.score);
							if(scores==0){
								scores=1;
							}
							float radio=(farmers[0].scoreBroad.score*1.0f/scores*1.0f);
							int farmer1Score=(int) (seat.scoreBroad.score*radio);
							int farmer2Score=seat.scoreBroad.score-farmer1Score;
							farmers[0].scoreBroad.score=farmer1Score;
							farmers[1].scoreBroad.score=farmer2Score;
						}	
						
						for(int i=0;i<2;i++){
							farmers[i].scoreBroad.currentScore=farmers[i].scoreBroad.oldScore+farmers[i].scoreBroad.score;
							farmers[i].player.setChips(farmers[i].scoreBroad.currentScore);
							farmers[i].player.winner=true;
						}
						seat.player.setChips(seat.scoreBroad.currentScore);
						
						landlord=seat;
						landlord.player.winner=false;
						ret=false;
					}
					
					
					
				
				
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
		
			reset();
			
			setStage(CHECK_WINNER, false);
			setStage(CALCULATE_SPRING,false);
			setStage(CHANGE_SPRING, false);
			
			setStage(CACULATE_SCORES, false);
			setStage(CHANGE_SCORES, false);
			
			// need to post the score status.
			// sorted the players.
			game.deck.teamSorted.sorted();
			LLGamePlayer[] players=game.deck.players.values().toArray(new LLGamePlayer[]{});
			players=LLTeam.sortGamePlayer(players, false, false);
			for (int i = 0; i < players.length; i++) {
				try {
				LLGamePlayer player = players[i];
				
				
				// add exp
				int exp=0;
				if(player.winner)
					exp=game.deck.conf.winExp;
				else
					exp=game.deck.conf.loseExp;
				game.append("["+String.format("%010d", player.id)+"]  addexp:"+exp);
				player.addExp(exp);
				
					
					if(player.winner&&!player.isRobot){
						GSPP.addExp(player.getId(), game.deck.room.getId(),exp);
						GSPP.updateUserProperty(player.getId(), PropertyTypes.MaxGot.id.intValue(), game.score.factor());
						//GSPP.reward(player.user, game.deck.room.id, game.id, player.rank, Proper, amount)
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			game.deck.sendBrokerMessage(game.wrapFinalScore(), false);
			
			game.append(GameContextGlobals.getJsonConvertor().format(game.wrapFinalScore()));
			
			if(game.state.eq(LLGameStates.Score))
				game.setNextState();
		

	}
	
	
	void reset(){
		
		isWinnerLL=false;
		landlord=null;
		farmers=new LLDeckSeat[2];
		locationOfFarmer=0;
		
		
	}
	
	/**
	 * 
	 * when the score ,the client need to show the current score status.
	 * 
	 * Winner :
	 * 	player   oldScore  + score  = currentScore
	 *  player   oldScore  - score  = currentScore.
	 *  player   oldScore  - score  = currentScore.
	 * 
	 * 
	 */

}
