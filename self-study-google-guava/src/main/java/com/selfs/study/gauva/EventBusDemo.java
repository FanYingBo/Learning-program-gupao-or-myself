package com.selfs.study.gauva;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * register：把监听器中申明的所有订阅事件方法注册到SubscriberRegistry（订阅者注册器）中。
 * post：发布事件给所有已注册过的订阅者，最终开启线程完成订阅方法。
 */
public class EventBusDemo {

    private static  ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        EventBus eventBus = new AsyncEventBus(executor);
        eventBus.register(new EventSubscriber());
        eventBus.post(new Event("hello"));
    }
}

/**
 * 发布的事件
 */
class Event{
    private String name;

    Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * 事件的订阅者 方法名必须使用{@link Subscribe}
 */
class EventSubscriber{

    @Subscribe
    public void listen(Event e){
        System.out.println("监听事件："+ e.getName());
    }

    @Subscribe
    public void listen2(Event e){
        System.out.println("监听事件2："+ e.getName());
    }
}
