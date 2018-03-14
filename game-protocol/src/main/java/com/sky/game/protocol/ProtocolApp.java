package com.sky.game.protocol;

import com.sky.game.context.SpringContext;
import com.sky.game.context.domain.MinaBeans.Handler;
import com.sky.game.context.service.ServerStarupService;
import com.sky.game.context.util.GameUtil;
import com.sky.game.protocol.handler.WX0001Handler;
import com.sky.game.service.logic.ShopService;


/**
 * Hello world!
 *
 */
public class ProtocolApp 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
    	
    	
    	SpringContext.init(new String[]{
    			"classpath:/META-INF/spring/applicationContext-game-service.xml",
    			"classpath:/META-INF/spring/applicationContext-game-protocol.xml"
    			
    	});
    	GameUtil.initTokenSerail();
    	
    //	MinaEventHandler.getHandler();
//    	ServerStarupService startupService=SpringContext.getBean("serverStarupService");
//    	startupService.startup();

    	
    
    }
}
