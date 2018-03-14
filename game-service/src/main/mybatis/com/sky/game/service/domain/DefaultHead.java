/**
 * 
 */
package com.sky.game.service.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class DefaultHead implements Serializable{

	/**
	 * 
	 */
	public DefaultHead() {
		// TODO Auto-generated constructor stub
	}
	long iconId	;
	String name	;
	public long getIconId() {
		return iconId;
	}
	public void setIconId(long iconId) {
		this.iconId = iconId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
