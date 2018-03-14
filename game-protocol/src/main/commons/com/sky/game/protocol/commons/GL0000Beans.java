/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.util.BeanUtil;
import com.sky.game.protocol.commons.GT0001Beans.State;
import com.sky.game.service.domain.GameUser;
import com.sky.game.service.domain.Item;

/**
 * 
 Room 4xxx Deck 3xxx Game 2xxx Seat 1xxx Player 0xxx
 * 
 * @author sparrow
 *
 */
public class GL0000Beans {
	
	
	/**
	 * namespace LLGame 
	 * {@link IProtocolAsyncHandler#onRecieve(Object, {@link Extra)}
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerNamespaceExtraType(namespace="LLGame")
	public static class Extra {
		String deviceId;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		
	}

	/**
	 * 
	 */
	public GL0000Beans() {
		// TODO Auto-generated constructor stub
	}

	
	//
	// protocol defined:
	// Configuraiton - 9xxx notify some kind of configuraiton changed.
	// 
	// Tournament 5xxx
	// Room - 4xxx
	// Deck - 3xxx
	// Game - 2xxx
	// Seat - 1xxx
	// Player - 0xxx

	//
	//
	// Basic Protocol Objects.
	//
	// channel id
	public static class Channel extends BaseRequest implements IIdentifiedObject{
		Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
	}
	
	// tournament protocol
	// 5000 - request free tournament room list
	// 5001 - request elimination tournament room list
	// 5002 - request blockade tournament room list.
	// 5003 
	// 
	//free tournament.
	//
	/**
	 * request the free tournament list
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GL5000")
	public static class GL5000Request extends Channel{
		
	}
	
	public static class FtRoom{
		long id;
		String name;
		String restrict;
		int base;
		int onlinePlayers;
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRestrict() {
			return restrict;
		}
		public void setRestrict(String restrict) {
			this.restrict = restrict;
		}
		public int getBase() {
			return base;
		}
		public void setBase(int base) {
			this.base = base;
		}
		public int getOnlinePlayers() {
			return onlinePlayers;
		}
		public void setOnlinePlayers(int onlinePlayers) {
			this.onlinePlayers = onlinePlayers;
		}
		
	}
	
	/**
	 * Free tournament room list.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL5000",responsecode="LG5000")
	public static class LG5000Response {
		
		List<FtRoom> rooms;

		public List<FtRoom> getRooms() {
			return rooms;
		}

		public void setRooms(List<FtRoom> rooms) {
			this.rooms = rooms;
		}
		
	}
	
	
	//
	// elimination tournament.
	/**
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GL5001")
	public static class GL5001Request extends Channel{
		boolean vip;

		public boolean isVip() {
			return vip;
		}

		public void setVip(boolean vip) {
			this.vip = vip;
		}
		
	}
	
	/**
	 * 
	 * Protocol wrap {@link Item}
	 *
	 */
	public static class PItem{
		int id;
		String name;
		String description;
		String icon;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		
		public void wrap(com.sky.game.service.domain.Item item){
			if(item==null)
				return;
			this.description=item.getDescription();
			this.name=item.getName();
			this.icon=BeanUtil.v(item, "icon.url");//item.getIcon().getUrl();
			this.id=(int) BeanUtil.getLong(item,"id");//item.getId().intValue();
			
			
		}
		
	}
	/**
	 * 
	 *Enroll ticket
	 *
	 */
	public static class PEnrollTicket extends PItem{
		
		int amount;

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}
		
		
	}
	
	public static class PReward extends PItem{
		String rank;
		int amount;
		
		

		public String getRank() {
			return rank;
		}

		public void setRank(String rank) {
			this.rank = rank;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}
		
	}
	
	/**
	 * 
	 * elimination room
	 *
	 */
	public static class ETRoom{
		String roomId;
		String roomName;
		String description;
		String matchTime;// ontime or scheduled time.
		String matchBeginPlayerNumbers;
		String onlinePlayerNumbers;
		
		boolean enrolled;
		long timeup;
		boolean robot;
		
		List<PEnrollTicket> enrollTickets;
		List<PReward> rewards;
		
		
		
		public String getRoomName() {
			return roomName;
		}
		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getMatchTime() {
			return matchTime;
		}
		public void setMatchTime(String matchTime) {
			this.matchTime = matchTime;
		}
		public String getMatchBeginPlayerNumbers() {
			return matchBeginPlayerNumbers;
		}
		public void setMatchBeginPlayerNumbers(String matchBeginPlayerNumbers) {
			this.matchBeginPlayerNumbers = matchBeginPlayerNumbers;
		}
		public String getOnlinePlayerNumbers() {
			return onlinePlayerNumbers;
		}
		public void setOnlinePlayerNumbers(String onlinePlayerNumbers) {
			this.onlinePlayerNumbers = onlinePlayerNumbers;
		}
		public List<PEnrollTicket> getEnrollTickets() {
			return enrollTickets;
		}
		public void setEnrollTickets(List<PEnrollTicket> enrollTickets) {
			this.enrollTickets = enrollTickets;
		}
		public List<PReward> getRewards() {
			return rewards;
		}
		public void setRewards(List<PReward> rewards) {
			this.rewards = rewards;
		}
		public boolean isEnrolled() {
			return enrolled;
		}
		public void setEnrolled(boolean enrolled) {
			this.enrolled = enrolled;
		}
		public long getTimeup() {
			return timeup;
		}
		public void setTimeup(long timeup) {
			this.timeup = timeup;
		}
		public String getRoomId() {
			return roomId;
		}
		public void setRoomId(String roomId) {
			this.roomId = roomId;
		}
		public boolean isRobot() {
			return robot;
		}
		public void setRobot(boolean robot) {
			this.robot = robot;
		}
		
	}
	
	
	/**
	 * 
	 * Elimination room list .
	 *
	 */
	@HandlerResponseType(transcode="GL5001",responsecode="LG5001")
	public static class LG5001Response{
		
		boolean vip;
		List<ETRoom> rooms;

		public List<ETRoom> getRooms() {
			return rooms;
		}

		public void setRooms(List<ETRoom> rooms) {
			this.rooms = rooms;
		}

		public boolean isVip() {
			return vip;
		}

		public void setVip(boolean vip) {
			this.vip = vip;
		}
	}
	
	/**
	 * 
	 *Request blockade room list.
	 *
	 */
	@HandlerRequestType(transcode="GL5002")
	public static class GL5002Request extends Channel{
		
	}
	/**
	 * 
	 * Blockade romm list
	 *
	 */
	@HandlerResponseType(transcode="GL5002",responsecode="LG5002")
	public static class LG5002Response{
		List<BTRoom> rooms;

		public List<BTRoom> getRooms() {
			return rooms;
		}

		public void setRooms(List<BTRoom> rooms) {
			this.rooms = rooms;
		}
		
	}
	
	/**
	 * 
	 *Blockade room level.
	 *
	 */
	public static class BTRoomLevel{
		String level; // player current level,if not enrolled. just show the 
		int onlinePlayerNumbers;
		int totalPlayerNumbers;
		PReward reward; // reward
		long timeup; // time for game begin.
		
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
		public int getOnlinePlayerNumbers() {
			return onlinePlayerNumbers;
		}
		public void setOnlinePlayerNumbers(int onlinePlayerNumbers) {
			this.onlinePlayerNumbers = onlinePlayerNumbers;
		}
		public int getTotalPlayerNumbers() {
			return totalPlayerNumbers;
		}
		public void setTotalPlayerNumbers(int totalPlayerNumbers) {
			this.totalPlayerNumbers = totalPlayerNumbers;
		}
		public PReward getReward() {
			return reward;
		}
		public void setReward(PReward reward) {
			this.reward = reward;
		}
		public long getTimeup() {
			return timeup;
		}
		public void setTimeup(long timeup) {
			this.timeup = timeup;
		}
		
	}
	
	/**
	 * 
	 * Blockade room level detail.
	 *
	 */
	public static class BTRoomLevelDetail{
		String level;
		Range integral;
		int base;
		String matchTime;
		int playingPlayerNumbers;
		PReward reward;
		String firstNickname;
		
		
		
		
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
	
		public int getBase() {
			return base;
		}
		public void setBase(int base) {
			this.base = base;
		}
		public String getMatchTime() {
			return matchTime;
		}
		public void setMatchTime(String matchTime) {
			this.matchTime = matchTime;
		}
		public int getPlayingPlayerNumbers() {
			return playingPlayerNumbers;
		}
		public void setPlayingPlayerNumbers(int playingPlayerNumbers) {
			this.playingPlayerNumbers = playingPlayerNumbers;
		}
		public PReward getReward() {
			return reward;
		}
		public void setReward(PReward reward) {
			this.reward = reward;
		}
		public Range getIntegral() {
			return integral;
		}
		public void setIntegral(Range integral) {
			this.integral = integral;
		}
		public String getFirstNickname() {
			return firstNickname;
		}
		public void setFirstNickname(String firstNickname) {
			this.firstNickname = firstNickname;
		}
		
		
	}
	/**
	 * 
	 * 
	 * Blockade room 
	 *
	 */
	public static class BTRoom{
		long id;
		String roomName;
		String description;
		String matchActive;// match start
		String matchExpired;//match end
		String onlinePlayerNumbers; // online player. 
		
		BTRoomLevel currentLevel;
		
		List<BTRoomLevelDetail> levels;
		
		List<PEnrollTicket> enrollTickets;
		int initIntegral;

		public String getRoomName() {
			return roomName;
		}

		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getMatchActive() {
			return matchActive;
		}

		public void setMatchActive(String matchActive) {
			this.matchActive = matchActive;
		}

		public String getMatchExpired() {
			return matchExpired;
		}

		public void setMatchExpired(String matchExpired) {
			this.matchExpired = matchExpired;
		}

		public String getOnlinePlayerNumbers() {
			return onlinePlayerNumbers;
		}

		public void setOnlinePlayerNumbers(String onlinePlayerNumbers) {
			this.onlinePlayerNumbers = onlinePlayerNumbers;
		}

		public BTRoomLevel getCurrentLevel() {
			return currentLevel;
		}

		public void setCurrentLevel(BTRoomLevel currentLevel) {
			this.currentLevel = currentLevel;
		}

		public List<BTRoomLevelDetail> getLevels() {
			return levels;
		}

		public void setLevels(List<BTRoomLevelDetail> levels) {
			this.levels = levels;
		}

		public List<PEnrollTicket> getEnrollTickets() {
			return enrollTickets;
		}

		public void setEnrollTickets(List<PEnrollTicket> enrollTickets) {
			this.enrollTickets = enrollTickets;
		}

		public int getInitIntegral() {
			return initIntegral;
		}

		public void setInitIntegral(int initIntegral) {
			this.initIntegral = initIntegral;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
		
		
		
	}
	
	@HandlerRequestType(transcode="GL5003")
	public static class GL5003Request extends Channel {
		
	}
	
	@HandlerResponseType(transcode="GL5003",responsecode="LG5003")
	public static class LG5003Response{
		int ftNumbers;
		int etNumbers;
		int etVipNumbers;
		int btNumbers;
		public int getFtNumbers() {
			return ftNumbers;
		}
		public void setFtNumbers(int ftNumbers) {
			this.ftNumbers = ftNumbers;
		}
		public int getEtNumbers() {
			return etNumbers;
		}
		public void setEtNumbers(int etNumbers) {
			this.etNumbers = etNumbers;
		}
		public int getEtVipNumbers() {
			return etVipNumbers;
		}
		public void setEtVipNumbers(int etVipNumbers) {
			this.etVipNumbers = etVipNumbers;
		}
		public int getBtNumbers() {
			return btNumbers;
		}
		public void setBtNumbers(int btNumbers) {
			this.btNumbers = btNumbers;
		}
		
		
	}

	
	

	/**
	 * 
	 * RoomObject
	 * @author sparrow
	 *
	 */
	public static class RoomObject extends BaseRequest implements IIdentifiedObject{
		Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
	}
	/**
	 * transcode - 3xxx deck object
	 * 
	 * @author sparrow
	 *
	 */
	public static class DeckObject extends BaseRequest  implements IIdentifiedObject {
		Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	}
	
	/**
	 * transcode - 2xxx LLGame base object
	 * 
	 * @author sparrow
	 *
	 */
	public static class GameObject  extends BaseRequest  implements IIdentifiedObject {
		Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	}

	/**
	 * transcode - 1xxx
	 * 
	 * @author sparrow
	 *
	 */
	public static class DeckSeatObject  extends DeckObject {
		int position;

		public int getPosition() {
			return position;
		}

		public void setPosition(int postion) {
			this.position = postion;
		}

	}

	/**
	 * transcode - 0xxx
	 * 
	 * @author sparrow
	 *
	 */
	public static class PlayerObject extends BaseRequest implements IIdentifiedObject {
		Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	}
	
	
	

	//
	// protocol for room:
	// 4000 - join room
	// 4001 - leave room
	// 4002 - room state 
	// 4003 - room exception
	// 4004 - free tournament user  ready.
	// 4005 - elimination tournament enroll 
	// 4006 - 
	// 4013 - check if user enroll the blockade 

	/**
	 * player request to join the Room.
	 * 
	 * Room 4xxx Deck 3xxx Game 2xxx Seat 1xxx Player 0xxx
	 * RequestType: client -> landlord.
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GL4000")
	public static class GL4000Request extends RoomObject{
		
	}

	/**
	 * 
	 * player request to leave the room.
	 * RequestType: client -> landlord
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GL4001")
	public static class GL4001Request extends RoomObject{
		

	}
	/**
	 * room states
	 * PushType: landlord ->client.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4002",responsecode="LG4002")
	public static class LG4002Response extends RoomObject {
		State state;

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}
		
	}
	
	/**
	 * room exceptions
	 * PushType
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4003",responsecode="LG4003")
	public static class LG4003Response extends RoomObject{
		State state;

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}
		
	}
	/**
	 * user ready
	 * RequestType:(client -> server)
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GL4004")
	public static class GL4004Request extends RoomObject {
		
	}
	
	/**
	 * user enroll request
	 * 
	 */
	@HandlerRequestType(transcode="GL4005")
	public static class GL4005Request extends RoomObject{
		private int itemId;
		//private boolean enrolled;

		public int getItemId() {
			return itemId;
		}

		public void setItemId(int itemId) {
			this.itemId = itemId;
		}

//		public boolean isEnrolled() {
//			return enrolled;
//		}
//
//		public void setEnrolled(boolean enrolled) {
//			this.enrolled = enrolled;
//		}
//		
	}
	/**
	 * user enroll response
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4005",responsecode="LG4005")
	public static class LG4005Response extends RoomObject {
		State state;

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}
		
		
	}
	
	//　User enroll ,player number change.
	/**
	 * player numbers change,duration user enroll.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4006",responsecode="LG4006")
	public static class LG4006Response extends RoomObject{
		int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
		
	}
	
	
	public static class Range{
		int current;
		int total;
		public int getCurrent() {
			return current;
		}
		public void setCurrent(int current) {
			this.current = current;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public static Range obtain(int current,int total){
			Range o=new Range();
			o.current=current;
			o.total=total;
			return o;
		}
		
	}
	
	/**
	 * elimination tournament end event.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4007",responsecode="LG4007")
	public static class LG4007Response extends RoomObject{
		int score;
		Range  teamRank;
		Range  deckRank;
		Range  upgradeRank;
		Range  deckFinished;
		public int getScore() {
			return score;
		}
		public void setScore(int score) {
			this.score = score;
		}
		public Range getTeamRank() {
			return teamRank;
		}
		public void setTeamRank(Range teamRank) {
			this.teamRank = teamRank;
		}
		public Range getDeckRank() {
			return deckRank;
		}
		public void setDeckRank(Range deckRank) {
			this.deckRank = deckRank;
		}
		public Range getUpgradeRank() {
			return upgradeRank;
		}
		public void setUpgradeRank(Range upgradeRank) {
			this.upgradeRank = upgradeRank;
		}
		public Range getDeckFinished() {
			return deckFinished;
		}
		public void setDeckFinished(Range deckFinished) {
			this.deckFinished = deckFinished;
		}
		
		
	}
	
	/**
	 * Elimination round changed event.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4008",responsecode="LG4008")
	public static class LG4008Response extends RoomObject{
		Range stage;
		Range round;
		Range deck;
		
		
		public Range getRound() {
			return round;
		}
		public void setRound(Range round) {
			this.round = round;
		}
		public Range getDeck() {
			return deck;
		}
		public void setDeck(Range deck) {
			this.deck = deck;
		}
		public Range getStage() {
			return stage;
		}
		public void setStage(Range stage) {
			this.stage = stage;
		}
		
	}
	
	/**
	 * Room request the user rank in the tournament.
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GL4009")
	public static class GL4009Request extends RoomObject{
		
	}
	
	public static class UserRank extends PlayerObject{
		int rank;
		int score;
		String nickName;
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		public int getScore() {
			return score;
		}
		public void setScore(int score) {
			this.score = score;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		
	}
	/**
	 * users rank
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4009",responsecode="LG4009")
	public static class LG4009Response extends RoomObject{
		List<UserRank> ranks;

		public List<UserRank> getRanks() {
			return ranks;
		}

		public void setRanks(List<UserRank> ranks) {
			this.ranks = ranks;
		}
		
	}
	/**
	 * reward player.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4010",responsecode="LG4010")
	public static class LG4010Response extends RoomObject{
		String nickName;
		int rank;
		List<PReward> rewards;
		String timestamp;
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public List<PReward> getRewards() {
			return rewards;
		}
		public void setRewards(List<PReward> rewards) {
			this.rewards = rewards;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		
	}
	
	/**
	 * GL4011 -
	 * <p>team states
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4011",responsecode="LG4011")
	public static class LG4011Response extends RoomObject{
		long teamId;
		State state;
		public long getTeamId() {
			return teamId;
		}
		public void setTeamId(long teamId) {
			this.teamId = teamId;
		}
		public State getState() {
			return state;
		}
		public void setState(State state) {
			this.state = state;
		}
		
	}
	
	/**
	 * LG4012 - 
	 * <p>update player rank changes.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4012",responsecode="LG4012")
	public static class LG4012Response extends RoomObject{
		
		Range  teamRank;
		Range  deckFinished;
		public Range getTeamRank() {
			return teamRank;
		}
		public void setTeamRank(Range teamRank) {
			this.teamRank = teamRank;
		}
		public Range getDeckFinished() {
			return deckFinished;
		}
		public void setDeckFinished(Range deckFinished) {
			this.deckFinished = deckFinished;
		}
	}
	
	/**
	 * GL4013 -
	 * 
	 * <p> before the player join the room,the player must check if the player alread enrolled.
	 * 
	 * @author sparrow
	 *@see RoomObject
	 */
	@HandlerRequestType(transcode="GL4013")
	public static class GL4013Request extends RoomObject{
		
	}
	
	/**
	 * LG4013 -
	 * <p> if enrolled={@code true} ,then the user contains current user block states.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4013",responsecode="LG4013")
	public static class LG4013Response {
		// user enroll?
		boolean enrolled;
		// current level.
		int level;
		// current stage integral range
		Range integralRange;
		// current online player of all level?
		Range onlinePlayerRange;
		// current player integral
		int playerIntegral;
		// current level max integral
		int currentLevelMaxIntegral;
		// left time before the level can start.
		long timeLeft;
		
		PReward reward;
		
		int base;
		
		int unReadMessageCount;
		
		boolean enableExchange;
		
		

		public boolean isEnrolled() {
			return enrolled;
		}

		public void setEnrolled(boolean enrolled) {
			this.enrolled = enrolled;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public Range getIntegralRange() {
			return integralRange;
		}

		public void setIntegralRange(Range integralRange) {
			this.integralRange = integralRange;
		}

		public Range getOnlinePlayerRange() {
			return onlinePlayerRange;
		}

		public void setOnlinePlayerRange(Range onlinePlayerRange) {
			this.onlinePlayerRange = onlinePlayerRange;
		}

		public int getPlayerIntegral() {
			return playerIntegral;
		}

		public void setPlayerIntegral(int playerIntegral) {
			this.playerIntegral = playerIntegral;
		}

		public int getCurrentLevelMaxIntegral() {
			return currentLevelMaxIntegral;
		}

		public void setCurrentLevelMaxIntegral(int currentLevelMaxIntegral) {
			this.currentLevelMaxIntegral = currentLevelMaxIntegral;
		}

		public long getTimeLeft() {
			return timeLeft;
		}

		public void setTimeLeft(long timeLeft) {
			this.timeLeft = timeLeft;
		}

		public PReward getReward() {
			return reward;
		}

		public void setReward(PReward reward) {
			this.reward = reward;
		}

		public int getBase() {
			return base;
		}

		public void setBase(int base) {
			this.base = base;
		}

		public int getUnReadMessageCount() {
			return unReadMessageCount;
		}

		public void setUnReadMessageCount(int unReadMessageCount) {
			this.unReadMessageCount = unReadMessageCount;
		}

		public boolean isEnableExchange() {
			return enableExchange;
		}

		public void setEnableExchange(boolean enableExchange) {
			this.enableExchange = enableExchange;
		}
		
		
	}
	
	/**
	 * LG4014 -(elimination tournament.)
	 *  return the current game stage and user integral changes.
	 * 
	 * @author sparrow
	 *
	 */
	public static class LG4014Response {
		Range stage;
		String name;
		int base;
		int integral;
		public Range getStage() {
			return stage;
		}
		public void setStage(Range stage) {
			this.stage = stage;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getBase() {
			return base;
		}
		public void setBase(int base) {
			this.base = base;
		}
		public int getIntegral() {
			return integral;
		}
		public void setIntegral(int integral) {
			this.integral = integral;
		}
	}
	
	public static class BTLevelReward{
		int level;
		PReward reward;
		Range integral;
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public PReward getReward() {
			return reward;
		}
		public void setReward(PReward reward) {
			this.reward = reward;
		}
		public Range getIntegral() {
			return integral;
		}
		public void setIntegral(Range integral) {
			this.integral = integral;
		}
		
		
	}
	
	@HandlerResponseType(transcode="GL4015",responsecode="LG4015")
	public static class LG4015Response {
		
		int rewardIntegral; // win or lose
		BTLevelReward currentLevel;
		BTLevelReward oldLevel;
		BTLevelReward maxLevel;
		int status;
		
		public int getRewardIntegral() {
			return rewardIntegral;
		}
		public void setRewardIntegral(int rewardIntegral) {
			this.rewardIntegral = rewardIntegral;
		}
		public BTLevelReward getCurrentLevel() {
			return currentLevel;
		}
		public void setCurrentLevel(BTLevelReward currentLevel) {
			this.currentLevel = currentLevel;
		}
		
		public BTLevelReward getMaxLevel() {
			return maxLevel;
		}
		public void setMaxLevel(BTLevelReward maxLevel) {
			this.maxLevel = maxLevel;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public BTLevelReward getOldLevel() {
			return oldLevel;
		}
		public void setOldLevel(BTLevelReward oldLevel) {
			this.oldLevel = oldLevel;
		}
		
		
		
		
	}
	
	/**
	 * <p>
	 * return the current blockade match levels.
	 * <p>
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GL4016")
	public static class GL4016Request extends RoomObject{
		
	}
	@HandlerResponseType(transcode="GL4016",responsecode="LG4016")
	public static class LG4016Response {
		List<BTRoomLevelDetail> levels;

		public List<BTRoomLevelDetail> getLevels() {
			return levels;
		}

		public void setLevels(List<BTRoomLevelDetail> levels) {
			this.levels = levels;
		}
		
	}
	
	/**
	 * return the list of users for inviting other players.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GL4017")
	public static class GL4017Request extends RoomObject{
		
	}
	
	@HandlerResponseType(transcode="GL4017",responsecode="LG4017")
	public static class LG4017Response{
		GameUser owner;
		List<GameUser> users;

		public List<GameUser> getUsers() {
			return users;
		}

		public void setUsers(List<GameUser> users) {
			this.users = users;
		}

		public GameUser getOwner() {
			return owner;
		}

		public void setOwner(GameUser owner) {
			this.owner = owner;
		}
	}
	
	
	/**
	 * check if user already enrolled(elimination tournament.)
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GL4018")
	public static class GL4018Request extends RoomObject{
		
	}
	@HandlerResponseType(transcode="GL4018",responsecode="LG4018")
	public static class LG4018Response{
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}
		
	}
	
	
	@HandlerRequestType(transcode="GL4019")
	public static class GL4019Request extends RoomObject{
		
	}
	/**
	 * user enroll response
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4019",responsecode="LG4019")
	public static class LG4019Response extends RoomObject {
		State state;

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}
		
		
	}
	
	@HandlerRequestType(transcode="GL4020")
	public static class GL4020Request extends RoomObject{
		
	}
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL4020",responsecode="LG4020")
	public static class LG4020Response extends RoomObject {
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}
		
		
		
	}
	
	
	
	//
	// protocol for deck
	//
	// 3000 - show all player position.
	// 3001 - show the kitty cards.
	//　

	/**
	 * show the player position of deck.
	 * 
	 * 
	 *
	 */
	/**
	 * show player position of deck.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL3000", responsecode = "LG3000")
	public static class LG3000Response extends DeckObject {
		List<LG1004Response> positions;

		public List<LG1004Response> getPositions() {
			return positions;
		}

		public void setPositions(List<LG1004Response> positions) {
			this.positions = positions;
		}

	}

	/**
	 * LG3001 -
	 * 	resume the deck game.
	 * 
	 * Deck Object
	 * Game Object
	 * Seats Object
	 * Player Object.
	 * 
	 * @author sparrow
	 *
	 */
	public static class LG3001Response {
		LG3001DeckObject deck;
		LG3001GameObject game;
		LG3001GameScoreObject gameScore;
		List<LG3001SeatObject> seats;
		String hand;
		public LG3001DeckObject getDeck() {
			return deck;
		}
		public void setDeck(LG3001DeckObject deck) {
			this.deck = deck;
		}
		public LG3001GameObject getGame() {
			return game;
		}
		public void setGame(LG3001GameObject game) {
			this.game = game;
		}
		public LG3001GameScoreObject getGameScore() {
			return gameScore;
		}
		public void setGameScore(LG3001GameScoreObject gameScore) {
			this.gameScore = gameScore;
		}
		public List<LG3001SeatObject> getSeats() {
			return seats;
		}
		public void setSeats(List<LG3001SeatObject> seats) {
			this.seats = seats;
		}
		public String getHand() {
			return hand;
		}
		public void setHand(String hand) {
			this.hand = hand;
		}
		
		
		
		
		//
	}
	
	public static class LG3001DeckObject{
		long deckId;
		
		State deckState;
		String kittyCards;
		Range gameOfDeck;
		String lastHand;
		public long getDeckId() {
			return deckId;
		}
		public void setDeckId(long deckId) {
			this.deckId = deckId;
		}
		public State getDeckState() {
			return deckState;
		}
		public void setDeckState(State deckState) {
			this.deckState = deckState;
		}
		public String getKittyCards() {
			return kittyCards;
		}
		public void setKittyCards(String kittyCards) {
			this.kittyCards = kittyCards;
		}
		public Range getGameOfDeck() {
			return gameOfDeck;
		}
		public void setGameOfDeck(Range gameOfDeck) {
			this.gameOfDeck = gameOfDeck;
		}
		public String getLastHand() {
			return lastHand;
		}
		public void setLastHand(String lastHand) {
			this.lastHand = lastHand;
		}
		
	}
	
	public static class LG3001GameObject{
		long gameId;
		State gameState;
		public long getGameId() {
			return gameId;
		}
		public void setGameId(long gameId) {
			this.gameId = gameId;
		}
		public State getGameState() {
			return gameState;
		}
		public void setGameState(State gameState) {
			this.gameState = gameState;
		}
		
		
	}
	
	public static class LG3001GameScoreObject{
		int bid;
		int baseScore;
		int bomb;
		int rocket;
		int noPlayerDeal;
		int gameScore;
		
		
		public int getBid() {
			return bid;
		}
		public void setBid(int bid) {
			this.bid = bid;
		}
		public int getBaseScore() {
			return baseScore;
		}
		public void setBaseScore(int baseScore) {
			this.baseScore = baseScore;
		}
		public int getBomb() {
			return bomb;
		}
		public void setBomb(int bomb) {
			this.bomb = bomb;
		}
		public int getRocket() {
			return rocket;
		}
		public void setRocket(int rocket) {
			this.rocket = rocket;
		}
		public int getNoPlayerDeal() {
			return noPlayerDeal;
		}
		public void setNoPlayerDeal(int noPlayerDeal) {
			this.noPlayerDeal = noPlayerDeal;
		}
		public int getGameScore() {
			return gameScore;
		}
		public void setGameScore(int gameScore) {
			this.gameScore = gameScore;
		}
		
	}
	
	public static class LG3001SeatObject {
		GamePlayerObject player;
		int position;
		State seatState;
		String lastHand;
		State positionState;
		State actionState;
		State showHandState;
		int bidScore;
		int cardLeft;
		public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		public State getSeatState() {
			return seatState;
		}
		public void setSeatState(State seatState) {
			this.seatState = seatState;
		}
		public String getLastHand() {
			return lastHand;
		}
		public void setLastHand(String lastHand) {
			this.lastHand = lastHand;
		}
		public State getPositionState() {
			return positionState;
		}
		public void setPositionState(State positionState) {
			this.positionState = positionState;
		}
		public State getActionState() {
			return actionState;
		}
		public void setActionState(State actionState) {
			this.actionState = actionState;
		}
		public State getShowHandState() {
			return showHandState;
		}
		public void setShowHandState(State showHandState) {
			this.showHandState = showHandState;
		}
		public int getBidScore() {
			return bidScore;
		}
		public void setBidScore(int bidScore) {
			this.bidScore = bidScore;
		}
		public GamePlayerObject getPlayer() {
			return player;
		}
		public void setPlayer(GamePlayerObject player) {
			this.player = player;
		}
		public int getCardLeft() {
			return cardLeft;
		}
		public void setCardLeft(int cardLeft) {
			this.cardLeft = cardLeft;
		}
		
	}

	//
	//
	// protocol for the game
	//
	// 2000 - game state.
	// 2001 - game score changed.

	/**
	 * 
	 * show the game state.
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL2000", responsecode = "LG2000")
	public static class LG2000Response extends GameObject {
		State state;

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

	}
	
	
	public static class PlayerScore extends PlayerObject {
		String icon;
		String nickName;
		boolean landLord;
		int kicker;
		
		ValueChange exp;
		ValueChange score;
		ValueChange sort;
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public boolean isLandLord() {
			return landLord;
		}
		public void setLandLord(boolean landLord) {
			this.landLord = landLord;
		}
		public int getKicker() {
			return kicker;
		}
		public void setKicker(int kicker) {
			this.kicker = kicker;
		}
		public ValueChange getExp() {
			return exp;
		}
		public void setExp(ValueChange exp) {
			this.exp = exp;
		}
		public ValueChange getSort() {
			return sort;
		}
		public void setSort(ValueChange sort) {
			this.sort = sort;
		}
		public ValueChange getScore() {
			return score;
		}
		public void setScore(ValueChange score) {
			this.score = score;
		}
		
		
		
	}
	
	public static class ValueChange{
		int old;
		int current;
		int change;
		public int getOld() {
			return old;
		}
		public void setOld(int old) {
			this.old = old;
		}
		public int getCurrent() {
			return current;
		}
		public void setCurrent(int current) {
			this.current = current;
		}
		public int getChange() {
			return change;
		}
		public void setChange(int change) {
			this.change = change;
		}
		
	}
	
	/**
	 * game score mulit changes.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL2001", responsecode = "LG2001")
	public static class LG2001Response extends GameObject{
	
		
		int multi;
		//int base;
		
		
		public int getMulti() {
			return multi;
		}
		public void setMulti(int multi) {
			this.multi = multi;
		}
		
		
		
		
	}
	/**
	 * game score in GameScore stage.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL2002", responsecode = "LG2002")
	public static class LG2002Response extends GameObject{
		int bid;
		int baseScore;
		int bomb;
		int rocket;
		int noPlayerDeal;
		int gameScore;
		
		List<PlayerScore> playerScores;
		
		
		
		
		public int getBaseScore() {
			return baseScore;
		}
		public void setBaseScore(int baseScore) {
			this.baseScore = baseScore;
		}
		public int getBomb() {
			return bomb;
		}
		public void setBomb(int bomb) {
			this.bomb = bomb;
		}
		public int getNoPlayerDeal() {
			return noPlayerDeal;
		}
		public void setNoPlayerDeal(int noPlayerDeal) {
			this.noPlayerDeal = noPlayerDeal;
		}
		
		public int getGameScore() {
			return gameScore;
		}
		public void setGameScore(int gameScore) {
			this.gameScore = gameScore;
		}
		public int getBid() {
			return bid;
		}
		public void setBid(int bid) {
			this.bid = bid;
		}
		public int getRocket() {
			return rocket;
		}
		public void setRocket(int rocket) {
			this.rocket = rocket;
		}
		public List<PlayerScore> getPlayerScores() {
			return playerScores;
		}
		public void setPlayerScores(List<PlayerScore> playerScores) {
			this.playerScores = playerScores;
		}
		
		
	}
	
	/**
	 * show player cards in GameScore stage.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL2003", responsecode = "LG2003")
	public static class LG2003Response extends GameObject {
		List<GL0005Request> hands;

		public List<GL0005Request> getHands() {
			return hands;
		}

		public void setHands(List<GL0005Request> hands) {
			this.hands = hands;
		}
		

	}

	//
	//
	// protocol for the seat.
	//
	// 1000 - seat state.
	// 1001 - seat action state
	// 1002 - seat position state
	// 1003 - seat show hand state
	// 1004 - seat location and player id
	// 1005 - seat show hands.

	/**
	 * 
	 * show the state.
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL1000", responsecode = "LG1000")
	public static class LG1000Response extends DeckSeatObject {

		State state;
		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}
	}
	/**
	 * deck action state.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL1001", responsecode = "LG1001")
	public static class LG1001Response extends DeckSeatObject {

		State state;
	
		
		
		
		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		

	}
	/**
	 * Deck position state
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL1002", responsecode = "LG1002")
	public static class LG1002Response extends DeckSeatObject {

		State state;
		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

	}
	
	/**
	 * deck seat show hand state.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL1003", responsecode = "LG1003")
	public static class LG1003Response extends DeckSeatObject {

		State state;
		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}
	}
	
	
	public static class GamePlayerObject extends PlayerObject{
		String icon;
		String nickName;
		int level;
		String levelName;
		int chips;
		int coins;
		int gold;
		int bestScore;
		int rank;
		int total;
		
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public String getLevelName() {
			return levelName;
		}
		public void setLevelName(String levelName) {
			this.levelName = levelName;
		}
		public int getCoins() {
			return coins;
		}
		public void setCoins(int coins) {
			this.coins = coins;
		}
		public int getGold() {
			return gold;
		}
		public void setGold(int gold) {
			this.gold = gold;
		}
		public int getBestScore() {
			return bestScore;
		}
		public void setBestScore(int bestScore) {
			this.bestScore = bestScore;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public int getChips() {
			return chips;
		}
		public void setChips(int chips) {
			this.chips = chips;
		}
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		
		
		
	}

	/**
	 * show the seat player.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL1004", responsecode = "LG1004")
	public static class LG1004Response extends DeckSeatObject {

		GamePlayerObject player;

		public GamePlayerObject getPlayer() {
			return player;
		}

		public void setPlayer(GamePlayerObject player) {
			this.player = player;
		}

	}

	/**
	 * show the seat out poker hand.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL1005", responsecode = "LG1005")
	public static class LG1005Response extends DeckSeatObject {
		//int position;
		String hex;

		
		public String getHex() {
			return hex;
		}

		public void setHex(String hex) {
			this.hex = hex;
		}

	}
	
	/**
	 * kitty cards
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL1006", responsecode = "LG1006")
	public static class LG1006Response extends DeckSeatObject {
		String hex;

		public String getHex() {
			return hex;
		}

		public void setHex(String hex) {
			this.hex = hex;
		}

	}
	
	
	public static class DeckPlayerObject {
		int position;
		int integral;
		int rank;
		public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		public int getIntegral() {
			return integral;
		}
		public void setIntegral(int integral) {
			this.integral = integral;
		}
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		
	}
	
	@HandlerResponseType(transcode = "GL1007", responsecode = "LG1007")
	public static class LG1007Response  extends DeckObject{
		int base;
		
		int integral;
		int rank;
		
		List<DeckPlayerObject> players;
		public int getBase() {
			return base;
		}
		public void setBase(int base) {
			this.base = base;
		}
		public List<DeckPlayerObject> getPlayers() {
			return players;
		}
		public void setPlayers(List<DeckPlayerObject> players) {
			this.players = players;
		}
		public int getIntegral() {
			return integral;
		}
		public void setIntegral(int integral) {
			this.integral = integral;
		}
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		
	}

	//
	// protocol of player.
	//
	//
	// 0001 - player bid.
	// 0002 - server broadcast bid message.
	// 0003 - player kick
	// 0004 - send cards to player
	// 0005 - player out hands.
	// 0006 - server broadcast the player hands.
	// 0007 - poker numbers.
	// 0008 - player pull
	// 0009 - player offline

	/**
	 * player offer a bid.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GL0001")
	public static class GL0001Request extends PlayerObject{
	
		int bid;

		public int getBid() {
			return bid;
		}

		public void setBid(int bid) {
			this.bid = bid;
		}
	}

	/**
	 * bid message
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL0002", responsecode = "LG0002")
	public static class LG0002Response extends DeckSeatObject {
		
		int bid;
		
		public int getBid() {
			return bid;
		}

		public void setBid(int bid) {
			this.bid = bid;
		}

	}

	/**
	 * player kick
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GL0003")
	public static class GL0003Request extends PlayerObject {
		boolean kick;
		

		public boolean isKick() {
			return kick;
		}

		public void setKick(boolean kick) {
			this.kick = kick;
		}

		
		

	}

	/**
	 * 
	 * give the player cards.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL0004", responsecode = "LG0004")
	public static class LG0004Response extends PlayerObject {

		String hex;

		public String getHex() {
			return hex;
		}

		public void setHex(String hex) {
			this.hex = hex;
		}

	}

	/**
	 * player show handk
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GL0005")
	public static class GL0005Request extends PlayerObject {
		String hex;
		

		public String getHex() {
			return hex;
		}

		public void setHex(String hex) {
			this.hex = hex;
		}

		

		
	}

	/**
	 * broadcast player message.
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GL0006", responsecode = "LG0006")
	public static class LG0006Response extends PlayerObject{
		
		String hex;
		int remains;
		
		


		public String getHex() {
			return hex;
		}

		public void setHex(String hex) {
			this.hex = hex;
		}

		public int getRemains() {
			return remains;
		}

		public void setRemains(int remains) {
			this.remains = remains;
		}

	}
	
	@Deprecated
	@HandlerResponseType(transcode = "GL0007", responsecode = "LG0007")
	public static class PokerObject extends PlayerObject{
		int numberOfLeft;

		public int getNumberOfLeft() {
			return numberOfLeft;
		}

		public void setNumberOfLeft(int numberOfLeft) {
			this.numberOfLeft = numberOfLeft;
		}
		
	}
	
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GL0008")
	public static class GL0008Request extends PlayerObject {
		boolean pull;

		public boolean isPull() {
			return pull;
		}

		public void setPull(boolean pull) {
			this.pull = pull;
		}
		

		
		
		

	}
	
	
	public static final int OFFLINE=-1;
	public static final int ONLINE=1;
	
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL0009",responsecode="LG0009")
	public static class LG0009Response extends PlayerObject{
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
	}
	
	/**
	 * token invalid
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="LG0010")
	public static class LG0010Response{
	
		
	}
	
	/**
	 * GL0011 - 
	 * request the current player state. using for offline/online switch
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GL0011")
	public static class GL0011Request extends PlayerObject{
		Long roomId;

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}
		
		
	}
	
	public static final int TEAM_STATE_AWAITING=0;
	public static final int TEAM_STATE_BETWEEN_DECKS=1;
	public static final int TEAM_STATE_BETWEEN_STAGE=2;
	
	public static class LG0011TeamObject{

		int score;
		Range  teamRank;
		Range  deckRank;
		Range  upgradeRank;

		 List<PReward>  rewards;
		
		

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public Range getTeamRank() {
			return teamRank;
		}

		public void setTeamRank(Range teamRank) {
			this.teamRank = teamRank;
		}

		public Range getDeckRank() {
			return deckRank;
		}

		public void setDeckRank(Range deckRank) {
			this.deckRank = deckRank;
		}

		public Range getUpgradeRank() {
			return upgradeRank;
		}

		public void setUpgradeRank(Range upgradeRank) {
			this.upgradeRank = upgradeRank;
		}

		public List<PReward> getRewards() {
			return rewards;
		}

		public void setRewards(List<PReward> rewards) {
			this.rewards = rewards;
		}
		
	}
	
	@HandlerResponseType(transcode="GL0011",responsecode="LG0011")
	public static class LG0011Response {
		State state;
		LG3001Response data;
		int teamState;
		
		int roomId;
		String roomName;
		int roomType;
		LG0011TeamObject teamData;
		
		
		
		
		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public LG3001Response getData() {
			return data;
		}

		public void setData(LG3001Response data) {
			this.data = data;
		}

		public int getTeamState() {
			return teamState;
		}

		public void setTeamState(int teamState) {
			this.teamState = teamState;
		}

		public int getRoomId() {
			return roomId;
		}

		public void setRoomId(int roomId) {
			this.roomId = roomId;
		}

		public String getRoomName() {
			return roomName;
		}

		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}

		public int getRoomType() {
			return roomType;
		}

		public void setRoomType(int roomType) {
			this.roomType = roomType;
		}

		public LG0011TeamObject getTeamData() {
			return teamData;
		}

		public void setTeamData(LG0011TeamObject teamData) {
			this.teamData = teamData;
		}

	}
	
	/**
	 * client auto
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GL0012")
	public static class GL0012Request extends PlayerObject{
		boolean enable;

		public boolean isEnable() {
			return enable;
		}

		public void setEnable(boolean enable) {
			this.enable = enable;
		}
		
	}
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode="GL0012",responsecode="LG0012")
	public static class LG0012Response extends DeckSeatObject{
		State state;

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}
		
	}
	
	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GL0013")
	public static class GL0013Request extends PlayerObject{
		String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		
	}
	
	@HandlerRequestType(transcode="GL0014")
	public static class GL0014Request extends RoomObject{
	}
	
	@HandlerResponseType(transcode="GL0014",responsecode="LG0014")
	public static class LG0014Response extends PlayerObject{
		boolean isEnrolled;
		long timeRemains;
		String scheduledTime;
		long roomId;
		int numbers;
		
		public boolean isEnrolled() {
			return isEnrolled;
		}
		public void setEnrolled(boolean isEnrolled) {
			this.isEnrolled = isEnrolled;
		}
		public long getTimeRemains() {
			return timeRemains;
		}
		public void setTimeRemains(long timeRemains) {
			this.timeRemains = timeRemains;
		}
		public String getScheduledTime() {
			return scheduledTime;
		}
		public void setScheduledTime(String scheduledTime) {
			this.scheduledTime = scheduledTime;
		}
		public long getRoomId() {
			return roomId;
		}
		public void setRoomId(long roomId) {
			this.roomId = roomId;
		}
		public int getNumbers() {
			return numbers;
		}
		public void setNumbers(int numbers) {
			this.numbers = numbers;
		}
		
	}
	
	
	
	//
	//
	// protocol for the room.
	//

}
