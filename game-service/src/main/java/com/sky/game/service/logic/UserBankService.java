/**
 * 
 */
package com.sky.game.service.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.BankShow;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserBank;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserBankMapper;
import com.sky.game.service.persistence.UserExtraMapper;
import com.sky.game.service.persistence.UserItemsMapper;

/**
 * @author Administrator
 *
 */
@Service
public class UserBankService {

	private static final Log logger = LogFactory.getLog(UserBankService.class);
	
	/**
	 * 
	 */
	@Autowired
	UserBankMapper userBankMapper;
	@Autowired
	UserItemsMapper userItemsMapper;
	@Autowired
	UserAccountMapper userAccountMapper;
	@Autowired
	UserExtraMapper userExtraMapper;
	

	public UserBankService() {
		// TODO Auto-generated constructor stub
	}
	/*
	 *  银行大厅展示
	 */
	@SuppressWarnings("unused")
	public BankShow selectBankShow(long userId){
		BankShow bankShow = new BankShow();
		UserBank userBank=userBankMapper.selectUserBankByUserId(userId);
		bankShow.setUserId(userId);
		Map<PropertyTypes,UserExtra> map=userAccountMapper.selectByPrimaryId(userId).getPropertiesAsMap();
		if(map.get(PropertyTypes.NickName)!=null){
    		bankShow.setNickName(map.get(PropertyTypes.NickName).getPropertyValue());//昵称
    	}
    	if(map.get(PropertyTypes.StartHead)!=null){
    		bankShow.setStartHead(map.get(PropertyTypes.StartHead).getPropertyValue());//头像
    	}
    	int reputationValue=0;
    	if(map.get(PropertyTypes.ReputationValue)!=null){
    		reputationValue=Integer.parseInt(map.get(PropertyTypes.ReputationValue).getPropertyValue());
    		bankShow.setReputationValue(reputationValue);					 //信誉值
    	}
    	UserItems userItems=userItemsMapper.selectUserCoin(userId);			 
    	if(userBank!=null){
    	bankShow.setDeposit(userBank.getDeposit()); 						 //当前银行存款
    	}
    	int loan=0;
    	if(userBank!=null){
    		loan=userBank.getLoan();
    		bankShow.setLoan(loan);
    	}else{
    		loan=0;
    		bankShow.setLoan(loan);
    	}
    	
		if(userItems!=null){
			
			bankShow.setCanDeposit(userItems.getItemValue());                //当前可存款数额
			
		}

		if(reputationValue>=4000 && reputationValue-loan>0){
			bankShow.setCanLoan(reputationValue-loan);      //当前可贷款数额
		}
		else{
			bankShow.setCanLoan(0);
		}
		    
		if(userBank!=null){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		bankShow.setLoanDate(sdf.format(userBank.getLoanDate()));
		bankShow.setRefundDate(sdf.format(userBank.getRefundDate()));
		}
		else{
			bankShow.setLoanDate("无");
			bankShow.setRefundDate("无");
		}
		return bankShow;
	}
	/*
	 *    银行存款
	 */
	
	@Transactional
	public int updateDeposit(long userId,int saveMoney){
		HashMap<String ,Object> mapSave=new HashMap<String,Object>();               
		mapSave.put("userId", userId);	
		mapSave.put("saveMoney",saveMoney);
		int state = 1;
		UserBank userBank = userBankMapper.selectUserBankByUserId(userId);
		UserItems userItems = userItemsMapper.selectUserCoin(userId);
		if(userBank!=null && userItems.getItemValue()>=saveMoney){
			if(userItems!=null && saveMoney>0 && userItems.getItemValue()>=saveMoney){
				userBankMapper.updateDeposit(mapSave);        //增加存款
				userItemsMapper.decDeposit(mapSave);		  //减少用户金币
			}
		}
		else{
			state=-1;					//未开户
		}
		   return state;
	}

	@SuppressWarnings("unused")
	@Transactional
	public int takeDeposit(long userId,int takeMoney,String passWd){
		HashMap<String,Object> mapTake = new HashMap<String,Object>();
		mapTake.put("userId", userId);
		mapTake.put("value", takeMoney);	
		int state = 1;
		UserItems userItems = userItemsMapper.selectUserCoin(userId);
		UserBank userBank = userBankMapper.selectUserBankByUserId(userId);
		String bankPw = userBank.getBankPw();
		if(userBank!=null){
			if(userItems!=null && takeMoney>0 && passWd!=null && bankPw.equals(passWd)
					&& userBank.getDeposit()>=takeMoney){
				userBankMapper.takeDeposit(mapTake);		 //取出存款存款减少
				userItemsMapper.addDeposit(mapTake);         //取出存款用户金币增加
			}
			else{
				state=-2;
			}
		}
		else{
			state=-1;					//未开户
		}
		return state;
	}
	/*
	 *  银行借款
	 */
	
	public HashMap<String,Object> updateBankLoan(long userId,int lend){
		UserBank userBank=userBankMapper.selectUserBankByUserId(userId);
		HashMap<String, Object> map = new HashMap<String,Object>();
		Date loanDate = new Date();
		map.put("loanDate",loanDate);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(loanDate);
		rightNow.add(Calendar.DATE,15);
		Date refundDate= new Date();
		refundDate=rightNow.getTime();
	
		map.put("refundDate", refundDate);
		map.put("userId", userId);
		map.put("lend", lend);
		HashMap<String, Object> map2 = new HashMap<String,Object>();
		map2.put("userId", userId);
		map2.put("count", lend);
		int reputationValue=0;
		int state = 1;
		String loanDate1="";
		String refundDate1="";
		Map<PropertyTypes,UserExtra> map1=userAccountMapper.selectByPrimaryId(userId).getPropertiesAsMap();
		if(map1.get(PropertyTypes.ReputationValue)!=null){
    		reputationValue=Integer.parseInt(map1.get(PropertyTypes.ReputationValue).getPropertyValue());
    	}
		if(userBank!=null){												//判断是否开通银行
			if(reputationValue>=4000){									//判断信誉值是否可用借款
				if(reputationValue-userBank.getLoan()>=lend){			//判断输入借款金额是否小于可贷金额
					if(userBank.getLoan()==0 && lend>0){   //新的借款，改变借款、还款时间
						userBankMapper.updateUserLoanStart(map);	   //还款完成后新的借款
						if(userItemsMapper.selectUserCoin(userId)!=null){
						userItemsMapper.updateUserCoin(map2);
						}else{
						userItemsMapper.insertUserCoin1(map2);
						}
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
						loanDate1=sdf.format(loanDate);
						refundDate1=sdf.format(refundDate);
						final long ids=userId;//定时的唯一标识
						String url="bank://"+userId;
						GameUtil.gameLife(url,14*24*3600*1000L,this,"timming",userBank);
					}else{
						userBankMapper.updateUserLoan(map);				//部分还款后的借款
						if(userItemsMapper.selectUserCoin(userId)!=null){
						userItemsMapper.updateUserCoin(map2);
						}else{
						userItemsMapper.insertUserCoin1(map2);
						}
						
					}
				}
			}
			else{
				state=-2;					//信誉值不足
				}
		}else{
		state = -1;						//未开通银行
		}
		HashMap<String,Object> map3 = new HashMap<String,Object>();
		map3.put("state", state);
		map3.put("loanDate1", loanDate1);
		map3.put("refundDate1", refundDate1);
		return map3;
	}
		
	@Transactional
	public int updateBankRepayment(long userId,int backMoney){
		int state=1;
		UserBank userBank=userBankMapper.selectUserBankByUserId(userId);
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("count", backMoney);
		HashMap<String, Object> map2 = new HashMap<String,Object>();
		map2.put("userId", userId);
		map2.put("value", backMoney);
		if(userBank!=null){			
			userBankMapper.updateRepayment(map);
			userBankMapper.updateBackLoan(map);
			userItemsMapper.decUserCoin(map);
			if(userBank.getLoan()==0){
				userBankMapper.reset(userId);
			}
			 Date date = new Date();
		     Calendar calendar   =new GregorianCalendar(); 
		     calendar.setTime(userBank.getLoanDate()); 
		     calendar.add(calendar.DATE,2); 
		     Date date1=calendar.getTime(); 					//两天后的时间	
		     long diff=date.getTime()-userBank.getLoanDate().getTime();
		     long weeks = diff / (7*1000 * 60 * 60 * 24)+1L;
		     
		     String str = userExtraMapper.selectReputationValue(userId).getPropertyValue();
		     
		     int reputationValue=Integer.parseInt(str);
		     if(date.after(date1) && date.before(userBank.getRefundDate()) && weeks<3 && reputationValue<20000){	
		    	 long  value = (backMoney/100)*(3-weeks);					//信誉值计算公式
		    	 HashMap<String,Object> map1 = new HashMap<String,Object>();
		    	 map1.put("userId", userId);
		    	 map1.put("value", value);
		    	 userExtraMapper.updateReputationValue(map1);    //增加信誉值
		     }
		}else{
			state=-1;			//未开通银行
		}
		return state;
	}
	public void timming(UserBank userBank){					//定时查数据
		if(userBank.getLoan()>0){
			String url="userBank:"+1;
			GameUtil.gameLife(url, "0 0 */3 * * ? ",this,"decReputationValue",userBank);
		}
	}
	public void decReputationValue(UserBank userBank){
		Date date = new Date();
		Calendar calendar   =new GregorianCalendar(); 
		 calendar.setTime(userBank.getLoanDate()); 
		int count = userBank.getLoan()-userBank.getRepayment();
		long diff=date.getTime()-userBank.getLoanDate().getTime();
		long weeks = diff / (7*1000 * 60 * 60 * 24)+1L;
		long  value = (count/100)*(3-weeks);
		int newReputationValue=Integer.parseInt(userExtraMapper.selectReputationValue(userBank.getUserAccountId()).getPropertyValue());
		if(userBank.getLoan()>0 && newReputationValue>0 && value<1000){
			 HashMap<String,Object> map = new HashMap<String,Object>();
	    	 map.put("userId", userBank.getUserAccountId());
	    	 map.put("value", value);
	    	 userBankMapper.decReputationValue(map);
			}
	}
	
}
