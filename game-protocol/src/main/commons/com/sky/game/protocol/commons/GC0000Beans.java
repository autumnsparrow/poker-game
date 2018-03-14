/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;

/**
 * 
 * Configuration Beans.
 * This jar should be loaded by game-configuration.
 * 
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class GC0000Beans {

	
	@HandlerNamespaceExtraType(namespace="LLGameConfiguration")
	public static class Extra {
		String deviceId;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		
	}
	
	/**
	 * 
	 */
	public GC0000Beans() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerRequestType(transcode="GC0001")
	public static class ChannelConfigurationRequest implements IIdentifiedObject {
		// channel id
		Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		
	}
	
	
	@HandlerResponseType(transcode="GC0001",responsecode="CG0001")
	public static class ChannelConfigurationResponse{
		Long id;
		List<Integer> categories;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public List<Integer> getCategories() {
			return categories;
		}
		public void setCategories(List<Integer> categories) {
			this.categories = categories;
		}
		
	}
	
	
	
	
	
	
	/**
	 * GC0002
	 * : fetching the configuration by channeld id and tournament type.
	 * 
	 * LLGameLandlord  ---->  GameConfiguration
	 * 
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GC0002")
	public static class ConfigurationRequest implements IIdentifiedObject{
		// channel id
		Long id;
		// tournament type.
		int category;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public int getCategory() {
			return category;
		}

		public void setCategory(int category) {
			this.category = category;
		}
		
		
		
		
	}
	@HandlerResponseType(transcode="GC0002",responsecode="CG0001")
	public static class ConfigurationResponse{
		String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
		
		
	}

}
