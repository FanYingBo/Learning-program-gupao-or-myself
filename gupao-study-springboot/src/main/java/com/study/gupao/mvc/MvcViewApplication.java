package com.study.gupao.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * {@link org.springframework.boot.autoconfigure.SpringBootApplication}
 *  = {@link SpringBootConfiguration} + {@link EnableAutoConfiguration} + {@link ComponentScan}
 */
@SpringBootApplication
public class MvcViewApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MvcViewApplication.class);
        springApplication.run(args);
    }

}
