package com.study.gupao.redis.jedis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Jedis-Sentinel
 * 哨兵模式
 */
public class JedisSentinelDemo {

    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxWaitMillis(1000*3);
        jedisPoolConfig.setTestOnBorrow(Boolean.TRUE);
        Set<String> sentinels = new HashSet<>();
        sentinels.add("192.168.1.156:30001");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("myredis",sentinels,jedisPoolConfig);
        Jedis resource = jedisSentinelPool.getResource();
        jedisSentinelPool.getCurrentHostMaster();
    }


}
