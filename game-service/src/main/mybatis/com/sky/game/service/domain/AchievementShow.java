/**
 * 
 */
package com.sky.game.service.domain;

/**
 * @author Administrator
 *
 */
public class AchievementShow {
	
	public AchievementShow() {
		super();
		// TODO Auto-generated constructor stub
	}
	long id;//系统成就id
	long userAchiementId;//个人成就Id
	String name;// 成就名
	String description	;//成就描述
	String imgName;//图片名
	String rewardDesc;//奖励描述	
	int state;//状态
	String schedule	;//进度
	int type;//成就立即完成跳转值
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getUserAchiementId() {
		return userAchiementId;
	}
	public void setUserAchiementId(long userAchiementId) {
		this.userAchiementId = userAchiementId;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getRewardDesc() {
		return rewardDesc;
	}
	public void setRewardDesc(String rewardDesc) {
		this.rewardDesc = rewardDesc;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
