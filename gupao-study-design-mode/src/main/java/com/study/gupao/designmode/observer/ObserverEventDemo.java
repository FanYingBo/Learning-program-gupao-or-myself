package com.study.gupao.designmode.observer;

import com.study.gupao.designmode.observer.core.Event;
import com.study.gupao.designmode.observer.enumeration.EventType;
import com.study.gupao.designmode.observer.subject.Observer;
import com.study.gupao.designmode.observer.subject.Subject;

import java.lang.reflect.Method;

public class ObserverEventDemo {

    public static void main(String[] args) {
        Observer observer = new Observer();

        try {
            Method method = Observer.class.getMethod("advice", new Class[]{Event.class});

            Subject subject = new Subject();
            subject.addListener(EventType.ON_ADD,observer,method);
            subject.add();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
