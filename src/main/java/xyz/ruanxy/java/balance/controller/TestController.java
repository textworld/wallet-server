package xyz.ruanxy.java.balance.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wallet")
public class TestController {
    @GetMapping
    public String index(){
        return "test";
    }
}
