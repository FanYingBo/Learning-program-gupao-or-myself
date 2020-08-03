package com.study.dubbo.consumer;

import com.study.dubbo.IPayService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

//@Configuration
//@EnableDubbo(scanBasePackages = "com.study.dubbo")
//@PropertySource("classpath:/dubbo-consumer.properties")
public class OrderServiceConsumerMainStart {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        for(int i = 0;i < 5;i++){
            IPayService alipayService = (IPayService)context.getBean("alipayService");
            System.out.println(alipayService.placeAnOrder("newGift","新手礼包",'$',1.99f+i));
        }
        System.in.read();
    }
}
