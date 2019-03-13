package com.study.gupao.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 *
 * _consumer_offers  分区所在的
 */
public class KafkaConsumerDemo extends Thread{

    private KafkaConsumer<Integer,String> kafkaConsumer;


    public  KafkaConsumerDemo(String topic){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.156:9094,192.168.1.156:9092,192.168.1.156:9093");// brokers
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"KafkaConsumerDemo2");//消费组ID
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");// 每次自动提交时间间隔
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        //分区分配策略 ： RoundRobinAssignor 和 RangeAssignor策略
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,"org.apache.kafka.clients.consumer.RoundRobinAssignor");
        this.kafkaConsumer = new KafkaConsumer<Integer, String>(properties);
        this.kafkaConsumer.subscribe(Collections.singletonList(topic));
    }
    @Override
    public void run() {
        while(true){
            ConsumerRecords<Integer, String> records = this.kafkaConsumer.poll(1000);
            for(ConsumerRecord record : records){
                System.out.println(record.value());
                kafkaConsumer.commitAsync();
            }
        }
    }

    public static void main(String[] args) {
        new KafkaConsumerDemo("client-test").start();
    }
}
