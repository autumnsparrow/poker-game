/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

/**
 * @author Administrator
 *
 */
public class GU0020Beans {

	/**
	 * 
	 */
	public GU0020Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0020")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		long itemId;
		long id;
		
		public long getItemId() {
			return itemId;
		}
		public void setItemId(long itemId) {
			this.itemId = itemId;
		}
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public Request(long itemId) {
			super();
			this.itemId = itemId;
		}
		@Override
		public String toString() {
			return "Request [id=" + itemId + "]";
		}
	}

	@HandlerResponseType(responsecode = "UG0020", transcode = "GU0020")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		long id;
	    int state;
	    String description;
	    
	    
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Response(int state,long id) {
			super();
			this.state = state;
			this.id=id;
		}
		@Override
		public String toString() {
			return "Response [state=" + state + "]";
		}
	};
}
