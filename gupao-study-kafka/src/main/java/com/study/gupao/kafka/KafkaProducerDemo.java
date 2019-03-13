package com.study.gupao.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.log4j.PropertyConfigurator;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class KafkaProducerDemo  extends  Thread {

    private KafkaProducer<Integer,String> kafkaProducer;

    private String topic;

    private boolean isSync;
    public KafkaProducerDemo(String topic,boolean isSync){
        InputStream file = this.getClass().getResourceAsStream("/log4j.properties");
        PropertyConfigurator.configure(file);
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.156:9094,192.168.1.156:9092,192.168.1.156:9093");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"KafkaProducerDemo");
        // 0 ,1或者-1 0代表发送消息后不需要等待brokers的消息确认 ，1 代表只需要向集群中的leader确认 all(-1)代表需要ISR中所有的Replica给予接收确认，速度最慢，安全性最高
        properties.put(ProducerConfig.ACKS_CONFIG,"-1");
        properties.put(ProducerConfig.LINGER_MS_CONFIG,"1000"); // 两次发送时间间隔，Producer会把所有的requests进行一次聚合然后再发送，对每次发送到broker的请求增加一些延迟
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        this.kafkaProducer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
        this.isSync = isSync;
    }
    @Override
    public void run() {
        int num = 0;
        while(num < 50){
            num++;
            String message = "message_"+num;
            System.out.println("send "+message);
            if(!isSync){
                kafkaProducer.send(new ProducerRecord<Integer, String>(topic, message), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if(metadata != null){
                            System.out.println("offset: "+metadata.offset()+" data: "+metadata.partition());
                        }

                    }
                });
            }else{
                try {
                    RecordMetadata recordMetadata = kafkaProducer.send(new ProducerRecord<Integer, String>(topic, message)).get();
                    System.out.println("offset: "+recordMetadata.offset()+" data: "+recordMetadata.partition());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        new KafkaProducerDemo("client-test",true).start();
    }
}

