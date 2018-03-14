/**
 * 
 */
package com.sky.game.protocol.commons;
import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.SysActivity;

/**
 * @author Administrator
 *
 */
public class UA0002Beans {

	/**
	 * 
	 */
	public UA0002Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UA0002")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	@HandlerResponseType(responsecode = "AU0002", transcode = "UA0002")
	public static class Response {
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	int state;
	String description;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Response(int state, String description) {
		super();
		this.state = state;
		this.description = description;
	}
	@Override
	public String toString() {
		return "Response [state=" + state + ", description=" + description
				+ "]";
	}
	   
	   
	   
  }
}
