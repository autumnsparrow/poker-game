/**
 * 
 */
package com.sky.game.protocol.commons;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.BankShow;

/**
 * @author Administrator
 *
 */
public class UB0001Beans {

	/**
	 * 
	 */
	public UB0001Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UB0001")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	@HandlerResponseType(responsecode = "BU0001", transcode = "UB0001")
	public static class Response {
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	BankShow bankShow;

	public BankShow getBankShow() {
		return bankShow;
	}

	public void setBankShow(BankShow bankShow) {
		this.bankShow = bankShow;
	}

	public Response(BankShow bankShow) {
		super();
		this.bankShow = bankShow;
	}

	@Override
	public String toString() {
		return "Response [bankShow=" + bankShow + "]";
	}
  }
}
