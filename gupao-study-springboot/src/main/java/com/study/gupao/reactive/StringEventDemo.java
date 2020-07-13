package com.study.gupao.reactive;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.Executors;

/**
 * spring 的监听事件  {@link org.springframework.context.ApplicationEvent}
 */
public class StringEventDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();

        multicaster.setTaskExecutor(Executors.newFixedThreadPool(1));
        multicaster.addApplicationListener(event -> {
            System.out.printf("[线程：%s] 启动\n",Thread.currentThread().getName());
        });
        multicaster.multicastEvent(new ApplicationContextEvent(annotationConfigApplicationContext) {
            @Override
            public Object getSource() {
                return super.getSource();
            }
        });
    }
}
