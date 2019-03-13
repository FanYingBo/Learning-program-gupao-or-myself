package com.study.gupao.designmode.decorator;

import com.study.gupao.designmode.decorator.passport.old.SignServiceImpl;
import com.study.gupao.designmode.decorator.passport.upgrade.ISignServiceUpgrade;
import com.study.gupao.designmode.decorator.passport.upgrade.SignServiceUpgradeImpl;

import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;

public class DecoratorDemo {

    public static void main(String[] args) {

        // 为了实现类在不修改原始类的基础上进行动态的覆盖和增加方法
        // 该实现方法保持了跟原有类的层级关系
        // 装饰器模式实际上是适配器模式的一种
        // 虽然Datainputstream功能更强大
        // dataInputStream同样要实现InputStream
//        InputStream is = null;
//
//        FilterInputStream filterInputStream =  new DataInputStream(is);

        ISignServiceUpgrade signServiceUpgrade = new SignServiceUpgradeImpl(new SignServiceImpl());
        // 升级后的登录服务
        signServiceUpgrade.loginByQQ("ioooddd");
        // 旧的登录服务
        signServiceUpgrade.login("ddsss","1212121");

        /**
         * ========================================================
         * 装饰器模式                            适配器模式
         * ---------------------------------------------------------
         * 是一种特殊的适配器模式                  可以不保留层级关系
         * ----------------------------------------------------------
         * 装饰着和被装饰这都需要实现同一个接口       适配这和被适配者没有必然的层级联系
         * 主要目的是为了扩展，依旧保留OOP           通常采用代理或者继承形式包装
         * （Object Oriented Programming）关系
         *------------------------------------------------------------------
         * 满足 is-a 的关系                        满足 has-a 的关系
         * ------------------------------------------------------------------
         * 注重的是覆盖、扩展                        注重的是兼容、展缓
         * ----------------------------------------------------------------
         *DataSource 包装
         * IO流   bufferInputStream  bufferOutputStream
         * Spring 中通常 有 Decorator  Wrapper
         *
         */

    }


}
