package com.geekcattle.conf.shiro;

import com.geekcattle.service.common.RedisService;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;

/**
 * author geekcattle
 * date 2017/3/22 0022 下午 15:32
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = Logger.getLogger(RedisSessionDAO.class);

    @Autowired
    RedisService redisService;

    @Override
    protected Serializable doCreate(Session session) {
        return null;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return null;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {

    }

    @Override
    public void delete(Session session) {

    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }
}
