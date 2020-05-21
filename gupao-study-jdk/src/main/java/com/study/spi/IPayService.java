package com.study.spi;

public interface IPayService {

    void payOrder(String orderNum, float money, String product);
}
