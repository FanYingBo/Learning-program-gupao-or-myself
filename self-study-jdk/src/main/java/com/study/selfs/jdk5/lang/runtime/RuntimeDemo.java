package com.study.selfs.jdk5.lang.runtime;

import org.junit.Test;

/**
 * Processors
 * shutDownHook
 */
public class RuntimeDemo {

    @Test
    public void getProcessors(){
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
    /**
     * 正常关闭
     * @throws InterruptedException
     */
    @Test
    public void normalShutDownHook() throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(()->
            System.out.println("程序已经关闭")
        ));
        System.out.println("程序正在执行....");
        Thread.sleep(1000);
    }

    /**
     * 异常关闭
     */
    @Test
    public void exceptionShutDownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(()->
                System.out.println("程序已经关闭")
        ));
        System.out.println("程序正在执行....");
        String[] dsd = new String[2];
        if(dsd[0] == null){
            throw new NullPointerException("字符串为空");
        }
    }

    /**
     * oom异常退出
     */
    @Test
    public void oomShutDownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(()->
                System.out.println("程序已经关闭")
        ));
        System.out.println("程序正在执行....");
        byte[] byts = new byte[1024*1024*1024];
        System.out.println(byts);
    }

}
