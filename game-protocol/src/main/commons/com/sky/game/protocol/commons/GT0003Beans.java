/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.protocol.commons.GT0001Beans.State;


/**
 * 
 * 
 * Join the Table
 * 
 * @author Administrator
 *
 */
public class GT0003Beans {

	/**
	 * 
	 */
	public GT0003Beans() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerRequestType(transcode="GT0003")
	public static class Request extends BaseRequest implements IIdentifiedObject{
		
		Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		public static Request obtain(){
			return new Request();
		}
		
	}
	
	@HandlerResponseType(transcode="GT0003",responsecode="TG0003")
	public static class Response{
		long id;// table id.
		State state;
		int playerCount;// current player count.
		String cards;
		
		public int getPlayerCount() {
			return playerCount;
		}
		public void setPlayerCount(int playerCount) {
			this.playerCount = playerCount;
		}
		public String getCards() {
			return cards;
		}
		public void setCards(String cards) {
			this.cards = cards;
		}


		
	
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public State getState() {
			return state;
		}
		public void setState(State state) {
			this.state = state;
		}
		
		
		/**
		 * 
		 * @return
		 */
		public static Response obtain(){
			return new Response();
		}
	}
	

   
	
}
