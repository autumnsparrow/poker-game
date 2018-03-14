/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

/**
 * @author rqz
 *
 */
public class GU0007Beans {

	/**
	 * 
	 */
	public GU0007Beans() {
		// TODO Auto-generated constructor stub
	}

	@HandlerRequestType(transcode = "GU0007")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		long id;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public Request(long id) {
			super();
			this.id = id;
		}
		@Override
		public String toString() {
			return "Request [id=" + id + "]";
		}
	}

	@HandlerResponseType(responsecode = "UG0007", transcode = "GU0007")
	public static class Response {

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		int state;
        long goodsExchangeId;
        String description;
        
		
		public long getGoodsExchangeId() {
			return goodsExchangeId;
		}

		public void setGoodsExchangeId(long goodsExchangeId) {
			this.goodsExchangeId = goodsExchangeId;
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

		public Response(int state, long goodsExchangeId, String description) {
			super();
			this.state = state;
			this.goodsExchangeId = goodsExchangeId;
			this.description = description;
		}

		@Override
		public String toString() {
			return "Response [state=" + state + ", goodsExchangeId="
					+ goodsExchangeId + ", description=" + description + "]";
		}
		
	
	}
}
