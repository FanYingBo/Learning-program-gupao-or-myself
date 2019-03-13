package com.study.gupao.designmode.observer.subject;

import com.study.gupao.designmode.observer.core.Event;

public class Observer {

    public void advice(Event event){
        System.out.println("=====触发事件，打印日志=====");
    }
}
