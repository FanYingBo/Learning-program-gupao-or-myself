package com.study.dubbo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@EnableDubbo(scanBasePackages = "com.study.dubbo")
@PropertySource("classpath:/dubbo-consumer.properties")
public class OrderServiceConsumerMainStart {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OrderServiceConsumerMainStart.class);
        context.refresh();
        context.start();
        System.in.read();
    }
}
