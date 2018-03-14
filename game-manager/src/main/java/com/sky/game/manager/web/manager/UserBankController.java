package com.sky.game.manager.web.manager;
import com.sky.game.manager.remote.activerecord.UserBank;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/userbanks")
@Controller
@RooWebScaffold(path = "userbanks", formBackingObject = UserBank.class)
public class UserBankController {
}
