package com.master.commander.oauth2.authorization.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/find")
public class Oauth2Controller {

    /**
     * 授权码请求地址模版
     * <a href="http://localhost/oauth2/authorize?client_id=client1&response_type=code&redirect_uri=http://localhost/find/code&scope=read/">
     */
    @RequestMapping(value = "/code", method = {RequestMethod.GET, RequestMethod.POST})
    public String code(@RequestParam("code") String code){
        return code;
    }

    @GetMapping("/consent")
    public String consent(){
        return "ok";
    }
}
