package com.study.selfs.spi;

public interface IPayService {

    void payOrder(String orderNum, float money, String product);
}
