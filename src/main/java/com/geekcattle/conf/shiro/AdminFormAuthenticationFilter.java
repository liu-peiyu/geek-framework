package com.geekcattle.conf.shiro;

import com.geekcattle.conf.LoginEnum;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * author geekcattle
 * date 2017/3/21 0021 下午 16:33
 */
public class AdminFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        if(subject.getPrincipal() != null){
            Session session = subject.getSession();
            if(session.getAttribute("loginType").equals(LoginEnum.ADMIN.toString())){
                return super.isAccessAllowed(request, response, mappedValue);
            }else{
                return false;
            }
        }else{
            return super.isAccessAllowed(request, response, mappedValue);
        }
    }

    @Override
    public void setLoginUrl(String loginUrl) {
        super.setLoginUrl("/console/login");
    }

    @Override
    public void setSuccessUrl(String successUrl) {
        super.setSuccessUrl("/console/index");
    }

}
