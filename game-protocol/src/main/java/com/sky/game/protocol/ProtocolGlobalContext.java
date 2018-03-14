/**
 * 
 */
package com.sky.game.protocol;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.context.util.GameUtil;
import com.sky.game.protocol.commons.GS0000Beans;
import com.sky.game.protocol.util.PushMessage;
import com.sky.game.service.domain.GameSystemMessage;
import com.sky.game.service.domain.UserToken;
import com.sky.game.service.token.Token;

/**
 * @author Administrator
 *
 */
public class ProtocolGlobalContext {

	/**
	 * 
	 */
	public ProtocolGlobalContext() {
		// TODO Auto-generated constructor stub
	}

	public static ProtocolGlobalContext instance() {
		return instance;
	}
	private static final Log logger=LogFactory.getLog(ProtocolGlobalContext.class);
	private static final ProtocolGlobalContext instance = new ProtocolGlobalContext();

	private static final PushMessage PUSHM_MESSAGE = new PushMessage();

	private ConcurrentHashMap<Long, BasePlayer> onlinePlayers = new ConcurrentHashMap<Long, BasePlayer>(
			10000);
	
	
	public void broadcastMessage(Object message){
		GameEventHandler.handler.broadcast(new GameEvent(GameEvent.GAME_PROTOCOL_MESSAGE,message));
	}
	
	public void addOnlinePlayer(BasePlayer player) throws GameProtocolException {
		if (player == null) {
			throw new GameProtocolException("online player is null");
		}
		// if(!onlinePlayers.containsKey(Long.valueOf(player.userId))){
		//removeOnlinePlayer(player.getDeviceId());
		if(player.deviceId.startsWith("R01234567")){
			return;
		}
		
		//
		// remove the other player who has the same device id.
		//
		for (BasePlayer p : getOnlinePlayers()) {
			if (p.getDeviceId() != null && player.getDeviceId() != null) {
				if (p.getDeviceId().equals(player.getDeviceId())) {
					if (p.getUserId() != player.getUserId()) {
						playerOffline(p);
					}
				}
			}
		}
		
		//
		// offline the ower
		

		playerOnline(player);
		
		
	}

	public Collection<BasePlayer> getOnlinePlayers() {

		return onlinePlayers.values();
	}

	public void removeOnlinePlayer(BasePlayer player)
			throws GameProtocolException {
		if (player == null) {
			throw new GameProtocolException("online player is null");
		}
		if (onlinePlayers.containsKey(Long.valueOf(player.userId))) {
			onlinePlayers.remove(Long.valueOf(player.userId));
		}
	}

	public BasePlayer getOnlinePlayer(long userId) throws GameProtocolException {
		Long uid = Long.valueOf(userId);
		BasePlayer player = null;
		if (onlinePlayers.containsKey(uid)) {
			player = onlinePlayers.get(uid);

		} 
//		  else { throw new GameProtocolException(
//		  " OnlinePlayer don't contains userId=" + userId); }
		 
		return player;
	}

	/*
	 * public BasePlayer getOnlinePlayerRemote(long userId) throws
	 * GameProtocolException { // Long uid=Long.valueOf(userId); BasePlayer
	 * player = null; GS0003Request req = GameUtil.obtain(GS0003Request.class);
	 * req.setUserId(userId); SG0003Response resp =
	 * ProxyMessageInvoker.invoke(req); if (resp != null) { player =
	 * resp.getPlayer(); }
	 * 
	 * return player; }
	 */

	public boolean validToken(String token) throws GameProtocolException {
		boolean valid = true;
		Token t = new Token(token);

		UserToken userToken = t.getUserToken();
		if (userToken == null) {
			throw new GameProtocolException("Can't retrieve the token from :"
					+ token);
		}
		BasePlayer player = getOnlinePlayer(userToken.getUserId());

		if (player.getToken().equals(token)) {
			valid = true;
		} else {
			valid = false;
		}
		return valid;

	}

	/*
	 * public BasePlayer getOnlinePlayerRemote(String token) throws
	 * GameProtocolException {
	 * 
	 * BasePlayer player = null; GS0002Request req =
	 * GameUtil.obtain(GS0002Request.class); req.setToken(token); SG0002Response
	 * resp = ProxyMessageInvoker.invoke(req); if (resp != null) { player =
	 * resp.getPlayer(); } return player;
	 * 
	 * }
	 */

	public BasePlayer getOnlinePlayerByToken(String token)
			throws GameProtocolException {

		BasePlayer player = null;
		Token t = new Token(token);

		UserToken userToken = t.getUserToken();
		if (userToken == null) {
			throw new GameProtocolException("Can't retrieve the token from :"
					+ token);
		}
		player = getOnlinePlayer(userToken.getUserId());
		return player;

	}

	public synchronized BasePlayer getOnlinePlayerByDeviceId(String deviceId) {
		BasePlayer offlinePlayer = null;
		for (BasePlayer player : getOnlinePlayers()) {
			if (player.getDeviceId().equals(deviceId)) {
				offlinePlayer = player;
				break;
			}
		}
		return offlinePlayer;
	}
	
	
	private void playerOnline(final BasePlayer p){
		// replace the token.
		BasePlayer old=onlinePlayers.get(Long.valueOf(p.userId));
		if(old!=null&&old.token!=null){
			//playerOffline(old);
			GameUtil.removeToken(old.token);
		}
		
		onlinePlayers.put(Long.valueOf(p.userId), p);
		
		GameContextGlobals.postTask(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				GS0000Beans.GS0002Request req = GameUtil
						.obtain(GS0000Beans.GS0002Request.class);
				req.setPlayer(p.wrap());
				
				ProxyMessageInvoker.invoke(req); 
				logger.info("player["+p.userId+"] online landlord server(GS0002)");
			}
			
		});
		
		
		
	
		
	}
	
	private void playerOffline(final BasePlayer player){
		GameUtil.removeToken(player.getToken());
		onlinePlayers.remove(Long.valueOf(player.getUserId()));
		//BasePlayer p=null;
		try {
			//p=player;//getOnlinePlayer(id.longValue());
			GameContextGlobals.postTask(new Runnable(){

				public void run() {
					// TODO Auto-generated method stub
					GS0000Beans.GS0003Request req = GameUtil
							.obtain(GS0000Beans.GS0003Request.class);
					req.setUserId(Long.valueOf(player.getUserId()));
					
					ProxyMessageInvoker.invoke(req);
					logger.info("player["+player.userId+"] offline landlord server(GS0003)");
				}
				
			});
			
			
			
//			GL0000Beans.GL0009Request connectionState = GameUtil
//					.obtain(GL0000Beans.GL0009Request.class);
//			connectionState.setId(p.getUserId());
//			connectionState.setState(GL0000Beans.OFFLINE);
//			connectionState.setToken(p.getToken());
//			GL0000Beans.Extra extra=new GL0000Beans.Extra();
//			extra.setDeviceId(p.deviceId);
//			ProxyMessageInvoker.onRecieve(connectionState, extra);
//			logger.info("player["+p.userId+"] offline state(GL0009)");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	public synchronized void removeOnlinePlayer(String deviceId) {
		BasePlayer offlinePlayer = null;
		//	offlinePlayer = getOnlinePlayerByDeviceId(deviceId);
		logger.info("deviceId["+deviceId+"] removeOnlinePlayer");
			//BasePlayer offlinePlayer = null;
			Collection<BasePlayer> players=getOnlinePlayers();
			for (BasePlayer player : players) {
				if (player.getDeviceId().equals(deviceId)) {
					offlinePlayer = player;
					if (offlinePlayer != null) {
					//	onlinePlayers.remove(Long.valueOf(offlinePlayer.getUserId()));
						playerOffline(offlinePlayer);
						
					}
				}
			}
			
	}
	
	public void removeOnlinePlayerDelay(String deviceId){
		
	}

}
