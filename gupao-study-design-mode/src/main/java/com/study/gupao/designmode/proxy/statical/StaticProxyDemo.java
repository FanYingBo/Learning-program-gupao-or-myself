package com.study.gupao.designmode.proxy.statical;

import com.study.gupao.designmode.proxy.statical.child.ProxyByProxyClass;
import com.study.gupao.designmode.proxy.statical.parent.ProxyClass;
import com.study.gupao.designmode.proxy.statical.service.ProxyService;

public class StaticProxyDemo {

    public static void main(String[] args) {
        ProxyService proxyByProxyClass = new ProxyByProxyClass();
        ProxyClass proxyClass = new ProxyClass(proxyByProxyClass);
        proxyClass.findLove();
    }
}
