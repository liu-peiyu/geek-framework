/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.core.redis;

import com.geekcattle.util.JsonUtil;
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
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * redis实现共享session
 * author geekcattle
 * date 2017/3/22 0022 下午 15:32
 */
@Repository("redisSessionDAO")
public class RedisSessionDAO extends AbstractSessionDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisConfiguration redisConfiguration;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getKey(String originalKey) {
        return redisConfiguration.getSessionPrefix() + originalKey;
    }

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        logger.debug("createSession:{}", sessionId.toString());
        redisTemplate.opsForValue().set(getKey(sessionId.toString()), session,redisConfiguration.getSessionTime(),TimeUnit.MINUTES);
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("readSession:{}", sessionId.toString());
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = null;
        if(session == null){
            session = (Session) redisTemplate.opsForValue().get(getKey(sessionId.toString()));
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    public void update(Session session) {
        logger.debug("updateSession:{}", session.getId().toString());
        String key = getKey(session.getId().toString());
        redisTemplate.opsForValue().set(key, session,redisConfiguration.getSessionTime(), TimeUnit.MINUTES);
    }

    // 删除session
    @Override
    public void delete(Session session) {
        logger.debug("delSession:{}", session.getId());
        redisTemplate.delete(getKey(session.getId().toString()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        logger.debug("activeSession");
        return Collections.emptySet();
    }
}
