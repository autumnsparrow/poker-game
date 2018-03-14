/**
 * 
 * @Date:Nov 21, 2014 - 2:19:57 PM
 * @Author: sparrow
 * @Project :game-protocol 
 * 
 *
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.domain.BaseRequest;

/**
 * @author sparrow
 *
 */
public class GT0006Beans {

	/**
	 * 
	 */
	public GT0006Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="GT0006")
	public static class Request extends BaseRequest implements IIdentifiedObject{
		Long id;
		
		int action;
		int chips;
		

		public int getAction() {
			return action;
		}

		public void setAction(int action) {
			this.action = action;
		}

		public int getChips() {
			return chips;
		}

		public void setChips(int chips) {
			this.chips = chips;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		
	}

}
