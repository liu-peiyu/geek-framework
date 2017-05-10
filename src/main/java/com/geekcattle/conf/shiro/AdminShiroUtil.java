package com.geekcattle.conf.shiro;

import com.geekcattle.model.console.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro工具类
 * author geekcattle
 * date 2016/12/6 0006 上午 10:45
 */
public class AdminShiroUtil {
    /**
     * 获取授权主要对象
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static Session getSession(){
        try{
            Session session = getSubject().getSession();
            if (session == null){
                session = getSubject().getSession();
            }
            if (session != null){
                return session;
            }
        }catch (InvalidSessionException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Admin getUserInfo(){
        try {
            if(getSession() != null){
                Admin admin = (Admin) getSubject().getPrincipals().getPrimaryPrincipal();
                return admin;
            }else{
                return null;
            }
        }catch (Exception e){

        }
        return null;
    }

    // ============== Shiro Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
        Object obj = getSession().getAttribute(key);
        return obj==null?defaultValue:obj;
    }

    public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
//		getCacheMap().remove(key);
        getSession().removeAttribute(key);
    }
}
