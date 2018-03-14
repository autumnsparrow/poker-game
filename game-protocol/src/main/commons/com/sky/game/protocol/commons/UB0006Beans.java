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
public class UB0006Beans {

	/**
	 * 
	 */
	public UB0006Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UB0006")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		int backMoney;
		public int getBackMoney() {
			return backMoney;
		}
		public void setBackMoney(int backMoney) {
			this.backMoney = backMoney;
		}
		public Request(int backMoney) {
			super();
			this.backMoney = backMoney;
		}
		@Override
		public String toString() {
			return "Request [backMoney=" + backMoney + "]";
		}
		
	}

	@HandlerResponseType(responsecode = "BU0006", transcode = "UB0006")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	int state;
	int backMoney;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getBackMoney() {
		return backMoney;
	}
	public void setBackMoney(int backMoney) {
		this.backMoney = backMoney;
	}
	public Response(int state, int backMoney) {
		super();
		this.state = state;
		this.backMoney = backMoney;
	}
	@Override
	public String toString() {
		return "Response [state=" + state + ", backMoney=" + backMoney + "]";
	}
	
	
  }
}
