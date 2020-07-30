package com.study.dubbo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableDubbo(scanBasePackages = "com.study.dubbo")
@PropertySource("classpath:/dubbo-provider.properties")
@ComponentScan(basePackages = {"com.study.dubbo.api.impl"})
public class PayOderMainStartSecond {

    public static void main(String[] args) {

    }
}
