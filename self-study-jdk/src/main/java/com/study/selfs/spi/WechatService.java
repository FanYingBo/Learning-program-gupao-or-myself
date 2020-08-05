package com.study.selfs.spi;

public class WechatService implements IPayService{
    @Override
    public void payOrder(String orderNum, float money, String product) {
        System.out.println("WeChat pay " + product+" : ￥"+money+" orderNum "+orderNum);
    }
}
