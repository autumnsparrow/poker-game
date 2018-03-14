package com.sky.game.landlord;

import java.io.InputStream;
import java.util.List;

import com.sky.game.context.SpringContext;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.context.service.ServerStarupService;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.protocol.commons.WX0001Beans;
import com.sky.game.service.domain.Item;


/**
 * Hello world!
 *
 */
public class LLGameApp 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        SpringContext.init(new String[]{
    			"classpath:/META-INF/spring/applicationContext-game-landlord.xml"
    			
    	});
      
      //
      // start the game-context routine load.
      //
      ServerStarupService startupService=SpringContext.getBean("serverStarupService");
  	  startupService.load();  
  	  
  	  //
  	  // load configuration from the jar should use getResourceAsStream.
  	  //
  	 
 	  try {
		LLGameGlobalContext.init();
	} catch (Exception e) {
		// TODO Auto-generated catch block
	}
//		e.printStackTrace();
//	}
 	 // GameTexasGlobalContext.init(url);
    	
  	  
  	 // testItemService();
  	  //
  	  //
  	  // startup the ice runtime.
  	  //
  	 	startupService.start();
 	  
 	  
 	  
 	
// 	
    }
    
    
    private static void testItemService(){
    	List<Item> heads=GSPP.getItems();
    	
    	//System.out.println("invoke item service");
    }
    
    
}
