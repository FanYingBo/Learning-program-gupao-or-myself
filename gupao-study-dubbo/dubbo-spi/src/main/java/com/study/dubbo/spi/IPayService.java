package com.study.dubbo.spi;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface IPayService {

    void payOrder(String orderNum, float money, String product);
}
