package com.sky.game.manager.web.channel;
import com.sky.game.manager.activerecord.UserActiveDayliyStatics;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/channeluser/dayliyStatics")
@Controller
@RooWebScaffold(path = "channeluser/dayliyStatics", formBackingObject = UserActiveDayliyStatics.class)
public class ChannelUserDayliyController {
}
