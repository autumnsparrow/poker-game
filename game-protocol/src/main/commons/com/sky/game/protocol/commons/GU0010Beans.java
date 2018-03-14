package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

public class GU0010Beans {

	public GU0010Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0010")
	public static class Request extends BaseRequest {
		
		Long id;

		

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

		

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Request(Long id) {
			super();
			this.id = id;
		}

		@Override
		public String toString() {
			return "Request [id=" + id + "]";
		}
		
	}
	@HandlerResponseType(responsecode = "UG0010", transcode = "GU0010")
	public static class Response {

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		int state;
        long goldExchangeId;
        String description;
        
		public long getGoldExchangeId() {
			return goldExchangeId;
		}

		public void setGoldExchangeId(long goldExchangeId) {
			this.goldExchangeId = goldExchangeId;
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

		public Response(int state, long goldExchangeId, String description) {
			super();
			this.state = state;
			this.goldExchangeId = goldExchangeId;
			this.description = description;
		}

		@Override
		public String toString() {
			return "Response [state=" + state + ", goldExchangeId="
					+ goldExchangeId + ", description=" + description + "]";
		}
	
	}
}
	
	

