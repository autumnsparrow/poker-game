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
public class GU0012Beans {

	/**
	 * 
	 */
	public GU0012Beans() {
		// TODO Auto-generated constructor stub
	}

	@HandlerRequestType(transcode = "GU0012")
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

		

	}

	@HandlerResponseType(responsecode = "UG0012", transcode = "GU0012")
	public static class Response {

		int state;
		String description;
        long pirceExchangeId;
        
		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public long getPirceExchangeId() {
			return pirceExchangeId;
		}

		public void setPirceExchangeId(long pirceExchangeId) {
			this.pirceExchangeId = pirceExchangeId;
		}

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	};
}
