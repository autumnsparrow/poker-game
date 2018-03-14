/**
 * @sparrow
 * @Dec 27, 2014   @4:15:28 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config;


import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.landlord.blockade.config.item.EnrollTicket;
import com.sky.game.landlord.blockade.config.item.Reward;
import com.sky.game.landlord.room.IDeckConfig;
import com.sky.game.landlord.room.DeckConfiguration;

/**
 * @author sparrow
 *
 */
public class FreeTournamentConfig  implements IIdentifiedObject ,IDeckConfig{
	
	
	String roomId; // room id
	String roomName; // room name
	int base; // base score 
	boolean cheat; // if cheat
	boolean chat; // if enable player chat.
	boolean showNickname; // if client show nick name;
	int  fee; // platform fee
	String activeTimePattern; // active time
	boolean enableRobot;// enable the robot.
	
	EnrollRestrict enrollRestrict;
	EnrollTicket enrollTicket;
	Reward reward;
	
	DeckConfiguration  deckConfig;
	
	int aiLevel;
	
	Long id;
	
	public static class EnrollRestrict{
		int minCoins;
		int maxCoins;
		public EnrollRestrict(int minCoins, int maxCoins) {
			super();
			this.minCoins = minCoins;
			this.maxCoins = maxCoins;
		}
		public EnrollRestrict() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getMinCoins() {
			return minCoins;
		}
		public void setMinCoins(int minCoins) {
			this.minCoins = minCoins;
		}
		public int getMaxCoins() {
			return maxCoins;
		}
		public void setMaxCoins(int maxCoins) {
			this.maxCoins = maxCoins;
		}
		
		
		
	}
	

	/**
	 * 
	 */
	public FreeTournamentConfig() {
		// TODO Auto-generated constructor stub
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}

	public boolean isCheat() {
		return cheat;
	}

	public void setCheat(boolean cheat) {
		this.cheat = cheat;
	}

	public boolean isChat() {
		return chat;
	}

	public void setChat(boolean chat) {
		this.chat = chat;
	}

	public boolean isShowNickname() {
		return showNickname;
	}

	public void setShowNickname(boolean showNickname) {
		this.showNickname = showNickname;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getActiveTimePattern() {
		return activeTimePattern;
	}

	public void setActiveTimePattern(String activeTimePattern) {
		this.activeTimePattern = activeTimePattern;
	}
	
	
	
	public static FreeTournamentConfig obtain(){
		FreeTournamentConfig o=new FreeTournamentConfig();
		o.setRoomId("2000");
		o.setRoomName("free tournament room name");
		o.setBase(10);
		o.setChat(false);
		o.setCheat(false);
		o.setShowNickname(false);
		o.setActiveTimePattern(" active time pattern ,see spring cron expression");
		o.setEnableRobot(false);
		o.setEnrollTicket(EnrollTicket.obtain(0, 1));
		o.setReward(Reward.obtain("1",0, 1));
		
		o.deckConfig=new DeckConfiguration();
		o.enrollRestrict=new EnrollRestrict();
		o.enrollRestrict.minCoins=5;
		o.enrollRestrict.maxCoins=1000;
//		
		
		return o;
	}

	public boolean isEnableRobot() {
		return enableRobot;
	}

	public void setEnableRobot(boolean enableRobot) {
		this.enableRobot = enableRobot;
	}

	public EnrollTicket getEnrollTicket() {
		return enrollTicket;
	}

	public void setEnrollTicket(EnrollTicket enrollTicket) {
		this.enrollTicket = enrollTicket;
	}

	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public EnrollRestrict getEnrollRestrict() {
		return enrollRestrict;
	}

	public void setEnrollRestrict(EnrollRestrict enrollRestrict) {
		this.enrollRestrict = enrollRestrict;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.annotation.introspector.IIdentifiedObject#getId()
	 */
	public Long getId() {
		// TODO Auto-generated method stub
		id=Long.parseLong(roomId);
		return id;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.annotation.introspector.IIdentifiedObject#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IDeckConfig#getDeckConfig()
	 */
	public DeckConfiguration getDeckConfig() {
		// TODO Auto-generated method stub

		return deckConfig;
	}

	public void setDeckConfig(DeckConfiguration deckConfig) {
		this.deckConfig = deckConfig;
	}

	public int getAiLevel() {
		return aiLevel;
	}

	public void setAiLevel(int aiLevel) {
		this.aiLevel = aiLevel;
	}

	

	
	
	
	

	
}
