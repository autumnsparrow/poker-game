/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.PrivilegeShow;

/**
 * @author Administrator
 *
 */
public class UC0008Beans {

	/**
	 * 
	 */
	public UC0008Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="UC0008")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@HandlerResponseType(responsecode="CU0008",transcode="UC0008")
	public static class Response{
		
		PrivilegeShow privilegeShow;
		
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}


		public PrivilegeShow getPrivilegeShow() {
			return privilegeShow;
		}


		public void setPrivilegeShow(PrivilegeShow privilegeShow) {
			this.privilegeShow = privilegeShow;
		}

		
	}
}
