package com.study.gupao.redis.jedis.distributed.lock;

import com.study.gupao.redis.jedis.util.JedisClusterUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.UUID;

/**
 * redis 集群实现分布式锁
 */
public class RedisClusterDistributedLock {

    // 获取分布式 锁
    public String acquireLock(String lockKeyName, long acquireTimeOut, int lockTimeOutSec){
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:"+lockKeyName;
        try{
            long endTime = System.currentTimeMillis() + acquireTimeOut;
            while(endTime > System.currentTimeMillis()){
                System.out.printf("[%s] \n",Thread.currentThread().getName());
                if(JedisClusterUtil.setex(lockKey, lockTimeOutSec, identifier) == 1){
                    return identifier;
                }
                // 判断key有没有超时时间
                if(JedisClusterUtil.ttl(lockKey) == -1){
                    JedisClusterUtil.expire(lockKey, lockTimeOutSec);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean releaseLock(String lockKeyName, String identifier,int timeOutMill){
        String lockKey = "lock:"+lockKeyName;
        boolean isRelease = false;
        try{
            Jedis jedis = JedisClusterUtil.watcher(lockKey);//在开启事务之前watch 如果对监听的key发生了操作,则事务中断
            if(jedis == null || identifier == null){
                return false;
            }
            long upTo = System.currentTimeMillis() + timeOutMill;
            while(!isRelease && upTo > System.currentTimeMillis()){
                if(jedis.get(lockKey).equals(identifier)){// 当获取的值相匹配则开启事务
                    Long ttl = jedis.ttl(lockKey);
                    if(ttl < 0){
                        return true;
                    }
                    Transaction multi = jedis.multi();
                    multi.del(lockKey);
                    if(multi.exec().isEmpty()){
                        continue;
                    }
                    isRelease = true;
                }
            }
            jedis.unwatch();
        }catch (Exception e){
            return false;
        }
        return isRelease;
    }

}
