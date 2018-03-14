/**
 * sparrow
 * game-service 
 * Apr 7, 2015- 8:25:19 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.Item;
import com.sky.game.service.internal.InternalItemService;
import com.sky.game.service.internal.ItemsWrapper;

/**
 * @author sparrow
 *
 */
@Service(value="InternalItemService")
public class DefaultInternalItemService implements InternalItemService {
	
	@Autowired
	ItemService itemService;

	/**
	 * 
	 */
	public DefaultInternalItemService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.service.internal.InternalItemService#getItems()
	 */
	public ItemsWrapper getItems() {
		// TODO Auto-generated method stub
		List<Item> ret= itemService.getItems();
		ItemsWrapper o=GameUtil.obtain(ItemsWrapper.class);
		o.setItems(ret);
		return o;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.service.internal.InternalItemService#getItemById(int)
	 */
	public Item getItemById(int id) {
		// TODO Auto-generated method stub
		return itemService.getItemById(id);
	}

}
