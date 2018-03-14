/**
 * 
 * @Date:Nov 14, 2014 - 2:19:00 PM
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
 * 
 * Leave the current table.
 * 
 * 
 * @author sparrow
 *
 */
public class GT0004Beans {

	/**
	 * 
	 */
	public GT0004Beans() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerRequestType(transcode="GT0004")
	public static class Request extends BaseRequest implements IIdentifiedObject{
		Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
	}

}
