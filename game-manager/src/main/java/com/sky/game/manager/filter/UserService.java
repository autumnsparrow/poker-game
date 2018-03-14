package com.sky.game.manager.filter;
import org.springframework.roo.addon.layers.service.RooService;

import com.sky.game.manager.model.security.Channel;

@RooService(domainTypes = { com.sky.game.manager.model.security.ChannelPrincipalAssignment.class })
public interface UserService {
	
	public Channel getChannelBySecurityPrincipal();
	
}
