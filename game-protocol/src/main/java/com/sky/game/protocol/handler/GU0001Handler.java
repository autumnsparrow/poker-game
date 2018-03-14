/**
 * 
 */
package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.commons.GU0001Beans;
import com.sky.game.protocol.commons.GU0001Beans.Request;
import com.sky.game.protocol.commons.GU0001Beans.Response;
import com.sky.game.service.ServiceException;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.logic.UserAccountService;
import com.sky.game.service.logic.UserTokenService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0001", enable = true, namespace = "GameUser")
@Component(value = "GU0001")
public class GU0001Handler implements
		IProtocolHandler<GU0001Beans.Request, GU0001Beans.Response> {

	@Autowired
	UserAccountService userAccountService;

	@Autowired
	UserTokenService userTokenService;

	/**
	 * 
	 */
	public GU0001Handler() {
		// TODO Auto-generated constructor stub
	}

	private static final int VISITOR = 0;
	private static final int NORMAL = 1;

	@HandlerMethod(enable = true)
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub

		boolean visitor = false;
		long userId = 0;

		// check if the user is visitor and with token.
		if (req.getToken() != null && !"".equals(req.getToken())) {
			userId = BasePlayer.getUserId(req.getToken());
			UserAccount userAccount = userAccountService
					.selectUserAccountByid(userId);
			visitor = userAccount != null ? userAccount.getUserType() == VISITOR
					: false;
		}

		
		if (visitor) {// 游客注册
			try {
				userId = userAccountService.ykregister(req.getAccount(),
						req.getPassword(), NORMAL, userId);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new ProtocolException(e.getState(), e.getMessage());
			}

		} else {
			try {
				userId = userAccountService.register(req.getAccount(),
						req.getPassword(), req.getChnalID(), req.getDeviceId(),
						NORMAL);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new ProtocolException(e.getState(), e.getMessage());
			}

		}

		

		if (userId == 0) {
			res.setDescription("账号已存在");
		} else {
			String token = userTokenService.generateToken(userId);
			res.setToken(token);
		}
		
		res.setUserId(userId);

		return true;
	}
}
