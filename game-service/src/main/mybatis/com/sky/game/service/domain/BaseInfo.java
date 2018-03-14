package com.sky.game.service.domain;

public class BaseInfo {

	public BaseInfo() {
		// TODO Auto-generated constructor stub
	}
	String nickName;
	int sex;
	String levelName;
	int experience;
	int fullExperience;
	public int getFullExperience() {
		return fullExperience;
	}
	public void setFullExperience(int fullExperience) {
		this.fullExperience = fullExperience;
	}
	int gold;
	int yb;
	String sign;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getYb() {
		return yb;
	}
	public void setYb(int yb) {
		this.yb = yb;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public BaseInfo(String nickName, int sex, String levelName, int experience,
			int gold, int yb, String sign , int fullExperience) {
		super();
		this.nickName = nickName;
		this.sex = sex;
		this.levelName = levelName;
		this.experience = experience;
		this.gold = gold;
		this.yb = yb;
		this.sign = sign;
		this.fullExperience = fullExperience;
	}
	@Override
	public String toString() {
		return "BaseInfo [nickName=" + nickName + ", sex=" + sex
				+ ", levelName=" + levelName + ", experience=" + experience
				+ ", gold=" + gold + ", yb=" + yb + ", sign=" + sign + ",fullExperience=" + fullExperience + "]";
	}
}
