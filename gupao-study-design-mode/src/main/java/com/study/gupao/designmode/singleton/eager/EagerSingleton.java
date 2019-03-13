package com.study.gupao.designmode.singleton.eager;


/**
 * 饿汉式 Eager Singleton
 */
public class EagerSingleton {

    private static EagerSingleton eagerSingleton = null;

    public static EagerSingleton getInstance(){
        if(eagerSingleton == null){
            eagerSingleton = new EagerSingleton();
        }
        return eagerSingleton;
    }

    private EagerSingleton(){}

}
