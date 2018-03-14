package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

public class GU0032Beans {

	public GU0032Beans() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerRequestType(transcode="GU0032")
	public static class Request  extends BaseRequest{

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}	
		long itemId;
		public long getItemId() {
			return itemId;
		}
		public void setItemId(long itemId) {
			this.itemId = itemId;
		}
	}
	
	@HandlerResponseType(responsecode="UG0032",transcode="GU0032")
	public static class Response{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		int state;
		String description;
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
	};

}
