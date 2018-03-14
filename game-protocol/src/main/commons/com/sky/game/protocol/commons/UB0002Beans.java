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
public class UB0002Beans {

	/**
	 * 
	 */
	public UB0002Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UB0002")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		int saveMoney;
		public int getSaveMoney() {
			return saveMoney;
		}
		public void setSaveMoney(int saveMoney) {
			this.saveMoney = saveMoney;
		}
		public Request(int saveMoney) {
			super();
			this.saveMoney = saveMoney;
		}
		@Override
		public String toString() {
			return "Request [saveMoney=" + saveMoney + "]";
		}
	}

	@HandlerResponseType(responsecode = "BU0002", transcode = "UB0002")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	int state;
	int saveMoney;
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
	

	public int getSaveMoney() {
		return saveMoney;
	}

	public void setSaveMoney(int saveMoney) {
		this.saveMoney = saveMoney;
	}
	
	public Response(int state, int saveMoney) {
		super();
		this.state = state;
		this.saveMoney = saveMoney;
	}

	@Override
	public String toString() {
		return "Response [state=" + state + ", saveMoney=" + saveMoney + "]";
	}

	
  }
}
