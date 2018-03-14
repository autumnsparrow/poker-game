/**
 * @sparrow
 * @Dec 11, 2014   @4:24:43 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.configuration.GameContxtConfigurationLoader;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.achievement.AchievementObserver;
import com.sky.game.landlord.blockade.config.LLGameTournamentConfig;
import com.sky.game.landlord.room.LLGameChannel;
import com.sky.game.landlord.room.LLGameObjectMap;
import com.sky.game.landlord.room.LLGamePlayerStateObserver;

/**
 * @author sparrow
 *
 */
public class LLGameGlobalContext {
	
	String configuration;

	/**
	 * 
	 */
	public LLGameGlobalContext() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * @param configuration
	 */
	@Autowired
	public LLGameGlobalContext(@Value("${game.landlord}")String configuration) {
		super();
		this.configuration = configuration;
	}



	private static final Log logger=LogFactory.getLog(LLGameGlobalContext.class);
	public static final LLGameObjectMap<LLGameChannel> channels=LLGameObjectMap.getMap();

	private static final ConcurrentHashMap<Long, IIdentifiedObject> rooms=new ConcurrentHashMap<Long, IIdentifiedObject>();
	
	private static final int max_room=10;
	
	// create the achievement observer.
	private static final AchievementObserver ACHIEVEMENT_OBSERVER=new AchievementObserver();
	private static final LLGamePlayerStateObserver NETWORK_STATE_OBSERVER=new LLGamePlayerStateObserver();
	
	public static void reInit(int id )throws Exception{
		InputStream url=LLGameApp.class.getResourceAsStream("/META-INF/game-landlord.conf");
		LLGameAppConfig config=GameContxtConfigurationLoader.loadConfiguration(url, LLGameAppConfig.class);
		String tournamentConfig=config.getTournamentConfig();
		LLGameChannel oldChannel=channels.get(Long.valueOf(id));
		oldChannel.destory();
		LLGameTournamentConfig  gameTournamentConfig=LLGameTournamentConfig.load(tournamentConfig);
		LLGameChannel channel=new LLGameChannel(Long.valueOf(id), gameTournamentConfig);
		
		channels.clear();
		channels.put(channel.getId(), channel);
	}
	
	public  static  void init() throws Exception{
		 InputStream in=GameUtil.getInputStream("/META-INF/game-landlord.conf");
		// should load the configurations.
		LLGameAppConfig config=GameContxtConfigurationLoader.loadConfiguration(in, LLGameAppConfig.class);
		
		String tournamentConfig=config.getTournamentConfig();
		LLGameTournamentConfig  gameTournamentConfig=LLGameTournamentConfig.load(tournamentConfig);
		LLGameChannel channel=new LLGameChannel(Long.valueOf(1), gameTournamentConfig);
		channels.put(channel.getId(), channel);
		
		logger.info("loading the channel:"+channel.getId());
		//LLGameTournamentConfig.config().update(LLAreaTypes.FreeTournamentArea.type, "/META-INF/tournament/1/1000.xml");
		
		// should create a room.
//		for(int i=0;i<max_room;i++){
//			LLRoom room=GameUtil.obtain(LLRoom.class);
//			rooms.put(room.getId(), room);
//		}
		
		
	}
	
	public static void main(String args[]){
//		 InputStream url=LLGameApp.class.getResourceAsStream("/game-landlord.conf");
//	  	  try {
//			LLGameGlobalContext.init(url);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	  	 // LLGameTournamentConfig.config().update(LLAreaTypes.FreeTournamentArea.type, "/META-INF/1/1000.xml");
	  	  
	}

}
