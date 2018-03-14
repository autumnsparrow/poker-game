/**
 * 
 */
package com.sky.game.protocol.commons;
import java.util.HashMap;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

/**
 * @author Administrator
 *
 */
public class UL0002Beans {

	/**
	 * 
	 */
	public UL0002Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UL0002")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		long itemId;
		int value;
		public long getItemId() {
			return itemId;
		}
		public void setItemId(long itemId) {
			this.itemId = itemId;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public Request(long itemId, int value) {
			super();
			this.itemId = itemId;
			this.value = value;
		}
		@Override
		public String toString() {
			return "Request [itemId=" + itemId + ", value=" + value + "]";
		}
		
	}

	@HandlerResponseType(responsecode = "LU0002", transcode = "UL0002")
	public static class Response extends BaseRequest{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		public HashMap<String, Object> getMap() {
			return map;
		}
		public void setMap(HashMap<String, Object> map) {
			this.map = map;
		}
		public Response(HashMap<String, Object> map) {
			super();
			this.map = map;
		}
		@Override
		public String toString() {
			return "Response [map=" + map + "]";
		}
  }
}
