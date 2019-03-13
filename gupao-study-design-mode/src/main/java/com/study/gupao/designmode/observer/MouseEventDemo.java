package com.study.gupao.designmode.observer;

import com.study.gupao.designmode.observer.core.Event;
import com.study.gupao.designmode.observer.enumeration.MouseEventType;
import com.study.gupao.designmode.observer.mouse.Mouse;
import com.study.gupao.designmode.observer.mouse.MouseEventCallBack;

import java.lang.reflect.Method;

public class MouseEventDemo {

    public static void main(String[] args) {

        //观察者
        MouseEventCallBack mouseEventCallBack = new MouseEventCallBack();

        try {
            // 被观察者
            Method onClick = MouseEventCallBack.class.getMethod("onClick", Event.class);
            Mouse mouse = new Mouse();
            mouse.addListener(MouseEventType.ON_CLICK,mouseEventCallBack,onClick);
            mouse.onClick();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
