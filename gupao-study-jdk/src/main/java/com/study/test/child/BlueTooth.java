package com.study.test.child;

import com.study.test.parent.Electronics;

public class BlueTooth extends Electronics{

    @Override
    public void loudsSpeak() {
        System.out.println("蓝牙耳机放音乐");
    }
}
