/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.BaseResponse;
import com.sky.game.internal.service.payment.domain.WxPayOrder;
import com.sky.game.service.domain.BankShow;
import com.sky.game.service.domain.TaskSet;


/**
 * @author Administrator
 *
 */
public class WX0001Beans {

	/**
	 * 
	 */
	public WX0001Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="WX0001")
	public static class Request  extends BaseRequest{
		
		String ip;
		long id;
		
		public Request() {
			super();
		}
	
		public String toString() {
			return "Request [ip=" + ip + ", id=" + id + "]";
		}

	public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
	}

	@HandlerResponseType(responsecode="XW0001",transcode="WX0001")
	public static class Response extends BaseResponse{
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		WxPayOrder wxPayOrder = new WxPayOrder();

		public WxPayOrder getWxPayOrder() {
			return wxPayOrder;
		}

		public void setWxPayOrder(WxPayOrder wxPayOrder) {
			this.wxPayOrder = wxPayOrder;
		}

		public Response(WxPayOrder wxPayOrder) {
			super();
			this.wxPayOrder = wxPayOrder;
		}

		@Override
		public String toString() {
			return "Response [wxPayOrder=" + wxPayOrder + "]";
		}
		
		
	}
	
}

