package com.sky.game.manager.web.manager;
import com.sky.game.manager.activerecord.UserActiveDayliyStatics;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;

@RequestMapping("/manager/userActiveDaylies")
@Controller
@RooWebScaffold(path = "manager/userActiveDaylies", formBackingObject = UserActiveDayliyStatics.class)
@RooWebFinder
public class UserActiveDayliyController {
}
