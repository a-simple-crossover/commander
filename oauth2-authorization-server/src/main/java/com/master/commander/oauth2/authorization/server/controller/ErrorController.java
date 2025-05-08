package com.master.commander.oauth2.authorization.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.POST, RequestMethod.GET})
    public String error(){
        return "error";
    }
}
