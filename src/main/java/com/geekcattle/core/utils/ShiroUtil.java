/*
 * Copyright (c) 2017-2018.  放牛极客<l_iupeiyu@qq.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * </p>
 *
 */

package com.geekcattle.core.utils;

import com.geekcattle.model.console.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * shiro工具类
 * author geekcattle
 * date 2016/12/6 0006 上午 10:45
 */
public class ShiroUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static Boolean isLogin(){
        return getSubject().isAuthenticated();
    }

    /**
     * 获取session信息
     * @return
     */
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

    /**
     * 获取用户信息
     * @return
     */
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
}
