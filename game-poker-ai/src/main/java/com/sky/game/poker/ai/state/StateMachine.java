/**
 * @Apr 17, 2015
 * @sparrow 
 * 
 * @TODO
 *  @copyright Beijing BZWT Technology Co., Ltd.
 */
package com.sky.game.poker.ai.state;

import java.util.LinkedList;
import java.util.List;

import com.sky.game.poker.ai.decision.Action;

/**
 * 
 *  Best demo to show the state machine work flow.
 *  state machine examples:
 *  
 *  
 *               |
 *          +----------+                         +-------+
 *     -- > |on guard  |   -----small enemy----->| fight |    
 *     |    +----------+                         +-------+
 *     escape      |                                 |
 *     |          large enemy                        |
 *     |     +----------+                            |
 *     |---  | run away |     <---------lose battle--
 *           +----------+
 * 
 * @author sparrow
 *
 */
public class StateMachine {
	
	IState currentState;
	
	

	/**
	 * 
	 */
	public StateMachine() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param currentState
	 */
	public StateMachine(IState initializedState) {
		super();
		this.currentState = initializedState;
	}
	
	
	public List<Action> update(){
		ITransition triggedTransition=null;
		List<Action> actions=new LinkedList<Action>();
		
		for(ITransition transition:currentState.getTransitions()){
			if(transition.isTriggered()){
				triggedTransition=transition;
				break;
			}
		}
		
		if(triggedTransition!=null){
			IState targetState=triggedTransition.getTargetState();
			actions.add(currentState.getExitAction());
			actions.add(triggedTransition.getAction());
			actions.add(targetState.getEntryAction());
			currentState=targetState;
		}else{
			actions.add(currentState.getAction());
		}
		
		return actions;
		
		
	}

	
}
