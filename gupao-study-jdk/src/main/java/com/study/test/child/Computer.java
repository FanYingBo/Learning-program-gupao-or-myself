package com.study.test.child;

import com.study.test.parent.Electronics;

public class Computer extends Electronics{
    @Override
    public void display() {
        System.out.println("电脑显示");
    }

    @Override
    public void input() {
        System.out.println("电脑输入");
    }

    @Override
    public void loudsSpeak() {
        System.out.println("电脑扬声");
    }
}
