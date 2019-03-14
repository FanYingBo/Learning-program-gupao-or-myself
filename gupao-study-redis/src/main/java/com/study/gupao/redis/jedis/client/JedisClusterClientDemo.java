package com.study.gupao.redis.jedis.client;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * 连接redis集群
 *
 * @see
 */
public class JedisClusterClientDemo {

    public static void main(String[] args) {
        // 需要配置所有的master节点 ，如果只配置单个master节点信息，当不是该master节点的其他master节点挂了，
        // 即使slave节点升为了master客户端依旧无法连接该master节点，处理失败
        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        hostAndPorts.add(new HostAndPort("192.168.1.156",30001));
        hostAndPorts.add(new HostAndPort("192.168.1.156",30002));
        hostAndPorts.add(new HostAndPort("192.168.1.156",30003));
        hostAndPorts.add(new HostAndPort("192.168.1.156",30004));
        hostAndPorts.add(new HostAndPort("192.168.1.156",30005));
        hostAndPorts.add(new HostAndPort("192.168.1.156",30006));
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts,1000);

        while(true){
            String test = null;
            try{
                test = jedisCluster.set("test", "12122");
            }catch (Exception e){
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//                test = jedisCluster.set("test","12122");
            }
            System.out.println(test);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }


    }
}
