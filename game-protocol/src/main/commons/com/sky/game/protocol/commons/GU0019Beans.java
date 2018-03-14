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
public class GU0019Beans {

	/**
	 * 
	 */
	public GU0019Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0019")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		long id;
		long itemId;
		String phone;	
		String validate;
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getValidate() {
			return validate;
		}
		public void setValidate(String validate) {
			this.validate = validate;
		}
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		
		public long getItemId() {
			return itemId;
		}
		public void setItemId(long itemId) {
			this.itemId = itemId;
		}
		
		public Request(long id, long itemId, String phone, String validate) {
			super();
			this.id = id;
			this.itemId = itemId;
			this.phone = phone;
			this.validate = validate;
		}
		@Override
		public String toString() {
			return "Request [phone=" + phone + ", validate=" + validate + "]";
		}
	}

	@HandlerResponseType(responsecode = "UG0019", transcode = "GU0019")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	    int state;
	    String description;
	    
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
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
	};
}
