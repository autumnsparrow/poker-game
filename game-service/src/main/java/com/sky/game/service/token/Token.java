/**
 * 
 */
package com.sky.game.service.token;


import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.UserToken;

/**
 * @author Administrator
 *
 */
public class Token {
	
	
	UserToken userToken;
	String token;

	/**
	 * 
	 */
	public Token() {
		// TODO Auto-generated constructor stub
	}

	public Token(UserToken userToken) {
		super();
		this.userToken = userToken;
		if(this.userToken!=null){
			
			String t=GameContextGlobals.getJsonConvertor().format(this.userToken);
			// encode
			
			this.token=GameUtil.encode(t);
		}
	}

	public Token(String t) {
		super();
		this.token = GameUtil.decode(t);
		if(this.token!=null&&!"".equals(this.token)){
			//string decode
			this.userToken=GameContextGlobals.getJsonConvertor().convert(this.token, UserToken.class);
		}
	}

	public UserToken getUserToken() {
		return userToken;
	}

	public String getToken() {
		return token;
	}
	
	
	
	
	
	

}
