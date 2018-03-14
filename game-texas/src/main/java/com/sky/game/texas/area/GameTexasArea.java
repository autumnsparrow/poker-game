/**
 * 
 * @Date:Nov 4, 2014 - 11:12:09 AM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.area;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.RegisterEventFilter;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.protocol.GameProtocolException;
import com.sky.game.protocol.commons.GT0001Beans;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.protocol.event.handler.MinaEventHandler;
import com.sky.game.texas.domain.table.GameTexasTable;
import com.sky.game.texas.house.GameTexasHouse;
import com.sky.game.texas.id.GlobalIdGenerator;

/**
 * 
 * 
 * give the house list.
 * @author sparrow
 *
 */
@SuppressWarnings("deprecation")
public class GameTexasArea  implements IIdentifiedObject{
	private static final Log logger=LogFactory.getLog(GameTexasArea.class);
	
	List<GameTexasHouse> houses;
	String name;
	long id;

	/**
	 * 
	 */
	public GameTexasArea() {
		// TODO Auto-generated constructor stub
		this.id=GlobalIdGenerator.getAreaId();
		this.name="Area-"+this.id;
		houses=new LinkedList<GameTexasHouse>();
		for(int i=0;i<3;i++){
			GameTexasHouse house=new GameTexasHouse();
			houses.add(house);
		}
		// add the 
		//MinaEventHandler.getHandler().registerMinaRecievedObserver(this);
		logger.info(toString());
		GameContextGlobals.registerEventHandler("GT0001", this);
		
	}
	

	@Override
	public String toString() {
		return "GameTexasArea [houses=" + houses + ", name=" + name + ", id="
				+ id + "]";
	}


	public List<GameTexasHouse> getHouses() {
		return houses;
	}

	public void setHouses(List<GameTexasHouse> houses) {
		this.houses = houses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return Long.valueOf(id);
	}

	public void setId(long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * 
	 * 
	 * @see com.sky.game.protocol.event.handler.IMinaRecieveObserver#shouldProcessEvent(com.sky.game.protocol.event.MinaEvent)
	 */
	@RegisterEventFilter(transcode="GT0001")
	public boolean shouldFilter(MinaEvent evt) {
		// TODO Auto-generated method stub
		boolean ret=false;
		ret=MinaEvent.filterMinaEvent(evt, this);
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.protocol.event.handler.IMinaRecieveObserver#onRecievedMinaEvent(com.sky.game.protocol.event.MinaEvent)
	 */
	@RegisterEventHandler(transcode="GT0001")
	public void handle(MinaEvent event)
			throws GameProtocolException {
		
		
		// TODO Auto-generated method stub
		GT0001Beans.Response response=new GT0001Beans.Response();
		List<GT0001Beans.House> protocolHouses=new LinkedList<GT0001Beans.House>();
		for(GameTexasHouse h:houses){
			GT0001Beans.House house=new GT0001Beans.House();
			house.setBigBlind(h.getConfiguration().getBigBlinderChips());
			house.setId(h.getId());
			house.setMaxChips(h.getConfiguration().getMaxChips());
			house.setMinChips(h.getConfiguration().getMinChips());
			int numbers=0;
			for(GameTexasTable t:h.getTables()){
				numbers+=t.getPlayerCount();
			}
			house.setOnlinePlayNumber(numbers);
			house.setSmallBlind(h.getConfiguration().getSmallBlinderChips());
			house.setName(getName());
			protocolHouses.add(house);
		}
		
		response.setHouses(protocolHouses);
		
		MinaEvent minaEvent=MinaEvent.obtainMinaEvent(event.userId,response,false);
		MinaEventHandler.addPushMinaEvent(minaEvent);
		
	}
	
	
	

	

}
