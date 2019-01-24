package com.geekcattle.core.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author geekcattle
 */
public class CustomerAuthenticationToken extends UsernamePasswordToken {

    private String captcha;
    /**
     * 用来区分登录用户的渠道
     */
    private String loginForm;

    private String token;

    public CustomerAuthenticationToken(String username, String password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

    public CustomerAuthenticationToken(String token){
        this.token = token;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getLoginForm() {
        return loginForm;
    }

    public void setLoginForm(String loginForm) {
        this.loginForm = loginForm;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        if(this.token != null){
            return this.token;
        }else{
            return this.getUsername();
        }

    }

    @Override
    public Object getCredentials() {
        if(this.token != null){
            return this.token;
        }else{
            return this.getPassword();
        }
    }
}
