/**
 * 
 */
package com.sky.game.context.configuration.ice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sparrow
 *
 */
public class IceServiceConfigRegstry {

	/**
	 * 
	 */
	public IceServiceConfigRegstry() {
		// TODO Auto-generated constructor stub
	}
	public static Map<String,IceServiceConfig> configMap=new HashMap<String,IceServiceConfig>();
	public static IceServiceConfig getServiceConfig(String service){
		return configMap.get(service);
	}

}
