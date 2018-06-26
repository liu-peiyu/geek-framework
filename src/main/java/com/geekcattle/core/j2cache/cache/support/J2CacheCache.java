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

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.concurrent.Callable;

public class J2CacheCache extends AbstractValueAdaptingCache {

    private CacheChannel cacheChannel;

    private String j2CacheName = "j2cache";

    public J2CacheCache(String cacheName, CacheChannel cacheChannel) {
        this(cacheName,cacheChannel, true);
    }

    public J2CacheCache(String cacheName, CacheChannel cacheChannel, boolean allowNullValues) {
        super(allowNullValues);
        j2CacheName = cacheName;
        this.cacheChannel = cacheChannel;
    }

    @Override
    public String getName() {
        return this.j2CacheName;
    }

    public void setJ2CacheNmae(String name) {
        this.j2CacheName = name;
    }

    @Override
    public Object getNativeCache() {
        return this.cacheChannel;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        T value;
        try {
            value = valueLoader.call();
        } catch (Exception ex) {
            throw new ValueRetrievalException(key, valueLoader, ex);
        }
        put(key, value);
        return value;
    }

    @Override
    public void put(Object key, Object value) {
        cacheChannel.set(j2CacheName, String.valueOf(key), value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        if (!cacheChannel.exists(j2CacheName, String.valueOf(key))) {
            cacheChannel.set(j2CacheName, String.valueOf(key), value);
        }
        return get(key);
    }

    @Override
    public void evict(Object key) {
        cacheChannel.evict(j2CacheName, String.valueOf(key));
    }

    @Override
    public void clear() {
        cacheChannel.clear(j2CacheName);
    }

    @Override
    protected Object lookup(Object key) {
        CacheObject cacheObject = cacheChannel.get(j2CacheName, String.valueOf(key));
        return cacheObject.getValue();
    }

}
