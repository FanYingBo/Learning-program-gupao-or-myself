package com.study.gupao.designmode.singleton.inner;

public class InnerStaticClassSingleton {

    private static class innerClass{
        private static InnerStaticClassSingleton instance = new InnerStaticClassSingleton();
    }

    public static InnerStaticClassSingleton getInstance(){
        return innerClass.instance;
    }

    private InnerStaticClassSingleton(){
    }

}
