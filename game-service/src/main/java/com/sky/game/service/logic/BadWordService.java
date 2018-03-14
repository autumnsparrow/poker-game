package com.sky.game.service.logic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sky.game.service.util.GameServiceUtil;


@Service
public class BadWordService {
	
	
	public BadWordService() {
		// TODO Auto-generated constructor stub
	}
  
    public boolean selectWords(List<String> keywords,String name) {
    	String str;
    	for(int i=0;i<keywords.size();i++){
    		str=keywords.get(i);
    		if(name.contains(str)){
    			return true;
    		}
    	}
			return false;
		}

    public boolean initfiltercode(String name) throws JsonParseException, JsonMappingException, IOException {
    	XmlMapper xmlMapper=new XmlMapper();
    	InputStream is=GameServiceUtil.class.getResourceAsStream("/META-INF/random/badword.xml");
    	LinkedList<String> keywords=xmlMapper.readValue(is, LinkedList.class);
       boolean bl= this.selectWords(keywords,name);
       return bl;
    }
}