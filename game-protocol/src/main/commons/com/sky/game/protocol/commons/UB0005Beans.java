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
public class UB0005Beans {

	/**
	 * 
	 */
	public UB0005Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UB0005")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		int lend;
		public int getLend() {
			return lend;
		}
		public void setLend(int lend) {
			this.lend = lend;
		}
		public Request(int lend) {
			super();
			this.lend = lend;
		}
		@Override
		public String toString() {
			return "Request [lend=" + lend + "]";
		}
	}

	@HandlerResponseType(responsecode = "BU0005", transcode = "UB0005")
	public static class Response {
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	int state;
	int lend;
	String loanDate;
	String refundDate;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getLend() {
		return lend;
	}
	public void setLend(int lend) {
		this.lend = lend;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}
	public Response(int state, int lend, String loanDate, String refundDate) {
		super();
		this.state = state;
		this.lend = lend;
		this.loanDate = loanDate;
		this.refundDate = refundDate;
	}
	@Override
	public String toString() {
		return "Response [state=" + state + ", lend=" + lend + ", loanDate="
				+ loanDate + ", refundDate=" + refundDate + "]";
	}
	
  }
}
