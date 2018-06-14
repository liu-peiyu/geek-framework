package com.geekcattle.core.j2cache.cache.support;

import com.geekcattle.core.redis.RedisConfiguration;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("shiroJ2CacheCacheManager")
public class ShiroJ2CacheCacheManager implements CacheManager {

    @Autowired
    private CacheChannel channel;

    @Resource
    private RedisConfiguration redisConfiguration;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if (channel == null)
            channel = J2Cache.getChannel();
        return new ShiroJ2Cache<K, V>(redisConfiguration.getCachePrefix(), channel);
    }
}
