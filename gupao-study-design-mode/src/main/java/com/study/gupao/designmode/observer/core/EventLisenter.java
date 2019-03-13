package com.study.gupao.designmode.observer.core;

import com.study.gupao.designmode.observer.enumeration.EventType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventLisenter {

    protected Map<Enum,Event> events = new HashMap<>();

    public void addListener(Enum eventType, Object target, Method method){
        this.events.put(eventType,new Event(target,method));

    }
    private void trigger_(Event event){
        event.setSource(this);
        event.setTime(System.currentTimeMillis());
        try {
            event.getCallback().invoke(event.getTarget(),event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void trigger(Enum eventType){
        if(this.events.containsKey(eventType)){
            Event event = this.events.get(eventType);
            event.setTrigger(eventType.toString());
            trigger_(event);
        }
    }
}
