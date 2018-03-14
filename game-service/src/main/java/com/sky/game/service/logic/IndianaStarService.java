package com.sky.game.service.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.service.domain.GameReward;
import com.sky.game.service.domain.IndianaStar;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.domain.UserLevel;
import com.sky.game.service.persistence.GameRewardMapper;
import com.sky.game.service.persistence.ItemMapper;
import com.sky.game.service.persistence.MatchCheckMapper;
import com.sky.game.service.persistence.RewardMapper;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserLevelMapper;
@Service
public class IndianaStarService {

	public IndianaStarService() {
		// TODO Auto-generated constructor stub
	}
    @Autowired
	UserAccountMapper userAccountMapper;
    @Autowired
    UserLevelMapper userLevelMapper;
    @Autowired
    MatchCheckMapper matchCheckMapper;
    @Autowired
    RewardMapper rewardMapper;
    @Autowired
    ItemMapper itemMapper;
    
    @Autowired
    GameRewardMapper gameRewardMapper;
    @Autowired
    UserItemLogMapper userItemLogMapper;
    @Autowired
    BillboardService billboardService;
	public List<IndianaStar> indianaStarList(){
		List<GameReward> gameRewardList=gameRewardMapper.selectIS();
		String matchScence=null;
		List<IndianaStar> indianaStarList=new ArrayList<IndianaStar>();
		for(GameReward gameReward:gameRewardList){
			IndianaStar indianaStar=new IndianaStar();
			HashMap<String,Object> hp=userItemLogMapper.ByIdSelectNameValue(gameReward.getUserItemLogId());
			indianaStar.setRank("第一名");
			String raward=hp.get("value")+(String)hp.get("name");
			indianaStar.setRaward(raward);
			/*if(gameReward.getRoomId()==10000){
				matchScence="初来乍到";
			}
			if(gameReward.getRoomId()==10001){
				matchScence="风光无限";
			}
			if(gameReward.getRoomId()==10002){
				matchScence="平步青云";
			}
			if(gameReward.getRoomId()==10003){
				matchScence="一鸣惊人";
			}*/
			if(gameReward.getRoomId()==2000){
				matchScence="免费新手赛";
			}
			if(gameReward.getRoomId()==2001){
				matchScence="100金币赚金赛";
			}
			if(gameReward.getRoomId()==2002){
				matchScence="5元充值卡大奖赛";
			}
			if(gameReward.getRoomId()==2003){
				matchScence="1000金币夺宝赛";
			}
			if(gameReward.getRoomId()==2004){
				matchScence="午间10元话费场";
			}
			if(gameReward.getRoomId()==2005){
				matchScence="天天赢话费";
			}
			if(gameReward.getRoomId()==2006){
				matchScence="周末赢大奖";
			}
			/*if(gameReward.getRoomId()==2007){
				matchScence="24人超快赛";
			}*/
			if(gameReward.getRoomId()==3000){
				matchScence="VIP免费赢大奖";
			}
			if(gameReward.getRoomId()==3001){
				matchScence="VIP2000金币PK赛";
			}
			if(gameReward.getRoomId()==3002){
				matchScence="VIP5000金币PK赛";
			}
			if(gameReward.getRoomId()==3003){
				matchScence="VIP10000金币PK赛";
			}
			if(gameReward.getRoomId()==3004){
				matchScence="VIP10000金币夺宝赛";
			}
			/*if(gameReward.getRoomId()==4000){
				matchScence="免费闯关赢金币";
			}
			if(gameReward.getRoomId()==4001){
				matchScence="100金币闯关赢金币";
			}
			if(gameReward.getRoomId()==4002){
				matchScence="500金币闯关赢金币";
			}
			if(gameReward.getRoomId()==4003){
				matchScence="1000金币闯关赢金币";
			}
			if(gameReward.getRoomId()==4004){
				matchScence="闯关赢iphone6";
			}*/
			indianaStar.setMatchScence(matchScence);
			Map<PropertyTypes,UserExtra> map=userAccountMapper.selectByPrimaryId(gameReward.getUserId()).getPropertiesAsMap();
			String sex=map.get(PropertyTypes.Sex).getPropertyValue();
			if(map.get(PropertyTypes.StartHead)!=null){
	    		indianaStar.setStartHead(map.get(PropertyTypes.StartHead).getPropertyValue());
	    	}else{
	    		indianaStar.setStartHead("0");
	    	}
			if(map.get(PropertyTypes.NickName)!=null){
	    		indianaStar.setNickName(map.get(PropertyTypes.NickName).getPropertyValue());
	    	}else{
	    		indianaStar.setNickName("0");
	    	}
			if(map.get(PropertyTypes.CreditValue)!=null){
	    		indianaStar.setCreditValue(map.get(PropertyTypes.CreditValue).getPropertyValue());
	    	}else{
	    		indianaStar.setCreditValue("0");
	    	}
	    	if(map.get(PropertyTypes.DsFen)!=null){
	    		indianaStar.setDsFen(map.get(PropertyTypes.DsFen).getPropertyValue());
	    	}else{
	    		indianaStar.setDsFen("0");
	    	}
	    	if(map.get(PropertyTypes.MaxGot)!=null){
	    		indianaStar.setMaxGot(map.get(PropertyTypes.MaxGot).getPropertyValue());
	    	}else{
	    		indianaStar.setMaxGot("0");
	    	}
	    	if(map.get(PropertyTypes.MlValue)!=null){
	    		indianaStar.setMlValue(map.get(PropertyTypes.MlValue).getPropertyValue());
	    	}else{
	    		indianaStar.setMlValue("0");
	    	}
	    	if(map.get(PropertyTypes.RpValue)!=null){
	    		indianaStar.setRpValue(map.get(PropertyTypes.RpValue).getPropertyValue());
	    	}else{
	    		indianaStar.setRpValue("0");
	    	}
	    	if(map.get(PropertyTypes.TtFen)!=null){
	    		indianaStar.setTtFen(map.get(PropertyTypes.TtFen).getPropertyValue());
	    	}else{
	    		indianaStar.setTtFen("0");
	    	}
	    	
	    	long exp;
	    	if(map.get(PropertyTypes.Experience)==null){
	    		exp=0L;
	    	}else{
	    		exp=Long.valueOf(map.get(PropertyTypes.Experience).getPropertyValue());
	    	}
	    	
	    	List<UserLevel> uList=userLevelMapper.selectUserLevel();
	    	UserLevel us=null;
	    	for(int i=0;i<uList.size();i++){
	    		if(uList.get(i).getExp()<=exp){
	    			us=uList.get(i);
	    		}
	    	}
	    	indianaStar.setLevel("lv"+us.getLevelName());
	    	indianaStar.setSex(sex);
	    	//战报
	    	
	    	indianaStar.setRecord(billboardService.selectWarReport(gameReward.getUserId()));
	    	indianaStarList.add(indianaStar);
		}
		
    	
		return indianaStarList;
	}
}
