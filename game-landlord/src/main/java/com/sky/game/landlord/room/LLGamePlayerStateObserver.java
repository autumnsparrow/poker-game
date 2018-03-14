/**
 * @sparrow
 * @Mar 18, 2015   @1:10:18 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.IGameEventObserver;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.PlayerStates;

/**
 * Detail of the player state changes:
 * 
 * 
 *     LLGamePlayer has some state to indicate the following behaviors:
 *     
 *     {@link LLGamePlayerMeta#enableRobot} 
 *     JOIN ROOM - when user can join a room,the player state change to enableRobot=false.
 *     			user can't join the room if the user leave the room,but in the progress of elimination is processing.
 *     			the elimination can finished only after the tournament change the player state to REWARD state.
 *     
 *     LEAVE ROOM - when user leave the room,the player state change to enableRobot=true, elimination=true
 *     {@link LLGamePlayerMeta#online}
 *     ONLINE - only the player not invoke the leave room can receive that message.
 *     OFFLINE - only the player not invoke the leave room can recieve that message.
 *     
 *     {@link LLGamePlayerMeta#resume}
 *     BROKEN CONNECTION - only the player do not invoke the leave room and the {@link LLGamePlayerMeta#online}=false
 *     RESUME CONNECTION - only the player do not invoke the leave room can receive that message.
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class LLGamePlayerStateObserver implements IGameEventObserver{


	public static interface IHandlerPlayer{
		public void handlePlayer(LLGamePlayer p,long roomId);
	}
	
	/**
	 * 
	 */
	public LLGamePlayerStateObserver() {
		// TODO Auto-generated constructor stub
		GameEventHandler.handler.registerObserver(GameEvent.NETWORK_EVENT,
				this);
		GameEventHandler.handler.registerObserver(GameEvent.ROOM_EVENT,
				this);
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.event.IGameEventObserver#getUri()
	 */
	public String getUri() {
		// TODO Auto-generated method stub
		return "NetworkStateObserver";
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.event.IGameEventObserver#observer(com.sky.game.context.event.GameEvent)
	 */
	public void observer(GameEvent evt) {
		// TODO Auto-generated method stub
		if (evt.isEvent(GameEvent.NETWORK_EVENT)){
			onNetworkEvent(evt);
		}else if(evt.isEvent(GameEvent.ROOM_EVENT)){
			onRoomEvent(evt);
		}
		
	}
	
	
	
	/**
	 * 
	 * @param evt
	 */
	private void onNetworkEvent(GameEvent evt){
		BasePlayer o=LLU.asObject(evt.obj);
		
		if(o.getState().eq(PlayerStates.Offline)){
			handleOfflineEvent(o);
		}else if(o.getState().eq(PlayerStates.Online)){
			handleOnlineEvent(o);
		}
		
	}
	
	public static void roomEvent(LLGamePlayer p){
		// don't deal with the robot.
		if(p.isRobot)
			return;
		GameEvent evt=LLU.o(GameEvent.class);
		evt.name=GameEvent.ROOM_EVENT;
		evt.obj=p;
		GameEventHandler.handler.broadcast(evt);
	}
	/**
	 * 
	 * @param evt
	 */
	private void onRoomEvent(GameEvent evt){
		LLGamePlayer p=LLU.asObject(evt.obj);
		if(p.joinRoom){
			handleJoinRoom(p);
		}else {
			handleLeaveRoom(p);
		}
		
	}
	
	/**
	 * must after the player in the state of room.
	 * can join the room ,only after 
	 * @param p
	 */
	private void handleJoinRoom(LLGamePlayer p){
		//p.lastAcess=System.currentTimeMillis();
		p.enableRobot=false;
		// if user can join the room
		if(!p.resume){
			p.resume=true;
		}
	}
	
	/**
	 * when the player leave, set the player current state as {@code Robot}.
	 * 
	 * @param p
	 */
	private void handleLeaveRoom(LLGamePlayer p){
		p.enableRobot=true;
		p.elimination=true;
		p.handleUserLeave();
	}
	
	
	/**
	 * player online
	 * @param p
	 */
	private void handleOnlineEvent(BasePlayer p){
		// all player must online
		hanlePlayers(p,new IHandlerPlayer(){

			public void handlePlayer(LLGamePlayer p,long roomId) {
				// TODO Auto-generated method stub
				
				if(!p.enableRobot){
					p.online=true;
					
					p.shouldHandleResume=false;
					p.append("--- online --"+p.lastAcess);
					p.jmxState();
					p.shouldHandleResume=false;
					if(p.state.eq(LLGamePlayerStates.Deck)||p.state.eq(LLGamePlayerStates.Team)){
						if(!p.elimination){
							p.shouldHandleResume=true;
							p.lastAcess=System.currentTimeMillis();
							p.sendUserState(LLGamePlayerStates.Resume.state);
							p.append("should resume :"+roomId);
						}
						
					}
					
				}
				
			}
			
		});
		
	}
	
	/**
	 * player offline
	 * @param p
	 */
	private void handleOfflineEvent(BasePlayer p){
		hanlePlayers(p,new IHandlerPlayer(){

			public void handlePlayer(LLGamePlayer p,long roomId) {
				// TODO Auto-generated method stub
				
				if(!p.enableRobot){
					p.append("--- offline --");
					p.online=false;
					p.setResume(false, "offline :"+roomId);

				}
				
				
			}
			
		});
	}
	
	
	
	public static void hanleLastAcessPlayers(BasePlayer p,IHandlerPlayer handler){
		//BasePlayer p = GSPP.getPlayerById(id);
		if (p != null) {
			Set<Long> roomIds = p.gameRoomIds();
			if (roomIds != null) {
				List<LLGamePlayer> players=new LinkedList<LLGamePlayer>();
				
				for (Long roomId : roomIds) {
					LLGamePlayer llp = p.getGamePlayer(roomId);
					players.add(llp);
					
				}
				
				Collections.sort(players,new Comparator<LLGamePlayer>() {

					public int compare(LLGamePlayer o1, LLGamePlayer o2) {
						// TODO Auto-generated method stub
						return (int)(o2.lastAcess-o1.lastAcess);
					}
				});
				
				if(players.size()>0){
					LLGamePlayer llp=players.get(0);
					LLRoom room=llp.getObject(llp.room);
					if(room!=null&&llp.team!=null){
						
						handler.handlePlayer(llp, room.getId().longValue());
					}
				}
			}
		}
	}
	
	public static void hanlePlayers(BasePlayer p,IHandlerPlayer handler){
		//BasePlayer p = GSPP.getPlayerById(id);
		if (p != null) {
			Set<Long> roomIds = p.gameRoomIds();
			if (roomIds != null) {
				for (Long roomId : roomIds) {
					LLGamePlayer llp = p.getGamePlayer(roomId);
					if(llp!=null){
						handler.handlePlayer(llp,roomId.longValue());
					}
					
				}
			}
		}
	}

}
