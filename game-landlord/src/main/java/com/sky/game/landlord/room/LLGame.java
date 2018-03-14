/**
 * 
 */
package com.sky.game.landlord.room;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.event.DefaultGameSession;
import com.sky.game.context.event.EventHandler;
import com.sky.game.context.event.EventProcessTask;
import com.sky.game.context.event.IGameSession;
import com.sky.game.context.game.CircleOrderArray;
import com.sky.game.context.game.IGameLife;
import com.sky.game.context.game.IGameStage;
import com.sky.game.context.id.LLIdTypes;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.jmx.JmxGame;
import com.sky.game.protocol.commons.GL0000Beans.LG2002Response;
import com.sky.game.protocol.commons.GL0000Beans.LG2003Response;
import com.sky.game.protocol.commons.GL0000Beans.LG2001Response;
import com.sky.game.protocol.commons.GL0000Beans.LG2000Response;
import com.sky.game.protocol.commons.GL0000Beans.GL0005Request;
import com.sky.game.protocol.commons.GL0000Beans.LG3001GameObject;
import com.sky.game.protocol.commons.GL0000Beans.LG3001GameScoreObject;
import com.sky.game.protocol.commons.GL0000Beans.PlayerScore;


/**
 * @author sparrow
 *
 */
public class LLGame  implements IIdentifiedObject {
	private static final Log logger =LogFactory.getLog(LLGame.class);
	
	Long id;
	
	LLDeck deck;
	
	LLGameStates state;
	LLGame game;
	
	LLGameScoreRecord score;
	
	
	CircleOrderArray<LLDeckSeat> seats;
	EventHandler<EventProcessTask<LLGame>> handlers;
	
	
	
	
	
	IGameStage stage;
	
	IGameLife gameLife;
	
	IGameSession gameSession;
	
	
	//boolean exceptionHappens;
	JmxGame jmxGame;
	
	
	long duration;
	
	
	
	public void append(String message){
		jmxGame.appaend(message);
		//buffer.append("#").append(CronUtil.getFormatedDateNow()).append(" - ").append(message).append("\n");
	}
	
	public void append(LLDeckSeat seat,String message){
		append(String.format("seat[%010d] -  %s",seat.id,message ));
		//jmxGame.setDetail(buffer.toString());
	}
	
	public void close(){
//		try {
//			File pwd=new File(".");
//			logger.info("PWD:"+pwd.getAbsolutePath());
//			File f=new File(pwd.getAbsolutePath()+String.format("/%d/%d/%d.log",game.deck.room.id,game.deck.id,game.id ));
//			FileUtils.writeStringToFile(f, buffer.toString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//logger.info(buffer.toString());
		
	}

	/**
	 * 
	 */
	public LLGame(LLDeck deck) {
		// TODO Auto-generated constructor stub
		this.deck=deck;
		this.id=LLU.getId(LLIdTypes.IdTypeGame);
		LLDeckSeat[] ss=deck.seats;
		Arrays.sort(ss,new Comparator<LLDeckSeat>() {

			public int compare(LLDeckSeat o1, LLDeckSeat o2) {
				// TODO Auto-generated method stub
				return o1.location-o2.location;
			}
		});
		
		seats=new CircleOrderArray<LLDeckSeat>(ss);
		handlers=EventHandler.obtain(GameContextGlobals.getExecutor());
		score=LLU.o(LLGameScoreRecord.class);
		game=this;
		this.deck.robot=new LLGameRobot(this);
		this.gameSession=LLU.o(DefaultGameSession.class);
		this.gameSession.setId(this.id);
		
		jmxGame=LLU.o(JmxGame.class);
		jmxGame.setBegin(System.currentTimeMillis());
		jmxGame.setId(id);
		append("\n");
		append(String.valueOf(id.longValue()));
		append("deck -"+deck.id);
		// set the game begin.
		setState(LLGameStates.Born);
		//this.state=LandlordGameStates.Born;
	}
	
	
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public LLGameStates getState() {
		return state;
	}

	public synchronized void setState(LLGameStates state) {
		
		try {
			this.state = state;
			logger.info("game["+getId().longValue()+"] - "+state.getState().toString());
			
			jmxGame.setState(state.getState());
			
			
			// switch the state.
			//();
			doStateChange();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jmxGame.append(e);
		}
		
	}
	
	private void doStateChange(){
		
		//onStateChanged();
		EventProcessTask<LLGame> task=new EventProcessTask<LLGame>(this){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				super.run();
				try {
					
					onStateChanged();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//exceptionHappens=true;
					//jmxGame.setException(true);
					//jmxGame.setEx(GameUtil.);
					jmxGame.append(e);
					
				}
				//sendStateChangedMessage();
			}
			
		};
		task.setSession(this.gameSession);
		
		handlers.addEvent(task);
	}
	
	public void setNextState(){
		if(state.equals(LLGameStates.END)){
			logger.info(getId().longValue()+" alread destoryed!");
			return;
		}
		LLGameStates s=LLGameStates.getState(this.state.value+1);
		logger.info("statechanged["+deck.getUri()+"/"+getId().longValue()+"]  - ("+state.getState().toString()+" --> "+s.getState().toString()+")");
		setState(s);
	}
	
	public void setPrevState(){
		
		if(state.equals(LLGameStates.END)){
			logger.info(getId().longValue()+" alread destoryed!");
			return;
		}
 		LLGameStates s=LLGameStates.getState(this.state.value-1);
		logger.info("statechanged["+deck.getUri()+"/"+getId().longValue()+"]  - ("+state.getState().toString()+" --> "+s.getState().toString()+")");
		
		setState(s);
	}
	
	private void sendStateChangedMessage(){
		// first notify every one the game begin
		LG2000Response gameState=wrapState();
				// notify the gameState.
		deck.sendBrokerMessage(gameState, false);
				
	}
	
	public synchronized void onStateChanged(){
		
		// notify the game state changed
		append(state.toString());
		//jmxGame.setDetail(buffer.toString());
		sendStateChangedMessage();
		switch (state.value) {
		case LLGameStates.BORN:
		{
			duration=System.currentTimeMillis();
			stage=LLGameBorn.obtain(this);
			stage.begin();
		}
			break;
		case LLGameStates.DEAL_CARDS:
		{
			stage=LLGameDealCards.obtain(this);
			stage.begin();
		}
		break;
		case LLGameStates.BID:
		{
			stage=LLGameBid.obtain(this);
			stage.begin();
		}
			break;
		
			
		case LLGameStates.KICKER:
		{
			// only the farmer can kick.
			stage=LLGameKicker.obtain(this);
			stage.begin();
			
		}
			break;
			
		case LLGameStates.PULL:
		{
			stage=LLGamePull.obtain(this);
			stage.begin();
		}
			break;
		case LLGameStates.END:
		{
			// deck should remove every player off the seats.
			stage=LLGameEnd.obtain(this);
			stage.begin();
		}
			break;
		case LLGameStates.GAMING:
		{
			stage=LLGaming.obtain(this);
			stage.begin();
			
		}
			break;
		case LLGameStates.KITTY_CARDS:
		{
			stage=LLGameKittyCards.obtain(this);
			stage.begin();
		}
			break;
		case LLGameStates.SCORE:
		{
			duration=System.currentTimeMillis()-duration;
			stage=LLGameScore.obtain(this);
			stage.begin();
		}
			break;

		default:
			break;
		}
	}
	
	
	/**
	 * 
	 * Protocol object
	 */
	public LG2000Response wrapState(){
		LG2000Response obj=new LG2000Response();
		obj.setId(id);
		obj.setState(this.state.getState());
		return obj;
		
	}
	
	
	/**
	 * 
	 * final score
	 * 
	 * @return
	 */
	public LG2002Response wrapFinalScore(){
		LG2002Response obj=LLU.o(LG2002Response.class);
		obj.setBaseScore(deck.conf.baseChips);
		obj.setBid(score.bid);
		obj.setBomb(score.bomb);
		obj.setId(getId());
		obj.setNoPlayerDeal(score.noDealPlayed?1:0);
		obj.setRocket(score.rocket);
		obj.setGameScore(score.calculate());
		
		
		
		
		List<PlayerScore> playerScore=GameUtil.getList();
		
		for(LLDeckSeat seat:deck.seats){
			playerScore.add(seat.wrapPlayScore());
		}
		
		obj.setPlayerScores(playerScore);
		return obj;
	}
	
	
	
	public LG2003Response wrapPlayerResultCards(){
		LG2003Response obj=LLU.o(LG2003Response.class);
		obj.setId(id);
		List<GL0005Request> hands=GameUtil.getList();
		for(LLDeckSeat seat:deck.seats){
			if(seat.player.getPokeCube().remains()>0)
				hands.add(seat.wrapPlayerResultHand());
		}
		obj.setHands(hands);
		return obj;
	}
	
	public LG2001Response wrapScore(){
		LG2001Response obj=LLU.o(LG2001Response.class);
		obj.setMulti(game.score.calculate());
		//obj.setBase(deck.conf.baseChips);
		//obj.setIntegral();
		//obj.setBase(base);
		
		return obj;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.annotation.introspector.IIdentifiedObject#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	public void destory(){
		state=LLGameStates.End;
		//setState(LLGameStates.End);
		jmxGame.setEnd(System.currentTimeMillis());
		jmxGame.setState(state.getState());
	}
	
	/**
	 * LG3001 -
	 * wrap the game object.
	 * @return
	 */
	public LG3001GameObject wrapOfflineGameObject(){
		LG3001GameObject o=LLU.o(LG3001GameObject.class);
		o.setGameId(id);
		o.setGameState(state.getState());
		return o;
		
	}

	/**
	 * LG3001 -
	 * 
	 * wrap the game score object
	 * @return
	 */
	public LG3001GameScoreObject wrapOfflineGameScore(){
		LG3001GameScoreObject o=LLU.o(LG3001GameScoreObject.class);
		o.setBaseScore(deck.conf.baseChips);
		o.setBid(score.bid);
		o.setBomb(score.bomb);
		o.setNoPlayerDeal(score.noDealPlayed?1:0);
		o.setRocket(score.rocket);
		o.setGameScore(score.calculate());
		
		return o;
	}
	
	
	public JmxGame getJmxGame(){
		//jmxGame.setDetail(buffer.toString());
		return jmxGame;
	}
	
	
}
