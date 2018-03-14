/**
 * 
 */
package com.sky.game.service.domain;

/**
 * @author Administrator
 *
 */
public class Honor {

	/**
	 * 
	 */
	public Honor() {
		// TODO Auto-generated constructor stub
	}
	UserHoner userHoner;
	
	int[][] record=new int[3][4];

	public UserHoner getUserHoner() {
		return userHoner;
	}
	public void setUserHoner(UserHoner userHoner) {
		this.userHoner = userHoner;
	}
	public int[][] getRecord() {
		return record;
	}
	public void setRecord(int[][] record) {
		this.record = record;
	}
}
