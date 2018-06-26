/*
 * Copyright (c) 2017-2018.  放牛极客<l_iupeiyu@qq.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * </p>
 *
 */

package com.geekcattle.core.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
