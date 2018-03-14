package com.sky.game.service.logic;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.service.persistence.UserFeedbackMapper;




@Service
public class UserFeedbackService {
	
@Autowired
UserFeedbackMapper userFeedbackMapper;

	/**
	 *  意见反馈
	 */

	public UserFeedbackService() {
		// TODO Auto-generated constructor stub
	}
	public void insertFeedback(long userId,String title,String description){
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("title", title);
		map.put("description", description);
		userFeedbackMapper.insertFeedback(map);
	}
}