package com.sky.game.service.domain;

import com.sky.game.context.util.GameUtil;

public class Achievement {

	public Achievement() {
		// TODO Auto-generated constructor stub
	}
    public long id;
	public String achievementName;
	public int achievementAwardCoin;
	public int achievementAwardExp;
	public String achievementDis;
	public String imgName;
	public int value;
    public int type;
	public String getAchievementName() {
		return achievementName;
	}

	public void setAchievementName(String achievementName) {
		this.achievementName = achievementName;
	}

	public int getAchievementAwardCoin() {
		return achievementAwardCoin;
	}

	public void setAchievementAwardCoin(int achievementAwardCoin) {
		this.achievementAwardCoin = achievementAwardCoin;
	}

	public int getAchievementAwardExp() {
		return achievementAwardExp;
	}

	public void setAchievementAwardExp(int achievementAwardExp) {
		this.achievementAwardExp = achievementAwardExp;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAchievementDis() {
		return achievementDis;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public void setAchievementDis(String achievementDis) {
		this.achievementDis = achievementDis;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static Achievement obtain() {
		Achievement o=GameUtil.obtain(Achievement.class);
		o.id = 0;
		o.achievementName = "achievementName";
		o.achievementAwardCoin = 100;
		o.achievementAwardExp = 50;
		o.achievementDis = "achievementDis";
		o.imgName = "imgName";
		o.value = 3;
		o.type = 5;
		return o;
	}

}
