/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.StoreShow;

/**
 * @author Administrator
 *
 */
public class GU0004Beans {

	/**
	 * 
	 */
	public GU0004Beans() {
		// TODO Auto-generated constructor stub
	}

	@HandlerRequestType(transcode = "GU0004")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	@HandlerResponseType(responsecode = "UG0004", transcode = "GU0004")
	public static class Response {

		List<StoreShow> chargeRateList = new ArrayList<StoreShow>();

		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}


		public List<StoreShow> getChargeRateList() {
			return chargeRateList;
		}

		public void setChargeRateList(List<StoreShow> chargeRateList) {
			this.chargeRateList = chargeRateList;
		}
	};

}
