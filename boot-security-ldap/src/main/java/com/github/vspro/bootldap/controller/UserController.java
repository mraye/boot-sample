package com.github.vspro.bootldap.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @RequestMapping("/work")
    public String work(){
        return "wow, it worked!!";
    }
}
