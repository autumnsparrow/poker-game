/**
 * 
 */
package com.sky.game.manager.mybatis.domain;

/**
 * @author Li
 *
 */
public class LoginStatics {
	
	Integer day;
	Integer times;
	Integer userType;
	Long channelId;
	
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	

}
