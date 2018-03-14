/**
 * sparrow
 * game-service 
 * Apr 7, 2015- 8:35:43 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.service.domain.Item;
import com.sky.game.service.internal.InternalItemService;
import com.sky.game.service.internal.ItemsWrapper;

/**
 * @author sparrow
 *
 */
@Service
public class InternalServiceInvoker {
	
	@Autowired
	InternalItemService internalItemService;

	/**
	 * 
	 */
	public InternalServiceInvoker() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemsWrapper getItems(){
		return internalItemService.getItems();
	}
	
	public Item getItemBytId(int id){
		return internalItemService.getItemById(id);
	}

}
