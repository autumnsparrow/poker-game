/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.AchievementShow;

/**
 * @author Administrator
 *
 */
public class GU0026Beans {

	/**
	 * 
	 */
	public GU0026Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0026")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}

	@HandlerResponseType(responsecode = "UG0026", transcode = "GU0026")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<AchievementShow> achementList = new ArrayList<AchievementShow>();
		
		public List<AchievementShow> getAchementList() {
			return achementList;
		}
		public void setAchementList(List<AchievementShow> achementList) {
			this.achementList = achementList;
		}
	}
}
