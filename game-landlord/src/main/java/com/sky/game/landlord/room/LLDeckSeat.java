/**
 * 
 */
package com.sky.game.landlord.room;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.IUniqueIdentifiedObject;
import com.sky.game.context.game.IGameLife;
import com.sky.game.context.game.IGameStage;
import com.sky.game.context.util.GameUtil;
import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.game.protocol.commons.GL0000Beans.DeckPlayerObject;
import com.sky.game.protocol.commons.GL0000Beans.DeckSeatObject;
import com.sky.game.protocol.commons.GL0000Beans.GL0012Request;
import com.sky.game.protocol.commons.GL0000Beans.GL0013Request;
import com.sky.game.protocol.commons.GL0000Beans.LG0012Response;
import com.sky.game.protocol.commons.GL0000Beans.LG1001Response;
import com.sky.game.protocol.commons.GL0000Beans.LG1004Response;
import com.sky.game.protocol.commons.GL0000Beans.LG1002Response;
import com.sky.game.protocol.commons.GL0000Beans.LG1003Response;
import com.sky.game.protocol.commons.GL0000Beans.LG1000Response;
import com.sky.game.protocol.commons.GL0000Beans.LG1006Response;
import com.sky.game.protocol.commons.GL0000Beans.GL0001Request;
import com.sky.game.protocol.commons.GL0000Beans.GL0005Request;
import com.sky.game.protocol.commons.GL0000Beans.LG0006Response;
import com.sky.game.protocol.commons.GL0000Beans.GL0003Request;
import com.sky.game.protocol.commons.GL0000Beans.GL0008Request;
import com.sky.game.protocol.commons.GL0000Beans.LG1007Response;
import com.sky.game.protocol.commons.GL0000Beans.LG3001SeatObject;
import com.sky.game.protocol.commons.GL0000Beans.PlayerScore;
import com.sky.game.protocol.commons.GL0000Beans.ValueChange;
import com.sky.game.protocol.event.MinaEvent;

/**
 * @author sparrow
 *
 */
public class LLDeckSeat implements IUniqueIdentifiedObject {
	// the logger
	private static final Log logger = LogFactory.getLog(LLDeckSeat.class);
	// the deck 
	LLDeck deck;
	
	Long id;
	
	int location;
	LLDeckSeat seat;
	
	// the poker cube list
	List<PokerCube> pokerCubes;
	// hold the player cards
	PokerCube pokerCube;
	
	
	// leave the card information in here.
	//
	LLDeckSeatStates state;
	LLDeckSeatPositionTypes positionState;
	LLDeckSeatActionTypes actionState;
	LLDeckShowHandsTypes showHandState;

	
	LLGamePlayer player;
	IGameLife gameLife;

	
	// the score board.
	int bidScore;
	LLDeckSeatScoreBroad scoreBroad;

	 

	/**
	 * 
	 */
	public LLDeckSeat() {
		// TODO Auto-generated constructor stub
	}

	public String getUri() {
		return deck.getUri() + "@seat://" + location;
	}

	public LLDeckSeat(LLDeck deck, int location) {
		super();
		this.deck = deck;
		this.location = location;
		this.seat = this;
		
		this.state = LLDeckSeatStates.Empty;
		
		reset();

	}

	public void reset() {
		this.actionState = LLDeckSeatActionTypes.Undefined;
		this.positionState = LLDeckSeatPositionTypes.Undefined;
		this.showHandState = LLDeckShowHandsTypes.Undefined;
		this.scoreBroad = GameUtil.obtain(LLDeckSeatScoreBroad.class);
		this.pokerCubes = GameUtil.getList();
		this.bidScore=-1;
	}

	public void seat(LLGamePlayer player) {
		// TODO Auto-generated method stub
		this.state = LLDeckSeatStates.Seat;
		this.player = player;
		this.player.roomType=deck.conf.roomType;
		this.player.roomName=deck.conf.roomName;
		observe();
		deck.sendBrokerMessage(deck.wrapPlayers(), false);

	}
	
	private void observe(){
		
		//if(this.player.getSeat())
		if(this.player==null)
			return;
		
		this.id = this.player.getId();
		this.player.setSeat(this);
		this.player.setState(LLGamePlayerStates.Deck);
		
		// for online/offline switch.
		//BasePlayer basePlayer=this.player.getBasePlayer();
		//if(basePlayer!=null){
			//basePlayer.setState(PlayerStates.Gaming);
		//}

		// enable robot or not.
		if(player.isRobot){
			deck.players.put(player.getId(), player);
		}else{
			deck.observe(player);
			LLU.regeisterHandler(this, "GL0001","GL0003","GL0005","GL0008","GL0012","GL0013");

		}
	}
	
	private void unObserve(){
		if(this.player.isRobot){
			deck.players.remove(player.getId());
		}else{
			deck.unObserve(this.player);
			LLU.removeHandler(this, "GL0001","GL0003","GL0005","GL0008","GL0012","GL0013");
			
		}
		
		// for online/offline switch.
//		BasePlayer basePlayer=this.player.getBasePlayer();
//		if(basePlayer!=null){
//			basePlayer.setState(PlayerStates.Tournament);
//		}
//		
		this.player.setState(LLGamePlayerStates.Team);
		
		this.player.setSeat(null);
		this.id = null;
		this.player=null;
		
		
	}

	
	public boolean isLandlord(){
		return this.positionState.equals(LLDeckSeatPositionTypes.Landlord)||this.positionState.equals(LLDeckSeatPositionTypes.Pull);
	}
	public void leave() {
		// TODO Auto-generated method stub
		this.state = LLDeckSeatStates.Empty;
		unObserve();

	}

	public LG1000Response wrapState() {
		LG1000Response obj = GameUtil.obtain(LG1000Response.class);
		obj.setId(deck.id);
		obj.setPosition(location);
		obj.setState(state.getState());

		return obj;
	}

	public LG1001Response wrapActionState() {
		LG1001Response obj = GameUtil
				.obtain(LG1001Response.class);
		obj.setId(deck.id);
		obj.setPosition(location);
		obj.setState(actionState.getState());


		return obj;
	}

	public LG1002Response wrapPositionState() {
		LG1002Response obj = GameUtil
				.obtain(LG1002Response.class);
		obj.setId(deck.id);
		obj.setPosition(location);
		obj.setState(positionState.getState());

		return obj;
	}

	public LG1003Response wrapShowHandState() {
		LG1003Response obj = GameUtil
				.obtain(LG1003Response.class);
		obj.setId(deck.id);
		obj.setPosition(location);
		obj.setState(showHandState.getState());

		return obj;
	}

	public LG0006Response wrapPlayerHand(String hex) {
		LG0006Response obj = LLU.o(LG0006Response.class);
		obj.setHex(hex);
		obj.setId(player.getId());
		return obj;

	}

	/**
	 * 
	 * send the postion
	 * 
	 * @return
	 */
	public LG1004Response wrapPlayers() {
		LG1004Response obj = LLU.o(LG1004Response.class);
		if(player!=null)
			obj.setPlayer(player.wrap());
		
		
		obj.setPosition(location);
		obj.setId(deck.id);	

		return obj;

	}
	
	public PlayerScore wrapPlayScore(){
		PlayerScore obj=LLU.o(PlayerScore.class);
		
		seat.player.wrapPlayerScore(obj);
		
		//
		ValueChange score=LLU.o(ValueChange.class);
		score.setOld(seat.scoreBroad.oldScore);
		score.setCurrent(seat.scoreBroad.currentScore);
		int chips=seat.scoreBroad.currentScore-seat.scoreBroad.oldScore;
		if(deck.conf.roomType==LLRoomTypes.FT.state){
			
			if(chips>0){
				chips=(int) (chips*0.95);
				score.setChange(chips);
			}else{
				score.setChange(chips);
			}
		}else{
			score.setChange(chips);
		}
		
		
		if(positionState.equals(LLDeckSeatPositionTypes.Farmer)||
				positionState.equals(LLDeckSeatPositionTypes.Kicker)){
			obj.setKicker((int)Math.pow(2, seat.scoreBroad.addition));
			obj.setLandLord(false);
			
		}else{
			
			obj.setLandLord(true);
			
		}
		obj.setScore(score);
		
		
		return obj;
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

	/***
	 * 
	 * Bid Stage.
	 */

	/**
	 * get the bid object from game.
	 * 
	 * @return
	 */
	
	
	

	private LLGameBid getGameBid() {
		LLGameBid obj = null;
		if (deck.game != null && deck.game.stage instanceof LLGameBid) {
			obj = LLU.asObject(deck.game.stage);
		}else{
			IGameStage o=null;
			if(deck.game!=null&&deck.game.stage!=null){
				o=deck.game.stage;
			}
			throw new RuntimeException("GameStage not in GameBid. -"+(o!=null?o.getClass().getSimpleName():"obj is empty"));
		}
		return obj;
	}
	
	/**
	 * must the game state is bid and the current user on turn.
	 * bid
	 * 
	 * TODO:how to deal with the bid message arrive,but current stage not the bid
	 * 
	 * @param evt
	 */

	@RegisterEventHandler(transcode = "GL0001")
	public void onReceiveBid(MinaEvent evt) {
		
		GL0001Request bid = LLU.evtAsObj(evt);
		LLGameBid gameBid=getGameBid();
		if(gameBid!=null){
			//gameBid.removeGameLife();
			gameBid.bid(this, bid.getBid());
		}
		
	}

	/**
	 * 
	 * set the bid score
	 * 
	 * @param score
	 */
	public void setBidScore(int score) {
		// remove the game life.
		if (score > 0) {
			actionState = LLDeckSeatActionTypes.Bid;

		} else {
			actionState = LLDeckSeatActionTypes.Pass;
		}
		bidScore = score;
	}

	
	public GL0005Request wrapPlayerResultHand(){
		GL0005Request obj=LLU.o(GL0005Request.class);
		obj.setId(seat.player.getId());
		obj.setHex(seat.player.getPokeCube().cubeToHexString());
		return obj;
	}
	
	public DeckPlayerObject wrapDeckPlayerObject(){
		DeckPlayerObject o=LLU.o(DeckPlayerObject.class);
		o.setIntegral(player.chips);
		o.setPosition(location);
		o.setRank(player.rank);
		return o;
	}
	
	

	/**
	 * wrap the kitty cards.
	 * 
	 * @return
	 */
	public LG1006Response wrapKittyCard() {
		LG1006Response obj = GameUtil.obtain(LG1006Response.class);
		obj.setId(id.longValue());
		obj.setPosition(location);
		obj.setHex(deck.kittyCards.cubeToHexString());

		return obj;

	}

	/**
	 * 
	 * 
	 * Kick stage.
	 * 
	 * 
	 * 
	 * 
	 */
	private LLGameKicker getGameKicker() {
		LLGameKicker obj = null;
		if (deck.game != null && deck.game.stage instanceof LLGameKicker) {
			obj = LLU.asObject(deck.game.stage);
		}else{
			
			IGameStage o=null;
			if(deck.game!=null&&deck.game.stage!=null){
				o=deck.game.stage;
			}
			//throw new RuntimeException("GameStage not in GameBid. -"+(o!=null?o.getClass().getSimpleName():"obj is empty"));
	
			throw new RuntimeException("GameStage not in GameKicker. -"+(o!=null?o.getClass().getSimpleName():"obj is empty"));
			
		}
		return obj;
	}
	
	
	/**
	 * 
	 * @param evt
	 */
	@RegisterEventHandler(transcode = "GL0003")
	public void onReceiveKick(MinaEvent evt) {
		GL0003Request obj = LLU.evtAsObj(evt);
		
		LLGameKicker kicker=getGameKicker();
		if(kicker!=null){
			kicker.kick(seat,obj.isKick());
		}
	}

	
	public void setKicker(boolean isKicker){
		if(isKicker){
			positionState = LLDeckSeatPositionTypes.Kicker;
			actionState = LLDeckSeatActionTypes.Kick;
		
		}else{
			positionState = LLDeckSeatPositionTypes.Farmer;
			actionState = LLDeckSeatActionTypes.Pass;
		}
		
	}

	

	/**
	 * 
	 * Kitty cards Stage. just send the kitty cards.
	 * 
	 */

	/**
	 * 
	 * Gaming stage.
	 * 
	 * 
	 * 
	 */
	

	/**
	 * 
	 * 
	 * @param evt
	 */
	private LLGaming getGaming() {
		LLGaming obj=null;
		if(deck.game.stage instanceof LLGaming){
		
			obj = LLU.asObject(deck.game.stage);
		}else{
			IGameStage o=null;
			if(deck.game!=null&&deck.game.stage!=null){
				o=deck.game.stage;
			}
			throw new RuntimeException("GameStage not in GameBid. -"+(o!=null?o.getClass().getSimpleName():"obj is empty"));
			
		}
		return obj;

	}
	
	/**
	 * 
	 * @param evt
	 */
	@RegisterEventHandler(transcode = "GL0005")
	public void onRecievePlayerHand(MinaEvent evt) {
		
		GL0005Request obj = LLU.evtAsObj(evt);

		// player pass
		pokerCube=PokerCube.getPokerCubeByHex(obj.getHex());
		pokerCube.setBelongToLandloard(player.getPokeCube().isBelongToLandloard());
		pokerCube.setDeckPoistion(location);
		
		LLGaming gaming=getGaming();
		if(gaming!=null){
			gaming.turnHands(seat);
		}

	}

	

	
	
	public void setPull(boolean isKicker){
		if(isKicker){
			positionState = LLDeckSeatPositionTypes.Pull;
			actionState = LLDeckSeatActionTypes.Pull;
			
		}else{
			positionState = LLDeckSeatPositionTypes.Landlord;
			actionState = LLDeckSeatActionTypes.Pass;
		}
		
	}
	
	LLGamePull getGamePull(){
		LLGamePull obj=null;
		if(deck.game.stage instanceof LLGamePull){
		
			obj = LLU.asObject(deck.game.stage);
		}else{
			IGameStage o=null;
			if(deck.game!=null&&deck.game.stage!=null){
				o=deck.game.stage;
			}
			throw new RuntimeException("GameStage not in GamePull. -"+(o!=null?o.getClass().getSimpleName():"obj is empty"));
			
		}
		return obj;
	}
	
	/**
	 * 
	 * @param evt
	 */
	@RegisterEventHandler(transcode = "GL0008")
	public void onPullEvent(MinaEvent evt) {
		GL0008Request obj=LLU.evtAsObj(evt);
		LLGamePull pull=getGamePull();
		if(pull!=null){
			pull.pull(seat,obj.isPull());
		}
		
	}
	@RegisterEventHandler(transcode="GL0012")
	public void onAuto(MinaEvent evt){
		GL0012Request obj=LLU.evtAsObj(evt);
		LG0012Response resp=LLU.o(LG0012Response.class);
		resp.setId(getId());
		resp.setPosition(location);
		if(obj.isEnable()){
			//player.enableRobot=true;
			state=LLDeckSeatStates.Auto;
		}else{
			//player.enableRobot=false;
			state=LLDeckSeatStates.Seat;
			
		}
		resp.setState(state.getState());
		deck.sendBrokerMessage(resp, false);
	}

	/**
	 * 
	 * @param evt
	 */
	@RegisterEventHandler(transcode="GL0013")
	public void onMessage(MinaEvent evt){
		GL0013Request req=LLU.evtAsObj(evt);
		req.setId(id);
		deck.sendBrokerMessage(req, true);
		
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

	@Override
	public String toString() {
		return "LLDeckSeat [id=" + id + ", location=" + location + ", state="
				+ state + ", positionState=" + positionState + ", actionState="
				+ actionState + ", showHandState=" + showHandState
				+ ", bidScore=" + bidScore + "]";
	}
	
	/**
	 * 
	 * @return
	 */
	public LG3001SeatObject wrapOfflineSeatObject(){
		LG3001SeatObject o=LLU.o(LG3001SeatObject.class);
		o.setActionState(actionState.getState());
		o.setBidScore(bidScore);
		o.setPlayer(seat.player.wrap());
		o.setPosition(location);
		o.setPositionState(positionState.getState());
		o.setShowHandState(showHandState.getState());
		o.setCardLeft(seat.player.getPokeCube().remains());
		//o.setLastHand(l);
		o.setSeatState(state.getState());
		if(pokerCube!=null){
			o.setLastHand(pokerCube.cubeToHexString());
		}
		
		return o;
	}
	
	
	
	

	

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IUniqueIdentifiedObject#equals(com.sky.game.landlord.room.IUniqueIdentifiedObject)
	 */
	public boolean equals(IUniqueIdentifiedObject obj) {
		// TODO Auto-generated method stub
		long roomId=getParentId();
		long pRoomId=obj.getParentId();
		logger.info(id+"-"+obj.getId()+","+roomId+" -" +pRoomId);
		return getId().longValue()==obj.getId().longValue()&&roomId==pRoomId;
		}

	/* (non-Javadoc)
	 * @see com.sky.game.context.annotation.introspector.IUniqueIdentifiedObject#getParentId()
	 */
	public long getParentId() {
		// TODO Auto-generated method stub
		
		return deck.room.id;
	}
	
	
	

}
