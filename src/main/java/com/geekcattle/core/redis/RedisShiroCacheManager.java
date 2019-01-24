package com.geekcattle.core.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author geekcattle
 */
@Repository("redisCacheManager")
public class RedisShiroCacheManager implements CacheManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisConfiguration redisConfiguration;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if(logger.isDebugEnabled()){
            logger.debug("获取名称为: " + name + " 的RedisCache实例");
        }
        Cache c = caches.get(name);

        if(c == null){
            c =  new RedisShiroCache<K, V>(name, redisTemplate, redisConfiguration);
            caches.put(name, c);
        }

        return  c;
    }

}
