package com.avalon.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import com.avalon.common.constant.PropCommon;

public class RedisUtil {
    private static final Logger log       = LogManager.getLogger(RedisUtil.class);

    private static JedisPool    jedisPool = null;

    static {
        JedisPoolConfig config = new JedisPoolConfig();

        String password = PropCommon.getProp().getRedisPassword();
        String host = PropCommon.getProp().getRedisHost();
        String port = PropCommon.getProp().getRedisPort();
        String timeOut = PropCommon.getProp().getRedisTimeOut();
        String maxTotal = PropCommon.getProp().getRedisMaxActive();
        String maxIdle = PropCommon.getProp().getRedisMaxIdle();
        String maxWait = PropCommon.getProp().getRedisMaxWait();
        if (StringUtil.isBlank(port)) {
            port = "6379";
        }
        if (StringUtil.isBlank(timeOut)) {
            timeOut = "10000";
        }
        if (StringUtil.isBlank(maxTotal)) {
            maxTotal = "1024";
        }
        if (StringUtil.isBlank(maxIdle)) {
            maxIdle = "100";
        }
        if (StringUtil.isBlank(maxWait)) {
            maxWait = "4000";
        }

        config.setMaxTotal(Integer.parseInt(maxTotal));
        config.setMaxIdle(Integer.parseInt(maxIdle));
        config.setMaxWaitMillis(Integer.parseInt(maxWait));
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);

        if (StringUtil.isNotBlank(password)) {
            jedisPool = new JedisPool(config, host, Integer.parseInt(port),
                Integer.parseInt(timeOut), password, Protocol.DEFAULT_DATABASE);
        } else {
            jedisPool = new JedisPool(config, host, Integer.parseInt(port),
                Integer.parseInt(timeOut));
        }
    }

    /**
     * 释放jedis资源
     * 
     * @param jedis
     */
    public static void returnResource(Jedis redis) {
        if (redis != null) {
            redis.close();
        }
    }

    /**
     * 获取Jedis实例
     * 
     * @return
     */
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取一个值
     * 
     * @param key
     * @return
     */
    public static String getKey(String key) {
        Jedis redis = null;
        try {
            redis = getJedis();
            if (redis == null) {
                return null;
            }
            return redis.get(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 根据key匹配key
     * 
     * @param key
     * @return
     */
    public static Set<String> keys(String key) {
        Jedis redis = null;
        try {
            redis = getJedis();
            if (redis == null) {
                return null;
            }
            return redis.keys(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 设置一个值
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        set(key, value, -1);
    }

    /**
     *  一个值
     * @param key
     * @param value
     * @param expire   单位为秒
     */
    public static void set(String key, String value, int expire) {
        Jedis redis = null;
        try {
            redis = getJedis();
            if (redis == null) {
                return;
            }
            redis.set(key, value);
            if (expire != -1) {
                redis.expire(key, expire);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            returnResource(redis);
        }

    }

    /**
     * 根据key删除缓存
     * @param key
     * @return
     */
    public static Long del(String key) {
        Jedis redis = null;
        try {
            redis = getJedis();
            if (redis == null) {
                return null;
            }
            Long result = redis.del(key);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 添加有序set
     * @param key
     * @param value
     * @param score
     * @param expire
     */
    public static void zadd(String key, String value, double score, int expire) {
        Jedis redis = null;
        try {
            redis = getJedis();
            if (redis == null) {
                return;
            }
            redis.zadd(key, score, value);
            if (expire != -1) {
                redis.expire(key, expire);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 添加有序set
     * @param key
     * @param value
     * @param score
     */
    public static void zadd(String key, String value, double score) {
        zadd(key, value, score, -1);
    }

    /**
     * 倒序排序获得set
     * @param key
     * @param limit
     * @return
     */
    public static List<String> zrevrange(String key, int limit) {
        Jedis redis = null;
        try {
            List<String> resultList = new ArrayList<String>();
            redis = getJedis();
            if (redis == null) {
                return resultList;
            }
            Set<String> set = redis.zrevrange(key, 0, limit - 1);
            Iterator<String> iter = set.iterator();

            while (iter.hasNext()) {
                resultList.add(iter.next());
            }
            return resultList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ArrayList<String>();
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 把对象放入Hash中
     * @param key
     * @param field
     * @param o
     */
    public static void hset(String key, String field, String value, int expireSeconds) {
        Jedis redis = null;
        try {
            redis = getJedis();
            if (redis == null) {
                return;
            }
            redis.hset(key, field, value);
            if (expireSeconds != -1) {
                redis.expire(key, expireSeconds);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 把对象放入Hash中
     * @param key
     * @param field
     * @param o
     */
    public static void hset(String key, String field, String value) {
        hset(key, field, value, -1);
    }

    /**
     * 从Hash中获取对象
     * @param key
     * @param field
     * @return
     */
    public static String hget(String key, String field) {
        Jedis redis = null;
        try {
            redis = getJedis();
            if (redis == null) {
                return null;
            }
            return redis.hget(key, field);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 从Hash中获取对象,转换成制定类型
     * @param key
     * @param field
     * @param clazz
     * @return
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static <T> T hget(String key, String field, Class<T> clazz) {
        String text = hget(key, field);
        if (StringUtil.isBlank(text)) {
            return null;
        }
        T result = JsonUtil.fromJSON(text, clazz);
        return result;
    }

    public static <T> List<T> hgetList(String key, String field, Class<T> clazz) {
        String text = hget(key, field);
        if (StringUtil.isBlank(text)) {
            return null;
        }
        List<T> result = JsonUtil.fromJSONList(text, clazz);
        return result;
    }

    /**
     * 从Hash中删除对象
     * @param key
     * @param field
     * @return
     */
    public static Long hdel(String key, String... field) {
        Jedis redis = null;
        try {
            redis = getJedis();
            return redis.hdel(key, field);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return 0l;
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 将一个值插入到列表头部，value可以重复，返回列表的长度
     * @param key
     * @param value String
     * @return 返回List的长度
     */
    public static Long lpush(String key, String value) {
        Jedis redis = null;
        try {
            redis = getJedis();
            Long length = redis.lpush(key, value);
            return length;
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 从列表尾部取出一个值
     * @param key    
     * @return 返回value
     */
    public static String rpop(String key) {
        Jedis redis = null;
        try {
            redis = getJedis();
            String value = redis.rpop(key);
            return value;
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 移除列表元素，返回移除的元素数量
     * @param key
     * @param value 匹配的元素
     * @return Long
     */
    public static Long lrem(String key, String value) {
        return lrem(key, 0, value);
    }

    /**
     * 移除列表元素，返回移除的元素数量
     * @param key
     * @param count，标识，表示动作或者查找方向
     * <li>当count=0时，移除所有匹配的元素；</li>
     * <li>当count为负数时，移除方向是从尾到头；</li>
     * <li>当count为正数时，移除方向是从头到尾；</li>
     * @param value 匹配的元素
     * @return Long
     */
    public static Long lrem(String key, long count, String value) {
        Jedis redis = null;
        try {
            redis = getJedis();
            Long length = redis.lrem(key, count, value);
            return length;
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 获取List列表
     * @param key
     * @param start long，开始索引
     * @param end long， 结束索引
     * @return List<String>
     */
    public static List<String> lrange(String key, long start, long end) {
        Jedis redis = null;
        try {
            redis = getJedis();
            List<String> list = redis.lrange(key, start, end);
            return list;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ArrayList<String>();
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 通过索引获取列表中的元素
     * @param key
     * @param index，索引，0表示最新的一个元素
     * @return String
     */
    public static String lindex(String key, long index) {
        Jedis redis = null;
        try {
            redis = getJedis();
            String value = redis.lindex(key, index);
            return value;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 获取列表长度，key为空时返回0
     * @param key
     * @return Long
     */
    public static Long llen(String key) {
        Jedis redis = null;
        try {
            redis = getJedis();
            Long length = redis.llen(key);
            return length;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return 0l;
        } finally {
            returnResource(redis);
        }
    }

    public static boolean setnx(String key, String value, int expire) {
        Jedis redis = null;
        try {
            redis = getJedis();
            boolean result = (redis.setnx(key, value) == 1);
            if (result && expire != -1) {
                redis.expire(key, expire);
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            returnResource(redis);
        }
    }

    public static void incr(String key) {
        Jedis redis = null;
        try {
            redis = getJedis();
            redis.incr(key);
        } finally {
            returnResource(redis);
        }
    }

    public static void incr(String key, int expire) {
        Jedis redis = null;
        try {
            redis = getJedis();
            redis.incr(key);
            if (expire != -1) {
                redis.expire(key, expire);
            }
        } finally {
            returnResource(redis);
        }
    }

    public static long decr(String key) {
        return decr(key, -1);
    }

    public static long decr(String key, int expire) {
        Jedis redis = null;
        try {
            redis = getJedis();
            long result = redis.decr(key);
            if (expire != -1) {
                redis.expire(key, expire);
            }
            return result;
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 分布式锁
     * @param key
     * @param timeout 超时时间,锁等待超时，防止线程饥饿，永远没有入锁执行代码的机会 (毫秒)
     * @param expire 锁持有超时，防止线程在入锁以后，无限的执行下去，让锁无法释放(毫秒)
     * @return
     * @throws InterruptedException
     */
    public static boolean getLock(String key, int timeout, int expire) throws InterruptedException {

        Jedis redis = null;
        try {
            redis = getJedis();
            if (timeout < 0) {
                timeout = 0;
            }
            boolean flag = false;
            while (timeout >= 0) {
                long expires = System.currentTimeMillis() + expire + 1;
                String expiresStr = String.valueOf(expires); //锁到期时间
                if (redis.setnx(key, expiresStr) == 1) {
                    flag = true;
                    break;
                }
                String currentValueStr = redis.get(key); //redis里的时间
                if (currentValueStr != null
                    && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                    redis.del(key);
                    flag = true;
                    break;
                }

                timeout -= 100;
                Thread.sleep(100);
            }
            return flag;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 获取key的超时时间
     * @param key
     * @return
     */
    public static Long getTimeOut(String key) {
        Jedis redis = null;
        try {
            redis = getJedis();
            Long timeOunt = redis.ttl(key);
            return timeOunt;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -2L;
        } finally {
            returnResource(redis);
        }
    }

    /**
     *  批量set
     * @param key
     * @param value
     */
    public static void batchSet(Map<String, String> map) {
        Jedis redis = null;
        try {
            redis = getJedis();
            if (redis == null) {
                return;
            }
            Pipeline pl = redis.pipelined();
            for (String key : map.keySet()) {
                pl.set(key, map.get(key));
            }
            pl.sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            returnResource(redis);
        }

    }

    /**
     * 添加坐标
     * @param key
     * @param geoMap
     */
    public static void geoAdd(String key, Map<String, GeoCoordinate> geoMap) {
        Jedis redis = null;
        try {
            redis = getJedis();
            redis.geoadd(key, geoMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            returnResource(redis);
        }

    }

    /**
     * 添加坐标
     * @param key
     * @param geoMap
     */
    public static void geoAdd(String key, double longitude, double latitude, String member) {
        Jedis redis = null;
        try {
            redis = getJedis();
            redis.geoadd(key, longitude, latitude, member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 
     * @param key
     * @param longitude
     * @param latitude
     * @param radius
     * @param unit
     * @param param
     * @return
     */
    public static List<GeoRadiusResponse> georadius(String key, double longitude, double latitude,
                                                    Double radius, GeoUnit unit,
                                                    GeoRadiusParam param) {
        Jedis redis = null;
        try {
            redis = getJedis();
            return redis.georadius(key, longitude, latitude, radius, unit, param);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            returnResource(redis);
        }
        return null;
    }

    /**
     * 删除成员
     * @param key
     * @param member
     */
    public static void zrem(String key, String member) {
        Jedis redis = null;
        try {
            redis = getJedis();
            if (redis == null) {
                return;
            }
            redis.zrem(key, member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            returnResource(redis);
        }
    }

    /**
     * 把对象放入Hash中
     * @param key
     * @param field
     * @param o
     */
    public static void batchHset(String key, Map<String, String> values) {

        Jedis redis = null;
        try {
            redis = getJedis();
            if (redis == null) {
                return;
            }
            Pipeline pl = redis.pipelined();
            for (String field : values.keySet()) {
                pl.hset(key, field, values.get(field));
            }
            pl.sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            returnResource(redis);
        }

    }
}
