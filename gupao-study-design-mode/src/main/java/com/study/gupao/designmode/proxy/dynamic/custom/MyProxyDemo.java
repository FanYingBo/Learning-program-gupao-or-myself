package com.study.gupao.designmode.proxy.dynamic.custom;

import com.study.gupao.designmode.proxy.dynamic.custom.child.MyProxyedClass;
import com.study.gupao.designmode.proxy.dynamic.custom.parent.MyProxyClass;
import com.study.gupao.designmode.proxy.dynamic.jdk.service.ProxyService;

public class MyProxyDemo {

    public static void main(String[] args) {
        ProxyService myProxyedClass= (ProxyService)new MyProxyClass().getInstance(new MyProxyedClass());
        myProxyedClass.findLove();
    }
}
