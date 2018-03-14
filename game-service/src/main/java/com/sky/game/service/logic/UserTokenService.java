/**
 * 
 */
package com.sky.game.service.logic;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.UserToken;
import com.sky.game.service.persistence.UserTokenMapper;
import com.sky.game.service.token.Token;

/**
 * @author Administrator
 *
 */
@Service
public class UserTokenService {

	private static final Log logger = LogFactory.getLog(ExchangeService.class);

	// 1hour alive
	private static final long TOKEN_ALIVE_TIME = 24 * 60 * 60 * 1000;

	@Autowired
	UserTokenMapper userTokenMapper;

	/**
	 * 
	 */
	public UserTokenService() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	private String decodeToken(String plain) {
		return plain;
	}

	@SuppressWarnings("unused")
	private String encodeToken(String encodedToken) {
		return encodedToken;
	}

	/**
	 * 
	 * 
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	@Transactional
	public boolean validToken(String token) {
		boolean valid = true;

		// process the old token
		Token t = new Token(token);
		UserToken oldUserToken = t.getUserToken();
        if(oldUserToken!=null){//蒋磊添加   屏蔽找回密码时token 为空
		UserToken userToken = userTokenMapper.selectByUserId(oldUserToken
				.getUserId());
		if (userToken == null || StringUtils.isEmpty(token)) {
			valid = false;
		} else {
			if (userToken.getTokenAliveTime() > TOKEN_ALIVE_TIME) {
				//GameUtil.removeToken(token);
				valid = false;
			} else {
				if (StringUtils.isEmpty(userToken.getToken())) {
					valid = false;
				} else {
					if (!token.equals(userToken.getToken())) {
						logger.error("token:"+token+" db.token:"+userToken.getToken());
						//GameUtil.removeToken(token);
						valid = false;
					}
				}
			}

		}
        }
		return valid;

	}

	/**
	 * 
	 * 
	 * generate the token
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional
	public String generateToken(long userId){
		String token=null;
		String plainToken=null;
		UserToken userToken=userTokenMapper.selectByUserId(Long.valueOf(userId));
		if(userId!=0){
		if(userToken==null){
			
				 logger.error("userId:"+userId);
				 userTokenMapper.insertToken(Long.valueOf(userId));
				 userToken=userTokenMapper.selectByUserId(Long.valueOf(userId));
		}

		userToken.setToken("");
		
		Token t = new Token(userToken);

		plainToken = t.getToken();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("plainToken", plainToken);
		userTokenMapper.updateToken(map);
		token = plainToken.trim();
		}
		return token;
	}
}
