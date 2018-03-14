/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.DefaultHead;

/**
 * @author Administrator
 *
 */
public class GU0024Beans {

	/**
	 * 
	 */
	public GU0024Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0024")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
	}

	@HandlerResponseType(responsecode = "UG0024", transcode = "GU0024")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	   List<DefaultHead> iconList;

	public List<DefaultHead> getIconList() {
		return iconList;
	}

	public void setIconList(List<DefaultHead> iconList) {
		this.iconList = iconList;
	}
	};
}
