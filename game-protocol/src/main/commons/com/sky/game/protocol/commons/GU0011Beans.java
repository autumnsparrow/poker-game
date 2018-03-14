/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.PriceExchange;

/**
 * @author Administrator
 *
 */
public class GU0011Beans {

	/**
	 * 
	 */
	public GU0011Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0011")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	@HandlerResponseType(responsecode = "UG0011", transcode = "GU0011")
	public static class Response {
		List<PriceExchange> priceExchangeList=new ArrayList<PriceExchange>();

		public List<PriceExchange> getPriceExchangeList() {
			return priceExchangeList;
		}

		public void setPriceExchangeList(List<PriceExchange> priceExchangeList) {
			this.priceExchangeList = priceExchangeList;
		}

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Response(List<PriceExchange> priceExchangeList) {
			super();
			this.priceExchangeList = priceExchangeList;
		}

		@Override
		public String toString() {
			return "Response [priceExchangeList=" + priceExchangeList + "]";
		}

	};

}
