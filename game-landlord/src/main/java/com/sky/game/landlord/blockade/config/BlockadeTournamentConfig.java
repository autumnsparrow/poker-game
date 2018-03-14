/**
 * @sparrow
 * @Dec 27, 2014   @4:49:31 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.lock.IObtain;
import com.sky.game.context.lock.VolatileLocking;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.blockade.config.item.EnrollTicket;
import com.sky.game.landlord.blockade.config.item.Reward;
import com.sky.game.landlord.blockade.config.item.TimeWindow;
import com.sky.game.landlord.room.IDeckConfig;
import com.sky.game.landlord.room.DeckConfiguration;

/**
 * @author sparrow
 *
 */
public class BlockadeTournamentConfig implements IIdentifiedObject, IDeckConfig {

	String roomId;
	String roomName;
	String tournamentDescription;

	TimePattern timeController;
	EnrollRestrict enrollRestrict;
	List<Stage> stages;
	List<EnrollTicket> enrollTickets;
	int initIntegral;
	int maxIntegral;

	boolean enableRobot;
	
	int gamePerDeck;
	
	DeckConfiguration deckConfig;
	
	Long id;

	@JsonIgnore
	VolatileLocking<Map<Integer, EnrollTicket>> ticketsMap;
	@JsonIgnore
	VolatileLocking<Map<Integer, Stage>> integralStageMap;
	@JsonIgnore
	VolatileLocking<Map<Integer, Stage>> levelStageMap;

	public static BlockadeTournamentConfig obtain() {
		BlockadeTournamentConfig o = new BlockadeTournamentConfig();
		o.roomId = " blocked room id";
		o.roomName = "blocked room name";
		o.tournamentDescription = "blocked description";
		o.timeController = TimePattern.obtain();
		o.enrollRestrict = EnrollRestrict.obtain();
		o.enrollTickets = GameUtil.getList();
		for (int i = 0; i < 2; i++) {
			o.enrollTickets.add(EnrollTicket.obtain(i, (i + 1) * 2000));
		}

		o.stages = GameUtil.getList();
		for (int i = 0; i < 5; i++) {
			o.stages.add(Stage.obtain(i + 1));
		}

		return o;

	}

	/**
	 * 
	 * Time pattern
	 * 
	 * @author sparrow
	 *
	 */
	public static class TimePattern {
		TimeWindow clientShow;
		TimeWindow enroll;

		public static TimePattern obtain() {
			TimePattern o = new TimePattern();
			o.clientShow = TimeWindow.obtain("client show", "client dissapear");
			o.enroll = TimeWindow.obtain("enroll start", "enroll end");
			return o;
		}

		public TimeWindow getClientShow() {
			return clientShow;
		}

		public void setClientShow(TimeWindow clientShow) {
			this.clientShow = clientShow;
		}

		public TimeWindow getEnroll() {
			return enroll;
		}

		public void setEnroll(TimeWindow enroll) {
			this.enroll = enroll;
		}

		public TimePattern() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	public static class EnrollRestrict {
		int enrollTimesPerDay;
		boolean differentLevelPK;

		public static EnrollRestrict obtain() {
			EnrollRestrict o = new EnrollRestrict();
			o.enrollTimesPerDay = 10;
			o.differentLevelPK = true;
			return o;
		}

		public int getEnrollTimesPerDay() {
			return enrollTimesPerDay;
		}

		public void setEnrollTimesPerDay(int enrollTimesPerDay) {
			this.enrollTimesPerDay = enrollTimesPerDay;
		}

		public boolean isDifferentLevelPK() {
			return differentLevelPK;
		}

		public void setDifferentLevelPK(boolean differentLevelPK) {
			this.differentLevelPK = differentLevelPK;
		}

		public EnrollRestrict() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	public static class Stage {
		int id;
		int base;
		int minIntegeral;
		@JsonIgnore
		int maxIntegeral;
		TimeWindow match;
		Reward reward;
		boolean enableExchange;
		
		int aiLevel;

		public static Stage obtain(int id) {
			Stage o = new Stage();
			o.match = TimeWindow.obtain("match start", "match end");
			o.minIntegeral = 1000;
			o.reward = Reward.obtain("0", 0, 100);
			o.id = id;
			o.base = 1000;
			return o;
		}

		public Stage() {
			super();
			// TODO Auto-generated constructor stub
		}

		public int getMinIntegeral() {
			return minIntegeral;
		}

		public void setMinIntegeral(int minIntegeral) {
			this.minIntegeral = minIntegeral;
		}

		public TimeWindow getMatch() {
			return match;
		}

		public void setMatch(TimeWindow match) {
			this.match = match;
		}

		public Reward getReward() {
			return reward;
		}

		public void setReward(Reward reward) {
			this.reward = reward;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getBase() {
			return base;
		}

		public void setBase(int base) {
			this.base = base;
		}

		public boolean isEnableExchange() {
			return enableExchange;
		}

		public void setEnableExchange(boolean enableExchange) {
			this.enableExchange = enableExchange;
		}

		public int getMaxIntegeral() {
			return maxIntegeral;
		}

		public void setMaxIntegeral(int maxIntegeral) {
			this.maxIntegeral = maxIntegeral;
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
	public BlockadeTournamentConfig() {
		// TODO Auto-generated constructor stub
		//this.integralStageMap=GameUtil.obtain(clz)
		this.integralStageMap=VolatileLocking.obtain();
		this.levelStageMap=VolatileLocking.obtain();
		this.ticketsMap=VolatileLocking.obtain();
	}
	
	
	public void init(){
		getLevelStageMap() ;
		getIntegralStageMap();
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

	public TimePattern getTimeController() {
		return timeController;
	}

	public void setTimeController(TimePattern timeController) {
		this.timeController = timeController;
	}

	public EnrollRestrict getEnrollRestrict() {
		return enrollRestrict;
	}

	public void setEnrollRestrict(EnrollRestrict enrollRestrict) {
		this.enrollRestrict = enrollRestrict;
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.context.annotation.introspector.IIdentifiedObject#getId()
	 */
	public Long getId() {
		// TODO Auto-generated method stub
		Long id = Long.parseLong(roomId);
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

	/*
	 * (non-Javadoc)
	 * 
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
	 * get the enroll ticket by the itemId
	 * 
	 * @return
	 */
	public Map<Integer, EnrollTicket> getTicketsMap() {
		
		return this.ticketsMap.getHelper(new IObtain<Map<Integer,EnrollTicket>,List<EnrollTicket>>() {
			
			public Map<Integer,EnrollTicket> obtain(List<EnrollTicket> tickets) {
				// TODO Auto-generated method stub
				
				Map<Integer,EnrollTicket>	map = GameUtil.getMap();
				for (EnrollTicket t : tickets) {
					map.put(t.getItemId(), t);
				}
			
				return map;
			}
		},  this.enrollTickets);
		
			
	}

	
	

	/**
	 * <p>
	 * the key is the integral.
	 * 
	 * @return
	 */
	public Map<Integer, Stage> getIntegralStageMap() {
		
		return this.integralStageMap.getHelper(new IObtain<Map<Integer,Stage>, List<Stage>>() {

			public Map<Integer, Stage> obtain(List<Stage> a) {
				// TODO Auto-generated method stub
				Map<Integer, Stage> map= GameUtil.getMap();
				for (Stage s : stages) {
					map.put(
							Integer.valueOf(s.getMinIntegeral()), s);
				}
				return map;
			}
		}, stages);
		
	}

	public Map<Integer, Stage> getLevelStageMap() {
	
		return this.levelStageMap.getHelper(new IObtain<Map<Integer,Stage>,List<Stage>>(){

			public Map<Integer, Stage> obtain(List<Stage> a) {
				// TODO Auto-generated method stub
				Map<Integer,Stage> m = GameUtil.getMap();
				for (Stage s : stages) {
					m.put(Integer.valueOf(s.getId()), s);
				}
				
				
				Integer[] levels=m.keySet().toArray(new Integer[]{});
				Arrays.sort(levels);
				for(int i=0;i<levels.length-1;i++){
					Stage s=m.get(levels[i]);
					Stage n=m.get(levels[i+1]);
					s.setMaxIntegeral(n.getMinIntegeral()-1);
				}
				Stage max=m.get(levels[levels.length-1]);
				max.setMaxIntegeral(getMaxIntegral());
				return m;
			}
			
		}, stages);
		
	}

	public void setDeckConfig(DeckConfiguration deckConfig) {
		this.deckConfig = deckConfig;
	}

	public int getMaxIntegral() {
		return maxIntegral;
	}

	public void setMaxIntegral(int maxIntegral) {
		this.maxIntegral = maxIntegral;
	}


	public int getGamePerDeck() {
		return gamePerDeck;
	}


	public void setGamePerDeck(int gamePerDeck) {
		this.gamePerDeck = gamePerDeck;
	}

}
