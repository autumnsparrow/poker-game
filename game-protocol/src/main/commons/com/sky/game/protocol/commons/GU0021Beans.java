/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.AwardRecord;

/**
 * @author Administrator
 *
 */
public class GU0021Beans {

	/**
	 * 
	 */
	public GU0021Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0021")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}

	@HandlerResponseType(responsecode = "UG0021", transcode = "GU0021")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<AwardRecord> awardRecord=new ArrayList<AwardRecord>();
		public List<AwardRecord> getAwardRecord() {
			return awardRecord;
		}
		public void setAwardRecord(List<AwardRecord> awardRecord) {
			this.awardRecord = awardRecord;
		}
		public Response(List<AwardRecord> awardRecord) {
			super();
			this.awardRecord = awardRecord;
		}
		@Override
		public String toString() {
			return "Response [awardRecord=" + awardRecord + "]";
		}
	};
}
