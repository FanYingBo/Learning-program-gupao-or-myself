package com.study.gupao.designmode.proxy.statical.child;

import com.study.gupao.designmode.proxy.statical.service.ProxyService;

/**
 * static proxy mode
 */
public class ProxyByProxyClass implements ProxyService{

    public void findLove(){
        System.out.println("子类找对象的条件A");
    }

    public void findJob() {

    }

    public void shopping() {

    }
}
