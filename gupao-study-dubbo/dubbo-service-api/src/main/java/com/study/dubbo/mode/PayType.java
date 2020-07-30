package com.study.dubbo.mode;

public enum PayType {

    WECHART(1,"WECHART"),ALIPAY(2,"ALIPAY");

    private int type;
    private String name;

    private PayType(int type,String name){
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
