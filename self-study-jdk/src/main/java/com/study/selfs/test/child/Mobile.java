package com.study.selfs.test.child;

import com.study.selfs.test.parent.Electronics;

public class Mobile extends Electronics {

    @Override
    public void loudsSpeak() {
        System.out.println("电话讲话");
    }
}
