package com.sky.game.service.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.service.domain.SysMessage;
import com.sky.game.service.persistence.SystemMessageMapper;
import com.sky.game.service.persistence.UserMessageMapper;



@Service
public class AnnounceService {

@Autowired
SystemMessageMapper systemMessageMapper;
@Autowired
UserMessageMapper userMessageMapper;
	
	public AnnounceService() {
		// TODO Auto-generated constructor stub
	}
	public List<SysMessage> selectMessage(long userId,long lastId){
		List<SysMessage> list = new ArrayList<SysMessage>();
		if(lastId==systemMessageMapper.selectDecId(userId)){
			return list;
		}else{
			return systemMessageMapper.selectMessage(userId);
		}
	}
}
