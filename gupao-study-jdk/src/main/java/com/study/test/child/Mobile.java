package com.study.test.child;

import com.study.test.parent.Electronics;

public class Mobile extends Electronics {

    @Override
    public void loudsSpeak() {
        System.out.println("电话讲话");
    }
}
