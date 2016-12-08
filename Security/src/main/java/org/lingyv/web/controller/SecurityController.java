package org.lingyv.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lingyv on 2016/11/3.
 */

@Controller
public class SecurityController {

    @RequestMapping(value = "/s")
    public String test(){
        return "index";
    }
}
