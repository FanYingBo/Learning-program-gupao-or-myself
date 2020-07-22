package com.study.gupao.redis.jedis.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

public class JedisClusterUtil {

    private static JedisCluster jedisCluster ;
    private static volatile TreeMap<Long,JedisPool> jedisPoolTreeMap;
    private static Object reloadLock ;
    private static Object checkLock ;

    static {
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        InputStream asStream = JedisClusterUtil.class.getResourceAsStream("/redis.properties");
        Properties properties = new Properties();
        try {
            properties.load(asStream);
            String address = properties.get("redis.address").toString();
            String[] split = address.split(",");
            Stream.of(split).forEach(ipHost ->{
                String[] strings = ipHost.split(":");
                hostAndPorts.add(new HostAndPort(strings[0],Integer.parseInt(strings[1])));
             }
            );
            GenericObjectPoolConfig<Jedis> genericObjectPoolConfig = new GenericObjectPoolConfig();
            genericObjectPoolConfig.setMaxIdle(Integer.parseInt(properties.get("redis.maxIdle").toString()));
            genericObjectPoolConfig.setMaxTotal(Integer.parseInt(properties.get("redis.maxTotal").toString()));
            genericObjectPoolConfig.setMaxIdle(Integer.parseInt(properties.get("redis.minIdle").toString()));
            jedisCluster = new JedisCluster(hostAndPorts, genericObjectPoolConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadLock = new Object();
        checkLock = new Object();
        reloadJedisPoolMap(Boolean.FALSE);
    }


    public static Jedis getJedisByKey(String key){
        int slot = getSlot(key);
        return getJedisBySlot(slot);
    }

    public static Jedis getJedisBySlot(long slots){
        Map.Entry<Long, JedisPool> longJedisPoolEntry = jedisPoolTreeMap.lowerEntry(slots);
        if(longJedisPoolEntry == null){
            longJedisPoolEntry = jedisPoolTreeMap.firstEntry();
        }
        JedisPool jedisPool = longJedisPoolEntry.getValue();
        if(jedisPool == null){
            synchronized (checkLock){
                longJedisPoolEntry = jedisPoolTreeMap.lowerEntry(slots);
                if(longJedisPoolEntry == null){
                    longJedisPoolEntry = jedisPoolTreeMap.firstEntry();
                }
                jedisPool = longJedisPoolEntry.getValue();
                if(jedisPool == null){
                    reloadJedisPoolMap(Boolean.TRUE);
                }
            }
            if(jedisPool == null){
                return null;
            }
        }
        Jedis resource = jedisPool.getResource();
        if(resource.isConnected()){
            return resource;
        }
        return null;
    }
    public static String set(String key, String value){
        return jedisCluster.set(key,value);
    }
    public static long setex(String key,int expireSecond,String value){
        long num = jedisCluster.setnx(key,value);
        if(num == 1){
            expire(key, expireSecond);
        }
        return num;
    }

    public static String get(String key){
        return jedisCluster.get(key);
    }

    public static long expire(String key,int expireSecond){
        return jedisCluster.expire(key,expireSecond);
    }

    public static long ttl(String key){
        return jedisCluster.ttl(key);
    }

    public static long lPush(String key,String ... values){
        return jedisCluster.lpush(key,values);
    }

    public static long rPush(String key,String ... values){
        return jedisCluster.rpush(key,values);
    }

    public static String lPop(String key){
        return jedisCluster.lpop(key);
    }

    public static List<String> blPop(String key){
        try{
            return jedisCluster.blpop(30000,key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * redis 阻塞队列
     * @param key
     * @return
     */
    public static List<String> bRPop(String key){
        try{
            return jedisCluster.brpop(30000,key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Transaction multi(String key){
        Jedis jedis = getJedisByKey(key);
        if(jedis != null){
            return jedis.multi();
        }
        return null;
    }

    /**
     * 集群情况下不适用 watch,所以根据key计算CRC16() 计算哪个节点的jedis
     * @param key
     */
    public static Jedis watcher(String key){
        int slot = getSlot(key);
        Jedis jedis = getJedisBySlot(slot);
        if(jedis != null){
            jedis.watch(key);
        }
        return jedis;
    }

    public static Jedis unWatcher(String key){
        int slot = JedisClusterCRC16.getSlot(key);
        Jedis jedis = getJedisBySlot(slot);
        if(jedis != null){
            jedis.unwatch();
        }
        return jedis;
    }

    private static void close(){
        try {
            jedisCluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static int getSlot(String key){
        return JedisClusterCRC16.getSlot(key);
    }
    private static void loadClusterJedisPoolSlots(){
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        TreeMap<Long,JedisPool> jedisPoolTreeMapTmp = new TreeMap<>();
        clusterNodes.forEach(
                (s,jedisPool) -> {
                    List<Object> objects = jedisPool.getResource().clusterSlots();
                    objects.stream().forEach(
                            object -> {
                                List<Object> total = (List<Object>) object;
                                long low = (long)total.get(0);
                                long high = (long)total.get(1);
                                // master 节点 【节点的IP,节点的端口号,节点的ID】
                                List<Object> master = (List<Object>)total.get(2);
                                JedisPool jedisPool1 = clusterNodes.get(new String((byte[]) master.get(0)) + ":" + master.get(1));
                                if(jedisPool1 != null && jedisPool1.getResource().isConnected()){
                                    jedisPoolTreeMapTmp.put(low,jedisPool1);
                                    jedisPoolTreeMapTmp.put(high,jedisPool1);
                                }
                            }
                    );
                });
        jedisPoolTreeMap = jedisPoolTreeMapTmp;
    }
    private static void reloadJedisPoolMap(boolean reload){
        if(jedisPoolTreeMap == null && !reload){
            synchronized (reloadLock){
                if(jedisPoolTreeMap == null){
                    loadClusterJedisPoolSlots();
                }
            }
        }else{
            if(reload){
                synchronized (reloadLock){
                    loadClusterJedisPoolSlots();
                }
            }
        }

    }
}
