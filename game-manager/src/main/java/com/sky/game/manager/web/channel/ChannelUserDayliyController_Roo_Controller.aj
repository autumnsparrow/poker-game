// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sky.game.manager.web.channel;

import com.sky.game.manager.activerecord.UserActiveDayliyStatics;
import com.sky.game.manager.web.channel.ChannelUserDayliyController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ChannelUserDayliyController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String ChannelUserDayliyController.create(@Valid UserActiveDayliyStatics userActiveDayliyStatics, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userActiveDayliyStatics);
            return "channeluser/dayliyStatics/create";
        }
        uiModel.asMap().clear();
        userActiveDayliyStatics.persist();
        return "redirect:/channeluser/dayliyStatics/" + encodeUrlPathSegment(userActiveDayliyStatics.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String ChannelUserDayliyController.createForm(Model uiModel) {
        populateEditForm(uiModel, new UserActiveDayliyStatics());
        return "channeluser/dayliyStatics/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String ChannelUserDayliyController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("useractivedayliystatics", UserActiveDayliyStatics.findUserActiveDayliyStatics(id));
        uiModel.addAttribute("itemId", id);
        return "channeluser/dayliyStatics/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String ChannelUserDayliyController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("useractivedayliystaticses", UserActiveDayliyStatics.findUserActiveDayliyStaticsEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) UserActiveDayliyStatics.countUserActiveDayliyStaticses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("useractivedayliystaticses", UserActiveDayliyStatics.findAllUserActiveDayliyStaticses(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "channeluser/dayliyStatics/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String ChannelUserDayliyController.update(@Valid UserActiveDayliyStatics userActiveDayliyStatics, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userActiveDayliyStatics);
            return "channeluser/dayliyStatics/update";
        }
        uiModel.asMap().clear();
        userActiveDayliyStatics.merge();
        return "redirect:/channeluser/dayliyStatics/" + encodeUrlPathSegment(userActiveDayliyStatics.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String ChannelUserDayliyController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, UserActiveDayliyStatics.findUserActiveDayliyStatics(id));
        return "channeluser/dayliyStatics/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String ChannelUserDayliyController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UserActiveDayliyStatics userActiveDayliyStatics = UserActiveDayliyStatics.findUserActiveDayliyStatics(id);
        userActiveDayliyStatics.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/channeluser/dayliyStatics";
    }
    
    void ChannelUserDayliyController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("userActiveDayliyStatics_staticsdate_date_format", "yyyy-MM-dd hh:mm:ss");
    }
    
    void ChannelUserDayliyController.populateEditForm(Model uiModel, UserActiveDayliyStatics userActiveDayliyStatics) {
        uiModel.addAttribute("userActiveDayliyStatics", userActiveDayliyStatics);
        addDateTimeFormatPatterns(uiModel);
    }
    
    String ChannelUserDayliyController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
