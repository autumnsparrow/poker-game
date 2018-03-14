/**
 * 
 */
package com.sky.game.mina.robot;

import java.util.List;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.mina.util.LLU;
import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.DeckObject;
import com.sky.game.protocol.commons.GL0000Beans.LG1004Response;
import com.sky.game.protocol.commons.GL0000Beans.LG2001Response;
import com.sky.game.protocol.commons.GL0000Beans.LG2000Response;
import com.sky.game.protocol.event.MinaEvent;

/**
 * @author sparrow
 *
 */
public class LLRobotPlayer  implements IIdentifiedObject{
	Long id;
	
	GL0000Beans.LG3000Response position;
	//PokerCube kittyCards;
	GL0000Beans.LG1006Response kittyCards;
	GL0000Beans.LG2000Response gameState;
	GL0000Beans.LG2001Response gameScore;
	GL0000Beans.LG1000Response deckSeatStateObject;
	GL0000Beans.LG1002Response deckSeatPositionStateObject;
	GL0000Beans.LG1003Response deckSeatShowHandStateObject;
	GL0000Beans.LG1001Response deckSeatActionStateObject;
	GL0000Beans.LG1005Response deckSeatHand;
	GL0000Beans.LG1004Response deckSeatPlayer;
	
	GL0000Beans.LG0002Response bidMessage;
	
	GL0000Beans.LG0004Response playerCards;
	GL0000Beans.LG0006Response handMessage;
	GL0000Beans.LG4002Response roomState;
	
	PokerCube owner;
	PokerCube kitty;
	
	
	// show out the hands
	

	/**
	 * 
	 */
	public LLRobotPlayer(long userid) {
		// TODO Auto-generated constructor stub
		id=Long.valueOf(userid);
		GameContextGlobals.registerEventHandler("LG4002", this);
		GameContextGlobals.registerEventHandler("LG3000", this);
		GameContextGlobals.registerEventHandler("LG3001", this);
		GameContextGlobals.registerEventHandler("LG2000", this);
		GameContextGlobals.registerEventHandler("LG2001", this);
		GameContextGlobals.registerEventHandler("LG1000", this);
		GameContextGlobals.registerEventHandler("LG1001", this);
		GameContextGlobals.registerEventHandler("LG1002", this);
		GameContextGlobals.registerEventHandler("LG1003", this);
		GameContextGlobals.registerEventHandler("LG1004", this);
		GameContextGlobals.registerEventHandler("LG1005", this);
		
		GameContextGlobals.registerEventHandler("LG0002", this);
		GameContextGlobals.registerEventHandler("LG0004", this);
		GameContextGlobals.registerEventHandler("LG0006", this);
	}

	public Long getId() {
		return id;
	}
	
	@RegisterEventHandler(transcode="LG4002")
	public void onLG4002(MinaEvent evt){
		roomState=LLU.evtAsObj(evt);
		
	}
	
	@RegisterEventHandler(transcode="LG3000")
	public void onLG3000(MinaEvent evt){
		position=LLU.evtAsObj(evt);
		
	}
	
	
	@RegisterEventHandler(transcode="LG3001")
	public void onLG3001(MinaEvent evt){
		kittyCards=LLU.evtAsObj(evt);
		kitty=PokerCube.getPokerCubeByHex(kittyCards.getHex());
	}
	@RegisterEventHandler(transcode="LG2000")
	public void onLG2000(MinaEvent evt){
		gameState=LLU.evtAsObj(evt);
	}
	@RegisterEventHandler(transcode="LG2001")
	public void onLG2001(MinaEvent evt){
		gameScore=LLU.evtAsObj(evt);
	}
	
	
	@RegisterEventHandler(transcode="LG1000")
	public void onLG1000(MinaEvent evt){
		deckSeatStateObject=LLU.evtAsObj(evt);
		//deckSeatStateObject.getPostion()
	}
	
	@RegisterEventHandler(transcode="LG1001")
	public void onLG1001(MinaEvent evt){
		deckSeatActionStateObject=LLU.evtAsObj(evt);
		// checking seat action on turn
		if(deckSeatActionStateObject.getState().getState()==1){
			if(gameState.getState().getState()==5){
				// gaming.
				if(deckSeatShowHandStateObject.getState().getState()==1){
					// active
					//show hand.
					
				}else{
					// passive.
				}
			}
			
		}
	}
	
	@RegisterEventHandler(transcode="LG1002")
	public void onLG1002(MinaEvent evt){
		deckSeatPositionStateObject=LLU.evtAsObj(evt);
	}
	
	@RegisterEventHandler(transcode="LG1003")
	public void onLG1003(MinaEvent evt){
		deckSeatShowHandStateObject=LLU.evtAsObj(evt);
	}
	
	
	@RegisterEventHandler(transcode="LG1004")
	public void onLG1004(MinaEvent evt){
		deckSeatPlayer=LLU.evtAsObj(evt);
	}
	
	@RegisterEventHandler(transcode="LG1005")
	public void onLG1005(MinaEvent evt){
		deckSeatHand=LLU.evtAsObj(evt);
	}
	
	@RegisterEventHandler(transcode="LG0002")
	public void onLG0002(MinaEvent evt){
		bidMessage=LLU.evtAsObj(evt);
	}
	
	
	@RegisterEventHandler(transcode="LG0004")
	public void onLG0004(MinaEvent evt){
		playerCards=LLU.evtAsObj(evt);
		owner=PokerCube.getPokerCubeByHex(playerCards.getHex());
	}
	
	@RegisterEventHandler(transcode="LG0006")
	public void onLG0006(MinaEvent evt){
		handMessage=LLU.evtAsObj(evt);
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	
}
