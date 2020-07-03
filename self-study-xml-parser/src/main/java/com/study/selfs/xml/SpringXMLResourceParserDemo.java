package com.study.selfs.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringXMLResourceParserDemo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("custom-xsd.xml");

        GenConfig custom = (GenConfig)context.getBean("genConfig");
        System.out.println(custom.getEntityPath());
    }
}
