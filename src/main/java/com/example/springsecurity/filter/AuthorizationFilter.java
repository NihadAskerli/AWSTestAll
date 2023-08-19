package com.example.springsecurity.filter;

import com.example.springsecurity.constants.TokenConstants;
import com.example.springsecurity.service.security.AccessTokenManager;
import com.example.springsecurity.service.security.AuthBusinessService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static com.example.springsecurity.constants.TokenConstants.PREFIX;


@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final AccessTokenManager accessTokenManager;
    private final AuthBusinessService authBusinessService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.nonNull(token) && token.startsWith(PREFIX)) {
            String decodeToken = token.substring(7);
            if (null != accessTokenManager.getEmail(decodeToken)) {
                authBusinessService.setAuthentication(
                        accessTokenManager.getEmail(
                                decodeToken
                        )
                );
            }

        }

        filterChain.doFilter(request, response);
    }
}
