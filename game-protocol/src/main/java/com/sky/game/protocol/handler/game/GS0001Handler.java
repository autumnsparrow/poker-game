/**
 * 
 */
package com.sky.game.protocol.handler.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.commons.GS0000Beans;
import com.sky.game.protocol.commons.GS0000Beans.GS0001Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0001Response;
import com.sky.game.service.domain.Item;
import com.sky.game.service.logic.ItemService;

/**
 * GS001 -
 * 	wrap the {@link ItemService#getItems()}
 * 
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0001",namespace="GameSystem")
@Component("GS0001")
public class GS0001Handler implements IProtocolHandler<GS0000Beans.GS0001Request, GS0000Beans.SG0001Response> {

	@Autowired
	ItemService itemService;
	
	/**
	 * 
	 */
	public GS0001Handler() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	@HandlerMethod(enable=true)
	public boolean handler(GS0001Request req, SG0001Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		List<Item> items=itemService.getItems();
		res.setItems(items);
		
		return true;
	}

}
