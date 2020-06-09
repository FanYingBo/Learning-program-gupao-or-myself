package com.study.gupao.designmode.observer.core;

import com.study.gupao.designmode.observer.core.Event;
import com.study.gupao.designmode.observer.core.EventProxy;
import com.study.gupao.designmode.observer.mouse.Mouse;
import com.study.gupao.designmode.observer.mouse.MouseEventCallBack;

import java.lang.reflect.Method;

public class EventProxyDemo {
    public static void main(String[] args) {
        Mouse mouse = (Mouse)new EventProxy().getInstance(Mouse.class, MouseEventCallBack.class);
        mouse.onClick();
//        mouse.onDblClick();
    }
}
