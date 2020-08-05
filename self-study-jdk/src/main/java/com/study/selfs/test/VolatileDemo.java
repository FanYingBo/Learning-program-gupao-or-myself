package com.study.selfs.test;

import com.study.selfs.test.util.PrintUtils;

/**
 *
 * volatile 关键字
 *
 */
public class VolatileDemo {

//    private volatile static boolean isStop = Boolean.TRUE;

    public static void main(String[] args) throws InterruptedException {
//        Thread thread = new Thread(() -> {
//           while(isStop){
//               System.out.println("ddsasdaddaas");
//           }
//        });
//        thread.start();
//        Thread.sleep(1000);
//        isStop = Boolean.FALSE;
//        System.out.println("线程停止");

        VolatileThread volatileThread = new VolatileThread();
        volatileThread.start();
        Thread.sleep(100);
        volatileThread.stopThread();
    }
}

class VolatileThread extends Thread{

    private volatile boolean stop = true;

    @Override
    public void run() {
        int count = 0;
        while(stop){
            PrintUtils.print(this,++count);
        }
        System.out.println("线程停止");
    }

    public void stopThread(){
        stop = false;
    }
}
