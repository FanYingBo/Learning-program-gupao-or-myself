package com.study.gupao.designmode.singleton.lazy;

/**
 * 懒汉式单例  Lazy Singleton
 */
public class LazySingleton {

    private static LazySingleton lazySingleton = new LazySingleton();

    public static LazySingleton getInstance(){
        return lazySingleton;
    }
    private LazySingleton(){}
}
