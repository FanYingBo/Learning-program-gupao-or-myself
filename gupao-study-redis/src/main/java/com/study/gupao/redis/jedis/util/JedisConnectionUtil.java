package com.study.gupao.redis.jedis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 */
public class JedisConnectionUtil {
    private static JedisPool pool=null;
    static {
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxWaitMillis(1000*5);
        pool=new JedisPool(jedisPoolConfig,"192.168.1.156",6379);
    }
    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnJedis(){

    }
}
