/**
 * sparrow
 * game-service 
 * Dec 29, 2014- 11:30:42 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.sky.game.service.domain.Item;
import com.sky.game.service.persistence.ItemMapper;

/**
 * 
 * 
 * @author sparrow
 *
 */
@Service
public class ItemService  {
	
	@Autowired
	ItemMapper itemMapper;

	/**
	 * 
	 */
	public ItemService() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * get item by id
	 * @param id
	 * @return
	 */
	public Item getItemById(int id){
		return itemMapper.selectByPrimaryKey(Long.valueOf(id));
	}
	
	/**
	 * get items by ids.
	 * 
	 * <p>
	 * {@link ItemMapper#selectAllItems()}
	 * @param ids
	 * @return
	 */
	public List<Item> getItems(){
		
		List<Item> items=itemMapper.selectAllItems();
		return items;
	}
	

}
