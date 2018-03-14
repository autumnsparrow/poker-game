/**
 * @sparrow
 * @Dec 27, 2014   @11:41:30 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.lock.IObtain;
import com.sky.game.context.lock.VolatileLocking;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.blockade.config.item.EnrollTicket;
import com.sky.game.landlord.blockade.config.item.PlayerNumberController;
import com.sky.game.landlord.blockade.config.item.Reward;
import com.sky.game.landlord.blockade.config.item.TimePattern;
import com.sky.game.landlord.blockade.config.item.TimeWindow;
import com.sky.game.landlord.room.IDeckConfig;
import com.sky.game.landlord.room.DeckConfiguration;

/**
 * @author sparrow
 *
 */
public class EliminationTournamentConfig implements IIdentifiedObject,IDeckConfig{
	
	
	/**
	 * 
	 */
	
	String roomId;  // room Id
	String roomName; // room name
	String tournamentDescription; // tournament description.
	int taskId;
	boolean enableRobot;
	int tournamentType;
	Long id;
	
	Long minOnlinePlayer;
	
	
	
	TimePattern timePattern;
	PlayerNumberController playerNumberControl;
	AStage aStage;
	BStage bStage;
	CStage cStage;
	
	List<EnrollTicket> enrollTickets;
	int initIntegral;
	List<Reward> rewards;
	
	DeckConfiguration deckConfig;
	
	@JsonIgnore
	VolatileLocking<Map<Integer,EnrollTicket>> ticketsMap;
	@JsonIgnore
	VolatileLocking<Map<String,List<Reward>>> rewardsMap;
	
	
	
	
	public static EliminationTournamentConfig obtain(){
		EliminationTournamentConfig o=new EliminationTournamentConfig();
		o.setRoomId("room id");
		o.setRoomName("room name ");
		o.setTournamentDescription(" room description.");
		o.setTimePattern(
				TimePattern.obtain(
						TimeWindow.obtain("client show", "client disappear"), 
						TimeWindow.obtain("enroll start", "enroll end"),
						TimeWindow.obtain("match start", "match end")));
		
		
	
		o.enrollTickets=GameUtil.getList();
		for(int i=0;i<2;i++){
			o.enrollTickets.add(EnrollTicket.obtain(i, (i+1)*2000));
		}
		
		
		o.rewards=GameUtil.getList();
		for(int i=0;i<2;i++){
			o.rewards.add(Reward.obtain("ranking", i, 1000));
		}
		
		o.setPlayerNumberControl(PlayerNumberController.obtain());
		o.setaStage(AStage.obtain());
		o.setbStage(BStage.obtain());
		o.setcStage(CStage.obtain());
		return o;
	}
	
	
	
	
	public static class AStage{
		String name;
		int base; // game base score
		long baseIncreaseTimeInterval; // game base score increase time interval
		int baseIncreaseAmount; // game base score increase amount.
		int eliminationScore; // user elimination score.
		int eliminationByScoreStoppedPlayerNumbers; // elimination stoped before the fixed player number left.
		int upgradedPlayerNumbers;// upgatde player numbers.
		int gamePerDeck;// game perdeck
		
		int aiLevel;
		
		
		
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
		public long getBaseIncreaseTimeInterval() {
			return baseIncreaseTimeInterval;
		}
		public void setBaseIncreaseTimeInterval(long baseIncreaseTimeInterval) {
			this.baseIncreaseTimeInterval = baseIncreaseTimeInterval;
		}
		public int getBaseIncreaseAmount() {
			return baseIncreaseAmount;
		}
		public void setBaseIncreaseAmount(int baseIncreaseAmount) {
			this.baseIncreaseAmount = baseIncreaseAmount;
		}
		public int getEliminationScore() {
			return eliminationScore;
		}
		public void setEliminationScore(int eliminationScore) {
			this.eliminationScore = eliminationScore;
		}
		public int getEliminationByScoreStoppedPlayerNumbers() {
			return eliminationByScoreStoppedPlayerNumbers;
		}
		public void setEliminationByScoreStoppedPlayerNumbers(
				int eliminationByScoreStoppedPlayerNumbers) {
			this.eliminationByScoreStoppedPlayerNumbers = eliminationByScoreStoppedPlayerNumbers;
		}
		public int getUpgradedPlayerNumbers() {
			return upgradedPlayerNumbers;
		}
		public void setUpgradedPlayerNumbers(int upgradedPlayerNumbers) {
			this.upgradedPlayerNumbers = upgradedPlayerNumbers;
		}
		public AStage(String name, int base, long baseIncreaseTimeInterval,
				int baseIncreaseAmount, int eliminationScore,
				int eliminationByScoreStoppedPlayerNumbers,
				int upgradedPlayerNumbers) {
			super();
			this.name = name;
			this.base = base;
			this.baseIncreaseTimeInterval = baseIncreaseTimeInterval;
			this.baseIncreaseAmount = baseIncreaseAmount;
			this.eliminationScore = eliminationScore;
			this.eliminationByScoreStoppedPlayerNumbers = eliminationByScoreStoppedPlayerNumbers;
			this.upgradedPlayerNumbers = upgradedPlayerNumbers;
		}
		
		public static AStage obtain(){
			return new AStage("primary stage", 1000, 5, 5, 800, 20, 16);
		}
		public AStage() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getGamePerDeck() {
			return gamePerDeck;
		}
		public void setGamePerDeck(int gamePerDeck) {
			this.gamePerDeck = gamePerDeck;
		}
		public int getAiLevel() {
			return aiLevel;
		}
		public void setAiLevel(int aiLevel) {
			this.aiLevel = aiLevel;
		}
		
		
	}
	
	public static class BStage{
		int aStageScorePercent; // bStage.player.score=aStageScorePercent*0.01*aStage.player.score
		int maxScoreFromAStage; // bStage.player.score <= maxScoreFromAStage.
		int base; // game base score
		int integral;
		int gameRoundPerDeck; // game round in a deck
		int rank2UpradePlayerNumbers;// each deck the ranking 2 upgrade player numbers.
		String name;
		int aiLevel;
		
		
		public static BStage obtain(){
			BStage o=new BStage();
			o.aStageScorePercent=15;
			o.maxScoreFromAStage=800;
			o.base=100;
			o.gameRoundPerDeck=3;
			o.rank2UpradePlayerNumbers=3;
			return o;
			
		}
		public int getaStageScorePercent() {
			return aStageScorePercent;
		}
		public void setaStageScorePercent(int aStageScorePercent) {
			this.aStageScorePercent = aStageScorePercent;
		}
		public int getMaxScoreFromAStage() {
			return maxScoreFromAStage;
		}
		public void setMaxScoreFromAStage(int maxScoreFromAStage) {
			this.maxScoreFromAStage = maxScoreFromAStage;
		}
		public int getBase() {
			return base;
		}
		public void setBase(int base) {
			this.base = base;
		}
		public int getGameRoundPerDeck() {
			return gameRoundPerDeck;
		}
		public void setGameRoundPerDeck(int gameRoundPerDeck) {
			this.gameRoundPerDeck = gameRoundPerDeck;
		}
		public int getRank2UpradePlayerNumbers() {
			return rank2UpradePlayerNumbers;
		}
		public void setRank2UpradePlayerNumbers(int rank2UpradePlayerNumbers) {
			this.rank2UpradePlayerNumbers = rank2UpradePlayerNumbers;
		}
		public BStage() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getIntegral() {
			return integral;
		}
		public void setIntegral(int integral) {
			this.integral = integral;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAiLevel() {
			return aiLevel;
		}
		public void setAiLevel(int aiLevel) {
			this.aiLevel = aiLevel;
		}
		
		
		
	}
	
	public static class CStage{
		
		int bStageScorePercent; //cStage.player.score=bStageScorePercent*0.01*bStage.player.score
		int maxScoreFromBStage; // 
		int base;//game score
		int integral;
		int gamePerRound;// game round
		int gamePerDeck;// game per deck
		int eliminationPerRoundPlayerNumbers;//
		String name;
		int aiLevel;
		
		
		public static CStage obtain(){
			CStage o=new CStage();
			o.bStageScorePercent=10;
			o.maxScoreFromBStage=200;
			o.base=10;
			o.gamePerRound=2;
			o.gamePerDeck=3;
			o.eliminationPerRoundPlayerNumbers=1;
			return o;
		}
		
		
		public int getbStageScorePercent() {
			return bStageScorePercent;
		}
		public void setbStageScorePercent(int bStageScorePercent) {
			this.bStageScorePercent = bStageScorePercent;
		}
		public int getMaxScoreFromBStage() {
			return maxScoreFromBStage;
		}
		public void setMaxScoreFromBStage(int maxScoreFromBStage) {
			this.maxScoreFromBStage = maxScoreFromBStage;
		}
		public int getBase() {
			return base;
		}
		public void setBase(int base) {
			this.base = base;
		}
		public int getGamePerRound() {
			return gamePerRound;
		}
		public void setGamePerRound(int gamePerRound) {
			this.gamePerRound = gamePerRound;
		}
		public int getEliminationPerRoundPlayerNumbers() {
			return eliminationPerRoundPlayerNumbers;
		}
		public void setEliminationPerRoundPlayerNumbers(
				int eliminationPerRoundPlayerNumbers) {
			this.eliminationPerRoundPlayerNumbers = eliminationPerRoundPlayerNumbers;
		}
		public CStage() {
			super();
			// TODO Auto-generated constructor stub
		}


		public int getGamePerDeck() {
			return gamePerDeck;
		}


		public void setGamePerDeck(int gamePerDeck) {
			this.gamePerDeck = gamePerDeck;
		}


		public int getIntegral() {
			return integral;
		}


		public void setIntegral(int integral) {
			this.integral = integral;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public int getAiLevel() {
			return aiLevel;
		}


		public void setAiLevel(int aiLevel) {
			this.aiLevel = aiLevel;
		}
		
		
		
		
		
	}
	
	
	
	
	

	/**
	 * 
	 */
	public EliminationTournamentConfig() {
		// TODO Auto-generated constructor stub
		ticketsMap=VolatileLocking.obtain();
		rewardsMap=VolatileLocking.obtain();
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






	public String getTournamentDescription() {
		return tournamentDescription;
	}






	public void setTournamentDescription(String tournamentDescription) {
		this.tournamentDescription = tournamentDescription;
	}






	public TimePattern getTimePattern() {
		return timePattern;
	}






	public void setTimePattern(TimePattern timePattern) {
		this.timePattern = timePattern;
	}






	public AStage getaStage() {
		return aStage;
	}






	public void setaStage(AStage aStage) {
		this.aStage = aStage;
	}






	public BStage getbStage() {
		return bStage;
	}






	public void setbStage(BStage bStage) {
		this.bStage = bStage;
	}






	public CStage getcStage() {
		return cStage;
	}






	public void setcStage(CStage cStage) {
		this.cStage = cStage;
	}






	





	




	public PlayerNumberController getPlayerNumberControl() {
		return playerNumberControl;
	}






	public void setPlayerNumberControl(PlayerNumberController playerNumberControl) {
		this.playerNumberControl = playerNumberControl;
	}






	public List<EnrollTicket> getEnrollTickets() {
		return enrollTickets;
	}






	public void setEnrollTickets(List<EnrollTicket> enrollTickets) {
		this.enrollTickets = enrollTickets;
	}






	public int getInitIntegral() {
		return initIntegral;
	}






	public void setInitIntegral(int initIntegral) {
		this.initIntegral = initIntegral;
	}






	public List<Reward> getRewards() {
		return rewards;
	}






	public void setRewards(List<Reward> rewards) {
		this.rewards = rewards;
	}






	/* (non-Javadoc)
	 * @see com.sky.game.context.annotation.introspector.IIdentifiedObject#getId()
	 */
	public Long getId() {
		// TODO Auto-generated method stub
		Long id=Long.parseLong(roomId);
		return id;
	}






	/* (non-Javadoc)
	 * @see com.sky.game.context.annotation.introspector.IIdentifiedObject#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}





	
	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IDeckConfig#getDeckConfig()
	 */
	public DeckConfiguration getDeckConfig() {
		// TODO Auto-generated method stub
		return deckConfig;//new DeckConfiguration();
	}






	public boolean isEnableRobot() {
		return enableRobot;
	}






	public void setEnableRobot(boolean enableRobot) {
		this.enableRobot = enableRobot;
	}




	
	/**
	 * map the enroll ticket itemId and {@link EnrollTicket}
	 * @return
	 * @see VolatileLocking
	 */
	public Map<Integer, EnrollTicket> getTicketsMap() {
		
		return this.ticketsMap.getHelper(new IObtain<Map<Integer,EnrollTicket>, List<EnrollTicket>>() {

			public Map<Integer, EnrollTicket> obtain(List<EnrollTicket> a) {
				// TODO Auto-generated method stub
				Map<Integer,EnrollTicket> m=GameUtil.getMap();
				for(EnrollTicket t:a){
					m.put(t.getItemId(), t);
				}
				return m;
			}
		}, enrollTickets);
		
	}






	public Map<String,List< Reward>> getRewardsMap() {
		return this.rewardsMap.getHelper(new IObtain<Map<String,List<Reward>>, List<Reward>>() {

			public Map<String, List<Reward>> obtain(List<Reward> a) {
				// TODO Auto-generated method stub
				Map<String, List<Reward>> m=GameUtil.getMap();
				for(Reward r:a){
					List<Reward> rewards=m.get(r.getRanking());
					if(rewards==null){
						rewards=GameUtil.getList();
						m.put(r.getRanking(), rewards);
					}
					rewards.add(r);
				}
				return m;
			}
		}, this.rewards);
		
	}






	public int getTaskId() {
		return taskId;
	}






	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}






	public void setDeckConfig(DeckConfiguration deckConfig) {
		this.deckConfig = deckConfig;
	}






	public int getTournamentType() {
		return tournamentType;
	}






	public void setTournamentType(int tournamentType) {
		this.tournamentType = tournamentType;
	}






	public Long getMinOnlinePlayer() {
		return minOnlinePlayer;
	}






	public void setMinOnlinePlayer(Long minOnlinePlayer) {
		this.minOnlinePlayer = minOnlinePlayer;
	}






}
