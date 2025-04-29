package com.master.oauth2.authorization.server;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RestController
public class AuthorizationServerApplication {



    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }


    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @GetMapping("/test")
    public String test() {
        return "nologin";
    }
    @GetMapping("/login")
    public String login() throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("user", "password"));
        System.out.println("---------------");
        return "login";
    }
    @PostMapping("/doLogin")
    public void doLogin(String username, String password) {
        System.out.println("---------------===============");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password));
    }



}
