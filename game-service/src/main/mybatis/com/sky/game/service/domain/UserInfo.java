package com.sky.game.service.domain;

import com.sky.game.context.util.GameUtil;
import com.sky.game.internal.service.protocol.domain.BasicUserInfo;

public class UserInfo {

	public UserInfo() {
		// TODO Auto-generated constructor stub
	}
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getYb() {
		return yb;
	}
	public void setYb(int yb) {
		this.yb = yb;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
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
	public BasicUserInfo wrapper() {
		BasicUserInfo o=GameUtil.obtain(BasicUserInfo.class);
		o.setUserId(userId); //userId;
		o.setNickName(nickName);//o.nickName = nickName;
		o.setGold(gold);//o.gold = gold;
		o.setExp(exp);//o.exp = exp;
		o.setLevel(level);//o.level = level;
		o.setImgName(imgName);//o.imgName = imgName;
		o.setYb(yb);//o.yb = yb;
		o.setSex(sex);//o.sex = sex;
		o.setIsVip(isVip);//o.isVip = isVip;
		o.setTotalExp(totalExp);//o.totalExp = totalExp;
		o.setCurrentExp(currentExp);//o.currentExp = currentExp;
		return o;
	}
	
	
	
	
}
