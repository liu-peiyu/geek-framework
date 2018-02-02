/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.core.shiro;

import com.geekcattle.core.redis.RedisCacheManager;
import com.geekcattle.core.redis.RedisSessionDAO;
import com.geekcattle.filter.CustomerLogoutFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.*;

/**
 * Shiro 配置
 *
 Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 *
 */
@Configuration
public class ShiroConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 前台身份认证realm;
     * @return
     */
    @Bean(name="customShiroRealm")
    public CustomShiroRealm customShiroRealm(){
        logger.debug("ShiroConfiguration.customShiroRealm()");
        CustomShiroRealm customShiroRealm = new CustomShiroRealm();
        customShiroRealm.setCacheManager(redisCacheManager());//redis权限缓存 默认缓存可注释此行
        customShiroRealm.setCredentialsMatcher(customHashedCredentialsMatcher());
        return customShiroRealm;
    }

    /**
     * 后台身份认证realm;
     * @return
     */
    @Bean(name="adminShiroRealm")
    public AdminShiroRealm adminShiroRealm(){
        logger.debug("ShiroConfiguration.adminShiroRealm()");
        AdminShiroRealm adminShiroRealm = new AdminShiroRealm();
        adminShiroRealm.setCacheManager(redisCacheManager());//redis权限缓存 默认缓存可注释此行
        adminShiroRealm.setCredentialsMatcher(adminHashedCredentialsMatcher());
        return adminShiroRealm;
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     * @return
     */
    @Bean(name="ehCacheManager")
    public EhCacheManager ehCacheManager(){
        logger.debug("ShiroConfiguration.ehCacheManager()");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        logger.debug("ShiroConfiguration.redisCacheManager()");
        return new RedisCacheManager();
    }

    @Bean(name = "redisSessionDAO")
    public RedisSessionDAO redisSessionDAO(){
        logger.debug("ShiroConfiguration.redisSessionDAO()");
        return new RedisSessionDAO();
    }

    @Bean(name = "customSessionListener")
    public CustomSessionListener customSessionListener(){
        logger.debug("ShiroConfiguration.customSessionListener()");
        return new CustomSessionListener();
    }

    /**
     * @see DefaultWebSessionManager
     * @return
     */
    @Bean(name="sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        logger.debug("ShiroConfiguration.defaultWebSessionManager()");
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //用户信息必须是序列化格式，要不创建用户信息创建不过去，此坑很大，
        sessionManager.setSessionDAO(redisSessionDAO());//如不想使用REDIS可注释此行
        Collection<SessionListener> sessionListeners = new ArrayList<>();
        sessionListeners.add(customSessionListener());
        sessionManager.setSessionListeners(sessionListeners);
        //单位为毫秒（1秒=1000毫秒） 3600000毫秒为1个小时
        sessionManager.setSessionValidationInterval(3600000*12);
        //3600000 milliseconds = 1 hour
        sessionManager.setGlobalSessionTimeout(3600000*12);
        //是否删除无效的，默认也是开启
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启 检测，默认开启
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //创建会话Cookie
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("WEBID");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    /**
     * @see org.apache.shiro.mgt.SecurityManager
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManage(){
        logger.debug("ShiroConfiguration.getDefaultWebSecurityManage()");
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();

        Map<String, Object> shiroAuthenticatorRealms = new HashMap<>();
        shiroAuthenticatorRealms.put("adminShiroRealm", adminShiroRealm());
        shiroAuthenticatorRealms.put("customShiroRealm", customShiroRealm());

        Collection<Realm> shiroAuthorizerRealms = new ArrayList<Realm>();
        shiroAuthorizerRealms.add(adminShiroRealm());
        shiroAuthorizerRealms.add(customShiroRealm());

        CustomModularRealmAuthenticator customModularRealmAuthenticator = new CustomModularRealmAuthenticator();
        customModularRealmAuthenticator.setDefinedRealms(shiroAuthenticatorRealms);
        customModularRealmAuthenticator.setAuthenticationStrategy(authenticationStrategy());
        securityManager.setAuthenticator(customModularRealmAuthenticator);
        securityManager.setRealms(shiroAuthorizerRealms);
        //注入缓存管理器;
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setSessionManager(defaultWebSessionManager());
        return securityManager;
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean(name="authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        logger.debug("ShiroConfiguration.authorizationAttributeSourceAdvisor()");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean(name = "shirFilter")
    public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager){
        logger.debug("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //增加自定义过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("admin", new AdminFormAuthenticationFilter());
        filters.put("custom", new CustomFormAuthenticationFilter());
        filters.put("logout", new CustomerLogoutFilter());
        shiroFilterFactoryBean.setFilters(filters);
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        /**
         * anon（匿名）  org.apache.shiro.web.filter.authc.AnonymousFilter
         * authc（身份验证）       org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         * authcBasic（http基本验证）    org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
         * logout（退出）        org.apache.shiro.web.filter.authc.LogoutFilter
         * noSessionCreation（不创建session） org.apache.shiro.web.filter.session.NoSessionCreationFilter
         * perms(许可验证)  org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
         * port（端口验证）   org.apache.shiro.web.filter.authz.PortFilter
         * rest  (rest方面)  org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
         * roles（权限验证）  org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
         * ssl （ssl方面）   org.apache.shiro.web.filter.authz.SslFilter
         * member （用户方面）  org.apache.shiro.web.filter.authc.UserFilter
         * user  表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
         */

        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**/login", "anon");
        filterChainDefinitionMap.put("/**/logout", "logout");
        filterChainDefinitionMap.put("/**/reg", "anon");
        //配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/console/**", "admin");
        filterChainDefinitionMap.put("/member/**", "custom");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        //shiroFilterFactoryBean.setLoginUrl("/member/login");
        // 登录成功后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/member/index");
        //未授权界面;
        //shiroFilterFactoryBean.setUnauthorizedUrl("/console/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * Shiro默认提供了三种 AuthenticationStrategy 实现：
     * AtLeastOneSuccessfulStrategy ：其中一个通过则成功。
     * FirstSuccessfulStrategy ：其中一个通过则成功，但只返回第一个通过的Realm提供的验证信息。
     * AllSuccessfulStrategy ：凡是配置到应用中的Realm都必须全部通过。
     * authenticationStrategy
     * @return
     */
    @Bean(name="authenticationStrategy")
    public AuthenticationStrategy authenticationStrategy() {
        logger.debug("ShiroConfiguration.authenticationStrategy()");
        return new FirstSuccessfulStrategy();
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * @return
     */
    @Bean(name = "adminHashedCredentialsMatcher")
    public HashedCredentialsMatcher adminHashedCredentialsMatcher(){
        logger.debug("ShiroConfiguration.adminHashedCredentialsMatcher()");
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，当于 m比如散列两次，相d5(md5(""));
        return hashedCredentialsMatcher;
    }

    @Bean(name = "customHashedCredentialsMatcher")
    public HashedCredentialsMatcher customHashedCredentialsMatcher(){
        logger.debug("ShiroConfiguration.adminHashedCredentialsMatcher()");
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，当于 m比如散列两次，相d5("");
        return hashedCredentialsMatcher;
    }

    /**
     * 注入LifecycleBeanPostProcessor
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        logger.debug("ShiroConfiguration.lifecycleBeanPostProcessor()");
        return new LifecycleBeanPostProcessor();
    }

    @ConditionalOnMissingBean
    @Bean(name = "defaultAdvisorAutoProxyCreator")
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        logger.debug("ShiroConfiguration.getDefaultAdvisorAutoProxyCreator()");
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }
}
