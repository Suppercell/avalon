package com.avalon.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.avalon.common.util.RedisUtil;
import com.avalon.common.util.StringUtil;

@SuppressWarnings("unchecked")
public class RedisShiroCache<K, V> implements Cache<K, V> {
    private static final String REDIS_SHIRO_CACHE = "REDIS_SHIRO_CACHE_";

    private String              cacheKey;

    public RedisShiroCache(String name) {
        this.cacheKey = REDIS_SHIRO_CACHE + name + ":";
    }

    @Override
    public V get(K key) throws CacheException {
        String result = RedisUtil.getKey((String) getCacheKey(key));
        if (StringUtil.isNotBlank(result)) {
            return null;
        }
        return (V) SerializationUtils.deserialize(result);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V old = get(key);
        RedisUtil.setnx((String) getCacheKey(key), SerializationUtils.serialize(value), 18000);
        return old;
    }

    @Override
    public V remove(K key) throws CacheException {
        V old = get(key);
        RedisUtil.del((String) getCacheKey(key));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        for (String key : (Set<String>) keys()) {
            RedisUtil.del(key);
        }
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return (Set<K>) RedisUtil.keys((String) getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

    private K getCacheKey(Object k) {
        return (K) (this.cacheKey + k);
    }

}
