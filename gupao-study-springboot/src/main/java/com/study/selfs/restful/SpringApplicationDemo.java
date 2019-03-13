package com.study.selfs.restful;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class SpringApplicationDemo {

	public static void main(String[] args) {
//		SpringApplicationBuilder 创建spring boot 服务
//		new SpringApplicationBuilder(SpringApplicationDemo.class)
//				// 随机端口
//				.properties("server.port=0")
//				.run(args);
//		SpringApplication.run(SpringApplicationDemo.class, args);
	}

//	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}
}
