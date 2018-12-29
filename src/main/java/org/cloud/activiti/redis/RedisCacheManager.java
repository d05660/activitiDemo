package org.cloud.activiti.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.cloud.activiti.redis.serializer.ObjectSerializer;
import org.cloud.activiti.redis.serializer.RedisSerializer;
import org.cloud.activiti.redis.serializer.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RedisCacheManager implements CacheManager {

    private final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    // fast lookup by name map
    @SuppressWarnings("rawtypes")
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
    @SuppressWarnings("rawtypes")
    private RedisSerializer keySerializer = new StringSerializer();
    @SuppressWarnings("rawtypes")
    private RedisSerializer valueSerializer = new ObjectSerializer();

    private IRedisManager redisManager;

    // expire time in seconds
    public static final int DEFAULT_EXPIRE = 1800;
    private int expire = DEFAULT_EXPIRE;

    /**
     * The Redis key prefix for caches
     */
    public static final String DEFAULT_CACHE_KEY_PREFIX = "shiro:cache:";
    private String keyPrefix = DEFAULT_CACHE_KEY_PREFIX;

    public static final String DEFAULT_PRINCIPAL_ID_FIELD_NAME = "id";
    private String principalIdFieldName = DEFAULT_PRINCIPAL_ID_FIELD_NAME;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("get cache, name=" + name);

        Cache cache = caches.get(name);

        if (cache == null) {
            cache = new RedisCache<K, V>(redisManager, keySerializer, valueSerializer, keyPrefix + name + ":", expire, principalIdFieldName);
            caches.put(name, cache);
        }
        return cache;
    }

    public IRedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(IRedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @SuppressWarnings("rawtypes")
    public RedisSerializer getKeySerializer() {
        return keySerializer;
    }

    @SuppressWarnings("rawtypes")
    public void setKeySerializer(RedisSerializer keySerializer) {
        this.keySerializer = keySerializer;
    }

    @SuppressWarnings("rawtypes")
    public RedisSerializer getValueSerializer() {
        return valueSerializer;
    }

    @SuppressWarnings("rawtypes")
    public void setValueSerializer(RedisSerializer valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getPrincipalIdFieldName() {
        return principalIdFieldName;
    }

    public void setPrincipalIdFieldName(String principalIdFieldName) {
        this.principalIdFieldName = principalIdFieldName;
    }

}
