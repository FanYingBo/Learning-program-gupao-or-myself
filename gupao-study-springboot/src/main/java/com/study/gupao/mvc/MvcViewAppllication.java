package com.study.gupao.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

//@EnableAutoConfiguration
public class MvcViewAppllication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MvcViewAppllication.class);
        springApplication.run(args);
    }

}
