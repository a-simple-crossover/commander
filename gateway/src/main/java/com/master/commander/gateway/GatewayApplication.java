package com.master.commander.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbo
 */
@SpringBootApplication
@RestController
@RequestMapping
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @GetMapping("/need")
    public String need() {
        return "need";
    }

    @GetMapping("/no-need")
    public String noNeed() {
        return "no-need";
    }

}
