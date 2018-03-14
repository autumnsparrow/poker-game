package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.GoldExchange;

public class GU0009Beans {

	public GU0009Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0009")
	public static class Request extends BaseRequest {
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
}
	@HandlerResponseType(responsecode="UG0009",transcode="GU0009")
	public static class Response{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<GoldExchange> goldExchangeList = new ArrayList<GoldExchange>();
		public List<GoldExchange> getGoldExchangeList() {
			return goldExchangeList;
		}
		public void setGoldExchangeList(List<GoldExchange> goldExchangeList) {
			this.goldExchangeList = goldExchangeList;
		}
		@Override
		public String toString() {
			return "Response [goldExchangeList=" + goldExchangeList + "]";
		}
		public Response(List<GoldExchange> goldExchangeList) {
			super();
			this.goldExchangeList = goldExchangeList;
		}
	}
}
