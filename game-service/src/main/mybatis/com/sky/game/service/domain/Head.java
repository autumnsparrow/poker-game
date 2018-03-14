/**
 * 
 */
package com.sky.game.service.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class Head implements Serializable{

	/**
	 * 
	 */
	public Head() {
		// TODO Auto-generated constructor stub
	}
	long id	;
	long iconId;
	String iconStr	;
	int count;
	int isPurchase;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIconStr() {
		return iconStr;
	}
	public void setIconStr(String iconStr) {
		this.iconStr = iconStr;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getIsPurchase() {
		return isPurchase;
	}
	public void setIsPurchase(int isPurchase) {
		this.isPurchase = isPurchase;
	}
	/*public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}*/
	public long getIconId() {
		return iconId;
	}
	public void setIconId(long iconId) {
		this.iconId = iconId;
	}
	
}
