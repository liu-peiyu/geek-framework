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

package com.geekcattle.core.j2cache.cache.support;

import com.geekcattle.core.redis.RedisConfiguration;
import net.oschina.j2cache.CacheChannel;
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
