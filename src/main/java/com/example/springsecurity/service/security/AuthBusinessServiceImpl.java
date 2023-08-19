package com.example.springsecurity.service.security;

import com.example.springsecurity.exception.BaseException;
import com.example.springsecurity.models.dto.RefreshTokenDto;
import com.example.springsecurity.models.entities.UserEntity;
import com.example.springsecurity.models.payload.auth.LoginPayload;
import com.example.springsecurity.models.payload.auth.RefreshTokenPayload;
import com.example.springsecurity.models.payload.auth.RegisterPayload;
import com.example.springsecurity.models.response.auth.LoginResponse;
import com.example.springsecurity.models.response.auth.RegisterResponse;
import com.example.springsecurity.service.user.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.io.DataInput;
import java.io.IOException;

import static com.example.springsecurity.models.enums.response.ErrorResponseMessages.EMAIL_ALREADY_REGISTERED;
import static com.example.springsecurity.models.enums.response.ErrorResponseMessages.PASSWORD_INCORRECT;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthBusinessServiceImpl implements AuthBusinessService {

    private final AuthenticationManager authenticationManager;
    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    public LoginResponse login(LoginPayload payload) {
      LoginResponse loginResponse=prepareLoginResponse(payload.getEmail(), payload.isRememberMe());
        authenticate(payload);

        return loginResponse;
    }

    @Override
    public LoginResponse refresh(RefreshTokenPayload payload) {
        return prepareLoginResponse(
                refreshTokenManager.getEmail(payload.getRefreshToken()),
                payload.isRememberMe()
        );
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        log.info("{} user logout succeed", userDetails.getUsername());
        SecurityContextHolder.clearContext();
        new SecurityContextLogoutHandler().logout(httpServletRequest,httpServletResponse,null);
//        log.info("{} user logout succeed", userDetails.getUsername());

    }

    @Override
    public void setAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities(), userDetails.getAuthorities())
        );
    }

    @Override
    public RegisterResponse register(RegisterPayload registerPayload) {

        return convertRegisterResponse(registerPayload);
    }

    // private util methods

    private  void authenticate(LoginPayload request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

        } catch (AuthenticationException e) {
            throw BaseException.of(PASSWORD_INCORRECT);
        }
    }

    private LoginResponse prepareLoginResponse(String email, boolean rememberMe) {
        UserEntity user = userService.getByEmail(email);

        return LoginResponse.builder()
                .accessToken(accessTokenManager.generate(user))
                .refreshToken(refreshTokenManager.generate(
                        RefreshTokenDto.builder()
                                .user(user)
                                .rememberMe(rememberMe)
                                .build()
                ))
                .build();
    }

    private RegisterResponse convertRegisterResponse(RegisterPayload registerPayload) {
        if(userService.checkEmail(registerPayload.getEmail())){
            throw BaseException.of(EMAIL_ALREADY_REGISTERED);
        }
        UserEntity user = objectMapper.convertValue(registerPayload, UserEntity.class);
        UserEntity userEntity = userService.save(user);
        RegisterResponse registerResponse = objectMapper.convertValue(userEntity, RegisterResponse.class);
        return registerResponse;
    }
}
