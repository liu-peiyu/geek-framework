package com.geekcattle.core.j2cache.cache.support;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroJ2CacheCacheManager extends AbstractCacheManager implements Initializable, Destroyable {

    @Autowired
    private CacheChannel channel;

    @Override
    public void init() throws ShiroException {
        channel = J2Cache.getChannel();
    }

    public void destroy() throws Exception {
        /*if (channel != null)
            channel.close();*/
    }

    @Override
    protected Cache<Object, Object> createCache(String name) throws CacheException {
        if (channel == null)
            channel = J2Cache.getChannel();
        return new ShiroJ2Cache<Object, Object>(name, channel);
    }


}
