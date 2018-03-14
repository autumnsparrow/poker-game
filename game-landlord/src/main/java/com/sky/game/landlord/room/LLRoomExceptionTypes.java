/**
 * @sparrow
 * @Dec 23, 2014   @1:46:32 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.landlord.exception.LLGameExecption;
import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum LLRoomExceptionTypes {
	
	// Deck game exceptions
	LLDeckPokerTypeMismatch(2000,"牌型错误"),
	LLDeckPokerTypeSizeLess(2001,"牌点小于上手牌"),
	LLDeckCannotGot(2003,"无法获取牌桌"),
	LLFEnrollItemValueNotEnough(2004,"用户%s不足!"),
	LLEnrollOk(2005,"用户报名成功"),
	LLEnrollBloackdeUpdateFailed(2006,"更新用户闯关赛积分失败!"),
	LLInvalidToken(2007,"Token失效!"),
	LLAlreadyInGame(2008,"用户已在本房间游戏中!"),
	LLRoomdisableJoinEvent(2009,"当前房间禁止用户加入!"),
	LLUnenrollOk(2010,"用户取消成功");
	
	public int state;
	public String message;
	private LLRoomExceptionTypes(int state, String message) {
		this.state = state;
		this.message = message;
	}
	
	public State getState(){
		return State.obtain(state, message);
	}
	

	public LLGameExecption exception(){
		return LLGameExecption.obtain(state,message);
	}
}
