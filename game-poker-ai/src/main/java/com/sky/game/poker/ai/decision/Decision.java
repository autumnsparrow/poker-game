/**
 * @Apr 17, 2015
 * @sparrow 
 * 
 * @TODO
 *  @copyright Beijing BZWT Technology Co., Ltd.
 */
package com.sky.game.poker.ai.decision;

/**
 * @author sparrow
 *
 */
public   class Decision implements INode{
	
	INode yesNode;
	INode noNode;
	boolean testValue;
	

	public void update(INode yNode,INode nNode){
		if(yNode!=null)
			yesNode=yNode;
		if(nNode!=null)
			noNode=nNode;
		
	}
	
	public void setValue(boolean value){
		testValue=value;
	}

	/**
	 * 
	 */
	public Decision() {
		// TODO Auto-generated constructor stub
		yesNode=noNode=new NoAction();
	}
	
	public   INode getBranch(){
		return testValue?yesNode:noNode;
	}

	public INode decide() {
		// TODO Auto-generated method stub
		return getBranch().decide();
	}
	
	
	
	
	
}
