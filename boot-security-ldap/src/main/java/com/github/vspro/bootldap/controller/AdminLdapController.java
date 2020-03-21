package com.github.vspro.bootldap.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminLdapController {


    @RequestMapping("/index")
    public String index(){
        return "hei, this is admin page";
    }


}
