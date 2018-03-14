/**
 * 
 */
package com.sky.game.protocol.handler.game;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.commons.GS0000Beans.GS0012Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0012Response;
import com.sky.game.service.domain.TaskTypes;
import com.sky.game.service.task.TaskRateService;

/**
 * GS0012 -
 *  wrap the {@link TaskRateService#updateTask(long, com.sky.game.service.domain.TaskTypes, int)}
 * 
 * @author sparrow
 * @see TaskTypes
 */
@HandlerType(enable=true,transcode="GS0012",namespace="GameSystem")
@Component("GS0012")
public class GS0012Handler implements
		IProtocolHandler<GS0012Request, SG0012Response> {
	
	private static final Log logger=LogFactory.getLog(GS0012Handler.class);
	@Autowired
	TaskRateService taskRate;

	/**
	 * 
	 */
	public GS0012Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0012Request req, SG0012Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		try {
			logger.info("update task id - "+req.getState().getState()+" , player["+req.getUserId()+"]");
			taskRate.updateTaskRate(req.getUserId(), req.getState().getState(), req.getRate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}
