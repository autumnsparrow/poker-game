package com.sky.game.service.domain;

import java.util.Date;

public class Certificate {

	public Certificate() {
		// TODO Auto-generated constructor stub
	}
	
	long id;
	String rank;//名次
	String nickName	;//昵称
    String  matchName	;//比赛名称
    String[] reWardArray;//奖励
    Date    time	;//获奖时间
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public String[] getReWardArray() {
		return reWardArray;
	}
	public void setReWardArray(String[] reWardArray) {
		this.reWardArray = reWardArray;
	}

	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
