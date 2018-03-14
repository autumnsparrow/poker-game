/**
 * 
 */
package com.sky.game.protocol;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.json.impl.XmlJsonConvertor;
import com.sky.game.context.util.GameUtil;
import com.sky.game.protocol.commons.GS0000Beans.BPlayer;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.service.domain.UserToken;
import com.sky.game.service.token.Token;

/**
 * Base player contains the userId,token,deviceId
 * 
 * @author Administrator
 *
 */
public class BasePlayer {
	 @JsonIgnore
	 PlayerStates state;
	 // room id ,object.
	 @JsonIgnore
	 Map<Long,Object> players;
	
	 long userId;
	
	 String token;
	 String deviceId;
	
	 long channelId;
	 
	 public BPlayer wrap(){
		 BPlayer p=new BPlayer();
		 p.setChannelId(channelId);
		 p.setToken(token);
		 p.setUserId(userId);
		 p.setDeviceId(deviceId);
		 return p;
	 }

	/**
	 * 
	 */
	public BasePlayer() {
		// TODO Auto-generated constructor stub
	}

	public BasePlayer(long userId, String token, String deviceId) {
		super();
		this.userId = userId;
		this.token = token;
		this.deviceId = deviceId;
		
	}

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
	
	
	public static BasePlayer getPlayer(MinaEvent evt){
		BasePlayer player=null;
		if(evt.token!=null){
			
			try {
				player=ProtocolGlobalContextRemote.instance().getOnlinePlayer(evt.token);
			} catch (GameProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception ex){
				ex.printStackTrace();
			}
			
		}
		return player;
	}
	
	public  static BasePlayer getPlayer(BaseRequest req){
		BasePlayer player=null;
		if(req instanceof BaseRequest){
			BaseRequest baseRequest=(BaseRequest)req;
			String token=baseRequest.getToken();
			
			player=getPlayer(token);
		}
		return player;
		
	}
	
	public static BasePlayer getPlayer(String token) {
		//String token=baseRequest.getToken();
		BasePlayer player=null;
		try {
			player=ProtocolGlobalContext.instance().getOnlinePlayerByToken(token);
		} catch (GameProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//throw new GameProtocolException(e.getMessage());
		}catch (Exception ex){
			ex.printStackTrace();
			//throw new GameProtocolException(e.getMessage());
		}
		return player;
		
	}
	
	public static long getUserId(String token){
		long userId=0L;
		Token t=new Token(token);
		UserToken userToken=t.getUserToken();
		if(userToken!=null)
			userId=userToken.getUserId();
		return userId;
	}

	public PlayerStates getState() {
		return state;
	}

	public void setState(PlayerStates state) {
		this.state = state;
	}

	public <T> T getGamePlayer(Long roomId) {
		Object o=this.players==null?null:this.players.get(roomId);
		T t=o==null?null:(T)o;
		return t;
	}

	public void addGamePlayer(Long roomId,Object gamePlayer) {
		if(this.players==null){
			this.players=GameUtil.getMap();
		}
		this.players.put(roomId, gamePlayer);
	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public void removeGamePlayer(Long id) {
		// TODO Auto-generated method stub
		if(this.players!=null){
			this.players.remove(id);
		}
	}
	
	public int getPlayerSize(){
		return this.players!=null?this.players.keySet().size():0;
	}
	
	public Set<Long> gameRoomIds(){
		return this.players!=null?this.players.keySet():null;
	}

	@Override
	public String toString() {
		return "[state=" + state + ", userId=" + userId + ", token="
				+ token + ", deviceId=" + deviceId + ", channelId=" + channelId
				+ "]\r\n";
	}

	
	public boolean equals(BasePlayer obj) {
		// TODO Auto-generated method stub
		boolean ret=false;
		if(obj!=null&&deviceId!=null)
			ret=obj.userId==userId&&deviceId.equals(obj.deviceId);
		
		return ret;
	}
	public static class test implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public test() {
			super();
			// TODO Auto-generated constructor stub
		}
		private String item;
		
		public String getItem() {
			return item;
		}

		public void setItem(String item) {
			this.item = item;
		}

		public test(String item, String ageitem) {
			super();
			this.item = item;
		}
	}
	public static void main(String[] args) {
		 XmlJsonConvertor xml=new XmlJsonConvertor();
        xml.convert("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
        		+ "<item>1000</item>"
        		, test.class);
	   // System.out.println(t.toString());
	}
	 

}
