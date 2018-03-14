/**
 * 
 */
package com.sky.game.protocol;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.UserToken;
import com.sky.game.service.token.Token;

/**
 * user information synced
 * 
 * 
 * @author sparrow
 *
 */
public class ProtocolGlobalContextRemote {
	private static final Log logger = LogFactory
			.getLog(ProtocolGlobalContextRemote.class);

	/**
	 * 
	 */
	public ProtocolGlobalContextRemote() {
		// TODO Auto-generated constructor stub
	}

	public static ProtocolGlobalContextRemote instance() {
		return instance;
	}

	private static final ProtocolGlobalContextRemote instance = new ProtocolGlobalContextRemote();

	private ConcurrentHashMap<Long, BasePlayer> onlinePlayers = new ConcurrentHashMap<Long, BasePlayer>(
			10000);
	private ConcurrentHashMap<String, BasePlayer> tokenPlayers = new ConcurrentHashMap<String, BasePlayer>(
			10000);

	private ConcurrentHashMap<String, BasePlayer> devicePlayers = new ConcurrentHashMap<String, BasePlayer>(
			10000);

	/**
	 * 
	 * @param player
	 * @throws GameProtocolException
	 */
	public void addOnlinePlayer(BasePlayer player) throws GameProtocolException {
		if (player == null) {
			throw new GameProtocolException("online player is null");
		}

		Long id = Long.valueOf(player.userId);

		GameUtil.clearGameLife("online://" + id);

		BasePlayer p = null;
		if (onlinePlayers.containsKey(id)) {
			// update the token
			// remove the timer
			p = onlinePlayers.get(id);
			if (p != null) {
				if (p.getToken() != null && !"".equals(p.getToken()))
					tokenPlayers.remove(p.getToken());
				if (p.getDeviceId() != null && !"".equals(p.getDeviceId()))
					devicePlayers.remove(p.getDeviceId());
			}
			p.setState(PlayerStates.Online);
			p.setToken(player.getToken());
			p.setDeviceId(player.getDeviceId());

			tokenPlayers.put(p.getToken(), p);
			devicePlayers.put(p.getDeviceId(), p);
			logger.info("player[" + player.userId + "] update token -"
					+ player.token + " broken conneciton resume. ["
					+ p.getPlayerSize() + "]");
		} else {
			player.setState(PlayerStates.Online);
			onlinePlayers.put(Long.valueOf(player.userId), player);
			tokenPlayers.put(player.getToken(), player);
			devicePlayers.put(player.getDeviceId(), player);
			logger.info("player[" + player.userId + "] add token -"
					+ player.token + "");
		}

	}
	
	public int size(){
		return onlinePlayers.size();
	}

	public Collection<BasePlayer> getOnlinePlayers() {

		return onlinePlayers.values();
	}

	public boolean isOnline(Long userId) {
		return onlinePlayers.containsKey(userId);
	}

	public void updateChannelId(Long userId, Long channelId)
			throws GameProtocolException {
		if (isOnline(userId)) {
			BasePlayer player = getOnlinePlayer(userId);
			player.channelId = channelId;
		}
	}

	public void removeOnline(BasePlayer player) {
		BasePlayer p = onlinePlayers.get(Long.valueOf(player.getUserId()));
		tokenPlayers.remove(p.getToken());
		onlinePlayers.remove(Long.valueOf(player.userId));
	}

	public BasePlayer getOnlinePlayer(long userId) throws GameProtocolException {
		Long uid = Long.valueOf(userId);
		BasePlayer player = null;
		if (onlinePlayers.containsKey(uid)) {
			player = onlinePlayers.get(uid);

		} else {

			throw new GameProtocolException(
					" OnlinePlayer don't contains userId=" + userId);
		}
		return player;
	}

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

	public BasePlayer getOnlinePlayer(String token)
			throws GameProtocolException {

		BasePlayer player = null;

		if (tokenPlayers.containsKey(token)) {
			player = tokenPlayers.get(token);

		} else {

			throw new GameProtocolException(
					" OnlinePlayer don't contains token=" + tokenPlayers);
		}
		return player;

	}

	public synchronized BasePlayer getOnlinePlayerByDeviceId(String deviceId)
			throws GameProtocolException {
		BasePlayer offlinePlayer = null;
		if (devicePlayers.containsKey(deviceId)) {
			offlinePlayer = devicePlayers.get(deviceId);

		} else {

			throw new GameProtocolException(
					" OnlinePlayer don't contains token=" + tokenPlayers);
		}
		return offlinePlayer;
	}

	public synchronized void removeOnlinePlayer(Long id) {

		/**
		 * 
		 * after the raw connection broken, the game server remains the player
		 * for 5minutes.
		 */
		if (onlinePlayers.containsKey(id)) {
			BasePlayer p = onlinePlayers.get(id);
			p.setState(PlayerStates.Offline);
			// after the offline state 5 mins remove the object form
			// onlinePlayers
			GameUtil.gameLife("online://" + id.longValue(), 5 * 60 * 1000,
					this, "removeOnline", p).setGameSession(
					GameUtil.DEFAULT_GAMESESSION);
			// onlinePlayers.remove(id);
		}
	}

}
