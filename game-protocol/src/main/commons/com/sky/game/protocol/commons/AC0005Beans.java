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
public class AC0005Beans {

	/**
	 * 
	 */
	public AC0005Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "AC0005")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		int roomId;
		public int getRoomId() {
			return roomId;
		}
		public void setRoomId(int roomId) {
			this.roomId = roomId;
		}
		
	}
	
	@HandlerResponseType(responsecode="CA0005",transcode="AC0005")
	public static class Response{
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		int state;
		
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
	}
}
