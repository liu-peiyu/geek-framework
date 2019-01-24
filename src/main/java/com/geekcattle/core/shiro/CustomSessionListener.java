package com.geekcattle.core.shiro;

import com.geekcattle.core.redis.RedisConfiguration;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author geekcattle
 */
@Configuration
public class CustomSessionListener implements SessionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisConfiguration redisConfiguration;

    /**
     * 一个回话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        if(logger.isDebugEnabled()){
            logger.debug("onStart:{}", session.getId());
        }

    }
    /**
     * 一个回话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
        if(logger.isDebugEnabled()){
            logger.debug("onStop:{}", session.getId());
        }
    }

    @Override
    public void onExpiration(Session session) {
        if(logger.isDebugEnabled()){
            logger.debug("onExpiration:{}", session.getId());
        }
        redisTemplate.delete(redisConfiguration.getSessionPrefix() + session.getId().toString());
    }

}

