/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.BaseResponse;
import com.sky.game.service.domain.AuctionGoods;
/**
 * @author Administrator
 *
 * 拍卖行  Beans
 */
public class AC0001Beans {

	/**
	 * 
	 */
	public AC0001Beans() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 需要token
	 * {@see BaseRequest}
	 *
	 */
	@HandlerRequestType(transcode = "AC0001")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
	/**
	 * 
	 * 返回 {@see AuctionGoods} 列表 
	 *
	 */
	@HandlerResponseType(responsecode="CA0001",transcode="AC0001")
	public static class Response extends BaseResponse{
		
		List<AuctionGoods> auctionGoodsList;
		

		public List<AuctionGoods> getAuctionGoodsList() {
			return auctionGoodsList;
		}

		public void setAuctionGoodsList(List<AuctionGoods> auctionGoodsList) {
			this.auctionGoodsList = auctionGoodsList;
		}

		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Response(List<AuctionGoods> auctionGoodsList) {
			super();
			this.auctionGoodsList = auctionGoodsList;
		}

		@Override
		public String toString() {
			return "Response [auctionGoodsList=" + auctionGoodsList + "]";
		}
	}
}
