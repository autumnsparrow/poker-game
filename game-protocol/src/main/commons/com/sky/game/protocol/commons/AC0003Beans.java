/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.MyAuctionGoods;
/**
 * @author Administrator
 *
 * 我的拍买行  Beans
 */
public class AC0003Beans {

	/**
	 * 
	 */
	public AC0003Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "AC0003")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@HandlerResponseType(responsecode="CA0003",transcode="AC0003")
	public static class Response{
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		List<MyAuctionGoods> myAuctionList;

		public List<MyAuctionGoods> getMyAuctionList() {
			return myAuctionList;
		}

		public void setMyAuctionList(List<MyAuctionGoods> myAuctionList) {
			this.myAuctionList = myAuctionList;
		}

	}
}
