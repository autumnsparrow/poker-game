/**
 * 
 */
package com.sky.game.service.domain;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class DefaultValue {

	/**
	 * 
	 */
	public DefaultValue() {
		// TODO Auto-generated constructor stub
	}
	List<DefaultHead> dHeadList;
	String name	;
	int sex	;
	public List<DefaultHead> getdHeadList() {
		return dHeadList;
	}
	public void setdHeadList(List<DefaultHead> dHeadList) {
		this.dHeadList = dHeadList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
}
