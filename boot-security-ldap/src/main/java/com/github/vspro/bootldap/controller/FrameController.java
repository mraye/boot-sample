package com.github.vspro.bootldap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrameController {



    @RequestMapping(value = "/accessDeny")
    public String accessDenyPage(){
        return "accessDeny";
    }


    @RequestMapping(value = "/home")
    public String home(){
        return "home";
    }
}
