package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

public class GU0023Beans
{
  @HandlerResponseType(responsecode="UG0023", transcode="GU0023")
  public static class Response
  {
    int state;
    String description;

    public int getState()
    {
      return this.state;
    }
    public void setState(int state) {
      this.state = state;
    }
    public String getDescription() {
      return this.description;
    }
    public void setDescription(String description) {
      this.description = description;
    }
  }

  @HandlerRequestType(transcode="GU0023")
  public static class Request extends BaseRequest
  {
    long userId;
    String url;
    String name;
    int sex;

    public String getUrl()
    {
      return this.url;
    }

    public long getUserId() {
      return this.userId;
    }

    public void setUserId(long userId) {
      this.userId = userId;
    }

    public void setUrl(String url) {
      this.url = url;
    }
    public String getName() {
      return this.name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public int getSex() {
      return this.sex;
    }
    public void setSex(int sex) {
      this.sex = sex;
    }
  }
}