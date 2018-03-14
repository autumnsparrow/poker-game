/**
 * 
 * @Date:Nov 4, 2014 - 11:08:38 AM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.house;

import java.util.LinkedList;
import java.util.List;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.protocol.commons.GT0002Beans;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.protocol.event.handler.MinaEventHandler;
import com.sky.game.texas.domain.configuration.GameTexasGameConfiguration;
import com.sky.game.texas.domain.table.GameTexasTable;
import com.sky.game.texas.id.GlobalIdGenerator;

/**
 * @author sparrow
 *
 */
public class GameTexasHouse implements IIdentifiedObject {
	
	
	
	
	GameTexasGameConfiguration configuration;
	List<GameTexasTable> tables;
	long id;
	

	/**
	 * 
	 */
	public GameTexasHouse() {
		// TODO Auto-generated constructor stub
		configuration=new  GameTexasGameConfiguration();
		this.id=GlobalIdGenerator.getHouseId();
		tables=new LinkedList<GameTexasTable>();
		for(int i=0;i<configuration.getMaxTables();i++){
			GameTexasTable table=new GameTexasTable();
			table.setGameTexasGameConfiguration(configuration);
			tables.add(table);
		}
		GameContextGlobals.registerEventHandler("GT0002", this);
		
	}


	public Long getId() {
		return Long.valueOf(id);
	}


	public void setId(long id) {
		this.id = id;
	}


	public GameTexasGameConfiguration getConfiguration() {
		return configuration;
	}


	public List<GameTexasTable> getTables() {
		return tables;
	}
	
	
	@RegisterEventHandler(transcode="GT0002")
	public void handleEvent(MinaEvent evt){
		GT0002Beans.Response response=GT0002Beans.Response.obtain();
		for(GameTexasTable table:tables){
			response.getTables().add(table.wrapBriefTableState());
		}
		
		MinaEvent minaEvent=MinaEvent.obtainMinaEvent(evt.userId,response,false);
		MinaEventHandler.addPushMinaEvent(minaEvent);
	}
	
	
	

}
