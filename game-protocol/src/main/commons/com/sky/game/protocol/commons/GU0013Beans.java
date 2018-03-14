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
public class GU0013Beans {

	/**
	 * 
	 */
	public GU0013Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0013")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
        String orderId;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public Request(String orderId) {
			super();
			this.orderId = orderId;
		}

		@Override
		public String toString() {
			return "Request [orderId=" + orderId + "]";
		}
        
	}

	@HandlerResponseType(responsecode = "UG0013", transcode = "GU0013")
	public static class Response {
		int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public Response(int state) {
			super();
			this.state = state;
		}

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "Response [state=" + state + "]";
		}
	};
}
