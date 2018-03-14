/**
 * 
 */
package com.sky.game.poker.robot.ai.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.poker.robot.ai.BidStates;
import com.sky.game.poker.robot.ai.DeckStates;
import com.sky.game.poker.robot.ai.IAiPlayer;
import com.sky.game.poker.robot.ai.IDeck;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.ai.PokerCubeTypes;
import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.game.poker.robot.poker.RoundOfPoker;

/**
 * @author sparrow
 * 
 */
public class DefaultDeck implements IDeck {
	private static final Log logger = LogFactory.getLog(DefaultDeck.class);

	DeckStates deckStates;
	BidStates bidStates;

	List<IAiPlayer> players;
	
	
	PokerCube pokerCube;

	Round bidRound;
	
	int points;// bid points
	int kickPoints;// kick points
	int bombsPoints;//
	
	boolean landlordWin;
	
	Round handsRound;
	int round;
	
	Stack<PokerCube> pokerStack;
	
	Stack<PokerCubeType> pokerCubeType;
	
	
	

	/**
	 * 
	 */
	public DefaultDeck() {
		// TODO Auto-generated constructor stub
		this.players = new LinkedList<IAiPlayer>();
		this.pokerCube = new PokerCube();
		this.deckStates = DeckStates.New;
		this.points=0;
		this.kickPoints=0;
		this.bombsPoints=0;
		this.round=0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.poker.robot.ai.IDeck#getDeckState()
	 */
	public DeckStates getDeckState() {
		// TODO Auto-generated method stub
		return deckStates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.poker.robot.ai.IDeck#join(com.sky.game.poker.robot.ai.IAiPlayer)
	 */
	public void join(IAiPlayer player) {
		// TODO Auto-generated method stub
		//if (this.deckStates.compareTo(DeckStates.WaitingPlayers) == 0) {
			this.players.add(player);
		//}
		
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.poker.robot.ai.IDeck#shuffles()
	 */
	public void shuffles() {
		// TODO Auto-generated method stub

		pokerCube.shuffles(0);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.poker.robot.ai.IDeck#getBidStates()
	 */
	public BidStates getBidStates() {
		// TODO Auto-generated method stub
		return this.bidStates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.poker.robot.ai.IDeck#setBidStates(com.sky.game.poker.robot.ai.BidStates)
	 */
	public void setBidStates(BidStates bidStates) {
		// TODO Auto-generated method stub
		this.bidStates = bidStates;

		// notify all players
		for (IAiPlayer p : players) {
			p.notifyBidStates(getBidStates());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.poker.robot.ai.IDeck#getPokerCubeTypes()
	 */
	public PokerCubeTypes getPokerCubeTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.poker.robot.ai.IDeck#setPokerCubeTypes(com.sky.game.poker.robot.ai.
	 * PokerCubeTypes)
	 */
	public void setPokerCubeTypes(PokerCubeTypes pokerCubeTypes) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.poker.robot.ai.IDeck#showHands(com.sky.game.poker.robot.poker.PokerCube)
	 */
	public void showHands(PokerCube pokerCube) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.poker.robot.ai.IDeck#notifyHands(com.sky.game.poker.robot.poker.PokerCube
	 * )
	 */
	public void notifyHands(PokerCube pokerCube) {
		// TODO Auto-generated method stub
		
		
//		RoundOfPoker roundOfPoker=handsRound.getRoundOfPoker();
//		pokerCube.rewriteHexToCubeByValue(pokerCube.cubeToHexString(), roundOfPoker.getPoker());
		for(IAiPlayer p:players){
			
			p.notifyHands(pokerCube);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.poker.robot.ai.IDeck#shouldShowHandPlayer()
	 */
	public IAiPlayer shouldShowHandPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void prizeAiPlayers() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * Round
	 * 
	 * Begin:
	 * 
	 * 
	 * A B C - position of players = 0 steps =steps + 1 next player = (position
	 * of players +1 )%3 A B C -| if the next player reply mark next A B C - if
	 * the c not reply. A B C a reply -|
	 * 
	 * A B C hasNext=true, no reply -
	 * 
	 * A B C hasNext=true,no reply - A B C hasNext=false,then the current
	 * location is A,end of the round. _ if other don't reply then round is
	 * over.
	 * 
	 * @author sparrow
	 * 
	 */
	private static class Round {

		int positionOfPlayers;
		int steps;
		int replyPosiftionOfPlayers;
		boolean flag;
		int round;

		private static final char[] PLAYERS = { 'A', 'B', 'C' };

		private Round(int positionOfPlayers,int round) {
			super();
			this.round=round;
			this.positionOfPlayers = positionOfPlayers;
			this.steps = 0;

			this.flag = true;
			
			this.replyPosiftionOfPlayers = -1;
		}

		public int getRound() {
			return round;
		}

		public int current() {

			logger.debug(PLAYERS[this.positionOfPlayers] + " Turn");
			return this.positionOfPlayers;
		}

		public boolean hasNext() {
			// this.positionOfPlayers++;
			return this.flag;
		}

		public void next() {
			this.steps++;
			this.positionOfPlayers = ((this.positionOfPlayers + 1) % 3);
			if(this.replyPosiftionOfPlayers==this.positionOfPlayers){
				flag=false;
			}
			
		}

		/**
		 * when the user reply ,then mark it
		 */
		public void reply() {
			logger.debug(PLAYERS[this.positionOfPlayers] + " Reply");
			this.replyPosiftionOfPlayers = this.positionOfPlayers;
		}
		
		
		public RoundOfPoker getRoundOfPoker(){
			try {
				return new RoundOfPoker((byte)current(), (byte)getRound());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public String toString() {
			return "Round [positionOfPlayers="
					+ PLAYERS[positionOfPlayers] + ", steps=" + steps
					+ ", replyPosiftionOfPlayers="
					+ PLAYERS[replyPosiftionOfPlayers] + ", flag=" + flag + "]";
		}

	}

	private static void log(Round r) {
		System.out.println(r.toString());
	}

	public static void main(String args[]) {
		Round round = new Round(0,0);
		// round.reply(); // a reply
		// log(round);
		round.current();
		if (!round.hasNext()) {
			System.out.println(" Round EEE");
		}
		if (round.hasNext())
			round.next(); // b turn, b reply
		round.current();
		round.reply();
		// log(round);

		if (round.hasNext())
			round.next();// c turn ,no reply
		round.current();
		// log(round);

		if (round.hasNext())
			round.next();// a turn,no reply
		round.current();

		// log(round);
		if (!round.hasNext()) {

			round.current();
			logger.debug("End of Round" + round.toString());
			// log(round);
		}

		if (round.hasNext())
			round.next();// b turn ,round end.
		round.current();
		logger.debug(round.toString());

	}

	@Override
	public String toString() {
		return "DefaultDeck [deckStates=" + deckStates + ", bidStates="
				+ bidStates + ", players=" + players + ", pokerCube="
				+ pokerCube + ", bidRound=" + bidRound + "]";
	}

	/**
	 * 
	 * assign the poker cube
	 */
	private void assignPoker() {
		Round r = new Round(0,round++);

		PokerCube cubeBottom = this.pokerCube
				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesRemains.value);

		IAiPlayer a = players.get(r.current());
		PokerCube cubeA = this.pokerCube
				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesA.value);
		a.setInitializePoker(cubeA);
		a.setBottomPoker(cubeBottom);

		r.hasNext();
		r.next();
		IAiPlayer b = players.get(r.current());
		PokerCube cubeB = this.pokerCube
				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesB.value);
		b.setInitializePoker(cubeB);
		b.setBottomPoker(cubeBottom);

		r.hasNext();
		r.next();
		IAiPlayer c = players.get(r.current());
		PokerCube cubeC = this.pokerCube
				.maskPokerCubeWithValue(PokerCube.Cube.ShufflesC.value);
		c.setInitializePoker(cubeC);
		c.setBottomPoker(cubeBottom);
		
		
		// overide as fixed for debug
		
	}
	
	private void applyLandLord(IAiPlayer player){
		for(IAiPlayer p:players){
			p.setLandLordFlag(false);
		}
		player.setLandLordFlag(true);
	}

	/**
	 * Using the scheduled task to check the deck state.
	 * 
	 * 
	 */
	public void run() {
		// TODO Auto-generated method stub
		logger.debug("Deck :" + deckStates.toString());
		switch (deckStates.state) {
		case DeckStates.NEW:
		{
			for(int i=0;i<3;i++){
				IAiPlayer player=new DefaultAiPlayer();
				this.join(player);
			}
			this.deckStates=DeckStates.WaitingPlayers;
			
		}
			break;
		case DeckStates.WAITING_PLAYER: {
			if (this.players.size() == 3) {
				
				
				// assign the deck position of the players.
				for(int i=0;i<this.players.size();i++){
					IAiPlayer player=this.players.get(i);
					player.setDeckPosition(i);
				}
				this.deckStates = DeckStates.AssignPoker;
			}
		}
			break;
		case DeckStates.ASSIGN_POKER: {
			shuffles();
			assignPoker();
			
			this.bidRound = new Round(0,round++);
			this.deckStates = DeckStates.Bid;
			this.bidRound.reply();
			setBidStates(BidStates.ZeroPoint);
			if(this.bidRound.hasNext())
				this.bidRound.next();
		}
			break;
		case DeckStates.BID: {
			
			IAiPlayer player = players.get(bidRound.current());
			// when the bid round over.
			if (!bidRound.hasNext()) {

				if (getBidStates().compareTo(BidStates.ZeroPoint) == 0) {
					setBidStates(player.offerLowestBidStates());
					//this.deckStates = DeckStates.Hands;
					applyLandLord(player);
				}
				this.deckStates = DeckStates.Kick;
				
			} else {

				// only when the user offers is larger than current bid
				BidStates bid = player.offerBidStates();

				if (bid.compareTo(BidStates.Pass) == 0) {
					// should let next player bid
					// the player no bid
					// if has other player to bid.

				} else if (bid.compareTo(getBidStates()) > 0) {
					bidRound.reply();
					setBidStates(bid);
					applyLandLord(player);
				} else if (bid.compareTo(BidStates.ThreePoints) == 0) {
					//player.setLandLordFlag(true);
					applyLandLord(player);
					this.deckStates = DeckStates.Kick;
					// state change.
				} else if (bid.compareTo(getBidStates()) < 0) {
					logger.warn("How that possible bid lower than the deck :"
							+ getBidStates().toString());
				}
				bidRound.next();
			}

		}
			break;
		case DeckStates.KICK:{
			// checking the points
			this.points=getBidStates().getState();
			
			for(IAiPlayer p:players){
				if(!p.isLandLorder()){
					if(p.offerKick())
						this.kickPoints++;
				}
			}
			
			this.deckStates=DeckStates.Hands;
			
			// create the first round 
			// get the landlord
			int position=0;
			for(int i=0;i<this.players.size();i++)
			{
				IAiPlayer p=this.players.get(i);
				if(p.isLandLorder()){
					position=i;
					p.combinatePokerCube();
					//p.notifyRoundStarter(true);
					break;
				}
			}
			
			
			// fixed the poker for debug
			//TODO:begin of the fixed code. Notice: remove that part code  on production 
		
		/*	this.players.get(0).setInitializePoker(PokerCube.getPokerCubeByHex("9804d70d10aa18"));
			this.players.get(1).setInitializePoker(PokerCube.getPokerCubeByHex("22d220a24a1064"));
			this.players.get(2).setInitializePoker(PokerCube.getPokerCubeByHex("45290850a54580"));
			
			for(int i=0;i<this.players.size();i++)
			{
				IAiPlayer p=this.players.get(i);
				if(p.isLandLorder()){
					position=i;
					
				//	p.notifyRoundStarter(true);
					break;
				}
			}   */
			//TODO: end of the fixed code
			
			
			handsRound=new Round(position,round++);
			pokerStack=new Stack<PokerCube>();
			pokerCubeType=new Stack<PokerCubeType>();
			
			
		}
			break;
		case DeckStates.HAND: {
			
			int i=handsRound.current();
			while(true){
				IAiPlayer player=this.players.get(i%3);
				PokerCube pokerCube=player.showHands();
				if(!pokerCube.isEmpty()){
					logger.debug("  Reply:"+pokerCube.cubeToHexString());
				}
				this.notifyHands(pokerCube);
				
				i++;
				
				// checking if one player is empty
				boolean finished=false;
				for(IAiPlayer p:players){
					if(p.pokerRemains()==0){
						finished=true;
						break;
					}
				}
				if(finished)
					break;
				
			}
			
			
			// who has the right to active show hands.
			/*IAiPlayer player=this.players.get(handsRound.current());
			PokerCube pokerCube=player.showHands();
			pokerCubeType.push(pokerCube.getPokerCubeType());
			handsRound.reply();
			this.notifyHands(pokerCube);
			this.pokerStack.push(pokerCube);
			
			while(handsRound.hasNext()){
				handsRound.next();
				player=this.players.get(handsRound.current());
				pokerCube=player.showHands();
				
				if(!pokerCube.isEmpty()){
					logger.debug("  Reply:"+pokerCube.cubeToHexString());
					handsRound.reply();
					
					// attach the meta
					//RoundOfPoker  roundOfPoker=new RoundOfPoker(handsRound, round)
					
					
				}
				
				PokerCube lastPokerCube=pokerStack.peek();
				// 
				if(!validPokerCube(pokerCube, lastPokerCube)){
					logger.warn("PokerCube valid failed!");
				}
				
				
				this.notifyHands(pokerCube);
			}
			
			
			// checking the poker cube types.
			PokerCube lastPokerCube=pokerStack.peek();
			if(lastPokerCube.isBomb()){
				bombsPoints=pokerStack.size();
			}
			
			
			// show the poker already showed
			
			// form the new handsRound
			handsRound=new Round(handsRound.current(),round++);
			pokerStack=new Stack<PokerCube>();
			IAiPlayer aiplayer=this.players.get(handsRound.current());
			aiplayer.notifyRoundStarter(true);
			// process the next step.
			

			// checking if somebody off all the cards.
			// the deck is over.
			for (IAiPlayer p : players) {
				logger.debug(p.toString());
			}
			*/
			for (IAiPlayer p : players) {
				if (p.pokerRemains() == 0) {
					landlordWin=p.isLandLorder();
					logger.debug(p.getDeckPosition()+" : win landlord:" +landlordWin);
					this.deckStates = DeckStates.Prize;
					break;
				}
			}
		}

			break;
		case DeckStates.PRIZE: {
			int score=(points+kickPoints)*(1+bombsPoints);
			for (IAiPlayer p : players) {
				if(p.isLandLorder()&&landlordWin){
					logger.debug(" LandLord win:"+score);
					p.setScore(score);
				}else{
					logger.debug(" Farmer win:"+score);
					p.setScore(-score/2);
				}
			}
			
			while(!pokerCubeType.isEmpty()){
				PokerCubeType t=pokerCubeType.pop();
				if(t!=null)
				logger.debug(t);
			}
			
				
			this.deckStates=DeckStates.End;
		}
			break;
		case DeckStates.END:
			break;

		default:
			break;
		}

	}
	
	
	private boolean validPokerCube(PokerCube currentPokerCube,PokerCube lastPokerCube){
		return true;
	}

}
