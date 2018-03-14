/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.Goods;

/**
 * @author rqz
 *
 */
public class GU0006Beans {

	/**
	 * 
	 */
	public GU0006Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="GU0006")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
	}
	@HandlerResponseType(responsecode="UG0006",transcode="GU0006")
	public static class Response{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		List<Goods> list = new ArrayList<Goods>();

		public List<Goods> getList() {
			return list;
		}

		public  void setList(List<Goods> list) {
			this.list = list;
		}

		public Response(List<Goods> list) {
			super();
			this.list = list;
		}

		@Override
		public String toString() {
			return "Goodslist [list=" + Arrays.toString(list.toArray(new Goods[]{})) + "]";
		}
		
		
	}
	
}
