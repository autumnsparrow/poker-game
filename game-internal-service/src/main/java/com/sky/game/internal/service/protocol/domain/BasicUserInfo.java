/**
 * 
 */
package com.sky.game.internal.service.protocol.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class BasicUserInfo implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = -7408066036842671283L;

	long userId;          // 账号id
	
	String nickName;//昵称

	int gold;      //金币
	int exp;       //经验
	String level;//用户等级
	String imgName;//头像
	
	int yb;//元宝
	String sex; //性别 1表示男 2表示女
	String isVip; //是否是Vip 1不是 2 是
	int totalExp;  // 经验进度 分母
	int currentExp; // 经验进度分子
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public int getYb() {
		return yb;
	}
	public void setYb(int yb) {
		this.yb = yb;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public int getTotalExp() {
		return totalExp;
	}
	public void setTotalExp(int totalExp) {
		this.totalExp = totalExp;
	}
	public int getCurrentExp() {
		return currentExp;
	}
	public void setCurrentExp(int currentExp) {
		this.currentExp = currentExp;
	}
	@Override
	public String toString() {
		return "BasicUserInfo [userId=" + userId + ", nickName=" + nickName
				+ ", gold=" + gold + ", exp=" + exp + ", level=" + level
				+ ", imgName=" + imgName + ", yb=" + yb + ", sex=" + sex
				+ ", isVip=" + isVip + ", totalExp=" + totalExp
				+ ", currentExp=" + currentExp + "]";
	}
	
	
	
}
