package com.study.dubbo.consumer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *  @see org.springframework.context.EnvironmentAware
 *  @see org.springframework.context.EmbeddedValueResolverAware
 *  @see org.springframework.context.ResourceLoaderAware
 *  @see org.springframework.context.ApplicationEventPublisherAware
 *  @see org.springframework.context.MessageSourceAware
 *  @see org.springframework.context.ApplicationContextAware
 *  @see org.springframework.context.support.AbstractApplicationContext#refresh()
 * 在进行Bean解析时候会通过Processor来判断继承关系。让后调用指定的方法设定需要的bean
 * {@link org.springframework.context.support.ApplicationContextAwareProcessor}
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
}
