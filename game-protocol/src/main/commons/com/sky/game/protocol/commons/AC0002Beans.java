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
 *  拍买行--竞拍  Beans
 */
public class AC0002Beans {

	/**
	 * 
	 */
	public AC0002Beans() {
		// TODO Auto-generated constructor stub
	}

	@HandlerRequestType(transcode = "AC0002")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		long id	;            //	物品的id
		int count;           //  竞拍加价
		String bankPassword	;//	银行密码
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public String getBankPassword() {
			return bankPassword;
		}
		public void setBankPassword(String bankPassword) {
			this.bankPassword = bankPassword;
		}
	}
	
	@HandlerResponseType(responsecode="CA0002",transcode="AC0002")
	public static class Response{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		int state;	//	状态
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
		
	}
}