package com.example.springsecurity.service.security;

import com.example.springsecurity.models.payload.auth.LoginPayload;
import com.example.springsecurity.models.payload.auth.RefreshTokenPayload;
import com.example.springsecurity.models.payload.auth.RegisterPayload;
import com.example.springsecurity.models.response.auth.LoginResponse;
import com.example.springsecurity.models.response.auth.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthBusinessService {

    LoginResponse login(LoginPayload payload);

    LoginResponse refresh(RefreshTokenPayload payload);

    void logout(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);

    void setAuthentication(String email);
    RegisterResponse register(RegisterPayload registerPayload);


}
