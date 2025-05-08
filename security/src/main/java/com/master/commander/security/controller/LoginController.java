package com.master.commander.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbo
 */
@RestController
@RequiredArgsConstructor
public class LoginController {


    // 验证管理器
    private final AuthenticationManager authenticationManager;

    // 上下文存储
    private final SecurityContextRepository securityContextRepository;


    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public boolean login(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);

        try {
            Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationResponse);
            securityContextRepository.saveContext(context, request, response);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }
}
