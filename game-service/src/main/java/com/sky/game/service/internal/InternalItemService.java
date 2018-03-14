/**
 * sparrow
 * game-service 
 * Apr 7, 2015- 8:21:04 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.internal;

import java.util.List;

import com.sky.game.context.spring.IRemoteService;
import com.sky.game.service.domain.Item;

/**
 * @author sparrow
 *
 */
public interface InternalItemService extends IRemoteService {
	
	public ItemsWrapper getItems();
	public Item getItemById(int id);
}
