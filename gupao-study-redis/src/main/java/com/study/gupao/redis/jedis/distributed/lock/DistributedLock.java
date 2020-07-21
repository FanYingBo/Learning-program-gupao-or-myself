package com.study.gupao.redis.jedis.distributed.lock;


import com.study.gupao.redis.jedis.util.JedisConnectionUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 分布式所只关注redis中的key
 */
public class DistributedLock {

    private AtomicReference<String> luaHash;
    private boolean isCluster;


    public DistributedLock(){
        this(false);
    }
    public DistributedLock(boolean isCluster){
        luaHash = new AtomicReference<>();
        this.isCluster = isCluster;
    }

    // 获取分布式 锁
    public String acquireLock(String lockKeyName, long acquireTimeOut, int lockTimeOutSec){
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:"+lockKeyName;
        Jedis jedis = null;
        try{
            jedis = JedisConnectionUtil.getJedis();
            long endTime = System.currentTimeMillis() + acquireTimeOut;
            while(endTime > System.currentTimeMillis()){
                if(jedis.setnx(lockKey, identifier) == 1){
                    jedis.expire(lockKey, lockTimeOutSec);
                    return identifier;
                }
                // 判断key有没有超时时间
                if(jedis.ttl(lockKey) == -1){
                    jedis.expire(lockKey, lockTimeOutSec);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }

    public boolean releaseLock(String lockKeyName, String identifier){
        String lockKey = "lock:"+lockKeyName;
        Jedis jedis = null;
        boolean isRelease = false;
        try{
            jedis = JedisConnectionUtil.getJedis();
            jedis.watch(lockKey);//在开启事务之前watch 如果对监听的key发生了操作,则事务中断
            while(true){
                if(jedis.get(lockKey).equals(identifier)){// 当获取的值相匹配则开启事务
                    Transaction multi = jedis.multi();
                    multi.del(lockKey);
                    if(multi.exec().isEmpty()){
                        continue;
                    }
                    isRelease = true;
                }
                jedis.unwatch();
                break;
            }
        }catch (Exception e){
            return false;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return isRelease;
    }

    /**
     * 借助Lua脚本的原子性操作来释放锁
     * @param lockKeyName
     * @param identifier
     * @return
     */
    public boolean releaseLockWithLua(String lockKeyName,String identifier){
        String lockKey = "lock:"+lockKeyName;
        String lua ="if redis.call('get',KEYS[1]) == ARGV[1] then " +
                "return redis.call('del',KEYS[1]) else return 0 end";
        long ret = 0l;
        Jedis jedis = null;
        try{
            jedis = JedisConnectionUtil.getJedis();
            if(luaHash.get() == null){
                luaHash.set(jedis.scriptLoad(lua));
            }
            ret = (long)jedis.evalsha(luaHash.get(),1, new String[]{lockKey, identifier});
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return ret == 1 ? true:false;
    }
}
