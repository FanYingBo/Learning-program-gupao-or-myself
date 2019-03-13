package com.study.gupao.designmode.observer.mouse;

import com.study.gupao.designmode.observer.core.Event;

/**
 *
 * 观察者
 * 回调响应
 */
public class MouseEventCallBack {

    public void onClick(Event event){
        System.out.println("===========鼠标单击事件========");
        System.out.println(event);
    }
    public void onDblClick(Event event){
        System.out.println("===========鼠标双击事件========");
        System.out.println(event);
    }
    public void onMouseDown(Event event){
        System.out.println("===========鼠标按下事件========");
        System.out.println(event);
    }
    public void onMouseUp(Event event){
        System.out.println("===========鼠标释放事件========");
        System.out.println(event);
    }
    public void onMouseMove(Event event){
        System.out.println("===========鼠标移动事件========");
        System.out.println(event);
    }
}
