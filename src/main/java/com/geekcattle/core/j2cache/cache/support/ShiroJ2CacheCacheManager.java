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
        if (channel == null) {
            channel = J2Cache.getChannel();
        }
        return new ShiroJ2Cache<K, V>(redisConfiguration.getCachePrefix(), channel);
    }
}
