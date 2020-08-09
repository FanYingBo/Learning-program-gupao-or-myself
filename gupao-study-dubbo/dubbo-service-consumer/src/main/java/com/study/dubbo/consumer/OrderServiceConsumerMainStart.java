package com.study.dubbo.consumer;

import com.study.dubbo.IPayService;
import org.apache.dubbo.container.Main;

import java.io.IOException;

//@Configuration
//@EnableDubbo(scanBasePackages = "com.study.dubbo")
//@PropertySource("classpath:/dubbo-consumer.properties")
public class OrderServiceConsumerMainStart{

    public static void main(String[] args) throws IOException {
        System.setProperty("dubbo.spring.config","consumer.xml");
        new Thread(()->{
            Main.main(new String[]{"log4j","spring"});
        }).start();

//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
//        context.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0;i < 5;i++){
            IPayService alipayService = (IPayService)ApplicationContextUtil.getBean("alipayService");
            System.out.println(alipayService.placeAnOrder("newGift","新手礼包",'$',1.99f+i));
        }
//        System.in.read();
    }

}
