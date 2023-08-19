package com.example.springsecurity.controller;

import com.example.springsecurity.models.base.BaseResponse;
import com.example.springsecurity.models.payload.auth.LoginPayload;
import com.example.springsecurity.models.payload.auth.RefreshTokenPayload;
import com.example.springsecurity.models.payload.auth.RegisterPayload;
import com.example.springsecurity.models.response.auth.LoginResponse;
import com.example.springsecurity.models.response.auth.RegisterResponse;
import com.example.springsecurity.service.security.AuthBusinessService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthBusinessService authBusinessService;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginPayload payload) {
        return BaseResponse.success(authBusinessService.login(payload));
    }

    // todo: refresh token except response
    @PostMapping("/token/refresh")
    public BaseResponse<LoginResponse> refresh(@RequestBody RefreshTokenPayload payload) {
        return BaseResponse.success(authBusinessService.refresh(payload));
    }

    @PostMapping("/logout")
    public BaseResponse<Void> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        authBusinessService.logout(httpServletRequest,httpServletResponse);
        return BaseResponse.success(null);
    }

    @PostMapping("/register")
    public BaseResponse<RegisterResponse> register(@RequestBody RegisterPayload registerPayload) {
        return BaseResponse.successRegister(authBusinessService.register(registerPayload));
    }

}
