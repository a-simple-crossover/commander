package com.master.oauth2.authorization.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/find")
public class Oauth2Controller {

    @RequestMapping(value = "/code", method = {RequestMethod.GET, RequestMethod.POST})
    public String code(@RequestParam("code") String code){
        return code;
    }
}
