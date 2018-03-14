package com.sky.game.service.lottery;

public class LotteryRule {

	public LotteryRule() {
		// TODO Auto-generated constructor stub
	}
	long id;
	int level;
	int count;
	String name;
	int gold;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public LotteryRule(long id, int level, int count, String name, int gold) {
		super();
		this.id = id;
		this.level = level;
		this.count = count;
		this.name = name;
		this.gold = gold;
	}
	@Override
	public String toString() {
		return "LotteryRule [id=" + id + ", level=" + level + ", count="
				+ count + ", name=" + name + ", gold=" + gold + "]";
	}
	
	
}
