package com.study.gupao.designmode.proxy.statical.parent;

import com.study.gupao.designmode.proxy.statical.service.ProxyService;

/**
 *
 * static proxy mode
 *
 */
public class ProxyClass {

    private ProxyService proxyService;

    public ProxyClass(ProxyService proxyService){
        this.proxyService = proxyService;
    }
    public void findLove(){
        System.out.println("帮助子类查找对象....");
        this.proxyService.findLove();
    }

}
