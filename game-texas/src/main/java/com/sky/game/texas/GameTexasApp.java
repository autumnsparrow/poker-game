package com.sky.game.texas;

import java.io.InputStream;
import com.sky.game.context.SpringContext;
import com.sky.game.context.service.ServerStarupService;

/**
 * 
 * Startup of the GameTexasApp.
 * 
 * 
 * 
 * Hello world!
 *
 */
public class GameTexasApp 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //

     // initialize the spring context.
     //
      SpringContext.init(new String[]{
    			"classpath:/META-INF/spring/applicationContext-game-service.xml",
    			"classpath:/META-INF/spring/applicationContext-game-protocol.xml"
    			
    	});
      
      //
      // start the game-context routine load.
      //
      ServerStarupService startupService=SpringContext.getBean("serverStarupService");
  	  startupService.load();  
  	  
  	  //
  	  // load configuration from the jar should use getResourceAsStream.
  	  //
  	  InputStream url=GameTexasConfiguration.class.getResourceAsStream("/game-texas.conf");
  	  GameTexasGlobalContext.init(url);
    	
  	  //
  	  // startup the ice runtime.
  	  //
  	  startupService.start();
      
        
    }
}
