/**
 * @sparrow
 * @Jan 8, 2015   @1:22:13 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.lock.IObtain;
import com.sky.game.context.lock.VolatileLocking;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.blockade.config.BlockadeTournamentConfig.Stage;
import com.sky.game.landlord.blockade.config.item.Reward;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.PReward;
import com.sky.game.protocol.commons.GL0000Beans.UserRank;
import com.sky.game.protocol.commons.GS0000Beans.PGameBlockadeUser;
import com.sky.game.service.domain.GameUser;
import com.sky.game.service.domain.Item;

/**
 * @author sparrow
 *
 */
public class LLBTLevel implements IIdentifiedObject, IGamePlayerObserver {

	LLBTRoom room;
	Stage stage;
	// a level can have many teams
	//
	LLGameObjectMap<LLBTTeam> teams;
	// a level can have many players.
	// in memory ,that's live players.
	LLGameObjectMap<LLGamePlayer> players;

	VolatileLocking<LLGameObjectMap<PGameBlockadeUser>> levelPlayers;

	VolatileLocking<LLGameObjectMap<PGameBlockadeUser>> idPlayers;

	Long id;

	LLBTTeam team;

	int minLevel;
	int maxLevel;

	/**
	 * 
	 */
	public LLBTLevel() {
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @param room
	 */
	public LLBTLevel(LLBTRoom room, Integer level) {
		super();
		
		Map<Integer, Stage> stages = room.config.getLevelStageMap();
		this.stage = stages.get(level);
		this.id = Long.valueOf(stage.getId());// LLGlobalIdGenerator.g.getId(LLIdTypes.IdTypeBTLevel.type);
		this.room = room;
		this.teams = LLU.getGameObjectMap();

		// register into the levels
		room.levels.put(this.id, this);
		this.levelPlayers = VolatileLocking.obtain();
		this.idPlayers = VolatileLocking.obtain();
		this.players = LLGameObjectMap.getMap();
		
		Map<Integer,Stage> stagesMap=room.config.getLevelStageMap();
		Integer[] btlevels=stagesMap.keySet().toArray(new Integer[]{});
		Arrays.sort(btlevels);
		
		minLevel=btlevels[0];
		maxLevel=btlevels[btlevels.length-1];//new LLBTLevel(this,stages[0]);
		

	}
	
	
	public boolean inRangeOfStage(){
		return id>=minLevel&&id<=maxLevel;
	}
	
	public boolean largerMax(){
		return id>maxLevel;
	}
	public boolean smallerMin(){
		return this.id<minLevel;
	}
	
	public Stage nextStage(){
		Stage stage=null;
		if(this.id.intValue()+1>maxLevel){
			stage=this.stage;
		}else{
			stage=room.getStageByLevel(this.id.intValue()+1);
		}
		return stage;
	}

	
	public int playerSize() {
		LLGameObjectMap<PGameBlockadeUser> users = getLevelUsers();
		return users.size();

	}

	public LLGameObjectMap<PGameBlockadeUser> getIdUsers() {
		return this.idPlayers.getHelper(
				new IObtain<LLGameObjectMap<PGameBlockadeUser>, Object>() {

					public LLGameObjectMap<PGameBlockadeUser> obtain(Object a) {
						// TODO Auto-generated method stub
						LLGameObjectMap<PGameBlockadeUser> users = LLGameObjectMap
								.getMap();
						List<PGameBlockadeUser> blockadeUsers = GSPP
								.getGameBlockadeUsers(room.id, getId());
						for (PGameBlockadeUser u : blockadeUsers) {
							users.put(u.getUserId(), u);
						}
						return users;
					}

				}, null);
	}

	public LLGameObjectMap<PGameBlockadeUser> getLevelUsers() {

		return this.levelPlayers.getHelper(
				new IObtain<LLGameObjectMap<PGameBlockadeUser>, Object>() {

					public LLGameObjectMap<PGameBlockadeUser> obtain(Object a) {
						// TODO Auto-generated method stub
						LLGameObjectMap<PGameBlockadeUser> users = LLGameObjectMap
								.getMap();
						List<PGameBlockadeUser> blockadeUsers = GSPP
								.getGameBlockadeUsers(room.id, getId());
						for (PGameBlockadeUser u : blockadeUsers) {
							users.put(u.getId(), u);
						}
						return users;
					}
				}, null);
	}

	public List<GameUser> getLimitedGameUser(Long id) {
		List<GameUser> gameUsers = GameUtil.getList();
		LLGameObjectMap<PGameBlockadeUser> users = getLevelUsers();
		if (users != null) {
			int maxUsers = users.size() > 10 ? 10 : users.size();
			PGameBlockadeUser[] limitedUsers = users.values().toArray(
					new PGameBlockadeUser[] {});
			// for(int i=0;i<)
			for (int i = 0; i < maxUsers; i++) {
				PGameBlockadeUser pbu = limitedUsers[i];
				if (pbu.getUserId().longValue() == id.longValue())
					continue;
				GameUser g = GSPP.getGameUser(limitedUsers[i].getUserId());
				g.setGbuId(limitedUsers[i].getId());
				gameUsers.add(g);
			}
		}
		return gameUsers;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.context.annotation.introspector.IIdentifiedObject#getId()
	 */
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
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
	 * when the user join this level.
	 * 
	 * @param player
	 */
	public void observer(LLGamePlayer player) {

		// LLGamePlayer player = evtAsPlayer(evt);
		LLBTTeam t = player.getObject(player.team);
		if (t != null && !t.state.eq(LLBTTeamStates.End)) {
			player.enableRobot = false;
			room.sendException(player,
					LLRoomExceptionTypes.LLAlreadyInGame);
			throw new RuntimeException("player[" + player.id
					+ "] alread in the BTTeam!");
		} else {
			// after the player join the level return the information of the
			// level.
			if (team == null
					|| (team != null && !team.state.eq(LLBTTeamStates.Waiting))) {

				team = new LLBTTeam(this);
			}

			if (team.state.eq(LLBTTeamStates.Born)) {
				team.setState(LLBTTeamStates.Waiting);
			}

			// if the team state is waiting
			// the joined player add into the team.
			if (team.state.eq(LLBTTeamStates.Waiting)) {
				// LLGamePlayer player = evtAsPlayer(evt);
				// logger.info("joinRoom[" + getId() + "] - " + player.getId());
				enter(player);
				team.observe(player);
			}
		}
	}

	/**
	 * when the player leave the current room.
	 * 
	 * @param player
	 */
	public void unObserver(LLGamePlayer player) {
		leave(player);
		// players.remove(player.getId());
		// when the player team state is Waiting ,
		// the player can quit the current game,without lost any integral.
		LLBTTeam t = player.getObject(player.team);
		if (t != null) {
			if (t.state.eq(LLBTTeamStates.Waiting)) {
				t.unObserve(player);

			}
		}

	}

	public PGameBlockadeUser getFirstUser() {
		PGameBlockadeUser user = null;
		PGameBlockadeUser users[] = getSortedUsers();
		if (users != null && users.length > 0) {
			user = users[0];
		}
		return user;
	}

	public PGameBlockadeUser[] getSortedUsers() {
		LLGameObjectMap<PGameBlockadeUser> users = getLevelUsers();
		PGameBlockadeUser usersArray[] = users.values().toArray(
				new PGameBlockadeUser[] {});
		Arrays.sort(usersArray, new Comparator<PGameBlockadeUser>() {

			public int compare(PGameBlockadeUser o1, PGameBlockadeUser o2) {
				// TODO Auto-generated method stub

				return (int) (o2.getIntegral().longValue() - o1.getIntegral()
						.longValue());
			}

		});
		return usersArray;
	}

	/**
	 * LG4009 - send the user rank in the level.
	 * 
	 * @param player
	 */
	public void sendUserRanks(LLGamePlayer player) {
		// TODO Auto-generated method stub
		LLGameObjectMap<PGameBlockadeUser> users = getLevelUsers();
		PGameBlockadeUser usersArray[] = users.values().toArray(
				new PGameBlockadeUser[] {});
		Arrays.sort(usersArray, new Comparator<PGameBlockadeUser>() {

			public int compare(PGameBlockadeUser o1, PGameBlockadeUser o2) {
				// TODO Auto-generated method stub

				return (int) (o2.getIntegral().longValue() - o1.getIntegral()
						.longValue());
			}

		});

		GL0000Beans.LG4009Response ranks = LLU
				.o(GL0000Beans.LG4009Response.class);
		List<UserRank> rr = GameUtil.getList();
		ranks.setRanks(rr);
		for (int i = 0; i < usersArray.length; i++) {
			PGameBlockadeUser p = usersArray[i];
			try {
				if (p != null) {
					UserRank userRank = LLU.o(UserRank.class);
					userRank.setId(p.getUserId());
					GameUser user = GSPP.getGameUser(p.getUserId());
					userRank.setNickName(user.getNickName());
					userRank.setRank(i + 1);
					userRank.setScore(p.getIntegral().intValue());
					rr.add(userRank);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		player.sendBrokerMessage(ranks, false);

	}

	Map<Long, Item> items;

	private synchronized void loadItems() {
		// load items
		if (items == null) {
			List<Item> heads = GSPP.getItems();
			items = GameUtil.getMap();

			for (Item h : heads) {
				items.put(Long.valueOf(h.getId()), h);
			}
		}
	}

	public PReward getReward() {
		loadItems();

		PReward obj = LLU.o(PReward.class);
		Reward r = stage.getReward();
		Item h = items.get(Long.valueOf(r.getItemId()));
		obj.wrap(h);
		obj.setAmount(r.getItemAmount());
		// obj.setRank(r.getRanking());

		return obj;
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
		getGameObjectMap().put(player.id, player);
		player.setState(LLGamePlayerStates.Level);
		player.level=this;
		//player.level = getGamePlayerObserver();

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
		player.setState(LLGamePlayerStates.Room);
		//player.level = null;
		player.level=null;
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

}
