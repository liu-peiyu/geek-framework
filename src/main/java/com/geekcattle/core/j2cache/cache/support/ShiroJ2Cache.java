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
        CacheObject val = this.channel.get(region, (String) key);
        if (val == null)
            return null;
        return (V) val.getValue();
    }

    public V put(K key, V value) throws CacheException {
        this.channel.set(region, (String)key, value);
        return null;
    }

    public V remove(K key) throws CacheException {
        this.channel.evict(region, (String)key);
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
