/**
 * sparrow
 * game-service 
 * Jan 10, 2015- 3:50:36 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;

/**
 * @author sparrow
 *s
 */
public enum UserLogTypes {
	Enroll(1,"报名"),
	Reward(2,"奖励"),
	UnEnroll(3,"取消报名"),
	GameEnd(4,"游戏结束");
	
	
	public int value;
	public String description;
	/**
	 * @param value
	 * @param description
	 */
	private UserLogTypes(int value, String description) {
		this.value = value;
		this.description = description;
	}
  

}
