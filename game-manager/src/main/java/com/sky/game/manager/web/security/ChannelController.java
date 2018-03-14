package com.sky.game.manager.web.security;
import com.sky.game.manager.model.security.Channel;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/security/channels")
@Controller
@RooWebScaffold(path = "security/channels", formBackingObject = Channel.class)
public class ChannelController {
}
