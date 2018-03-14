package com.sky.game.service.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.service.domain.GoldBillboard;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserBillboard;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.domain.UserLevel;
import com.sky.game.service.domain.WarReport;
import com.sky.game.service.persistence.GameDataStaticsMapper;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserExtraMapper;
import com.sky.game.service.persistence.UserItemsMapper;
import com.sky.game.service.persistence.UserLevelMapper;


@Service
public class BillboardService {

@SuppressWarnings("unused")
private static final Log logger = LogFactory.getLog(BillboardService.class);

@Autowired
UserAccountMapper userAccountMapper;
@Autowired
UserExtraMapper userExtraMapper;
@Autowired
UserLevelMapper userLevelMapper;
@Autowired
UserItemsMapper userItemsMapper;
@Autowired
GameDataStaticsMapper gameDataStaticsMapper;

    /**
     * 排行榜
     */
	
	public BillboardService() {
		// TODO Auto-generated constructor stub
	}
	
	public List<GoldBillboard> pagination(int type,int page){
		int startPage=0;
		int stopPage=10;
		for(int i=2;i<6;i++){
		if(page==i){
			startPage=page*stopPage-stopPage;
		}
		}
		List<GoldBillboard> list =new ArrayList<GoldBillboard>();
		if(type==1 && page<=5){
			list= selectGoldBillboard(startPage,stopPage);
		}
		if(type==2 && page<=5){
			list= selectCoinBillboard(startPage,stopPage);
		}
		if(type==3 && page<=5){
			list= selectExpBillboard(startPage,stopPage);
		}
		return list;

	}
	
	
	
	/**
	 * 排行榜元宝榜
	 */
	  public List<GoldBillboard> selectGoldBillboard(int startPage,int stopPage){
		  HashMap<String,Object> map1 = new HashMap<String,Object>();
		  map1.put("startPage", startPage);
		  map1.put("stopPage", stopPage);
		  List<GoldBillboard> list = new  ArrayList<GoldBillboard>();
		  List<UserItems> userItemsList = new ArrayList<UserItems>();
		  userItemsList= userItemsMapper.selectGoldboard(map1);  
		  
		  
		  
		  if(userItemsMapper.selectGoldboard(map1)!=null && userItemsMapper.selectGoldboard(map1).size()>0)
		  for(int i=0;i<userItemsMapper.selectGoldboard(map1).size();i++){
			  GoldBillboard goldBillboard = new GoldBillboard();
			  UserBillboard userBillboard = new UserBillboard();
			  userBillboard.setRank(startPage+i+1);
		  Map<PropertyTypes,UserExtra> map=userAccountMapper.selectByPrimaryId(userItemsList.get(i).getUserAccountId()).getPropertiesAsMap();	  
		  if(map.get(PropertyTypes.StartHead)!=null){
			  userBillboard.setIconImg(map.get(PropertyTypes.StartHead).getPropertyValue());
	    	}else{
	    		userBillboard.setIconImg("0");
	    	}
			if(map.get(PropertyTypes.NickName)!=null){
				userBillboard.setNickName(map.get(PropertyTypes.NickName).getPropertyValue());
	    	}else{
	    		userBillboard.setNickName("0");
	    	}
			if(map.get(PropertyTypes.CreditValue)!=null){
				userBillboard.setCreditValue(map.get(PropertyTypes.CreditValue).getPropertyValue());
	    	}else{
	    		userBillboard.setCreditValue("0");
	    	}
	    	if(map.get(PropertyTypes.DsFen)!=null){
	    		userBillboard.setDsFen(map.get(PropertyTypes.DsFen).getPropertyValue());
	    	}else{
	    		userBillboard.setDsFen("0");
	    	}
	    	if(map.get(PropertyTypes.MaxGot)!=null){
	    		userBillboard.setMaxGot(map.get(PropertyTypes.MaxGot).getPropertyValue());
	    	}else{
	    		userBillboard.setMaxGot("0");
	    	}
	    	if(map.get(PropertyTypes.MlValue)!=null){
	    		userBillboard.setMlValue(map.get(PropertyTypes.MlValue).getPropertyValue());
	    	}else{
	    		userBillboard.setMlValue("0");
	    	}
	    	if(map.get(PropertyTypes.RpValue)!=null){
	    		userBillboard.setRpValue(map.get(PropertyTypes.RpValue).getPropertyValue());
	    	}else{
	    		userBillboard.setRpValue("0");
	    	}
	    	if(map.get(PropertyTypes.TtFen)!=null){
	    		userBillboard.setTtFen(map.get(PropertyTypes.TtFen).getPropertyValue());
	    	}else{
	    		userBillboard.setTtFen("0");
	    	}
	    		userBillboard.setGold((userItemsMapper.selectGoldValue(map1).get(i)));
	    	
	    	long exp;
	    	if(map.get(PropertyTypes.Experience)==null){
	    		exp=0L;
	    	}else{
	    		exp=Long.valueOf(map.get(PropertyTypes.Experience).getPropertyValue());
	    	}
	    	List<UserLevel> uList=userLevelMapper.selectUserLevel();
	    	UserLevel us=null;
	    	for(int j=0;j<uList.size();j++){
	    		if(uList.get(j).getExp()<=exp){
	    			us=uList.get(j);
	    		}
	    	}
	    	userBillboard.setLevel(""+us.getLevelName()); 
	    	goldBillboard.setUserBillboard(userBillboard);
	    	goldBillboard.setRecord(selectWarReport(userItemsList.get(i).getUserAccountId()));
	    	list.add(goldBillboard);
	  }
		  return list;
	 }
	  /**
		 * 排行榜金币榜
		 */
	  
	  public List<GoldBillboard> selectCoinBillboard(int startPage,int stopPage){
		  UserItems u=userItemsMapper.selectByPrimaryKey(455L);
		  HashMap<String,Object> map1 = new HashMap<String,Object>();
		  map1.put("startPage", startPage);
		  map1.put("stopPage", stopPage);
		  List<GoldBillboard> list = new  ArrayList<GoldBillboard>();
		  List<UserItems> userItemsList =userItemsMapper.selectCoinboard(map1);
		  if(userItemsMapper.selectCoinboard(map1)!=null && userItemsMapper.selectCoinboard(map1).size()>0)
		  for(int i=0;i<userItemsMapper.selectCoinboard(map1).size();i++){
			  GoldBillboard goldBillboard = new GoldBillboard();
			  UserBillboard userBillboard = new UserBillboard();
			  userBillboard.setRank(startPage+i+1);
		  Map<PropertyTypes,UserExtra> map=userAccountMapper.selectByPrimaryId(userItemsList.get(i).getUserAccountId()).getPropertiesAsMap();	  
		  if(map.get(PropertyTypes.StartHead)!=null){
			  userBillboard.setIconImg(map.get(PropertyTypes.StartHead).getPropertyValue());
	    	}else{
	    		userBillboard.setIconImg("0");
	    	}
			if(map.get(PropertyTypes.NickName)!=null){
				userBillboard.setNickName(map.get(PropertyTypes.NickName).getPropertyValue());
	    	}else{
	    		userBillboard.setNickName("0");
	    	}
			if(map.get(PropertyTypes.CreditValue)!=null){
				userBillboard.setCreditValue(map.get(PropertyTypes.CreditValue).getPropertyValue());
	    	}else{
	    		userBillboard.setCreditValue("0");
	    	}
	    	if(map.get(PropertyTypes.DsFen)!=null){
	    		userBillboard.setDsFen(map.get(PropertyTypes.DsFen).getPropertyValue());
	    	}else{
	    		userBillboard.setDsFen("0");
	    	}
	    	if(map.get(PropertyTypes.MaxGot)!=null){
	    		userBillboard.setMaxGot(map.get(PropertyTypes.MaxGot).getPropertyValue());
	    	}else{
	    		userBillboard.setMaxGot("0");
	    	}
	    	if(map.get(PropertyTypes.MlValue)!=null){
	    		userBillboard.setMlValue(map.get(PropertyTypes.MlValue).getPropertyValue());
	    	}else{
	    		userBillboard.setMlValue("0");
	    	}
	    	if(map.get(PropertyTypes.RpValue)!=null){
	    		userBillboard.setRpValue(map.get(PropertyTypes.RpValue).getPropertyValue());
	    	}else{
	    		userBillboard.setRpValue("0");
	    	}
	    	if(map.get(PropertyTypes.TtFen)!=null){
	    		userBillboard.setTtFen(map.get(PropertyTypes.TtFen).getPropertyValue());
	    	}else{
	    		userBillboard.setTtFen("0");
	    	}
	    		userBillboard.setGold((userItemsMapper.selectCoinValue(map1).get(i)));
	    	
	    	long exp;
	    	if(map.get(PropertyTypes.Experience)==null){
	    		exp=0L;
	    	}else{
	    		exp=Long.valueOf(map.get(PropertyTypes.Experience).getPropertyValue());
	    	}
	    	List<UserLevel> uList=userLevelMapper.selectUserLevel();
	    	UserLevel us=null;
	    	for(int j=0;j<uList.size();j++){
	    		if(uList.get(j).getExp()<=exp){
	    			us=uList.get(j);
	    		}
	    	}
	    	userBillboard.setLevel(""+us.getLevelName()); 
	    	goldBillboard.setUserBillboard(userBillboard);
	    	goldBillboard.setRecord(selectWarReport(userItemsList.get(i).getUserAccountId()));
	    	list.add(goldBillboard);
	  }
		  return list;
	 }
	   /**
		 * 排行榜经验榜
		 */
	  public List<GoldBillboard> selectExpBillboard(int startPage,int stopPage){
		  HashMap<String,Object> map1 = new HashMap<String,Object>();
		  map1.put("startPage", startPage);
		  map1.put("stopPage", stopPage);
		  List<GoldBillboard> list = new  ArrayList<GoldBillboard>();
		  List<UserExtra> userExtraList =userExtraMapper.selectUserExtra(map1);
		  if(userExtraMapper.selectUserExtra(map1)!=null && userExtraMapper.selectUserExtra(map1).size()>0)
		  for(int i=0;i<userExtraMapper.selectUserExtra(map1).size();i++){
			  GoldBillboard goldBillboard = new GoldBillboard();
			  UserBillboard userBillboard = new UserBillboard();
			  userBillboard.setRank(startPage+i+1);
		  Map<PropertyTypes,UserExtra> map=userAccountMapper.selectByPrimaryId(userExtraList.get(i).getUserAccountId()).getPropertiesAsMap();	  
		  if(map.get(PropertyTypes.StartHead)!=null){
			  userBillboard.setIconImg(map.get(PropertyTypes.StartHead).getPropertyValue());
	    	}else{
	    		userBillboard.setIconImg("0");
	    	}
			if(map.get(PropertyTypes.NickName)!=null){
				userBillboard.setNickName(map.get(PropertyTypes.NickName).getPropertyValue());
	    	}else{
	    		userBillboard.setNickName("0");
	    	}
			if(map.get(PropertyTypes.CreditValue)!=null){
				userBillboard.setCreditValue(map.get(PropertyTypes.CreditValue).getPropertyValue());
	    	}else{
	    		userBillboard.setCreditValue("0");
	    	}
	    	if(map.get(PropertyTypes.DsFen)!=null){
	    		userBillboard.setDsFen(map.get(PropertyTypes.DsFen).getPropertyValue());
	    	}else{
	    		userBillboard.setDsFen("0");
	    	}
	    	if(map.get(PropertyTypes.MaxGot)!=null){
	    		userBillboard.setMaxGot(map.get(PropertyTypes.MaxGot).getPropertyValue());
	    	}else{
	    		userBillboard.setMaxGot("0");
	    	}
	    	if(map.get(PropertyTypes.MlValue)!=null){
	    		userBillboard.setMlValue(map.get(PropertyTypes.MlValue).getPropertyValue());
	    	}else{
	    		userBillboard.setMlValue("0");
	    	}
	    	if(map.get(PropertyTypes.RpValue)!=null){
	    		userBillboard.setRpValue(map.get(PropertyTypes.RpValue).getPropertyValue());
	    	}else{
	    		userBillboard.setRpValue("0");
	    	}
	    	if(map.get(PropertyTypes.TtFen)!=null){
	    		userBillboard.setTtFen(map.get(PropertyTypes.TtFen).getPropertyValue());
	    	}else{
	    		userBillboard.setTtFen("0");
	    	}
	    		userBillboard.setGold(Integer.parseInt((userExtraMapper.selectUserExtra(map1).get(i).getPropertyValue())));
	    	
	    	long exp;
	    	if(map.get(PropertyTypes.Experience)==null){
	    		exp=0L;
	    	}else{
	    		exp=Long.valueOf(map.get(PropertyTypes.Experience).getPropertyValue());
	    	}
	    	List<UserLevel> uList=userLevelMapper.selectUserLevel();
	    	UserLevel us=null;
	    	for(int j=0;j<uList.size();j++){
	    		if(uList.get(j).getExp()<=exp){
	    			us=uList.get(j);
	    		}
	    	}
	    	userBillboard.setLevel(""+us.getLevelName()); 
	    	goldBillboard.setUserBillboard(userBillboard);
	    	goldBillboard.setRecord(selectWarReport(userExtraList.get(i).getUserAccountId()));
	    	list.add(goldBillboard);
	  }
		  return list;
	 }
		public int[][] selectWarReport(long userId){
			HashMap<String,Object> map = new HashMap<String,Object>();
			List<WarReport> list=new ArrayList<WarReport>();
			for(int i=1;i<=3;i++){
			map.put("userId", userId);
			map.put("type", i);
			if(gameDataStaticsMapper.selectWarReport(map)!=null){
			list.add(gameDataStaticsMapper.selectWarReport(map));
			}else{
				WarReport w=new WarReport();
				w.setStagebTimes(0);
				w.setWinner1Times(0);
				w.setWinner2Times(0);
				w.setWinner3Times(0);
				list.add(w);
			}
			}
			int[][] array={{list.get(0).getWinner1Times(),list.get(0).getWinner2Times(),list.get(0).getWinner3Times(),list.get(0).getStagebTimes()},
					{list.get(1).getWinner1Times(),list.get(1).getWinner2Times(),list.get(1).getWinner3Times(),list.get(1).getStagebTimes()},
					{list.get(2).getWinner1Times(),list.get(2).getWinner2Times(),list.get(2).getWinner3Times(),list.get(2).getStagebTimes()}};
			 	
			return array;
		}
		
}
