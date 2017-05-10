package com.geekcattle.conf.shiro;

import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

/**
 * author geekcattle
 * date 2017/3/22 0022 下午 15:28
 */
public class CustomSessionDAO extends EnterpriseCacheSessionDAO {

    @Override
    public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
        sessionIdGenerator = new JavaUuidSessionIdGenerator();
        System.out.println("SessionID"+ sessionIdGenerator);
        super.setSessionIdGenerator(sessionIdGenerator);
    }
}
