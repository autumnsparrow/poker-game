/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.BaseResponse;

/**
 * @author Administrator
 *
 */
public class GU0028Beans {

	/**
	 * 
	 */
	public GU0028Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="GU0028")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}		
		String cardId;//卡号
		
		public String getCardId() {
			return cardId;
		}
		public void setCardId(String cardId) {
			this.cardId = cardId;
		}
		
	}
	
	@HandlerResponseType(responsecode="UG0028",transcode="GU0028")
	public static class Response extends BaseResponse{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		int state	;            //状态
		String description	;    //描述
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
	};
}
