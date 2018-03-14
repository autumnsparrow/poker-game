package com.sky.game.manager.filter;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.sky.game.manager.model.security.Channel;
import com.sky.game.manager.model.security.ChannelPrincipalAssignment;
import com.sky.game.manager.model.security.Principal;

public class UserServiceImpl implements UserService {

	@Override
	public Channel getChannelBySecurityPrincipal() {
		// TODO Auto-generated method stub
		Principal username=(Principal)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		//ChannelPrincipalAssignment.find
		List<ChannelPrincipalAssignment> assignments=ChannelPrincipalAssignment.findChannelPrincipalAssignmentsByPrinciple(username).getResultList();
		ChannelPrincipalAssignment assignment=null;
		if(assignments!=null)
			assignment=assignments.get(0);
			
		
		return assignment!=null?assignment.getChannel():null;
	}

}
