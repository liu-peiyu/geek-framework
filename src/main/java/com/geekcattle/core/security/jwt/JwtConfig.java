package com.geekcattle.core.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author geekcattle
 */
@Component
public class JwtConfig {

    @Value("${jwt.header}")
    private String jwtHeader;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh_expiration}")
    private Long refreshExpiration;

    @Value("${jwt.token.head}")
    private String tokenHead;

    @Value("${jwt.secret}")
    private String secret;


    public String getJwtHeader() {
        return jwtHeader;
    }

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    public Long getExpiration() {
        return expiration * 1000;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Long getRefreshExpiration() {
        return refreshExpiration;
    }

    public void setRefreshExpiration(Long refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
