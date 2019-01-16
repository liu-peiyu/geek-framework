package com.geekcattle.core.security;

import com.geekcattle.util.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geekcattle
 */
public class AjaxAuthFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(isAjaxRequest(request)){
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().print(JsonUtil.toJsonError("登录失败",exception.getMessage()));
            response.getWriter().flush();
        }else{
            super.onAuthenticationFailure(request, response, exception);
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }
}
