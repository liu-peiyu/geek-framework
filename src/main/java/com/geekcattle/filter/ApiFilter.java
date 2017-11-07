package com.geekcattle.filter;

import com.geekcattle.conf.shiro.CustomerAuthenticationToken;
import com.geekcattle.util.JsonUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.jwt.JwtConfig;
import com.geekcattle.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@ServletComponentScan
@WebFilter(filterName = "apiFilter", urlPatterns = "/api/member/*")
public class ApiFilter implements Filter {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        /*
        Enumeration headNames = httpRequest.getHeaderNames();
        while (headNames.hasMoreElements()){
            String headerName = headNames.nextElement().toString();
            System.out.println(headerName);
            String headerValue = httpRequest.getHeader(headerName);
            System.out.println(headerValue);
        }*/

        String auth = httpRequest.getHeader(jwtConfig.getJwtHeader());
        if ((auth != null) && (auth.length() > 7)) {
            String headStr = auth.substring(0, 6);
            if (headStr.compareTo(jwtConfig.getTokenHead()) == 0) {
                auth = auth.substring(7, auth.length());
                Claims claims = jwtUtil.parseJWT(auth, jwtConfig.getSecret());
                if (claims != null) {
                    String userId = claims.getId();
                    String userName =claims.getSubject();
                    httpRequest.setAttribute("userId",userId);
                    httpRequest.setAttribute("userName",userName);
                    chain.doFilter(httpRequest, response);
                    return;
                }
            }
        }

        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write(JsonUtil.toJson(ReturnUtil.Error("TOKEN验证失败")));
        return;
    }

    @Override
    public void destroy() {

    }
}
