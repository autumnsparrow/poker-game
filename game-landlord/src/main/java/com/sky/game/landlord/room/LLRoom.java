/**
 * 
 */
package com.sky.game.landlord.room;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.message.AbstractBroker;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.commons.GL0000Beans.LG4003Response;
import com.sky.game.protocol.commons.GL0000Beans.LG4002Response;
import com.sky.game.protocol.commons.GT0001Beans.State;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.service.domain.Item;

/**
 * 
 * 
 * All the Object with unique id can receive the message from the client.
 * 
 * 
 * The Message can send direct to the deck or the room ,or everything the has
 * the unique id. IIdentifiedObject.
 * 
 * @author sparrow
 *
 */
public class LLRoom extends AbstractBroker implements IIdentifiedObject,
		IDeckConfig ,IGamePlayerObserver{
	protected static final Log logger = LogFactory.getLog(LLRoom.class);
	// ConcurrentMap<Long,IIdentifiedObject> decks;
	// ConcurrentMap<Long, IIdentifiedObject> players;

	LLGameObjectMap<LLGamePlayer> players;

	Long id;
	LLRoomStates state;
	// manage to create the deck .
	// loading the item from the protocol server.
	Map<Long, Item> items;

	/**
	 * 
	 */
	public LLRoom(Long id) {
		// TODO Auto-generated constructor stub
		// id=LLU.getId(LLIdTypes.IdTypeRoom);
		this.id = id;
		// let's register the object to message event bus.
		init();
		// GameContextGlobals.registerEventHandler("GL4002", this);
	}

	protected void init() {
		// decks=new ConcurrentHashMap<Long, IIdentifiedObject>();
		players = LLGameObjectMap.getMap();
		LLU.regeisterHandler(this, "GL4000","GL4001","GL4005","GL4009");
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getUri() {
		return "room://" + (id == null ? 0 : id.longValue());
	}

	@Override
	public void observe(LLGamePlayer player) {
		// TODO Auto-generated method stub
		// super.observe(player);

		// observer the player
		// on time out the player will be removed from the player map.
		// 2hour when the player not active.
		// this gameLifeOfPlayer attached with player.
		attacheGamelifeToPlayer(player);

		// player send message
		player.sendBrokerMessage(wrapState(), false);

	}

	@Override
	public void unObserve(LLGamePlayer player) {
		// TODO Auto-generated method stub
		// super.unObserve(player);

		// player send message.
		player.sendBrokerMessage(wrapState(), false);
		
		//player.setState(LLGamePlayerStates.Leave);
		//player.destory(this);
		//players.remove(player.getId());

	}

	/**
	 * When player join room ,attach gamelife to player. if the player not
	 * active from 2hours,the room will clear that player out .
	 * 
	 * @param player
	 * @return
	 */
	private LLGameLifeOfPlayer attacheGamelifeToPlayer(LLGamePlayer player) {
		LLGameLifeOfPlayer gameLifeOfPlayer = new LLGameLifeOfPlayer(this,
				player, 5* LLGameLifeOfPlayer.MIN);
		return gameLifeOfPlayer;
	}

	public void setState(LLRoomStates state) {
		this.state = state;
		log();
		// sendBrokerMessage(wrapState(), false);
	}

	private void log() {
		logger.info(getUri() + " -  " + state.getState().toString());
	}

	public synchronized LLGamePlayer evtAsPlayer(MinaEvent evt) {

		LLGamePlayer player = null;
		BasePlayer basePlayer = GSPP.getPlayer(evt.token);
		//basePlayer.setState(PlayerStates.Tournament);
		if (basePlayer != null) {
			// checking the basePlayer
			Long playerid = Long.valueOf(basePlayer.getUserId());
			// fetch the game player from base player.
			// room id -> player map.
			player = basePlayer.getGamePlayer(id);// players.get(id);
			if (player == null) {
				player = new LLGamePlayer(playerid);
				// set room object.
				// put the player into the player.
				basePlayer.addGamePlayer(id, player);

			}
			LLGamePlayer oldPlayer=players.get(playerid);
			if(player.state.eq(LLGamePlayerStates.Idle)||oldPlayer==null){
				enter(player);
			}
		}
		return player;
	}

	

	LG4002Response wrapState() {
		LG4002Response obj = LLU.o(LG4002Response.class);
		obj.setId(id);
		obj.setState(state.getState());
		return obj;
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
		this.id = id;
	}
	
	
	public void sendException(LLGamePlayer player, LLRoomExceptionTypes types,String description) {
		LG4003Response obj = LLU.o(LG4003Response.class);
		obj.setId(id);
		obj.setState(State.obtain(types.state, description));

		player.sendPrivateMessage(obj, false);
	}

	public void sendException(LLGamePlayer player, LLRoomExceptionTypes types) {
		LG4003Response obj = LLU.o(LG4003Response.class);
		obj.setId(id);
		obj.setState(types.getState());

		player.sendPrivateMessage(obj, false);
	}

	public int onlinePlayerNumbers() {
		int numberOfOnline=players.size();
		if(numberOfOnline<10){
			numberOfOnline=numberOfOnline+GameUtil.getRandom(100);
		}
		return numberOfOnline;
	}

	DeckConfiguration conf;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.room.IDeckConfig#getDeckConfig()
	 */
	public DeckConfiguration getDeckConfig() {
		// TODO Auto-generated method stub
		conf = new DeckConfiguration();
		return conf;
	}

	public LLGamePlayer[] getIdlePlayers() {
		LLGamePlayer[] idlePlayers = null;
		LLGamePlayer[] objs = null;
		if (players.size() > 0) {
			objs = players.values().toArray(new LLGamePlayer[] {});
			Arrays.sort(objs, new Comparator<LLGamePlayer>() {

				public int compare(LLGamePlayer o1, LLGamePlayer o2) {
					// TODO Auto-generated method stub
					return o2.getState().state - o1.getState().state;
				}
			});
			int location = -1;
			for (int i = 0; i < objs.length; i++) {
				LLGamePlayerStates state = objs[i].getState();
				if (state != null
						&& state.state == LLGamePlayerStates.Idle.state) {
					location = i;
					break;
				}
			}
			if (location > -1) {
				idlePlayers = Arrays.copyOfRange(objs, location, objs.length);
			}

		}

		return idlePlayers;
	}

	/**
	 * <p>
	 * if the items is {@code null} ,load the List<Item> from the protocol
	 * server.
	 * 
	 * @see GSPP#getItems()
	 */
	protected synchronized void loadItems() {
		// load items
		if (items == null) {
			List<Item> heads = GSPP.getItems();
			items = GameUtil.getMap();

			for (Item h : heads) {
				items.put(Long.valueOf(h.getId()), h);
			}
		}
	}
	
	void itemNotEnough(LLGamePlayer player,Integer itemId){
		loadItems();
		Item item=items.get(itemId);
		if(item==null)
			throw new RuntimeException("can't find item by item id:"+itemId);
		sendException(player,
				LLRoomExceptionTypes.LLFEnrollItemValueNotEnough,String.format(LLRoomExceptionTypes.LLFEnrollItemValueNotEnough.message,item.getDescription()));
	}

	//
	//
	// protocol handler part
	//
	//

	private void resetGamePlayer(MinaEvent evt){
		BasePlayer basePlayer = GSPP.getPlayer(evt.token);
		basePlayer.removeGamePlayer(id);
		players.remove(basePlayer.getUserId());
	}
	/**
	 * 
	 * every player just join the seat,waiting other players. Player join the
	 * room protocol.
	 * after the player join a team, the player can only change the state to {@link LLGamePlayerStates#Idle }
	 * by the {@link LLTeam#reward(LLGamePlayer)} method. 
	 * 
	 * 
	 * @param evt
	 */
	@RegisterEventHandler(transcode = "GL4000")
	public void onReceiveJoinRoomEvent(MinaEvent evt) {
		
		
		setState(LLRoomStates.UserJoin);
		
		LLGamePlayer player = null;
		
		player = evtAsPlayer(evt);
		if(player.level!=null){
			if(!(player.state.eq(LLGamePlayerStates.Level)||player.state.eq(LLGamePlayerStates.Room))){
				//player.checkState(LLGamePlayerStates.Level);
				sendException(player, LLRoomExceptionTypes.LLAlreadyInGame);
				throw new RuntimeException("player["+getId()+"] not in the ("+LLGamePlayerStates.Level+" or " +LLGamePlayerStates.Room+") state but in "+player.state);
			}
			
		}else{
			
			player.checkState(LLGamePlayerStates.Room);
		}
		
		player.joinRoom=true;
		LLGamePlayerStateObserver.roomEvent(player);
		//player.handleJoinRoom();
		//player.enableRobot = false;
		observe(player);
		
		// update game statics
		//GSPP.updateGameDataStatics(player.id, GameDataStaticsTypes.TournamentTimes);

		// state=LLRoomStates.UserJoin;
		// find an deck which alread has players.

		//
	}

	/**
	 * Player leave the room protocol.
	 * 
	 * 
	 * @param evt
	 */
	@RegisterEventHandler(transcode = "GL4001")
	public void onReceiveLeaveRoomEvent(MinaEvent evt) {
		setState(LLRoomStates.UserLeave);

		LLGamePlayer player = null;
		player = evtAsPlayer(evt);
		
		
		//state = LLRoomStates.UserLeave;
		
		//player.setState(LLGamePlayerStates.Leave);
		player.joinRoom=false;
		LLGamePlayerStateObserver.roomEvent(player);
		//player.handleUserLeave();
		unObserve(player);
	//	GSPP.updateGameDataStatics(player.id, GameDataStaticsTypes.TournamentUnfinished);

	}


	
	/**
	 * enalbe the enroll
	 * @param evt
	 */
	@RegisterEventHandler(transcode="GL4005")
	public void handleEnroll(MinaEvent evt){
		LLGamePlayer p = null;
		p = evtAsPlayer(evt);
		// don't let the user enroll
		// if the player already enrolled.s
		//
		//onle idle can enroll
		//p.checkState(LLGamePlayerStates.Room);
		if(!(p.state.eq(LLGamePlayerStates.Room)||p.state.eq(LLGamePlayerStates.Level))){
			sendException(p, LLRoomExceptionTypes.LLAlreadyInGame);
			throw new RuntimeException("player["+p.id+"] not in room or level state");
		}
		//p.checkStateInRange(LLGamePlayerStates.Room);
		
		if (p != null&&!p.isRobot) {
			p.enableRobot = false;
		}
	}
	
	
	/**
	 * level rank
	 * @param evt
	 */
	@RegisterEventHandler(transcode="GL4009")
	public void handleUserRankList(MinaEvent evt){
		LLGamePlayer p = null;
		p = evtAsPlayer(evt);
		if (p != null&&!p.isRobot) {
			p.enableRobot = false;
		}
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#enter(com.sky.game.landlord.room.LLGamePlayer)
	 */
	public void enter(LLGamePlayer player) {
		// TODO Auto-generated method stub
		getGameObjectMap().put(player.getId(), player);
		//player.room.put(id, this);
		player.setState(LLGamePlayerStates.Room);
		player.room=this;
		
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#leave(com.sky.game.landlord.room.LLGamePlayer)
	 */
	public void leave(LLGamePlayer player) {
		// TODO Auto-generated method stub
		getGameObjectMap().remove(player.getId());
		player.setState(LLGamePlayerStates.Idle);
		player.room=null;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#getGameObjectMap()
	 */
	public LLGameObjectMap<LLGamePlayer> getGameObjectMap() {
		// TODO Auto-generated method stub
		return players;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#getGamePlayerObserver()
	 */
	public IGamePlayerObserver getGamePlayerObserver() {
		// TODO Auto-generated method stub
		return this;
	}

}
