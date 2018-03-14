/**
 * 
 */
package com.sky.game.protocol.commons;


import java.util.List;

import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.util.GameUtil;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * 
 * Push table state
 * 
 * @author Administrator
 *
 */
public class GT0005Beans {
	
	@HandlerResponseType(transcode="GT0005",responsecode="TG0005")
	public static class Response {
		Long id;// table id.
		State state;// table state.
		int playerCount;// current player count.
		String cards;
		
		List<Seat> seats;
		Game game;
		
		public String getCards() {
			return cards;
		}
		public void setCards(String cards) {
			this.cards = cards;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public State getState() {
			return state;
		}
		public void setState(State state) {
			this.state = state;
		}
		public int getPlayerCount() {
			return playerCount;
		}
		public void setPlayerCount(int playerCount) {
			this.playerCount = playerCount;
		}
		public List<Seat> getSeats() {
			return seats;
		}
		public void setSeats(List<Seat> seats) {
			this.seats = seats;
		}
		public Game getGame() {
			return game;
		}
		public void setGame(Game game) {
			this.game = game;
		}
		@Override
		public String toString() {
			return "Request [id=" + id + ", state=" + state + ", playerCount="
					+ playerCount + ", seats=" + seats + ", game=" + game + "]";
		}
		
		
		public static Response obtain(){
			Response obj=new Response();
			obj.seats=GameUtil.getList();
			return obj;
		}
		
	}
	
	/**
	 * Table SEAT.
	 * @author sparrow
	 *
	 */
	
	public static class Seat{
		int location;  // seat location.
		State state;    // seat state
		State positionState;//position state.
		int chips;// current chips in table.
		Player player;
		public int getLocation() {
			return location;
		}
		public void setLocation(int location) {
			this.location = location;
		}
		public State getState() {
			return state;
		}
		public void setState(State state) {
			this.state = state;
		}
		public State getPositionState() {
			return positionState;
		}
		public void setPositionState(State positionState) {
			this.positionState = positionState;
		}
		public int getChips() {
			return chips;
		}
		public void setChips(int chips) {
			this.chips = chips;
		}
		public Player getPlayer() {
			return player;
		}
		public void setPlayer(Player player) {
			this.player = player;
		}
		
		public static Seat obtain(){
			return new Seat();
		}
		@Override
		public String toString() {
			return "Seat [location=" + location + ", state=" + state
					+ ", positionState=" + positionState + ", chips=" + chips
					+ ", player=" + player + "]";
		}
		
		
		
	};
	
	/**
	 * Seat player.
	 * @author sparrow
	 *
	 */
	public static class Player{
		
		long id;// user id.
		State gameState;
		State betState;
		State onTurnState;
		String cards;
		int chips;
		Hands hands;
		Prize prize;
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public State getGameState() {
			return gameState;
		}
		public void setGameState(State gameState) {
			this.gameState = gameState;
		}
		public State getBetState() {
			return betState;
		}
		public void setBetState(State betState) {
			this.betState = betState;
		}
		public State getOnTurnState() {
			return onTurnState;
		}
		public void setOnTurnState(State onTurnState) {
			this.onTurnState = onTurnState;
		}
		public String getCards() {
			return cards;
		}
		public void setCards(String cards) {
			this.cards = cards;
		}
		public int getChips() {
			return chips;
		}
		public void setChips(int chips) {
			this.chips = chips;
		}
		
		public static Player obtain(){
			return new Player();
		}
		
		
		
		public Hands getHands() {
			return hands;
		}
		public void setHands(Hands hands) {
			this.hands = hands;
		}
		
		
		public Prize getPrize() {
			return prize;
		}
		public void setPrize(Prize prize) {
			this.prize = prize;
		}
		@Override
		public String toString() {
			return "Player [id=" + id + ", gameState=" + gameState
					+ ", betState=" + betState + ", onTurnState=" + onTurnState
					+ ", cards=" + cards + ", chips=" + chips + ", hands="
					+ hands + "]";
		}
		
		
	}
	
	
	public static class Hands{
		State rankState;
		String hex;
		
		public State getRankState() {
			return rankState;
		}
		public void setRankState(State rankState) {
			this.rankState = rankState;
		}
		public String getHex() {
			return hex;
		}
		public void setHex(String hex) {
			this.hex = hex;
		}
		@Override
		public String toString() {
			return "Hands [rankState=" + rankState + ", hex=" + hex + "]";
		}
		
		
		
	}
	
	public static class Prize{
		boolean winner;
		public boolean isWinner() {
			return winner;
		}

		public void setWinner(boolean winner) {
			this.winner = winner;
		}

		int chips;

		public int getChips() {
			return chips;
		}

		public void setChips(int chips) {
			this.chips = chips;
		}
		
	}
	
	public static class Game{
		long id;
		State state;// game state.
		Bet bet;
		public Bet getBet() {
			return bet;
		}
		public void setBet(Bet bet) {
			this.bet = bet;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public State getState() {
			return state;
		}
		public void setState(State state) {
			this.state = state;
		}
		@Override
		public String toString() {
			return "Game [id=" + id + ", state=" + state + "]";
		}
		public static Game obtain(){
			return new Game();
		}
		
	}
	
	
	public static class Bet{
		
		State state;

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}
		
		public static Bet obtain(){
			return new Bet();
		}

		@Override
		public String toString() {
			return "Bet [state=" + state + "]";
		}
		
		
		
	}
	

//	/**
//	 * 
//	 */
//	public GT0005Beans() {
//		// TODO Auto-generated constructor stub
//	}
//	@HandlerRequestType(transcode="GT0005")
//	public static class Request{
//		int state;
//		long chips;
//		public Request() {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//		public int getState() {
//			return state;
//		}
//		public void setState(int state) {
//			this.state = state;
//		}
//		public long getChips() {
//			return chips;
//		}
//		public void setChips(long chips) {
//			this.chips = chips;
//		}
//		public Request(int state, long chips) {
//			super();
//			this.state = state;
//			this.chips = chips;
//		}
//		@Override
//		public String toString() {
//			return "Request [state=" + state + ", chips=" + chips + "]";
//		}
//		
//	}
//	
//	@HandlerResponseType(responsecode="TG0005",transcode="GT0005")
//	public static class Response extends BaseResponse{
//		int state;
//		List<Integer> potList=new ArrayList<Integer>();
//		long chips;
//		public int getState() {
//			return state;
//		}
//		public void setState(int state) {
//			this.state = state;
//		}
//		public List<Integer> getPotList() {
//			return potList;
//		}
//		public void setPotList(List<Integer> potList) {
//			this.potList = potList;
//		}
//		public long getChips() {
//			return chips;
//		}
//		public void setChips(long chips) {
//			this.chips = chips;
//		}
//		public Response(int state, List<Integer> potList, long chips) {
//			super();
//			this.state = state;
//			this.potList = potList;
//			this.chips = chips;
//		}
//		public Response() {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//		@Override
//		public String toString() {
//			return "Response [state=" + state + ", potList=" + potList
//					+ ", chips=" + chips + "]";
//		}
//		
//	};
}
