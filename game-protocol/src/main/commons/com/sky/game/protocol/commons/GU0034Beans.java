/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.HashMap;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
/**
 * @author Administrator
 *
 */
public class GU0034Beans {

	/**
	 * 
	 */
	public GU0034Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0034")
	public static class Request extends BaseRequest {
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		long id;
		String channelId;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getChannelId() {
			return channelId;
		}
		public void setChannelId(String channelId) {
			this.channelId = channelId;
		}
	}

	@HandlerResponseType(responsecode = "UG0034", transcode = "GU0034")
	public static class Response {
		//PaymentResponse paymentResponse;
    
		HashMap<String,Object> orderInfoMap;

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HashMap<String, Object> getOrderInfoMap() {
		return orderInfoMap;
	}

	public void setOrderInfoMap(HashMap<String, Object> orderInfoMap) {
		this.orderInfoMap = orderInfoMap;
	}
	};
}
