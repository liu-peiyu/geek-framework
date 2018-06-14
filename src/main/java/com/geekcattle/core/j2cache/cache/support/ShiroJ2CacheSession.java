package com.geekcattle.core.j2cache.cache.support;

import com.geekcattle.core.redis.RedisConfiguration;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Repository("shiroJ2CacheSession")
public class ShiroJ2CacheSession extends AbstractSessionDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CacheChannel channel;

    @Resource
    private RedisConfiguration redisConfiguration;

    private String getKey(String originalKey) {
        return redisConfiguration.getSessionPrefix() + originalKey;
    }

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        logger.debug("createSession:{}", sessionId.toString());
        channel.set(redisConfiguration.getSessionPrefix(), sessionId.toString(), session);
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("readSession:{}", sessionId.toString());
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = null;
        if(session == null){
            Object cacheObject = (Object) channel.get(redisConfiguration.getSessionPrefix(), sessionId.toString()).getValue();
            session = (Session) cacheObject;
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    public void update(Session session) {
        logger.debug("updateSession:{}", session.getId().toString());
        String key = getKey(session.getId().toString());
        channel.set(redisConfiguration.getSessionPrefix(), session.getId().toString(), session);
    }

    // 删除session
    @Override
    public void delete(Session session) {
        logger.debug("delSession:{}", session.getId());
        channel.evict(redisConfiguration.getSessionPrefix(), session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        logger.debug("activeSession");
        return Collections.emptySet();
    }
}
