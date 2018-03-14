/**
 * 
 */
package com.sky.game.protocol;

import org.springframework.beans.factory.annotation.Autowired;

import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.service.logic.UserTokenService;


/**
 * @author Administrator
 *
 */
public class BaseProtocolHandler<Req, Resp> implements IProtocolHandler<Req, Resp> {

	@Autowired
	UserTokenService userTokenService;
	

	
	/**
	 * 
	 */
	public BaseProtocolHandler() {
		// TODO Auto-generated constructor stub
	}
	
	
	public BasePlayer getPlayer(BaseRequest req){
		BasePlayer player=null;
		if(req instanceof BaseRequest){
			BaseRequest baseRequest=(BaseRequest)req;
			String token=baseRequest.getToken();
			
			try {
				player=ProtocolGlobalContext.instance().getOnlinePlayerByToken(token);
			} catch (GameProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception ex){
				ex.printStackTrace();
			}
			
		}
		return player;
		
	}

	public boolean handler(Req req, Resp res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=false;
		if(req instanceof BaseRequest){
			BaseRequest baseRequest=(BaseRequest)req;
			String token=baseRequest.getToken();
			ret=userTokenService.validToken(token);
			
			if(!ret){
				throw new ProtocolException(-1000, "Token Invalid");
			}
			
			
		}
		
		return ret;
	}

}
