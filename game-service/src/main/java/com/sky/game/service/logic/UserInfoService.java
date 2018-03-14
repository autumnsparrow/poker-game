package com.sky.game.service.logic;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.service.domain.UserInfo;
import com.sky.game.service.persistence.UserBaseMapper;
@Service
public class UserInfoService {
	/**
	 * 
	 */
	
	@Autowired
	UserBaseMapper userBaseMapper;
	

	public UserBaseMapper getUserBaseMapper() {
		return userBaseMapper;
	}
	public void setUserBaseMapper(UserBaseMapper userBaseMapper) {
		this.userBaseMapper = userBaseMapper;
	}
	public UserInfoService() {
		// TODO Auto-generated constructor stub
	}
	public List<UserInfo> selectUserInfo(long id)
	{
		return userBaseMapper.selectUserInfo(id);
	}
	
	
}
