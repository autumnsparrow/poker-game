/**
 * 
 */
package com.sky.game.service.domain;

/**
 * @author Administrator
 *
 */
public class IndianaStar {

	/**
	 * 今日之星
	 */
	public IndianaStar() {
		// TODO Auto-generated constructor stub
	}
	long userId;      //用户id
	String startHead; //头像
	String nickName;	//昵称
	String matchScence;//比赛场次
	String rank;//名次
	String raward;//奖励
	String level;//级加
	String dsFen;//大师分
	String ttFen;//天梯分
	String rpValue;// rp值
	String creditValue;//信誉值
	String mlValue;//魅力值
	String maxGot;//最大倍数
	String sex;
	int[][] record=new int[3][4] ;//战报
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
	public String getMatchScence() {
		return matchScence;
	}
	public void setMatchScence(String matchScence) {
		this.matchScence = matchScence;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getRaward() {
		return raward;
	}
	public void setRaward(String raward) {
		this.raward = raward;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDsFen() {
		return dsFen;
	}
	public void setDsFen(String dsFen) {
		this.dsFen = dsFen;
	}
	public String getTtFen() {
		return ttFen;
	}
	public void setTtFen(String ttFen) {
		this.ttFen = ttFen;
	}
	public String getRpValue() {
		return rpValue;
	}
	public void setRpValue(String rpValue) {
		this.rpValue = rpValue;
	}
	public String getCreditValue() {
		return creditValue;
	}
	public void setCreditValue(String creditValue) {
		this.creditValue = creditValue;
	}
	public String getMlValue() {
		return mlValue;
	}
	public void setMlValue(String mlValue) {
		this.mlValue = mlValue;
	}
	public String getMaxGot() {
		return maxGot;
	}
	public void setMaxGot(String maxGot) {
		this.maxGot = maxGot;
	}
	public int[][] getRecord() {
		return record;
	}
	public void setRecord(int[][] record) {
		this.record = record;
	}
	public String getStartHead() {
		return startHead;
	}
	public void setStartHead(String startHead) {
		this.startHead = startHead;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}	
}
