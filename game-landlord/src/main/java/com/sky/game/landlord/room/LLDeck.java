/**
 * 
 */
package com.sky.game.landlord.room;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.id.LLIdTypes;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.exception.LLGameExceptionTypes;
import com.sky.game.landlord.exception.LLGameExecption;
import com.sky.game.landlord.jmx.JmxDeck;
import com.sky.game.landlord.jmx.JmxPlayer;
import com.sky.game.landlord.message.AbstractBroker;
import com.sky.game.landlord.message.IBrokerMessage;
import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.game.protocol.commons.GL0000Beans.DeckPlayerObject;
import com.sky.game.protocol.commons.GL0000Beans.LG1004Response;
import com.sky.game.protocol.commons.GL0000Beans.LG1007Response;
import com.sky.game.protocol.commons.GL0000Beans.LG3000Response;
import com.sky.game.protocol.commons.GL0000Beans.LG3001DeckObject;
import com.sky.game.protocol.commons.GL0000Beans.LG3001Response;
import com.sky.game.protocol.commons.GL0000Beans.LG3001SeatObject;
import com.sky.game.protocol.commons.GL0000Beans.LG4003Response;
import com.sky.game.protocol.commons.GL0000Beans.Range;
import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * 
 * Deck receive message.
 * 
 * @author sparrow
 *
 */
public class LLDeck extends AbstractBroker implements IIdentifiedObject,
		IGamePlayerObserver {
	private static final Log logger = LogFactory.getLog(LLDeck.class);
	public static final int MAX_SEATS = 3;

	Long id;
	LLRoom room;
	ITeamSorted teamSorted;

	LLDeckStates state;

	// deck configuration.
	DeckConfiguration conf;

	// the deck hold the kitty cards
	PokerCube kittyCards;

	// current start game in the deck.
	int gamePerDeck;

	// LLDeckShowHandsRoundTypes showHandsRoundType;
	PokerCube lastHandPokerCube;

	LLGame game;

	LLDeckSeat seats[];

	LLGameRobot robot;

	// Map<Long,LLGamePlayer> players;
	LLGameObjectMap<LLGamePlayer> players;

	JmxDeck jmxDeck;

	public LLDeck(LLRoom room) {
		super();
		this.room = room;
		players = LLGameObjectMap.getMap();
		seats = new LLDeckSeat[MAX_SEATS];
		for (int i = 0; i < MAX_SEATS; i++) {
			seats[i] = new LLDeckSeat(this, i);
		}
		id = LLU.getId(LLIdTypes.IdTypeDeck);// LLGlobalIdGenerator.g.getId(LLIdTypes.IdTypeDeck.type);

		conf = room.getDeckConfig();// GameUtil.obtain(LLDeckConfiguration.class);

		jmxDeck = LLU.o(JmxDeck.class);
		// add decks into the room.
		// room.decks.put(getId(), this);

		state = LLDeckStates.DeckIdle;

		// robot=LLU.o(LLGameRobot.class);
	}

	public synchronized void reset(boolean leave) {

		if (game != null) {
			game.destory();
			game = null;
		}

		for (int i = 0; i < MAX_SEATS; i++) {
			seats[i].reset();

		}

		if (leave) {
			gamePerDeck = 0;
			for (int i = 0; i < MAX_SEATS; i++) {

				if (seats[i].player != null) {
					seats[i].leave();
				}

			}

			//

			// remove from the decks
			// room.decks.remove(id);
			players.clear();
		}
		lastHandPokerCube = null;
		state = LLDeckStates.DeckIdle;

	}

	public String getUri() {
		return room.getUri() + "/deck://" + (id == null ? 0 : id.longValue());
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void observe(LLGamePlayer player) {
		// TODO Auto-generated method stub
		super.observe(player);
		// player.setHandler(handler);
		// player.d=this;
		// players.put(player.getId(), player);
		enter(player);
	}

	@Override
	public void unObserve(LLGamePlayer player) {
		// TODO Auto-generated method stub
		super.unObserve(player);
		leave(player);

		// player.setHandler(null);
	}

	// public void tryRobot(LLGamePlayer player){
	// super.unObserve(player);
	// player.set
	// }
	//

	public int idleSeats() {
		int count = 0;
		LLDeckSeat seat = null;
		for (int i = 0; i < MAX_SEATS; i++) {
			seat = seats[i];
			if (seat.state.equals(LLDeckSeatStates.Empty)) {
				count++;
			}
		}
		return count;

	}

	private LLDeckSeat findIdleSeat() {
		LLDeckSeat seat = null;

		for (int i = 0; i < MAX_SEATS; i++) {
			seat = seats[i];
			if (seat.state.equals(LLDeckSeatStates.Empty)) {
				break;
			}
		}
		return seat;
	}

	public synchronized boolean shouldStartGame() {

		boolean ret = idleSeats() == 0;

		return ret;
	}

	public synchronized void seat(LLGamePlayer player) {
		LLDeckSeat seat = findIdleSeat();
		seat.seat(player);
		// player.setDeck(this);
		createGame();
	}

	public synchronized void createGame() {
		if (shouldStartGame()) {
			start();
		}
	}

	/**
	 * 
	 * @param players
	 * @throws LLGameExecption
	 */
	public void seat(LLGamePlayer players[]) throws LLGameExecption {
		if (players != null && players.length == seats.length && seats != null) {
			for (int i = 0; i < MAX_SEATS; i++) {
				seats[i].seat(players[i]);
			}
		} else {
			throw LLGameExceptionTypes.DeckSeatMismatchPlayers.exception();
		}
		// after seat the players ,begin the game.
		// restart();
		createGame();
	}

	/**
	 * wrap the deck positions
	 * 
	 * @return
	 */
	public LG3000Response wrapPlayers() {
		LG3000Response obj = GameUtil.obtain(LG3000Response.class);
		obj.setId(id);
		//
		List<LG1004Response> entries = GameUtil.getList();
		for (LLDeckSeat seat : seats) {
			if (seat.player != null)
				entries.add(seat.wrapPlayers());
		}
		obj.setPositions(entries);

		return obj;

	}

	// can send the broad cast.
	// also can send to single player

	public void sendPrivateMessage(IBrokerMessage player, Object obj,
			boolean request) {

		player.sendBrokerMessage(obj, request);
	}

	private void log(String message) {
		logger.info(GameUtil.formatId(id, this) + " - " + message);
	}

	/**
	 * 
	 * change the deck can play configuration games.
	 * 
	 * 
	 */
	public void start() {

		state = LLDeckStates.DeckGaming;
		// showHandsRoundType=LLDeckShowHandsRoundTypes.Undefined;
		lastHandPokerCube = null;
		gamePerDeck++;
		// born the game
		game = new LLGame(this);
		if (game != null)
			jmxDeck.setGame(game.getJmxGame());
		log("create game.");
		// logger.info(GameUtil.formatId(id, this)+" - create game");
		// logger.info("deck["+getId()+"] - create game "+game.getId());

	}

	/**
	 * how to control the games continue in a deck?
	 * 
	 * 
	 * @return
	 */
	public synchronized boolean shouldEndGames() {

		boolean ret = this.gamePerDeck >= this.conf.getGamePerDeck();

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.context.annotation.introspector.IIdentifiedObject#setId(
	 * java.lang.Long)
	 */
	public void setId(Long id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * 
	 * @param seat
	 * @param types
	 */
	public void sendException(LLDeckSeat seat, LLRoomExceptionTypes types) {
		LG4003Response obj = LLU.o(LG4003Response.class);
		obj.setId(room.id);
		obj.setState(types.getState());

		sendPrivateMessage(seat.player, obj, false);
	}

	@Override
	public String toString() {
		return "LLDeck [id=" + id + ", state=" + state + ", gamePerDeck="
				+ gamePerDeck + ",seats=" + Arrays.toString(seats) + "]";
	}

	public LG3001DeckObject wrapOfflineDeckObject() {
		LG3001DeckObject o = LLU.o(LG3001DeckObject.class);
		o.setDeckId(this.id);
		o.setDeckState(State.obtain(state.state, state.toString()));
		o.setKittyCards(kittyCards.cubeToHexString());
		if (lastHandPokerCube != null)
			o.setLastHand(lastHandPokerCube.cubeToHexString());
		else
			o.setLastHand((PokerCube.ZERO));
		o.setGameOfDeck(Range.obtain(gamePerDeck, conf.getGamePerDeck()));
		
		return o;
	}

	public LG3001Response wrapOffline(LLGamePlayer player) {
		LG3001Response o = LLU.o(LG3001Response.class);
		if (player.getPokeCube() != null)
			o.setHand(player.getPokeCube().cubeToHexString());
		o.setDeck(wrapOfflineDeckObject());
		if (game != null) {
			o.setGame(game.wrapOfflineGameObject());
			o.setGameScore(game.wrapOfflineGameScore());

			List<LG3001SeatObject> lgSeats = GameUtil.getList();
			for (LLDeckSeat seat : seats) {
				lgSeats.add(seat.wrapOfflineSeatObject());
			}
			o.setSeats(lgSeats);
		}
		return o;
	}
	
	public LG1007Response wrapSeatObject(){
		LG1007Response o=LLU.o(LG1007Response.class);
		o.setBase(conf.baseChips);
//		o.setIntegral(chips);
//		o.setRank(rank);
		List<DeckPlayerObject> l=GameUtil.getList();
		for (LLDeckSeat seat : seats) {
			l.add(seat.wrapDeckPlayerObject());
		}
		o.setPlayers(l);
		
		
		return o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.room.IGamePlayerObserver#enter(com.sky.game.landlord
	 * .room.LLGamePlayer)
	 */
	public void enter(LLGamePlayer player) {
		// TODO Auto-generated method stub
		getGameObjectMap().put(player.getId(), player);
		// player.deck=getGamePlayerObserver();
		player.deck = this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.room.IGamePlayerObserver#leave(com.sky.game.landlord
	 * .room.LLGamePlayer)
	 */
	public void leave(LLGamePlayer player) {
		// TODO Auto-generated method stub
		getGameObjectMap().remove(player.getId());
		// player.deck=null;
		player.deck = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#getGameObjectMap()
	 */
	public LLGameObjectMap<LLGamePlayer> getGameObjectMap() {
		// TODO Auto-generated method stub
		return players;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.room.IGamePlayerObserver#getGamePlayerObserver()
	 */
	public IGamePlayerObserver getGamePlayerObserver() {
		// TODO Auto-generated method stub
		return this;
	}

	public JmxDeck getJmxDeck() {
		jmxDeck.getPlayers().clear();
		for (LLGamePlayer p : players.values()) {
			JmxPlayer player = LLU.o(JmxPlayer.class);
			player.setId(p.id);
			player.setDescription(p.toString());
			jmxDeck.getPlayers().add(player);
		}

		return jmxDeck;

	}

}
