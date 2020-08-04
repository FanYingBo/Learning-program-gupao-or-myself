package com.study.dubbo.springboot;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableDubbo(scanBasePackages = {"com.study.dubbo.api.impl"})
@SpringBootApplication
public class SpringBootDubboStart {

    public static void main(String[] args) {
//        SpringApplication.run(SpringBootDubboStart.class);
        new SpringApplicationBuilder().profiles("provider").sources(SpringBootDubboStart.class).build(args).run();
    }
}
