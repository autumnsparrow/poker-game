/**
 * 
 */
package com.sky.game.protocol.commons;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.LoanShow;

/**
 * @author Administrator
 *
 */
public class UB0004Beans {

	/**
	 * 
	 */
	public UB0004Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UB0004")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
	}

	@HandlerResponseType(responsecode = "BU0004", transcode = "UB0004")
	public static class Response {
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	LoanShow loanShow;

	public Response(LoanShow loanShow) {
		super();
		this.loanShow = loanShow;
	}

	public LoanShow getLoanShow() {
		return loanShow;
	}

	public void setLoanShow(LoanShow loanShow) {
		this.loanShow = loanShow;
	}

	@Override
	public String toString() {
		return "Response [loanShow=" + loanShow + "]";
	}
	
  }
}
