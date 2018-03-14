/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.LinkedList;
import java.util.List;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.util.GameUtil;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.commons.GT0001Beans.State;
import com.sky.game.service.domain.GameBlockadeUser;
import com.sky.game.service.domain.GameEnroll;
import com.sky.game.service.domain.GameSystemMessage;
import com.sky.game.service.domain.GameUser;
import com.sky.game.service.domain.Item;
import com.sky.game.service.logic.ItemService;
import com.sky.game.service.logic.game.GameBlockadeMessageService;
import com.sky.game.service.logic.game.GameBlockadeService;
import com.sky.game.service.logic.game.GameEnrollService;

/**
 * 
 * GameSystem.
 * 
 * @author sparrow
 *
 */
public class GS0000Beans {

	/**
	 * 
	 */
	public GS0000Beans() {
		// TODO Auto-generated constructor stub
	}

	@HandlerNamespaceExtraType(namespace = "GameSystem")
	public static class Extra {
		String deviceId;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

	}

	/**
	 * 
	 * landlord --> protocol {@link ItemService#getItems()}
	 * 
	 * @author sparrow
	 *
	 */

	@HandlerRequestType(transcode = "GS0001")
	public static class GS0001Request {

		List<Long> ids;

		public List<Long> getIds() {
			return ids;
		}

		public void setIds(List<Long> ids) {
			this.ids = ids;
		}

	}

	@HandlerResponseType(transcode = "GS0001", responsecode = "SG0001")
	public static class SG0001Response {

		List<Item> items;

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}

	}

	public static class BPlayer {
		long userId;

		String token;
		String deviceId;

		long channelId;

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public long getChannelId() {
			return channelId;
		}

		public void setChannelId(long channelId) {
			this.channelId = channelId;
		}
		
		public BasePlayer wrap(){
			BasePlayer p=new BasePlayer();
			p.setChannelId(channelId);
			p.setDeviceId(deviceId);
			p.setToken(token);
			p.setUserId(userId);
			return p;
		}

	}

	/**
	 * protocol -->landlord get base player.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0002")
	public static class GS0002Request {
		BPlayer player;

		public BPlayer getPlayer() {
			return player;
		}

		public void setPlayer(BPlayer player) {
			this.player = player;
		}

		

	}

	@HandlerResponseType(transcode = "GS0002", responsecode = "SG0002")
	public static class SG0002Response {

	}

	/**
	 * 
	 * protocol --> landlord
	 * 
	 * remove the player from landlord.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0003")
	public static class GS0003Request extends BaseRequest {
		Long userId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

	}

	@HandlerResponseType(transcode = "GS0003", responsecode = "SG0003")
	public static class SG0003Response {

	}

	/**
	 * 
	 * landlord --> protocol
	 * 
	 * get user info from the protocol
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0004")
	public static class GS0004Request extends BaseRequest {
		Long userId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

	}

	/**
	 * 
	 * landlord --> protocol get game user by userid
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GS0004", responsecode = "SG0004")
	public static class SG0004Response {
		GameUser user;

		public GameUser getUser() {
			return user;
		}

		public void setUser(GameUser user) {
			this.user = user;
		}

	}

	/**
	 * check user coins of free tournament. landlord --> protocol
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0005")
	public static class GS0005Request extends BaseRequest {
		Long userId;
		int min;
		int itemId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
		}

		public int getItemId() {
			return itemId;
		}

		public void setItemId(int itemId) {
			this.itemId = itemId;
		}

	}

	@HandlerResponseType(transcode = "GS0005", responsecode = "SG0005")
	public static class SG0005Response {
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	}

	/**
	 * 
	 * landlord --> protocol enroll
	 * 
	 * Long userId,Long roomId,Long itemId,Integer amount
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0006")
	public static class GS0006Request extends BaseRequest {
		Long userId;
		Long roomId;
		Long teamId;
		Long itemId;
		Integer amount;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

		public Long getItemId() {
			return itemId;
		}

		public void setItemId(Long itemId) {
			this.itemId = itemId;
		}

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

		public Long getTeamId() {
			return teamId;
		}

		public void setTeamId(Long teamId) {
			this.teamId = teamId;
		}

	}

	@HandlerResponseType(transcode = "GS0006", responsecode = "SG0006")
	public static class SG0006Response {
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	}

	/**
	 * 
	 * landlord --> protocol reward
	 * 
	 * Long userId,Long roomId,Long itemId,Integer amount,Long
	 * propertyId,Integer propertyValue,Integer rank
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0007")
	public static class GS0007Request extends BaseRequest {
		Long userId;
		Long roomId;
		Long teamId;
		Long itemId;
		Integer amount;

		Integer rank;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

		public Long getItemId() {
			return itemId;
		}

		public void setItemId(Long itemId) {
			this.itemId = itemId;
		}

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

		public Integer getRank() {
			return rank;
		}

		public void setRank(Integer rank) {
			this.rank = rank;
		}

		public Long getTeamId() {
			return teamId;
		}

		public void setTeamId(Long teamId) {
			this.teamId = teamId;
		}

	}

	@HandlerResponseType(transcode = "GS0007", responsecode = "SG0007")
	public static class SG0007Response {
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	}

	/**
	 * 
	 * landlord --> protocol update exp or other property.
	 * 
	 * Long userId,Long roomId,Long itemId,Integer amount,Long
	 * propertyId,Integer propertyValue,Integer rank
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0008")
	public static class GS0008Request extends BaseRequest {
		Long userId;
		Long roomId;

		Long propertyId;
		Integer propertyValue;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

		public Long getPropertyId() {
			return propertyId;
		}

		public void setPropertyId(Long propertyId) {
			this.propertyId = propertyId;
		}

		public Integer getPropertyValue() {
			return propertyValue;
		}

		public void setPropertyValue(Integer propertyValue) {
			this.propertyValue = propertyValue;
		}

	}

	@HandlerResponseType(transcode = "GS0008", responsecode = "SG0008")
	public static class SG0008Response {
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	}

	/**
	 * wrap the service domain {@link GameBlockadeUser} .
	 * 
	 * @author sparrow
	 *
	 */
	public static class PGameBlockadeUser {
		Long id;
		Long userId;
		Long roomId;
		Long integral;
		Integer level;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

		public Long getIntegral() {
			return integral;
		}

		public void setIntegral(Long integral) {
			this.integral = integral;
		}

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

		public void wrap(GameBlockadeUser o) {
			this.id = o.getId();
			this.integral = o.getIntegral();
			this.level = o.getLevel();
			this.roomId = o.getRoomId();
			this.userId = o.getUserId();

		}
	}

	/**
	 * GS0009 - wrap the
	 * {@link GameBlockadeService#getGameBlockadeUserByRoomLevel(Long, Long)}
	 * parameters landlord -> protocol
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0009")
	public static class GS0009Request {
		Long roomId;
		Long level;

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

		public Long getLevel() {
			return level;
		}

		public void setLevel(Long level) {
			this.level = level;
		}

		public static GS0009Request obtain(Long roomId, Long level) {
			GS0009Request o = GameUtil.obtain(GS0000Beans.GS0009Request.class);
			o.level = level;
			o.roomId = roomId;
			return o;
		}

	}

	/**
	 * SG0009 - wrap the
	 * {@link GameBlockadeService#getGameBlockadeUserByRoomLevel(Long, Long)}
	 * return type.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GS0009", responsecode = "SG0009")
	public static class SG0009Response {
		List<PGameBlockadeUser> users;

		public List<PGameBlockadeUser> getUsers() {
			return users;
		}

		public void setUsers(List<PGameBlockadeUser> users) {
			this.users = users;
		}

		/**
		 * wrap the List<GameBlockadeUser>
		 * 
		 * @param objs
		 */
		public void wrap(List<GameBlockadeUser> objs) {
			// create the list
			users = GameUtil.getList();
			for (GameBlockadeUser o : objs) {
				PGameBlockadeUser p = GameUtil.obtain(PGameBlockadeUser.class);
				p.wrap(o);
				users.add(p);
			}
		}

	}

	/**
	 * GS0010 - wrap the
	 * {@link GameBlockadeService#getGameBlockadeUser(Long, Long)}
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0010")
	public static class GS0010Request {
		Long roomId;
		Long userId;

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public static GS0010Request obtain(Long roomId, Long userId) {
			GS0010Request o = GameUtil.obtain(GS0010Request.class);
			o.roomId = roomId;
			o.userId = userId;
			return o;
		}

		/**
		 * 
		 */
		public GS0010Request() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	/**
	 * SG0010 - wrap the
	 * {@link GameBlockadeService#getGameBlockadeUser(Long, Long)} return type.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GS0010", responsecode = "SG0010")
	public static class SG0010Response {
		PGameBlockadeUser user;

		public PGameBlockadeUser getUser() {
			return user;
		}

		public void setUser(PGameBlockadeUser user) {
			this.user = user;
		}

		public void wrap(GameBlockadeUser u) {
			user = GameUtil.obtain(PGameBlockadeUser.class);
			user.wrap(u);
		}

		/**
		 * 
		 */
		public SG0010Response() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	/**
	 * GS0011 - wrap the
	 * {@link GameBlockadeService#updateBlockade(Long, Long, Long, Integer)}
	 * parameters.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0011")
	public static class GS0011Request {
		Long userId;
		Long roomId;
		Long integral;
		Integer level;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

		public Long getIntegral() {
			return integral;
		}

		public void setIntegral(Long integral) {
			this.integral = integral;
		}

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

		public static GS0011Request obtain(Long roomId, Long userId,
				Long integral, Integer level) {
			GS0011Request o = GameUtil.obtain(GS0011Request.class);
			o.integral = integral;
			o.roomId = roomId;
			o.userId = userId;
			o.level = level;
			return o;
		}
	}

	/**
	 * SG0011 - wrap the
	 * {@link GameBlockadeService#updateBlockade(Long, Long, Long, Integer)}
	 * return type.
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GS0011", responsecode = "SG0011")
	public static class SG0011Response {
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	}

	/**
	 * GS0012 - landlord -> protocol update the user task
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0012")
	public static class GS0012Request {
		long userId;
		State state;
		int rate;

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public int getRate() {
			return rate;
		}

		public void setRate(int rate) {
			this.rate = rate;
		}

		public static GS0012Request obtain(long userId, State state, int rate) {
			GS0012Request o = GameUtil.obtain(GS0012Request.class);
			o.setRate(rate);
			o.setState(state);
			o.setUserId(userId);
			return o;
		}

	}

	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GS0012", responsecode = "SG0012")
	public static class SG0012Response {

	}

	/**
	 * GS0012 - game data statics system landlord -> protocol
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0013")
	public static class GS0013Request {
		long userId;
		State state;
		State category;

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public static GS0013Request obtain(long userId, State state,
				State category) {
			GS0013Request o = GameUtil.obtain(GS0013Request.class);

			o.setState(state);
			o.setCategory(category);
			o.setUserId(userId);
			return o;
		}

		public State getCategory() {
			return category;
		}

		public void setCategory(State category) {
			this.category = category;
		}

	}

	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GS0013", responsecode = "SG0013")
	public static class SG0013Response {

	}

	/**
	 * GS0014 - update the achivement progress landlord -> protocol
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0014")
	public static class GS0014Request {
		long userId;
		State state;
		State landlord;

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public static GS0014Request obtain(long userId, State state,
				State landlord) {
			GS0014Request o = GameUtil.obtain(GS0014Request.class);

			o.setState(state);
			o.setUserId(userId);
			o.setLandlord(landlord);
			return o;
		}

		public State getLandlord() {
			return landlord;
		}

		public void setLandlord(State landlord) {
			this.landlord = landlord;
		}

	}

	/**
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerResponseType(transcode = "GS0014", responsecode = "SG0014")
	public static class SG0014Response {

	}

	/*
	 * 积分卡合成
	 */
	@HandlerRequestType(transcode = "GS0015")
	public static class GS0015Request {
		long userId;

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public static GS0015Request obtain(long userId, State state,
				State landlord) {
			GS0015Request o = GameUtil.obtain(GS0015Request.class);

			o.setUserId(userId);
			return o;
		}

	}

	@HandlerResponseType(transcode = "GS0015", responsecode = "SG0015")
	public static class SG0015Response {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
	}

	/**
	 * {@link GameEnrollService#getGameEnrollByState(Long)}
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0016")
	public static class GS0016Request {
		Long roomId;

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

	}

	@HandlerResponseType(transcode = "GS0016", responsecode = "SG0016")
	public static class SG0016Response {
		List<GameEnroll> gameEnrolles;

		public List<GameEnroll> getGameEnrolles() {
			return gameEnrolles;
		}

		public void setGameEnrolles(List<GameEnroll> gameEnrolles) {
			this.gameEnrolles = gameEnrolles;
		}

	}

	/**
	 * {@link GameEnrollService#getGameEnrollByUserId(Long)}
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0017")
	public static class GS0017Request {
		Long userId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

	}

	@HandlerResponseType(transcode = "GS0017", responsecode = "SG0017")
	public static class SG0017Response {
		List<GameEnroll> gameEnrolles;

		public List<GameEnroll> getGameEnrolles() {
			return gameEnrolles;
		}

		public void setGameEnrolles(List<GameEnroll> gameEnrolles) {
			this.gameEnrolles = gameEnrolles;
		}

	}

	/**
	 * {@link GameEnrollService#getGameEnrollByUserIdAndRoomId(Long, Long)}
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0018")
	public static class GS0018Request {
		Long userId;
		Long roomId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

	}

	@HandlerResponseType(transcode = "GS0018", responsecode = "SG0018")
	public static class SG0018Response {
		GameEnroll gameEnroll;

		public GameEnroll getGameEnroll() {
			return gameEnroll;
		}

		public void setGameEnroll(GameEnroll gameEnroll) {
			this.gameEnroll = gameEnroll;
		}

	}

	/**
	 * {@link GameEnrollService#updateEnrollState(Long)}
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0019")
	public static class GS0019Request {
		Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	}

	@HandlerResponseType(transcode = "GS0019", responsecode = "SG0019")
	public static class SG0019Response {
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	}

	/**
	 * 
	 * 
	 * {@link GameEnrollService#updateTeamId(Long, Long)}
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0020")
	public static class GS0020Request {
		Long id;
		Long teamId;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getTeamId() {
			return teamId;
		}

		public void setTeamId(Long teamId) {
			this.teamId = teamId;
		}

	}

	@HandlerResponseType(transcode = "GS0020", responsecode = "SG0020")
	public static class SG0020Response {
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	}

	/**
	 * {@link GameBlockadeMessageService#getUnreadMessageCount(Long)}
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GS0021")
	public static class GS0021Request {
		Long gbuToId;

		public Long getGbuToId() {
			return gbuToId;
		}

		public void setGbuToId(Long gbuToId) {
			this.gbuToId = gbuToId;
		}

	}

	@HandlerResponseType(transcode = "GS0021", responsecode = "SG0021")
	public static class SG0021Response {
		Long total;

		public Long getTotal() {
			return total;
		}

		public void setTotal(Long total) {
			this.total = total;
		}

	}

	@HandlerRequestType(transcode = "GS0022")
	public static class GS0022Request {

		long userId;
		String name;
		String message;

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public static GS0022Request wrap(GameSystemMessage message) {
			GS0022Request o = GameUtil.obtain(GS0022Request.class);
			o.message = message.getMessage();
			o.userId = message.getUserId();
			return o;

		}
	}

	@HandlerResponseType(responsecode = "SG0022", transcode = "GS0022")
	public static class SG0022Response {

		boolean sent;

		public boolean isSent() {
			return sent;
		}

		public void setSent(boolean sent) {
			this.sent = sent;
		}

	};

	@HandlerRequestType(transcode = "GS0023")
	public static class GS0023Request {
		Long id;
		Integer status;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

	}

	@HandlerResponseType(transcode = "GS0023", responsecode = "SG0023")
	public static class SG0023Response {

		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	};

	@HandlerRequestType(transcode = "GS0024")
	public static class GS0024Request {
		Long roomId;
		Integer level;

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

	}

	@HandlerResponseType(transcode = "GS0024", responsecode = "SG0024")
	public static class SG0024Response {

		int count;

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

	};

	@HandlerRequestType(transcode = "GS0025")
	public static class GS0025Request {
		Long userId;
		Long roomId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

	}

	@HandlerResponseType(transcode = "GS0025", responsecode = "SG0025")
	public static class SG0025Response {

		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	};

	@HandlerRequestType(transcode = "GS0026")
	public static class GS0026Request {
		Long userId;
		Integer itemId;
		Integer itemValue;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Integer getItemId() {
			return itemId;
		}

		public void setItemId(Integer itemId) {
			this.itemId = itemId;
		}

		public Integer getItemValue() {
			return itemValue;
		}

		public void setItemValue(Integer itemValue) {
			this.itemValue = itemValue;
		}

	}

	@HandlerResponseType(transcode = "GS0026", responsecode = "SG0026")
	public static class SG0026Response {

		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	};

	
	@HandlerRequestType(transcode = "GS0027")
	public static class GS0027Request {
		Long userId;
		Integer fen;
		Long propertyId;
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public Integer getFen() {
			return fen;
		}
		public void setFen(Integer fen) {
			this.fen = fen;
		}
		public Long getPropertyId() {
			return propertyId;
		}
		public void setPropertyId(Long propertyId) {
			this.propertyId = propertyId;
		}
	}

	@HandlerResponseType(transcode = "GS0027", responsecode = "SG0027")
	public static class SG0027Response {

		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

	};
	public static void main(String args[]) {
		GS0001Request req = GameUtil.obtain(GS0001Request.class);
		req.setIds(new LinkedList<Long>());
		for (long i = 0; i < 50; i++) {
			req.getIds().add(Long.valueOf(i));
		}

		String request = GameContextGlobals.getJsonConvertor().format(req);

		System.out.println(request);

		GS0001Request r = GameContextGlobals.getJsonConvertor().convert(
				request, GS0001Request.class);
		System.out.println(r);
	}

}
