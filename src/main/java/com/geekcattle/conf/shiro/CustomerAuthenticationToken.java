package com.geekcattle.conf.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * author geekcattle
 * date 2017/3/9 0009 下午 16:05
 */
public class CustomerAuthenticationToken extends UsernamePasswordToken {

    private String captcha;
    /**
     * 用来区分前后台登录的标记
     */
    private String loginType;

    public CustomerAuthenticationToken(String username, String password, boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
