package com.study.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

//@Configuration
//@EnableDubbo(scanBasePackages = "com.study.dubbo")
//@PropertySource("classpath:/dubbo-provider.properties")
//@ComponentScan(basePackages = {"com.study.dubbo.api.impl"})
public class PayOderMainStart {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        context.start();
        System.in.read();
    }
}
