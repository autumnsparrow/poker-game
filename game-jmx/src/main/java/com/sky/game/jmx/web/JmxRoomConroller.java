package com.sky.game.jmx.web;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.management.JMException;
import javax.servlet.http.HttpServletRequest;

import com.j256.simplejmx.client.JmxClient;
import com.sky.game.jmx.JmxRoom;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;

@RequestMapping("/jmx/room")
@Controller
@RooWebScaffold(path = "jmx/room", formBackingObject = JmxRoom.class)
@RooWebFinder
public class JmxRoomConroller {
	
	private static final Log logger=LogFactory.getLog(JmxRoomConroller.class);

    @RequestMapping(params = { "find=ByChannelIdEqualsAndRoomIdEqualsAndVipNot", "form" }, method = RequestMethod.GET)
    public String findJmxRoomsByChannelIdEqualsAndRoomIdEqualsAndVipNotForm(Model uiModel) {
        return "jmx/room/findJmxRoomsByChannelIdEqualsAndRoomIdEqualsAndVipNot";
    }

    
    
    
    
    
    
    public   String  checkRoom(long channelId, long roomId, boolean vip) {
		// TODO Auto-generated method stub
		String jmxString=null;
		try {
			JmxClient client=new JmxClient("www.ipagat.com", 30000, "admin", "admin");
			Object obj=client.invokeOperation("com.sky.game.landlord.jmx", "gameJmxObserver", "checkRoom", new Object[]{Long.valueOf(channelId),Long.valueOf(roomId),Boolean.valueOf(vip)});
			if(obj!=null){
				jmxString=(String)obj;
				jmxString=writeFile(roomId, jmxString);
			}
			//client.getOperationsInfo("com.sky.game.landlord.jmx", "gameJmxObserver");
		} catch (JMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jmxString;
	}
    
    @Autowired
	private HttpServletRequest request;
    
    
    private String  writeFile( long roomId,String message){
    	String path=(String.format("%d.xml",roomId));
    	
		try {
		String p=request.getSession().getServletContext().getRealPath("/");
		logger.info("real path:"+p);
		
		File f=new File(String.format("%s/%s", p,path));
		
		FileUtils.writeStringToFile(f, message);
		logger.info(f.getAbsolutePath());
		path="../resources/"+path;
		logger.info(path);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return path;
    }
    
    @RequestMapping(params = "find=ByChannelIdEqualsAndRoomIdEqualsAndVipNot", method = RequestMethod.GET)
    public String findJmxRoomsByChannelIdEqualsAndRoomIdEqualsAndVipNot(@RequestParam("channelId") Long channelId, @RequestParam("roomId") Long roomId, @RequestParam(value = "vip", required = false) Boolean vip, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            List<JmxRoom> jmxrooms=new LinkedList<JmxRoom>();
            if(vip==null)
            	vip=Boolean.FALSE;
            JmxRoom room=new JmxRoom();
            room.setChannelId(channelId);
            room.setRoomId(roomId);
            room.setVip(vip);
            String message=checkRoom(channelId, roomId, vip);
            room.setContent(message);
            jmxrooms.add(room);
            
            uiModel.addAttribute("jmxroom",room );
           // float nrOfPages = 1;//(float) JmxRoom.countFindJmxRoomsByChannelIdEqualsAndRoomIjdEqualsAndVipNot(channelId, roomId, vip == null ? Boolean.FALSE : vip) / sizeNo;
            //uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
           // uiModel.addAttribute("jmxrooms", JmxRoom.findJmxRoomsByChannelIdEqualsAndRoomIdEqualsAndVipNot(channelId, roomId, vip == null ? Boolean.FALSE : vip, sortFieldName, sortOrder).getResultList());
        }
        return "jmx/room/show";
    }

    
    
   

   
}
