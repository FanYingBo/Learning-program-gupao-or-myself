package com.study.spi;

public class AliPayService implements IPayService {
    @Override
    public void payOrder(String orderNum, float money, String product) {
        System.out.println("Ali pay " + product+" : ￥"+money+" orderNum "+orderNum);
    }
}
