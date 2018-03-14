/**
 * @Apr 17, 2015
 * @sparrow 
 * 
 * @TODO
 *  @copyright Beijing BZWT Technology Co., Ltd.
 */
package com.sky.game.poker.ai.state;

import com.sky.game.poker.ai.decision.Action;

/**
 * @author sparrow
 *
 */
public interface ITransition {
	
	public boolean isTriggered();
	public Action getAction();
	public IState getTargetState();

}
