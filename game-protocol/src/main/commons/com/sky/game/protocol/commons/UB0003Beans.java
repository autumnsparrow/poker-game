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
public class UB0003Beans {

	/**
	 * 
	 */
	public UB0003Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UB0003")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		int takeMoney;
		String bankPw;
		public int getTakeMoney() {
			return takeMoney;
		}
		public void setTakeMoney(int takeMoney) {
			this.takeMoney = takeMoney;
		}
		public String getBankPw() {
			return bankPw;
		}
		public void setBankPw(String bankPw) {
			this.bankPw = bankPw;
		}
		public Request(int takeMoney, String bankPw) {
			super();
			this.takeMoney = takeMoney;
			this.bankPw = bankPw;
		}
		@Override
		public String toString() {
			return "Request [takeMoney=" + takeMoney + ", bankPw=" + bankPw
					+ "]";
		}
	}

	@HandlerResponseType(responsecode = "BU0003", transcode = "UB0003")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	int state;
	int takeMoney;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getTakeMoney() {
		return takeMoney;
	}
	public void setTakeMoney(int takeMoney) {
		this.takeMoney = takeMoney;
	}
	public Response(int state, int takeMoney) {
		super();
		this.state = state;
		this.takeMoney = takeMoney;
	}
	@Override
	public String toString() {
		return "Response [state=" + state + ", takeMoney=" + takeMoney + "]";
	}
  }
}
