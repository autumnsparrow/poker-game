package com.sky.game.manager.web.security;
import com.sky.game.manager.model.security.ChannelPrincipalAssignment;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/security/channelPrincipalAssignments")
@Controller
@RooWebScaffold(path = "security/channelPrincipalAssignments", formBackingObject = ChannelPrincipalAssignment.class)
public class ChannelPrincipalAssignmentController {
}
