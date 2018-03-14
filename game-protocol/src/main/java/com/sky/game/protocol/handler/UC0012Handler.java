/**
 * 
 */
package com.sky.game.protocol.handler;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0012Beans;
import com.sky.game.protocol.commons.UC0012Beans.Request;
import com.sky.game.protocol.commons.UC0012Beans.Response;
import com.sky.game.protocol.util.ConstantCache;
import com.sky.game.service.domain.UserBank;
import com.sky.game.service.persistence.UserBankMapper;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="UC0012",enable=true,namespace="UserCenter")
@Component(value="UC0012")
public class UC0012Handler extends BaseProtocolHandler<UC0012Beans.Request, UC0012Beans.Response>{

	/**
	 * 
	 */
	public UC0012Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserBankMapper userBankMapper;
	
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		String confirm=req.getConfirm();
		String password=req.getPassword();
		UserBank userBank=userBankMapper.selectUserBankByUserId(BasePlayer.getPlayer(req).getUserId());
		int state=0;
		String description=null;
		int a=ConstantCache.selectValidate(req.getPhone(), req.getVerification());
		if(a==1){
			if(confirm.equals(password)){
				if(userBank==null){
					HashMap<String,Object> hashmap=new HashMap<String,Object>();
					hashmap.put("userId",BasePlayer.getPlayer(req).getUserId());
					hashmap.put("bankPw",password);
					state=userBankMapper.insertBankPw(hashmap);
					description="银行开户成功";
				}else{
					UserBank record=new UserBank();
					record.setBankPw(password);
					record.setUserAccountId(BasePlayer.getPlayer(req).getUserId());
					state=userBankMapper.updateByPrimaryKeySelective(record);
					description="银行密码修改成功";
				}
			}
		}else{
			state=-1;
			description="验证码错误银行密码修改失败";
		}
		res.setState(state);
		res.setDescription(description);
		return ret;
	}
}
