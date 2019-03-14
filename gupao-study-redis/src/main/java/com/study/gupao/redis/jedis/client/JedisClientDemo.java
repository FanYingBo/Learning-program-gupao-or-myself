package com.study.gupao.redis.jedis.client;

import redis.clients.jedis.Jedis;

/**
 *  jedis for redis client demo
 *  连接redis 单机
 *
 * @see Jedis
 */
public class JedisClientDemo {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.156", 30002);
//        long suffix = System.currentTimeMillis();
//         1.字符串
//        String test = jedis.get("test"); // 字符串操作
//        System.out.println(test);
//        jedis.set("test", test.replaceAll("[\\s+\\d{0,}]{1,}$", "") + " " + suffix);
//        test = jedis.get("test");
//        System.out.println(test);
//        //2.列表操作
//        jedis.lpush("test10", "1");// 从列表前端插入数据
//        String test10 = jedis.lpop("test10");// 从列表前端取出数据
//        System.out.println(test10);
//        jedis.rpush("test10","2");// 从列表末端插入数据
//        String test101 = jedis.rpop("test10");// 从列表末端取出数据
//        System.out.println(test101);
//        String rpoplpush = jedis.rpoplpush("test10", "test11");// 将test10列表的末尾元素添加到test11列表的头元素的
//        System.out.println(rpoplpush);
//        Long lrem = jedis.lrem("test11",0,"w22"); //从列表头到列表尾移除某个元素
//        System.out.println(lrem);

//        Thread thread_1 = new Thread(){
//            Jedis jedis = new Jedis("192.168.1.156", 6379);
//            public void run(){
//                while(true){
//                    // 应用场景，消息队列
//                    List<String> test102 = jedis.blpop(3000,"test10");// 列表没有值的时候阻塞
//                    System.out.println("[Thread "+Thread.currentThread()+"]"  +test102);
//                }
//            }
//        };
//        Runnable runnable = () -> {
//            Jedis jedis = new Jedis("192.168.1.156", 6379);
//            while (true) {
//                List<String> test102 = jedis.blpop(3000, "test10");// 列表没有值的时候阻塞
//                System.out.println("[Thread "+Thread.currentThread()+"]"  +test102);
//            }
//        };
//        Thread thread_2 = new Thread(runnable);
//        thread_1.start();
//        thread_2.start();
        // 3.散列
//        jedis.hset("test12", "name", "tom");
//        String hget = jedis.hget("test12", "name");
//        System.out.println(jedis.hvals("test12"));
//        System.out.println(jedis.hkeys("test12"));
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("name","jerry");
//        hashMap.put("age","23");
//        String test13 = jedis.hmset("test13", hashMap);
//        System.out.println(hget);
//        System.out.println(test13);
        // 4.无序集合
//        Long test14 = jedis.sadd("test14", new String[]{"assd", "sasww"});
//        System.out.println(test14);
//        Set<String> test141 = jedis.smembers("test14");
//        System.out.println(test141);
//        String test142 = jedis.spop("test14");
//        System.out.println(test142);
//        jedis.sadd("test14","3221");// 随机返回集合中一个元素并删除
//        String test143 = jedis.srandmember("test14");// 随机返回一个集合中元素但不删除
//        System.out.println(test143);
//        System.out.println(jedis.sismember("test14","3221"));
//        System.out.println(jedis.sismember("test14","sasww"));
//        jedis.srem("test14","3221");// 移除集合的一个元素
//        System.out.println(jedis.smembers("test14"));//
//        Long smove = jedis.smove("test14", "test15", "sasww");
//        System.out.println(smove);
//        Long test144 = jedis.scard("test14");
//        System.out.println(test144);

        // 5.有序集合
//        Map<String,Double> members = new HashMap<>();
//        members.put("google",1d);
//        members.put("baidu",2d);
//        Long test16 = jedis.zadd("test16", members);
//        System.out.println(test16);


//        Pipeline pipelined = jedis.pipelined();
//
//        pipelined.lpush("test10","21222");
//        pipelined.sync();
        //lua 脚本
        String s = jedis.scriptLoad("return redis.call('get','test')");// 获取lua脚本的摘要
        Object evalsha = jedis.evalsha(s);
        System.out.println(evalsha);

    }
}