package com.example.springsecurity.controller;

import com.example.springsecurity.config.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/")
    public String test(){
        return "Salam";
    }
    @GetMapping("/secured")
    public String test2(@AuthenticationPrincipal UserPrinciple userPrinciple){
        return "login olundu tokenle:"+userPrinciple.getEmail()+"User Id:"+userPrinciple.getUserId();
    }
    @GetMapping("/admin")
    public String testAdmin(@AuthenticationPrincipal UserPrinciple userPrinciple){
        return "login olundu tokenle:"+userPrinciple.getEmail()+"User Id:"+userPrinciple.getUserId();
    }
}
