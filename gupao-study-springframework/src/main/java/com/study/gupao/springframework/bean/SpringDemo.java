package com.study.gupao.springframework.bean;

import com.study.gupao.springframework.service.CustomAutowireServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * bean 注入的实现方式：
 * 1.xml注入
 */
public class SpringDemo {
    public static void main(String[] args) {
        ApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext(
                        "spring-beans.xml");
        CustomAutowireServiceImpl member = (CustomAutowireServiceImpl) classPathXmlApplicationContext.getBean("customAutowire");
//        CustomAutowireServiceImpl member = classPathXmlApplicationContext.getBean("customAutowire",CustomAutowireServiceImpl.class);
//        CustomAutowireServiceImpl member = classPathXmlApplicationContext.getBean(CustomAutowireServiceImpl.class);
        System.out.println(member.getCount());




//        GenericApplicationContext context = new GenericApplicationContext();
//        context.refresh();
//        RootBeanDefinition beanDefinition = new RootBeanDefinition();
//        RootBeanDefinition beanDefinitionCustomService = new RootBeanDefinition();
//        RootBeanDefinition beanDefinitionCustomShopping = new RootBeanDefinition();
//        beanDefinitionCustomService.setBeanClass(CustomServiceImpl.class);
//        beanDefinitionCustomShopping.setBeanClass(CustomShoppingService.class);
//        beanDefinition.setBeanClass(CustomAutowireServiceImpl.class);
//        context.registerBeanDefinition("customAutowire",beanDefinition);
//        context.registerBeanDefinition("customService",beanDefinitionCustomService);
//        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
//        mutablePropertyValues.add("baseCustomService","");
//        beanDefinitionCustomService.setPropertyValues(mutablePropertyValues);
//        context.registerBeanDefinition("customShopping",beanDefinitionCustomShopping);
//        CustomAutowireServiceImpl customAutowire = context.getBean("customAutowire", CustomAutowireServiceImpl.class);
//        CustomServiceImpl customShopping = context.getBean("customShopping", CustomServiceImpl.class);
//        customShopping.getCustomGreetint("jack");
//        System.out.println(customAutowire.getCount());




//        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(context);// 定位
//        beanDefinitionReader.loadBeanDefinitions("spring-beans.xml");// 加载
//        context.refresh();//注册
//        CustomShoppingService customShopping = context.getBean("customShopping", CustomShoppingService.class);
//        customShopping.meetSomeone("jack");

    }
}
