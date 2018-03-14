package com.sky.game.protocol.handler;

import org.springframework.scheduling.annotation.Scheduled;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.ProtocolGlobalContext;
import com.sky.game.protocol.event.MinaEvent;

public class TG0006Handler {
	/**
	 * 
	 */
	//TG0006Beans t6=new TG0006Beans();
	public TG0006Handler() {
		// TODO Auto-generated constructor stub
	}
	@Scheduled(fixedRate=10000)
	public void sendMina(){
		
		
	}
	public void sendMinaMessage(Object obj){
		
		for(BasePlayer player:ProtocolGlobalContext.instance().getOnlinePlayers()){
			MinaEvent evt=new MinaEvent(player.getUserId(),obj);
			evt.sendMessage();
		}
		
	}
		
}
