/**
 * 
 */
package com.sky.game.context.configuration.ice;

import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sky.game.context.configuration.GameContxtConfigurationLoader;
import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
@Service
public class IceServiceConfigLoader {

	String configuration;

	@Autowired
	public IceServiceConfigLoader(
			@Value("${ice.config.url}") String configuration) {
		super();
		this.configuration = configuration;
		// init();
	}

	/**
	 * 
	 */
	public IceServiceConfigLoader() {
		// TODO Auto-generated constructor stub
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	// begin to init
	public void init() {
		InputStream is = null;
		try {
			is = GameUtil.getInputStream(configuration);

			IceServiceConfigIndex index = GameContxtConfigurationLoader
					.loadXmlConfiguration(is, IceServiceConfigIndex.class);
			// load all the configuration.
			Map<String, IceServiceConfig> configMap = IceServiceConfigRegstry.configMap;// GameUtil.getMap();
			for (IceServiceConfigInfo info : index.getIndex()) {
				is = GameUtil.getInputStream(info.url);
				IceServiceConfig iceServiceConfig = GameContxtConfigurationLoader
						.loadXmlConfiguration(is, IceServiceConfig.class);
				configMap.put(info.name, iceServiceConfig);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// load all the service.

	}

}
