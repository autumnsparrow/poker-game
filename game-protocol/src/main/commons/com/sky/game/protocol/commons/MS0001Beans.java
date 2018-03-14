/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;

/**
 * @author Administrator
 *
 */
public class MS0001Beans {

	/**
	 * 
	 */
	public MS0001Beans() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerRequestType(transcode="MS0001")
	public static class Request{
		private String deviceId;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

		private Request(String deviceId) {
			super();
			this.deviceId = deviceId;
		}
		
		public static Request getRequest(String deviceId){
			return new Request(deviceId);
		}
		
	}
	@HandlerNamespaceExtraType(namespace="MinaServer")
	public static class Extra {
		String deviceId;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		
	}

}
