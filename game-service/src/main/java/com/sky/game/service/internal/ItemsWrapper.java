/**
 * sparrow
 * game-service 
 * Apr 8, 2015- 9:24:43 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.internal;

import java.util.List;

import com.sky.game.service.domain.Item;

/**
 * @author sparrow
 *
 */
public class ItemsWrapper {
	
	List<Item> items;

	/**
	 * 
	 */
	public ItemsWrapper() {
		// TODO Auto-generated constructor stub
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
