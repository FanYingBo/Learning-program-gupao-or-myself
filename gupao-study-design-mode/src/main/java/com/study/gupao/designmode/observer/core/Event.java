package com.study.gupao.designmode.observer.core;

import java.lang.reflect.Method;

public class Event {

    private Object source;
    /**
     * 目标对象调用的方法
     */
    private Method callback;
    /**
     * 目标实体对象
     */
    private Object target;

    private String trigger;

    private long time;

    public Event(Object target,Method callback) {
        this.callback = callback;
        this.target = target;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    protected void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public long getTime() {
        return time;
    }

    protected void setTime(long time) {
        this.time = time;
    }

    public Method getCallback() {
        return callback;
    }

    public void setCallback(Method callback) {
        this.callback = callback;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public String getTrigger() {
        return trigger;
    }

    @Override
    public String toString() {
        return "Event{" +
                "source=" + source +"\r\n"+
                " callback=" + callback +"\r\n"+
                " target=" + target +"\r\n" +
                " trigger='" + trigger + '\'' +"\r\n"+
                " time=" + time +"\r\n"+
                '}';
    }
}
