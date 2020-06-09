package com.study.gupao.designmode.observer.subject;

import com.study.gupao.designmode.observer.core.Event;
import com.study.gupao.designmode.observer.core.EventListener;
import com.study.gupao.designmode.observer.enumeration.EventType;

public class Subject extends EventListener {

    //通常采用动态代理来实现监控，避免了代码入侵
    public void add(){
        System.out.println("调用添加的方法");
        trigger(EventType.ON_ADD);
    }
    public void remove(){
        System.out.println("调用移除的方法");
        trigger(EventType.ON_REMOVE);
    }

}
