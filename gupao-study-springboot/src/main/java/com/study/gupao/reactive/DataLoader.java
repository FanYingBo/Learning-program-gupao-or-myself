package com.study.gupao.reactive;

import java.util.concurrent.TimeUnit;

public class DataLoader {

    public void load(){

        long startTime = System.currentTimeMillis();// 开始时间
        doLoad();// 具体执行
        long costTime = System.currentTimeMillis() - startTime;// 消耗时间
        System.out.println("dao() 总耗时：" + costTime + " 毫秒");
    }
    protected void doLoad(){
        loadConfigurations();
        loadUsers();
        loadOrders();
    }

    protected void loadConfigurations(){
        loadMock("loadConfigurations",1);
    }
    protected void loadUsers(){
        loadMock("loadUsers",2);
    }
    protected void loadOrders(){
        loadMock("loadOrders",3);
    }
    private void loadMock(String source,int seconds){
        long start = System.currentTimeMillis();
        long millisSeconds = TimeUnit.SECONDS.toMillis(seconds);
        try {
            Thread.sleep(millisSeconds);
            long costTime = System.currentTimeMillis()-start;
            System.out.printf("[线程 : %s] %s 耗时 :  %d 毫秒\n", Thread.currentThread().getName(), source, costTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DataLoader().load();
    }
}
