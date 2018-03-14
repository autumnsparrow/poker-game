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
public class UF0001Beans {

	/**
	 * 
	 */
	public UF0001Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UF0001")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String title;
		String description;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Request(String title, String description) {
			super();
			this.title = title;
			this.description = description;
		}
		@Override
		public String toString() {
			return "Request [title=" + title + ", description=" + description
					+ "]";
		}
		
	}

	@HandlerResponseType(responsecode = "FU0001", transcode = "UF0001")
	public static class Response extends BaseRequest{
		
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
		public Response(int state) {
			super();
			this.state = state;
		}
		@Override
		public String toString() {
			return "Response [state=" + state + "]";
		}
		
		
	}
}
