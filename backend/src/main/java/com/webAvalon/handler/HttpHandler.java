package com.webAvalon.handler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpHandler {
    @RequestMapping("/")
    public String hello() {
        return "hello stomp!";
    }
}
