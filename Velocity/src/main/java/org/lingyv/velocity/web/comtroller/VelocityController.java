package org.lingyv.velocity.web.comtroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lingyv on 2016/12/8.
 */

@Controller
@RequestMapping("/v")
public class VelocityController {

    @RequestMapping("/h")
    public String Hello(ModelMap map) {
        map.addAttribute("User", "lingyv");
        return "hello";
    }
}
