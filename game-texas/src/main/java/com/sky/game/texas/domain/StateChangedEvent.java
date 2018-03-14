package com.sky.game.texas.domain;

public class StateChangedEvent {
	
	// state changed
	public int state;
	// which object state changed.
	public Object object;

	public StateChangedEvent() {
		// TODO Auto-generated constructor stub
	}
	
	

	private StateChangedEvent(int state, Object object) {
		super();
		this.state = state;
		this.object = object;
	}


	/**
	 * 
	 * @param state
	 * @param object
	 * @return
	 */
	public static StateChangedEvent getEvent(int state,Object object){
		return new StateChangedEvent(state, object); 
	}
}
