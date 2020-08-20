package com.study.dubbo.serial;

public class StartTime {

    private long start;
    private long end;
    private String desc;

    public StartTime(String desc){
        this.desc = desc;
    }
    public StartTime setDesc(String desc){
        this.desc = desc;
        return this;
    }
    public void start(){
        start = System.currentTimeMillis();
    }

    public void stop(){
        end = System.currentTimeMillis();
        System.out.println(desc + " 耗时：" +(end - start));
    }
}
