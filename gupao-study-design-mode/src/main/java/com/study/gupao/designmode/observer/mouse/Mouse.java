package com.study.gupao.designmode.observer.mouse;

import com.study.gupao.designmode.observer.core.EventListener;
import com.study.gupao.designmode.observer.enumeration.MouseEventType;

/**
 * 被观察者
 * 代码具有侵入性
 *
 * 通过动态代理优化 （不需要trigger ）
 */
public class Mouse extends EventListener {

    public void onClick(){
        System.out.println("鼠标单击");
//        trigger(MouseEventType.ON_CLICK);
    }

    public void onDblClick(){
        System.out.println("鼠标双击");
//        trigger(MouseEventType.ON_DBL_CLICK);
    }

    public void onMouseMove(){
        System.out.println("鼠标移动");
//        trigger(MouseEventType.ON_MOUSE_MOVE);
    }

    public void onMouseDown(){
        System.out.println("鼠标按下");
//        trigger(MouseEventType.ON_MOUSE_DOWN);
    }

    public void onMouseUp(){
        System.out.println("鼠标释放");
//        trigger(MouseEventType.ON_MOUSE_UP);
    }

}
