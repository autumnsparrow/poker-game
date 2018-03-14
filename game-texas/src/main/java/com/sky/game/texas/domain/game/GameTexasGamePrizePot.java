/**
 * 
 * @Date:Nov 24, 2014 - 6:57:08 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.pack.GameCardRankEnum;
import com.sky.game.texas.domain.pack.GameTexasCardMatrix;
import com.sky.game.texas.domain.player.GameTexasPlayer;
import com.sky.game.texas.domain.player.GameTexasPlayerBetStatesEnum;
import com.sky.game.texas.domain.player.GameTexasPlayerGameStatesEnum;
import com.sky.game.texas.domain.table.GameTexasSeat;
import com.sky.game.texas.onturn.IDoOnTurn;
import com.sky.game.texas.onturn.OnTurnManager;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * @author sparrow
 * 
 */
public class GameTexasGamePrizePot implements IDoOnTurn, IGameStage {

	private static final int prize_pot = 1;
	private static final int find_all_in_players = 2;
	private static final int fill_all_prize_pot = 3;
	private static final int compare_the_hands_within_prize_pot = 4;
	private static final int collect_all_prizes = 5;
	GameTexasGame game;
	CircleOrderArray<GameTexasSeat> seats;
	int stage;

	// using for search allinChips.
	int prizePots;
	int allInChips[];
	// using for calculate the prize pot
	int prizePotPosition;
	int prizePot[];
	int prizePotBase[];
	
	// 
	GameCardRankEnum currentPrizePotCardRank;
	//int currentPrizePotCardSameRankPlayers;
	List<GameTexasPlayer> currentPrizePotPlayers;

	
	/**
	 * 
	 */
	public GameTexasGamePrizePot() {
		// TODO Auto-generated constructor stub
	}

	public GameTexasGamePrizePot(GameTexasGame game) {
		super();
		this.game = game;
		this.seats = this.game.getSeats().clone();
		allInChips = new int[game.gameTexasTable.MAX_SEATS];
		prizePot = new int[game.gameTexasTable.MAX_SEATS];
		currentPrizePotPlayers=new LinkedList<GameTexasPlayer>();
		currentPrizePotCardRank=GameCardRankEnum.Nothing;
	}

	/*
	 * 
	 * PrizePots:
	 * 
	 * Give 5  player.  
	 * 
	 * A       B         C        D       E        
	 * allin   inactive    allin     allin   allin   
	 * 800     1000      1200      1800     2000   
	 * 
	 * -------------------------------------------------
	 *                                     total : 7300
	 *                                     prizePots=5
	 *  allInChips
	 *  0        1        2          3       4                                           
	 * 1. find all the allin players from active player.
	 * 2. sort the allin chips from low to high.
	 * 3. calculate the prize pot.
	 * 
	 * prize pot 
	 * 1   800*5  A,B,C,D,E
	 * 2,  200*4  B,C,D,E
	 * 3,  200*3  C,D,E
	 * 4,  600*2  D,E
	 * 5,  200    E
	 * 
	 * ----------------------------------
	 *       4000+800+600+1200+200 = 6800.
	 *       prizePot
	 *       [0] = 4000
	 *       [1] = 800
	 *       [2] = 600
	 *       [3] = 1200
	 *       [4] = 200
	 *       
	 * 
	 * to calculate the prize pot ,need get the level of each pot.
	 * 
	 * prize pot
	 * 0   base =800
	 * 1   base =200
	 * 2   base =200
	 * 3   base =600
	 * 4   base =200 
	 * 
	 * prizePotBase []
	 * 
	 * 
	 * prizePotPosition : check the prizePobBase location.
	 * define the prizePotLevel in the seat.
	 * that means the player can take chips from the prize pot
	 * Such as:
	 * 
	 * playerE has the prizeLevel 4. ...[0,1,,2,3,4]
	 * playerD has the prizeLevel 3. .. [0,1,2,3]
	 * playerC has the prizeLevel 2. can get prize pot [0,1,2]
	 * playerB has the prizeLevel 1. can get prize pot [0,1]
	 * playerA has the prizeLevel 0. can only get prize pot [0]
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.texas.domain.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		setStage(find_all_in_players, false);
		// find the stand prize pot.
		// no matter pot numbers.
		if(prizePots>0){
			prizeStandPot();
		}else{
			// prize the main pot.
			
			setStage(compare_the_hands_within_prize_pot,false);
		}
			
		// should send the message of the current prize pot.
		setStage(prize_pot,false);

		game.setNextState();
	}
	
	
	private void prizePrimaryPot(){
		prizePotPosition=0;
		
		
	}
	
	
	private void prizeCurrentPot(){
		
		
		currentPrizePotCardRank=GameCardRankEnum.Nothing;
		//currentPrizePotCardSameRankPlayers=0;
		currentPrizePotPlayers.clear();
		setStage(compare_the_hands_within_prize_pot, false);
		if(currentPrizePotPlayers.size()==1){
			// only one win the prize pot
			// current prize pot.
			// give the current prize pot to the winner.
			GameTexasPlayer player=currentPrizePotPlayers.get(0);
			player.setPrizeChips(prizePot[prizePotPosition]);
			// should we notify that.
			
		
		}else{
			// the prize pot need to split.
			// the card the same ,we need to find out who has larger card.
			// 
			// TODO: find out which has larger cards.
			Collections.sort(currentPrizePotPlayers, new Comparator<GameTexasPlayer>(){

				public int compare(GameTexasPlayer arg0,
						GameTexasPlayer arg1) {
					// TODO Auto-generated method stub
					String hex0=arg0.getMatrix().getCards().getHex();
					String hex1=arg1.getMatrix().getCards().getHex();
					return hex0.compareToIgnoreCase(hex1);
				}
				
			});
			
			// after the current player sort,we also need to check if there exist more than one position the highest
			String  hex=currentPrizePotPlayers.get(currentPrizePotPlayers.size()-1).getMatrix().getCards().getHex();
			int looper=1;
			for(int i=currentPrizePotPlayers.size()-2;i>=0;i--){
				String hex0=currentPrizePotPlayers.get(i).getMatrix().getCards().getHex();
				if(hex0.equals(hex)){
					looper++;
					continue;
				}else{
					break;
				}
			}
			
			if(looper>0){
				int currentPotPrize=prizePot[prizePotPosition]/looper;
				// prize the more than one player. or just one player.
				// 
				for(int i=currentPrizePotPlayers.size()-1;i>=0;i--){
					if(looper>0){
						GameTexasPlayer player=currentPrizePotPlayers.get(i);
						player.setPrizeChips(currentPotPrize);
						looper--;
					}
				}
				
				
			}
		}
		
	}
	
	private void prizeStandPot(){
		int pots[] = new int[prizePots];
		for (int i = 0; i < prizePots; i++) {
			pots[i] = allInChips[i];
		}
		
		// the prize pot

		Arrays.sort(pots);
		// the allin chips
		for(int i=pots.length-1;i>0;i--){
			prizePotBase[i]=pots[i]=pots[i-1];
		}
		prizePotBase[0]=pots[0];

		for (prizePotPosition = 0; prizePotPosition < prizePots; prizePotPosition++){
			setStage(fill_all_prize_pot, false);
		}
		// just who win.
		// compare the players with a prizepot.
		
		
		for(prizePotPosition=0;prizePotPosition<prizePots;prizePotPosition++){
			prizeCurrentPot();
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.texas.domain.game.IGameStage#setStage(int, boolean)
	 */
	public void setStage(int stage, boolean resume) {
		// TODO Auto-generated method stub

		this.stage = stage;
		OnTurnManager.obtain().turn(this, resume);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getGame()
	 */
	public GameTexasGame getGame() {
		// TODO Auto-generated method stub
		return game;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getSeats()
	 */
	public CircleOrderArray<GameTexasSeat> getSeats() {
		// TODO Auto-generated method stub
		return seats;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.texas.onturn.IDoOnTurn#onTurn(com.sky.game.texas.domain.
	 * table.GameTexasSeat)
	 */
	public boolean onTurn(GameTexasSeat seat) throws GameTexasException {
		// TODO Auto-generated method stub
		boolean ret = false;

		switch (stage) {
		
		case collect_all_prizes:{
			
			if (seat.getChips()>0) {
				prizePot[0]+=seat.getChips();
				seat.subSeatChips(seat.getChips());
			}
		}
		break;
		case find_all_in_players: {
			if (GameTexasPlayer.isActivePlayer(seat.getPlayer())) {
				if (seat.getPlayer().compareBetState(
						GameTexasPlayerBetStatesEnum.Allin)) {
					allInChips[prize_pot] = seat.getChips();
					prizePots++;

				}
			}
			ret = true;

		}
			break;
		case fill_all_prize_pot: {
			
			if (seat.getChips()>0) {
				int base=prizePotBase[prizePotPosition];
				if(seat.getChips()>=base){
					prizePot[prizePotPosition]+=base;
					seat.subSeatChips(base);
					if(GameTexasPlayer.isActivePlayer(seat.getPlayer())){
						// only the active user has the right to claim the prizepot.
						seat.setPrizePotLevel(prizePotPosition);
					}
				}
			}
			ret = true;

		}
			break;
		case compare_the_hands_within_prize_pot:
			if(GameTexasPlayer.isActivePlayer(seat.getPlayer())){
				
				GameCardRankEnum rankType=seat.getPlayer().getMatrix().getRankType();
				if(rankType.largerThan(currentPrizePotCardRank)){
					currentPrizePotPlayers.clear();
					currentPrizePotPlayers.add(seat.getPlayer());
					currentPrizePotCardRank=seat.getPlayer().getMatrix().getRankType();
				}else if(seat.getPlayer().getMatrix().getRankType().eqauls(currentPrizePotCardRank)){
					currentPrizePotPlayers.add(seat.getPlayer());
				}
				
			}
			ret=true;
			break;
		case prize_pot:
			// update the prize pot.
			if(seat.getPlayer()!=null)
			seat.getTable().updateState(seat.wrap(true, true));
			ret=true;
			break;

		default:
			break;
		}

		return ret;
	}

}
