package com.example.springsecurity.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Component
public class JWTToPrincipalConverter {
public UserPrinciple convert(DecodedJWT jwt){
    return UserPrinciple.builder()
            .userId(Long.valueOf(jwt.getSubject()))
            .email(jwt.getClaim("e").asString())
            .authorities(extractAuthoritiesFromClaim(jwt))
            .build();
}
public List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
    var claim=jwt.getClaim("a");
    if(claim.isNull()|| claim.isMissing()) return List.of();
    return claim.asList(SimpleGrantedAuthority.class);
}

}
