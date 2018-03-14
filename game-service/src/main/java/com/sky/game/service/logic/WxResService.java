package com.sky.game.service.logic;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sky.game.context.util.GameUtil;

@Service
public class WxResService {
	public WxResService() {
		
	}
	public int resOrder(String order){
		int state=1;
		try {
			InputStream is = new ByteArrayInputStream(order.getBytes("UTF-8"));
			 XmlMapper xmlMapper=new XmlMapper();
		     HashMap<String,Object> map=xmlMapper.readValue(is, HashMap.class);
		     String str=(String) map.get("result_code");
		     if(str.equals("success")){
		    	 state=1;
		     }else{
		    	 state=-1;
		     }
		     
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return state;
		
	}
	
}
