/**
 * 
 */
package com.sky.game.protocol.handler;


import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0017Beans;
import com.sky.game.protocol.commons.GU0017Beans.Request;
import com.sky.game.protocol.commons.GU0017Beans.Response;
import com.sky.game.protocol.util.ConstantCache;
import com.sky.game.protocol.util.Validate;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.logic.SmsMessageService;
import com.sky.game.service.persistence.UserExtraMapper;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0017", enable = true, namespace = "GameUser")
@Component(value = "GU0017")
public class GU0017Handler extends BaseProtocolHandler<GU0017Beans.Request, GU0017Beans.Response>{

	
	@Autowired
	SmsMessageService smsMessageService;
	@Autowired
	UserExtraMapper userExtraMapper;
	/**
	 * 
	 */
	public GU0017Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		String t=req.getToken();
		int flag=req.getIsFlag();
		int state=0;
		boolean z=false;
		String description=null;
		if(t.equals("")){//找回密码  验证码
			String phone=req.getPhone();
			UserExtra userExtra=userExtraMapper.selectUserExtraByPhone(phone);
			if(userExtra==null){
				state=-1;
				description="手机号码没有绑定";
				z=true;
			}else{
				state=1;
				description="请输入验证码";
				int a =new Random().nextInt(10);
				 int b =new Random().nextInt(10);
				 int c =new Random().nextInt(10);
				 int d =new Random().nextInt(10);
				 int e =new Random().nextInt(10);
				 int f =new Random().nextInt(10);
				 String y=String.valueOf(a)+b+c+d+e+f;
				Validate vaidate=new Validate();
				vaidate.setPhone(phone);
			    vaidate.setUserId(userExtra.getUserAccountId());
				vaidate.setVal(y);
				vaidate.setBornTime(System.currentTimeMillis());
				ConstantCache.addValidate(phone, vaidate);
				smsMessageService.send(phone, y);
				z=true;
			}
		}else{ // 其它获取验证码
			boolean ret= super.handler(req, res);
			if(flag==0){
			state=1;
			description="请输入验证码";
			String phone=req.getPhone();
			int a =new Random().nextInt(10);
			 int b =new Random().nextInt(10);
			 int c =new Random().nextInt(10);
			 int d =new Random().nextInt(10);
			 int e =new Random().nextInt(10);
			 int f =new Random().nextInt(10);
			 String y=String.valueOf(a)+b+c+d+e+f;
			Validate vaidate=new Validate();
			vaidate.setPhone(phone);
			vaidate.setUserId(BasePlayer.getPlayer(req).getUserId());
			vaidate.setVal(y);
			vaidate.setBornTime(System.currentTimeMillis());
			ConstantCache.addValidate(phone, vaidate);
			smsMessageService.send(phone, y);
			}else{
			 long userId=BasePlayer.getPlayer(req).getUserId();
			 HashMap<String,Object> hashmap=new HashMap<String,Object>();
			 hashmap.put("userId", userId);
			 hashmap.put("propertyId", 12);
			 
			 UserExtra userExtra=userExtraMapper.selectByUserIdAndProperty(hashmap);
			 if(userExtra==null){
				 state=-2;
				 description="用户没有绑定手机";
			 }else{
				 if(userExtra.getPropertyValue().equals(req.getPhone())){
					 state=1;
						description="请输入验证码";
						String phone=req.getPhone();
						int a =new Random().nextInt(10);
						 int b =new Random().nextInt(10);
						 int c =new Random().nextInt(10);
						 int d =new Random().nextInt(10);
						 int e =new Random().nextInt(10);
						 int f =new Random().nextInt(10);
						 String y=String.valueOf(a)+b+c+d+e+f;
						Validate vaidate=new Validate();
						vaidate.setPhone(phone);
						vaidate.setUserId(BasePlayer.getPlayer(req).getUserId());
						vaidate.setVal(y);
						vaidate.setBornTime(System.currentTimeMillis());
						ConstantCache.addValidate(phone, vaidate);
						smsMessageService.send(phone, y);
				 }else{
					 state=-3;
					 description="账号绑定的手机号不匹配,请查看是否输入正确"; 
				 }
			 }
			}
			z=true;
		}
		res.setDescription(description);
		res.setState(state);
		return z;
	}
	public static void main(String[] args) {
		int a=new Random().nextInt(10);
		System.out.println(a);
	}
}
