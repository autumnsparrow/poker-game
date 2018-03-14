/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;

/**
 * @author Administrator
 *
 */
public class TG0003Beans {

	/**
	 * 
	 */
	public TG0003Beans() {
		// TODO Auto-generated constructor stub
	}
	
	
	@HandlerRequestType(transcode="TG0003")
	public static class Request{
		Table table;
        
		public Table getTable() {
			return table;
		}

		public void setTable(Table table) {
			this.table = table;
		}

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Request(Table table) {
			super();
			this.table =table;
		}

		@Override
		public String toString() {
			return "Request [table=" + table + "]";
		}
		
	}
//	public static class Response extends BaseResponse{
//		
//	};
	
	public static class Table {

		/**
		 * 
		 */
		int state;
		List<Player> playerList=new ArrayList<Player>();
		int playerState;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public List<Player> getPlayerList() {
			return playerList;
		}
		public void setPlayerList(List<Player> playerList) {
			this.playerList = playerList;
		}
		public int getPlayerState() {
			return playerState;
		}
		public void setPlayerState(int playerState) {
			this.playerState = playerState;
		}
		public Table(int state, List<Player> playerList, int playerState) {
			super();
			this.state = state;
			this.playerList = playerList;
			this.playerState = playerState;
		}
		public Table() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "Table [state=" + state + ", playerList=" + playerList
					+ ", playerState=" + playerState + "]";
		}
	}
	public static class Player {

		int state;
		int seatId;
		String name;
		long chips;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public int getSeatId() {
			return seatId;
		}
		public void setSeatId(int seatId) {
			this.seatId = seatId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public long getChips() {
			return chips;
		}
		public void setChips(long chips) {
			this.chips = chips;
		}
		public Player(int state, int seatId, String name, long chips) {
			super();
			this.state = state;
			this.seatId = seatId;
			this.name = name;
			this.chips = chips;
		}
		public Player() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "Player [state=" + state + ", seatId=" + seatId + ", name="
					+ name + ", chips=" + chips + "]";
		}
	}
	
	
	public static Request getRequest(){
		TG0003Beans.Table ta=new TG0003Beans.Table();
		List<TG0003Beans.Player> playl=new ArrayList<TG0003Beans.Player>();
		playl.add(new Player(1,1,"testP1",4));
		playl.add(new Player(1,2,"testP2",5));
		ta.setPlayerList(playl);
		ta.setPlayerState(2);
		ta.setState(1);
		TG0003Beans.Request request=new TG0003Beans.Request(ta);
		return request;
	}
}
