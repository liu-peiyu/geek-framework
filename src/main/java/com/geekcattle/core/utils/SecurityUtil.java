package com.geekcattle.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * security工具类
 * @author geekcattle
 */
public class SecurityUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public static SecurityContext getContext(){
        return SecurityContextHolder.getContext();
    }

    /**
     * 判断是否登录
     * @return
     */
    public static Boolean isLogin(){
        return getContext().getAuthentication() != null && getContext().getAuthentication().isAuthenticated() && !"anonymousUser".equals(getContext().getAuthentication().getName());
    }

    /**
     * 获取前台用户信息
     * @return
     */
    public static User getFontUserInfo(){
        User user =  (User) getContext().getAuthentication().getPrincipal();
        return user;
    }

}
