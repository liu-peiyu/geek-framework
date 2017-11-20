package com.geekcattle.filter;

import com.geekcattle.util.JsonUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.core.jwt.JwtConfig;
import com.geekcattle.core.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@ServletComponentScan
@WebFilter(filterName = "apiFilter", urlPatterns = "/api/member/*")
public class ApiFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        /*
        Enumeration headNames = httpRequest.getHeaderNames();
        while (headNames.hasMoreElements()){
            String headerName = headNames.nextElement().toString();
            System.out.println(headerName);
            String headerValue = httpRequest.getHeader(headerName);
            System.out.println(headerValue);
        }*/

        String auth = request.getHeader(jwtConfig.getJwtHeader());
        if ((auth != null) && (auth.length() > 7)) {
            String headStr = auth.substring(0, 6);
            if (headStr.compareTo(jwtConfig.getTokenHead()) == 0) {
                auth = auth.substring(7, auth.length());
                Claims claims = jwtUtil.parseJWT(auth, jwtConfig.getSecret());
                if (claims != null) {
                    String userId = claims.getId();
                    String userName =claims.getSubject();
                    String sessionId = claims.getAudience();
                    request.setAttribute("userId", userId);
                    request.setAttribute("userName", userName);
                    CustomHttpServletRequest customHttpServletRequest = new CustomHttpServletRequest(request);
                    customHttpServletRequest.putHeader("Cookie", "WEBID="+ sessionId);
                    chain.doFilter(customHttpServletRequest, response);
                    return;
                }
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JsonUtil.toJson(ReturnUtil.Error("TOKEN验证失败")));
        return;
    }
}
