package com.study.dubbo.app.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 *
 */
public class DubboProviderDemo {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("/config/provider.xml");
        classPathXmlApplicationContext.start();
        System.in.read();
    }
}
