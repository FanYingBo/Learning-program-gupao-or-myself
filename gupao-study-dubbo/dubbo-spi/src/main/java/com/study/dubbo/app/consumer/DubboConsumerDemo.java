package com.study.dubbo.app.consumer;

import com.study.dubbo.app.service.IPayService;
import com.study.dubbo.app.service.PayOrderContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboConsumerDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("/config/consumer.xml");
        classPathXmlApplicationContext.start();
        IPayService alipayService = (IPayService)classPathXmlApplicationContext.getBean("alipayService");
        PayOrderContext payOrderContext = alipayService.placeAnOrder("newGift", "新手礼包", '$', 1.99f);
        System.out.println(payOrderContext);
    }
}
