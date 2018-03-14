/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseResponse;
import com.sky.game.service.domain.IndianaStar;

/**
 * @author Administrator
 *
 */
public class IS0001Beans {

	/**
	 * 
	 */
	public IS0001Beans() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerRequestType(transcode="IS0001")
	public static class Request{

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}		
		
	}
	
	@HandlerResponseType(responsecode="SI0001",transcode="IS0001")
	public static class Response extends BaseResponse{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<IndianaStar> indianaStarList;
		public List<IndianaStar> getIndianaStarList() {
			return indianaStarList;
		}
		public void setIndianaStarList(List<IndianaStar> indianaStarList) {
			this.indianaStarList = indianaStarList;
		}
	};
}
