/**
 * @Apr 17, 2015
 * @sparrow 
 * 
 * @TODO
 *  @copyright Beijing BZWT Technology Co., Ltd.
 */
package com.sky.game.poker.ai.state;

import java.util.List;

import com.sky.game.poker.ai.decision.Action;

/**
 * @author sparrow
 *
 */
public interface IState {

	public Action getAction();
	
	public Action getEntryAction();
	
	public Action getExitAction();
	
	public List<ITransition> getTransitions();
}
