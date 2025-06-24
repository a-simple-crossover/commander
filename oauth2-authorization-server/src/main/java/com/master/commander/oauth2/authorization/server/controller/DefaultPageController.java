package com.master.commander.oauth2.authorization.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultPageController {

    @GetMapping("/loginPage")
    public String loginPage(){
        return "loginPage";
    }
}
