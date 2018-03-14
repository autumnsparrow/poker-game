/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.MyPrice;

/**
 * @author Administrator
 *
 */
public class GU0016Beans {

	/**
	 * 
	 */
	public GU0016Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0016")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
	}

	@HandlerResponseType(responsecode = "UG0016", transcode = "GU0016")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
       List<MyPrice> myPrices =new ArrayList<MyPrice>();
       
	public List<MyPrice> getMyPrices() {
		return myPrices;
	}
	public void setMyPrices(List<MyPrice> myPrices) {
		this.myPrices = myPrices;
	}
	public Response(List<MyPrice> myPrices) {
		super();
		this.myPrices = myPrices;
	}
	@Override
	public String toString() {
		return "Response [myPrices=" + myPrices + "]";
	}
	};
}
