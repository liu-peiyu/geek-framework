package com.geekcattle.core.j2cache.cache.support;

import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.CacheChannel;
import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link Cache} implementation for J2Cache.
 * @author zhangsaizz
 *
 */
public class J2CacheCacheManger extends AbstractCacheManager implements Initializable, Destroyable {

	@Autowired
	private CacheChannel channel;

	public void init() throws ShiroException {
		channel = J2Cache.getChannel();
	}

	public void destroy() throws Exception {
		if (channel != null)
			channel.close();
	}

	protected Cache<Object, Object> createCache(String name) throws CacheException {
		if (channel == null)
			channel = J2Cache.getChannel();
		return new ShiroJ2Cache<Object, Object>(name, channel);
	}

}
