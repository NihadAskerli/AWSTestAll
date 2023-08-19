package com.example.springsecurity.service.security;

import com.example.springsecurity.exception.BaseException;
import com.example.springsecurity.models.entities.UserEntity;
import com.example.springsecurity.models.proporties.security.SecurityProperties;
import com.example.springsecurity.service.base.TokenGenerator;
import com.example.springsecurity.service.base.TokenReader;
import com.example.springsecurity.service.getters.EmailGetter;

import com.example.springsecurity.utils.PublicPrivateKeyUtils;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.springsecurity.constants.TokenConstants.EMAIL_KEY;
import static com.example.springsecurity.models.enums.response.ErrorResponseMessages.BEARER_TOKEN;


@Component
@Slf4j
@RequiredArgsConstructor
public class AccessTokenManager implements TokenGenerator<UserEntity>,
        TokenReader<Claims>, EmailGetter {

    private final SecurityProperties securityProperties;

    @Override
    public String generate(UserEntity obj) {

        Claims claims = Jwts.claims();
        claims.put(EMAIL_KEY, obj.getEmail());
        claims.put("role", obj.getRole());
        Date now = new Date();
        Date exp = new Date(now.getTime() + securityProperties.getJwt().getAccessTokenValidityTime());

        return Jwts.builder()
                .setSubject(String.valueOf(obj.getId()))
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(claims)
                .signWith(PublicPrivateKeyUtils.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }


    @Override
    public Claims read(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(PublicPrivateKeyUtils.getPublicKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            BaseException.of(BEARER_TOKEN);
        }

        return claims;
    }

    @Override
    public String getEmail(String token) {
        if (read(token) != null) {
            return read(token).get(EMAIL_KEY, String.class);
        } else {
            return null;
        }
    }

}
