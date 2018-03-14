/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.Detail;

/**
 * @author Administrator
 *
 */
public class UC0009Beans {

	/**
	 * 
	 */
	public UC0009Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="UC0009")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		int page;
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
	}
	
	@HandlerResponseType(responsecode="CU0009",transcode="UC0009")
	public static class Response{
		
		
		List<Detail> detailList=new ArrayList<Detail>();	

		
		public List<Detail> getDetailList() {
			return detailList;
		}

		public void setDetailList(List<Detail> detailList) {
			this.detailList = detailList;
		}

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Response(List<Detail> detailList) {
			super();
			this.detailList = detailList;
		}

		
	}
}
