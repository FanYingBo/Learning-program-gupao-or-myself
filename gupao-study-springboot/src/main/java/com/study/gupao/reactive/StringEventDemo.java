package com.study.gupao.reactive;

import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.Executors;

public class StringEventDemo {

    public static void main(String[] args) {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();

        multicaster.setTaskExecutor(Executors.newFixedThreadPool(1));
        multicaster.addApplicationListener(event -> {
            System.out.printf("[线程：%s] %s\n");
        });



    }
}
