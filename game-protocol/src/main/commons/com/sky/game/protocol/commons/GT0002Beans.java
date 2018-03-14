package com.sky.game.protocol.commons;

import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.util.GameUtil;
import com.sky.game.protocol.commons.GT0001Beans.State;

public class GT0002Beans {

	public GT0002Beans() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * retrieve the tables by house id.
	 * 
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode = "GT0002")
	public static class Request extends BaseRequest implements IIdentifiedObject{
		Long id;

		public Long getId() {
			return Long.valueOf(id);
		}

		public void setId(Long id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "Request [id=" + id.longValue() + "]";
		}
		
	}

	@HandlerResponseType(responsecode = "TG0002", transcode = "GT0002")
	public static class Response {
		
		List<Table> tables;

		public List<Table> getTables() {
			return tables;
		}

		public void setTables(List<Table> tables) {
			this.tables = tables;
		}

		@Override
		public String toString() {
			return "Response [tables=" + tables + "]";
		}
		public static Response obtain(){
			Response obj=new Response();
			
			obj.tables=GameUtil.getList();
			return obj;
		}
		
		
	};
	
	
	
	
	/**
	 * 
	 * Table brief information.
	 * @author sparrow
	 *
	 */
	public static class Table{
		long id;
		int playerCount;
		State state;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public int getPlayerCount() {
			return playerCount;
		}
		public void setPlayerCount(int playerCount) {
			this.playerCount = playerCount;
		}
		
		public State getState() {
			return state;
		}
		public void setState(State state) {
			this.state = state;
		}
		@Override
		public String toString() {
			return "Table [id=" + id + ", playerCount=" + playerCount
					+ ", state=" + state + "]";
		}
		
		
		
	}
}
