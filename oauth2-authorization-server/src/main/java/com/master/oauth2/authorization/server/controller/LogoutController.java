package com.master.oauth2.authorization.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登出的默认路径是 POST /logout
 * 登出成功后会重定向到配置的地址/logout/success
 * @see SimpleUrlLogoutSuccessHandler#onLogoutSuccess(HttpServletRequest, HttpServletResponse, Authentication)
 * @author zhangbo
 */
@RestController
public class LogoutController {



    @RequestMapping(value = "/logout/success", method = {RequestMethod.POST, RequestMethod.GET})
    public boolean logoutSuccess() {
        return true;
    }
}
