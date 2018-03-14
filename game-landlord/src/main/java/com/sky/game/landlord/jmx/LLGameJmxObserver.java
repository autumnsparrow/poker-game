/**
 * @sparrow
 * @Feb 11, 2015   @10:57:41 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.jmx;

import com.j256.simplejmx.common.JmxOperation;
import com.j256.simplejmx.common.JmxResource;
import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.LLGameGlobalContext;
import com.sky.game.landlord.room.LLGameChannel;
import com.sky.game.landlord.room.LLGameObjectMap;
import com.sky.game.landlord.room.LLU;
import com.sky.game.protocol.commons.GU0008Beans;
import com.sky.game.protocol.commons.GU0031Beans;
import com.sky.game.protocol.commons.WX0001Beans;
import com.sky.game.protocol.commons.GU0031Beans.Request;
import com.sky.game.landlord.room.LLGamePlayer;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.GameProtocolException;
import com.sky.game.protocol.ProtocolGlobalContextRemote;

/**
 * @author sparrow
 *
 */
@JmxResource(domainName="com.sky.game.landlord.jmx",beanName="gameJmxObserver",description="current match process jmx management beans")
public class LLGameJmxObserver {
	
	LLGameObjectMap<LLGameChannel> channels;

	/**
	 * 
	 */
	public LLGameJmxObserver() {
		// TODO Auto-generated constructor stub
		
	}
	@JmxOperation(parameterNames={"channelId"},parameterDescriptions={"channel id"})
	public void reloadConfig(int channelId){
		try {
			LLGameGlobalContext.reInit(channelId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@JmxOperation(parameterNames={"channelId","roomId","vip"},parameterDescriptions={" channel  id","room id"," is vip room"})
	public String checkRoom(long channelId,long roomId,boolean vip){
		String room=null;
		channels=LLGameGlobalContext.channels;
		LLGameChannel channel=channels.get(Long.valueOf(channelId));
		JmxRoom r=channel.getJmxRoom(roomId, vip);
		
		room=GameContextGlobals.getXmlJsonConvertor().format(r);
		return room;
	}
	
	@JmxOperation(parameterNames={"channelId","roomId","userId"},parameterDescriptions={"channel id","room id"," user id"})
	public String checkFTBTGame(long channelId,long roomId,long userId){
		
		channels=LLGameGlobalContext.channels;
		LLGameChannel channel=channels.get(Long.valueOf(channelId));
		String xml=channel.checkGame(roomId, userId);
		return xml;
	}
	
	@JmxOperation(parameterNames={"channelid","roomId","vip","teamId"},parameterDescriptions={"channel id","room id"," vip "," team id"})
	public String teamSorted(long channelId,long roomId,boolean vip,long teamId){
		channels=LLGameGlobalContext.channels;
		LLGameChannel channel=channels.get(Long.valueOf(channelId));
		String xml=channel.teamSorted(roomId, vip, teamId);
		return xml;
	}
	
	
	@JmxOperation(parameterNames={"channelId","roomId","vip","teamId"},parameterDescriptions={"channel id","room id","is vip room","team id"})
	public void closeTeam(long channelId,long roomId,boolean vip,long teamId){
		String room=null;
		channels=LLGameGlobalContext.channels;
		LLGameChannel channel=channels.get(Long.valueOf(channelId));
		channel.destroyTeam(roomId, vip, teamId);
		
	}
	
	@JmxOperation(parameterNames={"type","count"},parameterDescriptions={"type","count"})
	public void insertChargeCard(int type,int count){
        Request request =LLU.o(GU0031Beans.Request.class);
        request.setType(type);
        request.setCount(count);
        ProxyMessageInvoker.invoke(request);
	}
	
	@JmxOperation(parameterNames={"channelId","roomId","vip","teamId","deckId"},parameterDescriptions={"channel id","room id","is vip room","team id","deck id"})
	public void destoryDeckGame(long channelId,long roomId,boolean vip,long teamId,long deckId){
		String room=null;
		channels=LLGameGlobalContext.channels;
		LLGameChannel channel=channels.get(Long.valueOf(channelId));
		channel.destoryGame(roomId, vip, teamId, deckId);
	}

	@JmxOperation(parameterNames={"userId","roomId"},parameterDescriptions={" user id","room id"})
	public void destoryPlayer(long playerid,long roomId){
		BasePlayer basePlayer=null;
		try {
			basePlayer = ProtocolGlobalContextRemote.instance().getOnlinePlayer(playerid);
		} catch (GameProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//basePlayer.setState(PlayerStates.Tournament);
		if (basePlayer != null) {
			LLGamePlayer player=basePlayer.getGamePlayer(Long.valueOf(roomId));
			player.reset();
		}
	}
	
	@JmxOperation(parameterNames={"userId"},parameterDescriptions={" user id"})
	public String checkPlayer(long playerid){
		
		BasePlayer basePlayer=null;
		try {
			basePlayer = ProtocolGlobalContextRemote.instance().getOnlinePlayer(playerid);
		} catch (GameProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuffer buf=new StringBuffer();
		//basePlayer.setState(PlayerStates.Tournament);
		if (basePlayer != null) {
			buf.append("<JmxPlayers>");
			for(Long id:basePlayer.gameRoomIds()){
				LLGamePlayer player=basePlayer.getGamePlayer(id);
				if(player!=null){
					buf.append("<room>").append(id).append("</room>");
					buf.append(GameContextGlobals.getXmlJsonConvertor().format(player.getJmxPlayer()));
				}
			}
			buf.append("</JmxPlayers>");
		}
		return buf.toString();
	}
	@JmxOperation()
	public int onlinePlayerSize(){
		return ProtocolGlobalContextRemote.instance().size();
	}
	
	@JmxOperation()
	public String onlinePlayers(){
		StringBuffer sb=new StringBuffer();
		for(BasePlayer p:ProtocolGlobalContextRemote.instance().getOnlinePlayers()){
			sb.append(p);
		}
		return sb.toString();
	}
	@JmxOperation()
	public void callWx(){
		 WX0001Beans.Request request=GameUtil.obtain(WX0001Beans.Request.class);
		 	request.setId(1);
		 	request.setIp("0.0.0.0");
		 	WX0001Beans.Response response=ProxyMessageInvoker.invoke(request);
	}
	
	
	@JmxOperation()
	public void callGU(){
		GU0008Beans.Request 
		 request=GameUtil.obtain(GU0008Beans.Request .class);
		 request.setToken("");
		 GU0008Beans.Response response=ProxyMessageInvoker.invoke(request);
	}
	
	
	
}
