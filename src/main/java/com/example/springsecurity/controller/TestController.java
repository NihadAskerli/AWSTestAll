package com.example.springsecurity.controller;

import com.example.springsecurity.models.base.BaseResponse;
import com.example.springsecurity.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public BaseResponse<String> test() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return BaseResponse.success(userDetails.getUsername());
    }

    @GetMapping("/test/no-auth")
    public BaseResponse<String> testNoAuth() {

        userService.getByEmail("sdkjfhsdkjfh");

        return BaseResponse.successRegister("Course ERP - No Auth");
    }
    @GetMapping("/test2")
    public BaseResponse<String> test2() {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return BaseResponse.success("salam");
    }
}
