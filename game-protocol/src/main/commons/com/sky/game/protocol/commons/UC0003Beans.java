/**
 * 
 */
package com.sky.game.protocol.commons;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

/**
 * @author Administrator
 *
 */
public class UC0003Beans {

	/**
	 * 
	 */
	public UC0003Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0003")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String newNickName;
		public String getNewNickName() {
			return newNickName;
		}
		public void setNewNickName(String newNickName) {
			this.newNickName = newNickName;
		}
		public Request(String newNickName) {
			super();
			this.newNickName = newNickName;
		}
		@Override
		public String toString() {
			return "Request [newNickName=" + newNickName + "]";
		}
		
		
	}

	@HandlerResponseType(responsecode = "CU0003", transcode = "UC0003")
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
