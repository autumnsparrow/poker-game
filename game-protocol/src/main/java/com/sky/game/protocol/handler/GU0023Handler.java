package com.sky.game.protocol.handler;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0023Beans;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.logic.BadWordService;
import com.sky.game.service.logic.UserAccountService;
import com.sky.game.service.persistence.UserExtraMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@HandlerType(transcode="GU0023", enable=true, namespace="GameUser")
@Component("GU0023")
public class GU0023Handler extends BaseProtocolHandler<GU0023Beans.Request, GU0023Beans.Response>
{

  @Autowired
  UserAccountService userAccountService;

  @Autowired
  UserExtraMapper userExtraMapper;

  @Autowired
  BadWordService badWordService;

  @HandlerMethod(enable=true)
  public boolean handler(GU0023Beans.Request req, GU0023Beans.Response res)
    throws ProtocolException
  {
    int state = 0;
    String description = "服务器异常";
    long userId = BasePlayer.getPlayer(req).getUserId();
    UserExtra extra = this.userExtraMapper.selectNickNameByUserId(userId);
    boolean b = false;
    try {
      b = this.badWordService.initfiltercode(req.getName());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (("".equals(req.getName())) || (req.getName() == null)) {
      state = -1;
      description = "用户昵称不能为空";
    }
    else if (b) {
      state = -2;
      description = "昵称中含有敏感字符";
    }
    else if (extra == null) {
      List userExtraList = new ArrayList();
      UserExtra ue = new UserExtra();
      ue.setUserAccountId(Long.valueOf(userId));
      ue.setPropertyValue(req.getName());
      ue.setPropertyId(Long.valueOf(26L));

      UserExtra ue1 = new UserExtra();
      ue1.setUserAccountId(Long.valueOf(userId));
      ue1.setPropertyValue(String.valueOf(req.getSex()));
      ue1.setPropertyId(Long.valueOf(13L));

      UserExtra ue2 = new UserExtra();
      ue2.setUserAccountId(Long.valueOf(userId));
      ue2.setPropertyValue(req.getUrl());
      ue2.setPropertyId(Long.valueOf(27L));

      userExtraList.add(ue);
      userExtraList.add(ue1);
      userExtraList.add(ue2);
      state = this.userAccountService.inserDefaultValue(userExtraList);
      description = "";
    } else {
      state = 1;
      description = "";
    }

    res.setState(state);
    res.setDescription(description);
    return true;
  }
}