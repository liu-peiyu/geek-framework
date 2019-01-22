package com.geekcattle.core.redis;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis实现共享session
 *
 * @author geekcattle
 */
@Repository("redisSessionDAO")
public class RedisSessionDAO extends AbstractSessionDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisConfiguration redisConfiguration;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getKey(String originalKey) {
        return redisConfiguration.getSessionPrefix() + ":" + originalKey;
    }

    /**
     * 创建session，保存到数据库
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        if (logger.isDebugEnabled()) {
            logger.debug("createSession:{}", sessionId.toString());
        }
        redisTemplate.opsForValue().set(getKey(sessionId.toString()), session, redisConfiguration.getSessionTime(), TimeUnit.MINUTES);
        return sessionId;
    }

    /**
     * 获取session
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readSession:{}", sessionId.toString());
        }
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = null;
        if (session == null) {
            session = (Session) redisTemplate.opsForValue().get(getKey(sessionId.toString()));
        }
        return session;
    }

    /**
     * 更新session的最后一次访问时间
     *
     * @param session
     */
    @Override
    public void update(Session session) {
        if (logger.isDebugEnabled()) {
            logger.debug("updateSession:{}", session.getId().toString());
        }
        String key = getKey(session.getId().toString());
        redisTemplate.opsForValue().set(key, session, redisConfiguration.getSessionTime(), TimeUnit.MINUTES);
    }

    /**
     * 删除session
     *
     * @param session
     */
    @Override
    public void delete(Session session) {
        if (logger.isDebugEnabled()) {
            logger.debug("delSession:{}", session.getId());
        }
        redisTemplate.delete(getKey(session.getId().toString()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        if (logger.isDebugEnabled()) {
            logger.debug("activeSession");
        }
        redisTemplate.keys(redisConfiguration.getSessionPrefix() + "*");
        Collection<Session> sessions = redisTemplate.opsForValue()
                .multiGet(redisTemplate.keys(redisConfiguration.getSessionPrefix() + "*"))
                .stream()
                .map(s -> (Session) s)
                .collect(Collectors.toSet());
        return sessions;
    }
}
