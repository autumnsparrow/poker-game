// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sky.game.jmx.web;

import com.sky.game.jmx.JmxRoom;
import com.sky.game.jmx.web.JmxRoomConroller;
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

privileged aspect JmxRoomConroller_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String JmxRoomConroller.create(@Valid JmxRoom jmxRoom, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, jmxRoom);
            return "jmx/room/create";
        }
        uiModel.asMap().clear();
        jmxRoom.persist();
        return "redirect:/jmx/room/" + encodeUrlPathSegment(jmxRoom.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String JmxRoomConroller.createForm(Model uiModel) {
        populateEditForm(uiModel, new JmxRoom());
        return "jmx/room/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String JmxRoomConroller.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("jmxroom", JmxRoom.findJmxRoom(id));
        uiModel.addAttribute("itemId", id);
        return "jmx/room/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String JmxRoomConroller.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("jmxrooms", JmxRoom.findJmxRoomEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) JmxRoom.countJmxRooms() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("jmxrooms", JmxRoom.findAllJmxRooms(sortFieldName, sortOrder));
        }
        return "jmx/room/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String JmxRoomConroller.update(@Valid JmxRoom jmxRoom, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, jmxRoom);
            return "jmx/room/update";
        }
        uiModel.asMap().clear();
        jmxRoom.merge();
        return "redirect:/jmx/room/" + encodeUrlPathSegment(jmxRoom.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String JmxRoomConroller.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, JmxRoom.findJmxRoom(id));
        return "jmx/room/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String JmxRoomConroller.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        JmxRoom jmxRoom = JmxRoom.findJmxRoom(id);
        jmxRoom.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/jmx/room";
    }
    
    void JmxRoomConroller.populateEditForm(Model uiModel, JmxRoom jmxRoom) {
        uiModel.addAttribute("jmxRoom", jmxRoom);
    }
    
    String JmxRoomConroller.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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