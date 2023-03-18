package com.example.springsecurity.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTDecoder jwtDecoder;
    private final JWTToPrincipalConverter jwtToPrincipalConverter;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFrpmRequest(request)
                .map(jwtDecoder::decode)
                        .map(jwtToPrincipalConverter::convert)
                                .map(UserPrincipalAuthenticationToken::new)
                                        .ifPresent(authetication-> SecurityContextHolder.getContext().setAuthentication(authetication));
        filterChain.doFilter(request,response);
    }
    private Optional<String> extractTokenFrpmRequest(HttpServletRequest httpServletRequest){
        var token=httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}
