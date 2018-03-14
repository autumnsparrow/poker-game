/**
 * sparrow
 * game-service 
 * Jan 23, 2015- 1:21:04 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;


/**
 * @author sparrow
 *
 */
public enum UserAchievementStates {
	ZD(1,"炸弹"),
	TTS(2,"通天顺"),
	CT(3,"春天"),
	HJ(4,"火箭"),
	LD(5,"连对"),
	SDY(6,"三带一"),
	SDE(7,"三带二"),
	SDS(8,"四带三"),
	FJ(9,"飞机"),
	S(10,"胜"),
	KSJS15(11,"游戏开始到结束少于15秒"),
	SYZDZDFZD(12,"使用炸弹炸对方炸弹"),
	DZD(13,"身为地主在没出牌时使用炸弹"),
	NWDHS(14,"身为农民在队友没出牌的情况下获胜"),
	NBHS(15,"身为农民在不出牌的情况下获胜"),
	ZHC3(16,"最后出3胜"),
	HSZHSZD(17,"获胜最后一手是炸弹"),
	DCSZYSDP(18,"打出十张以上的牌"),
	SCFBS(19,"比赛中出现四次翻倍胜"),
	BSQSDG(20,"比赛中全胜夺冠"),
	TTSDG(21,"淘汰赛中夺冠"),
	NUM1(22,"第一名"),
	NUM2(23,"第二名"),
	NUM3(24,"第三名"),
	DC12C(25,"一手打出十二张牌"),
	SZZDMC(26,"手中有炸弹或火箭，但直到游戏结束没有出"),
	
	LIANSU5(27,"连输5场"),
	LS3(28,"连赢3场"),
	LS6(29,"连赢6场"),
	LS10(30,"连赢10场"),	
	LS15(31,"连赢15场");	
	public int state;
	public String message;
	/**
	 * @param state
	 * @param message
	 */
	private UserAchievementStates(int state, String message) {
		this.state = state;
		this.message = message;
	}
	
	public boolean eq(UserAchievementStates states){
		return state==states.state;
	}
}
