package com.sky.game.service.logic;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.configuration.GameContxtConfigurationLoader;
import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserReport;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;
import com.sky.game.service.persistence.UserReportLogMapper;
import com.sky.game.service.persistence.UserReportMapper;
import com.sky.game.service.report.ReportItem;
import com.sky.game.service.report.ReportItem.Reports;


@Service
public class UserReportService {
	
@Autowired
UserReportMapper userReportMapper;

@Autowired
UserItemsMapper userItemsMapper;

@Autowired
UserReportLogMapper userReportLogMapper;

@Autowired
UserItemLogMapper userItemLogMapper;

	/**
	 *  签到模块
	 */



	public UserReportService() {
//		 TODO Auto-generated constructor stub 
		String url ="lottery:"+1;
		GameUtil.gameLife(url, "0 1 0 1 * ? ",this,"updateReportDays");//签到每月清空签到记录和连续抽奖次数
		
	}
	
	
	InputStream is=ReportItem.class.getResourceAsStream("/META-INF/report.json");
	Reports reports =GameContxtConfigurationLoader.loadConfiguration(is, Reports.class);  
	int continucus=0;
	int value=0;
	
	public HashMap<String,Object> selectReport(long userId){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		Date date=new Date();  
		String str=sdf.format(date);  
		List<String> listDate = new ArrayList<String>();
		if(userReportLogMapper.selectCheckDate(userId)!=null){
		listDate=userReportLogMapper.selectCheckDate(userId);
		}
		int state=1;
		for(int i=0;i<listDate.size();i++){
			if(listDate.get(i).contains(str)){
				state=-1;
			}
			else{
				state=1;
			}
		}
		String item = null;
		UserReport userReport = userReportMapper.selectByUserId(userId);
		Date date2 = new Date();
		Calendar calendar = new GregorianCalendar(); 
		List<String> listDay=new ArrayList<String>();
		for(int i=0;i<listDate.size();i++){
			listDay.add(listDate.get(i).substring(8,10));
		}
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(userReportMapper.selectByUserId(userId)!=null){
		continucus=userReportMapper.selectContinucus(userId);
		}
		else{
			continucus=0;
		}
		if(userReport!=null){
			Date date3 = userReport.getLastDate();
		    calendar.setTime(date3); 
		    calendar.add(calendar.DATE,1);
		    Date date1=calendar.getTime();   
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			String ds1 = sdf1.format(date2);
			String ds2 = sdf1.format(date1);
			String ds3 = sdf1.format(date3);
			
			if(continucus<reports.getItems().size()){
				continucus=continucus+1;
				if(ds1.equals(ds2)){
					value=reports.getItems().get(continucus).getValue();
				}
				else{
					value=50;
					
				}
				
			}
			
		}
		else{
			continucus=1;
			value=reports.getItems().get(continucus).getValue();
			item=reports.getItems().get(continucus).getItem();
		}
		item=reports.getItems().get(continucus).getItem();
		
		map.put("listDay", listDay);
		map.put("reports", reports);
		map.put("value", value);
		map.put("item",item);
		map.put("state", state);
		return map;
	}
	
	/**
	 *   签到
	 */
	@Transactional
	public HashMap<String,Object> updateReportGold(long userId){
		UserReport userReport = userReportMapper.selectByUserId(userId);
		Date date = new Date();
		Calendar calendar = new GregorianCalendar(); 
		if(userReport!=null){
		Date date3 = userReport.getLastDate();
	    calendar.setTime(date3); 
	    calendar.add(calendar.DATE,1);
	    Date date1=calendar.getTime();   
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String ds1 = sdf1.format(date);
		String ds2 = sdf1.format(date1);
		if(ds1.equals(ds2)) {                                      //天数是否连续
			continucus=userReport.getContinuousDays();
			continucus=continucus+1;	
			}
			else{
				continucus=1;
			}
		}
		if(continucus>reports.getItems().size()){    //签到次数超过7天变成1天
			continucus=1;
		}
		value=reports.getItems().get(continucus).getValue();
		int days = reports.getItems().get(continucus).getDays();
		Timestamp ts = new Timestamp(System.currentTimeMillis());   
	    String time = "";   
	    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	    try {    
	        time = sdf.format(ts);   
	    } catch (Exception e) {   
	        e.printStackTrace();   
	    } 
		HashMap<String,Object> map1=new HashMap<String,Object>();
		map1.put("value", value);
		map1.put("userId", userId);
		map1.put("time", time);
		HashMap<String,Object> map2=new HashMap<String,Object>();
		map2.put("count", value);
		map2.put("userId", userId);
		HashMap<String,Object> map3=new HashMap<String,Object>();
		map3.put("ts", ts);
		map3.put("userId", userId);
		map3.put("days", days);
		HashMap<String,Object> map4=new HashMap<String,Object>();
		map4.put("ts", ts);
		map4.put("userId", userId);
		int coin =0;
		if(userItemsMapper.selectUserCoin(userId)!=null){
			coin=userItemsMapper.selectUserCoin(userId).getItemValue();
		}
		userItemsMapper.updateUserCoin(map2);
		userReportLogMapper.intsertReportLog(map1);
		
		if(userReportMapper.selectByUserId(userId)!=null){
		userReportMapper.updateContinucusDays(map3);
		}
		else{
			userReportMapper.insertContinucusDays(map4);
		}
		int laterDays=userReportMapper.selectContinucus(userId)+1;
		int laterValue=0;
		laterValue = reports.getItems().get(laterDays).getValue();
		String laterItem = reports.getItems().get(laterDays).getItem();
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("laterValue", laterValue);
		map.put("laterItem", laterItem);
		insertUserItemLog(userId,1001,value,coin);
		return map;
	}

	
	public void insertUserItemLog(long userId,long itemId,int value,int itemValue){
    	UserItemLog userItemLog=new UserItemLog();
    	userItemLog.setItemId(itemId);
    	userItemLog.setValue(value);
    	userItemLog.setItemValue(itemValue);
    	userItemLog.setResumType("签到");
    	userItemLog.setUserAccountId(userId);
    	userItemLog.setUpdateTime(new Date());
    	userItemLogMapper.insertSelective(userItemLog);
	}
	
	public void updateReportDays(){
		userReportLogMapper.deleteReportsLog();
		userReportMapper.updateReportTimmer();
	}
	
	
}