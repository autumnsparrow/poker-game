// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sky.game.manager.web.manager;

import com.sky.game.manager.activerecord.UserActiveDayliyStatics;
import com.sky.game.manager.web.manager.UserActiveDayliyController;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

privileged aspect UserActiveDayliyController_Roo_Controller_Finder {
    
    @RequestMapping(params = { "find=ByChannelId", "form" }, method = RequestMethod.GET)
    public String UserActiveDayliyController.findUserActiveDayliyStaticsesByChannelIdForm(Model uiModel) {
        return "manager/userActiveDaylies/findUserActiveDayliyStaticsesByChannelId";
    }
    
    @RequestMapping(params = "find=ByChannelId", method = RequestMethod.GET)
    public String UserActiveDayliyController.findUserActiveDayliyStaticsesByChannelId(@RequestParam("channelId") int channelId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("useractivedayliystaticses", UserActiveDayliyStatics.findUserActiveDayliyStaticsesByChannelId(channelId, sortFieldName, sortOrder).setFirstResult(firstResult).setMaxResults(sizeNo).getResultList());
            float nrOfPages = (float) UserActiveDayliyStatics.countFindUserActiveDayliyStaticsesByChannelId(channelId) / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("useractivedayliystaticses", UserActiveDayliyStatics.findUserActiveDayliyStaticsesByChannelId(channelId, sortFieldName, sortOrder).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "manager/userActiveDaylies/list";
    }
    
    @RequestMapping(params = { "find=ByStaticsDateBetween", "form" }, method = RequestMethod.GET)
    public String UserActiveDayliyController.findUserActiveDayliyStaticsesByStaticsDateBetweenForm(Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        return "manager/userActiveDaylies/findUserActiveDayliyStaticsesByStaticsDateBetween";
    }
    
    @RequestMapping(params = "find=ByStaticsDateBetween", method = RequestMethod.GET)
    public String UserActiveDayliyController.findUserActiveDayliyStaticsesByStaticsDateBetween(@RequestParam("minStaticsDate") @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") Date minStaticsDate, @RequestParam("maxStaticsDate") @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") Date maxStaticsDate, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("useractivedayliystaticses", UserActiveDayliyStatics.findUserActiveDayliyStaticsesByStaticsDateBetween(minStaticsDate, maxStaticsDate, sortFieldName, sortOrder).setFirstResult(firstResult).setMaxResults(sizeNo).getResultList());
            float nrOfPages = (float) UserActiveDayliyStatics.countFindUserActiveDayliyStaticsesByStaticsDateBetween(minStaticsDate, maxStaticsDate) / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("useractivedayliystaticses", UserActiveDayliyStatics.findUserActiveDayliyStaticsesByStaticsDateBetween(minStaticsDate, maxStaticsDate, sortFieldName, sortOrder).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "manager/userActiveDaylies/list";
    }
    
}
