/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.core.jwt;

import com.geekcattle.core.LoginEnum;
import com.geekcattle.core.shiro.CustomerAuthenticationToken;
import com.geekcattle.model.member.Member;
import com.geekcattle.service.member.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * 前台身份校验核心类
 * author geekcattle
 * date 2016/11/22 0022 下午 15:27
 */
public class JwtShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtUtil jwtUtil;



/*    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }*/


    /**
     * 认证信息.(身份验证)
     * :
     * Authentication 是用来验证用户身份
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("前台登录认证：JwtShiroRealm.doGetAuthenticationInfo()");
        String auth = (String) token.getCredentials();
        if (auth == null || auth.length() < 7 || StringUtils.isEmpty(auth)) {
            throw new AuthenticationException("token为空");
        }
        String headStr = auth.substring(0, 6);
        if (headStr.compareTo(jwtConfig.getTokenHead()) != 0) {
            throw new AuthenticationException("token格式错误");
        }
        String tmpAuth = auth.substring(7, auth.length());
        String userName = jwtUtil.getUsernameFromToken(tmpAuth);

        if (userName == null) {
            throw new AuthenticationException("用户名错误");
        }
        Member userInfo = memberService.findByUsername(userName);
        if(userInfo == null){
            throw new AuthenticationException("User didn't existed!");
        }
        if(!jwtUtil.validateToken(tmpAuth, userInfo)){
            throw new AuthenticationException("token验证失败");
        }
        logger.info("登录用户："+userName);
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法

        if("0".equals(userInfo.getState().toString())) {
            throw new LockedAccountException(); //帐号锁定
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                auth, //密码
                userInfo.getAccount()
        );

        return authenticationInfo;
    }

    /**
     * 此方法调用  hasRole,hasPermission的时候才会进行回调.
     *
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，
     * 调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
       /*
        * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
        * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
        * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
        * 缓存过期之后会再次执行。
        */
        System.out.println("前台权限校验-->CustomShiroRealm.doGetAuthorizationInfo()");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        return authorizationInfo;
    }


}
