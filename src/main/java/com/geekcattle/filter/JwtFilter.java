package com.geekcattle.filter;

import com.geekcattle.core.jwt.JwtConfig;
import com.geekcattle.core.jwt.JwtToken;
import com.geekcattle.core.jwt.JwtUtil;
import com.geekcattle.model.member.Member;
import com.geekcattle.service.member.MemberService;
import com.geekcattle.util.JsonUtil;
import com.geekcattle.util.ReturnUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubject.Builder;
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

/*@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/api/member/**")*/
public class JwtFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

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
        if ((auth != null) && (auth.length() > 7) && StringUtils.isNotEmpty(auth)) {
            String headStr = auth.substring(0, 6);
            if (headStr.compareTo(jwtConfig.getTokenHead()) == 0) {
                auth = auth.substring(7, auth.length());
                String userName = jwtUtil.getUsernameFromToken(auth);
                Member member = memberService.findByUsername(userName);
                if(userName != null){
                    if(jwtUtil.validateToken(auth, member)){
                       /* PrincipalCollection principal = new SimplePrincipalCollection(member, "customShiroRealm");
                        Builder builder = new Builder(request, response);
                        builder.principals(principal);
                        builder.authenticated(true);
                        WebSubject subject = builder.buildWebSubject();
                        ThreadContext.bind(subject);*/
                        JwtToken token = new JwtToken(auth);
                        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
                        SecurityUtils.getSubject().login(token);
                        chain.doFilter(request, response);
                    }
                }
                /*Claims claims = jwtUtil.parseJWT(auth, jwtConfig.getSecret());
                if (claims != null) {
                    String userId = claims.getId();
                    String userName =claims.getSubject();
                    request.setAttribute("userId", userId);
                    request.setAttribute("userName", userName);
                    CustomHttpServletRequest customHttpServletRequest = new CustomHttpServletRequest(request);
                    chain.doFilter(customHttpServletRequest, response);
                    return;
                }*/
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JsonUtil.toJson(ReturnUtil.Error("TOKEN验证失败")));
        return;
    }
}
