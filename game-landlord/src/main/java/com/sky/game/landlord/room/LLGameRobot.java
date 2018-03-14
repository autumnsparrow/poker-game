/**
 * @sparrow
 * @Dec 19, 2014   @10:02:06 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.game.GameDoOnTurnException;
import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.ai.impl.DefaultPokerCubeAnalyzer;
import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.poke.util.PokeHelper;

/**
 * @author sparrow
 *
 */
public class LLGameRobot extends AbstractGameStage {

	private static final int FOUND_WHO_ON_TURN = 0;
	private final Log logger=LogFactory.getLog(LLGameRobot.class);

	/**
	 * 
	 */
	public LLGameRobot() {
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @param game
	 */
	public LLGameRobot(LLGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	// LLDeckSeat currentSeat;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.context.game.IGameDoOnTurn#onTurn(java.lang.Object)
	 */
	public boolean onTurn(LLDeckSeat seat) throws GameDoOnTurnException {
		// TODO Auto-generated method stub
		boolean ret = false;
		switch (stage) {
		case FOUND_WHO_ON_TURN:
			if (seat.player.isRobot()
					&& seat.actionState.equals(LLDeckSeatActionTypes.Turn)) {
				seats.holderMoveTo(seat);
				ret = false;
			} else {
				ret = true;
			}

			break;

		default:
			break;
		}
		return ret;
	}
	
	PokerCube pc = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.room.AbstractGameStage#begin()
	 */
	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
		
	
	}
	
	
	public void handle(LLDeckSeat seat){
		pc=null;
		// first find who get the first to get rid of poker.
		//setStage(FOUND_WHO_ON_TURN, false);

		PokerCube owner=null;
		try {
			PokerCube[][] cubes = new PokerCube[4][];

			IPokerCubeAnalyzer pokerCubeAnalyzer = new DefaultPokerCubeAnalyzer();

			// current holder is the current seat.
			LLDeckSeat currentSeat = seat;
//	seats.holderMoveTo(seat);
			PokerCube lastHands = game.deck.lastHandPokerCube;
			owner = currentSeat.player.getPokeCube();
			
			// found who own the last hands?
			seats.holderMoveTo(seat);
			LLDeckSeat nextSeat = seats.moveNext();
			
			LLDeckSeat prevSeat = seats.moveNext();
			
			// nextSeat=seats.moveNext()

			cubes[0] = currentSeat.pokerCubes.toArray(new PokerCube[] {});
			cubes[1] = nextSeat.pokerCubes.toArray(new PokerCube[] {});
			cubes[2] = prevSeat.pokerCubes.toArray(new PokerCube[] {});
			cubes[3] = new PokerCube[] { owner, lastHands, game.deck.kittyCards };
			
			
			byte[] extra=null;
			extra=new byte[6];
			
			extra[0]=(byte)(owner.isBelongToLandloard()?1:0);
			extra[1]=(byte)owner.remains();
			extra[2]=nextSeat.player.pokeCube.isBelongToLandloard()?(byte)1:(byte)0;
			extra[3]=(byte)nextSeat.player.pokeCube.remains();
			extra[4]=prevSeat.player.pokeCube.isBelongToLandloard()?(byte)1:(byte)0;
			extra[5]=(byte)prevSeat.player.pokeCube.remains();
			
			

			int rightLeftCards = extra[3];
			int leftLeftCards = extra[5];
			boolean oldAiDealer = false;
			if (rightLeftCards <= 10|| leftLeftCards <= 10) {
				oldAiDealer = true;
			}
			oldAiDealer=true;
			

			
			try {
				if(!oldAiDealer){
					try {
						pokerCubeAnalyzer.analyze(cubes);
						if (currentSeat.showHandState.equals(LLDeckShowHandsTypes.Active)) {
							// active
							if(pokerCubeAnalyzer!=null)
								pc = pokerCubeAnalyzer.getBestActivePokerCube();

						} else {
							// passive
							if(pokerCubeAnalyzer!=null){
								pc = pokerCubeAnalyzer.getBestPassivePokerCube();
							}
							

						}
						
						// BUGFIXED: poker cube type  3with2pairs.
						PokerCubeType pt=pc.judgePokerCubeType();
						if(pt==null){
							if(!pc.isEmpty())
								throw new RuntimeException("pokertype - "+pc.cubeToHexString());
						}
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						logger.error("pokerCubeAnalyzer analyze failed:"+e.getMessage());
						pc=PokeHelper.oldGetOneSendCardBiggerButLeastPokerCube(owner, lastHands, extra, cubes[0] , cubes[1], cubes[2]);
					}

				}else{
					
					pc=PokeHelper.oldGetOneSendCardBiggerButLeastPokerCube(owner, lastHands, extra, cubes[0] , cubes[1], cubes[2]);
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				pc=null;
			}
			
			if(pc==null){
				pc=PokerCube.getPokerCubeByHex(PokerCube.ZERO);
			}
			
			
			
			pc.setDeckPoistion(owner.getDeckPosition());
			pc.setBelongToLandloard(owner.isBelongToLandloard());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	

}
