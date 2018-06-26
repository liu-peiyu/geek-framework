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
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.*;

@SuppressWarnings("unchecked")
public class ShiroJ2Cache<K, V> implements Cache<K, V> {

    protected String region;

    protected CacheChannel channel;

    public ShiroJ2Cache(String region, CacheChannel channel) {
        this.region = region;
        this.channel = channel;
    }

    public V get(K key) throws CacheException {
        CacheObject val = this.channel.get(region, key.toString());
        if (val == null)
            return null;
        return (V) val.getValue();
    }

    public V put(K key, V value) throws CacheException {
        this.channel.set(region, key.toString(), value);
        return null;
    }

    public V remove(K key) throws CacheException {
        this.channel.evict(region, key.toString());
        return null;
    }

    public void clear() throws CacheException {
        this.channel.clear(region);
    }

    public int size() {
        return this.channel.keys(region).size();
    }

    public Set<K> keys() {
        return new HashSet<>((Collection)this.channel.keys(region));
    }

    public Collection<V> values() {
        List<V> list = new ArrayList<V>();
        for (K k : keys()) {
            list.add(get(k));
        }
        return list;
    }

}
