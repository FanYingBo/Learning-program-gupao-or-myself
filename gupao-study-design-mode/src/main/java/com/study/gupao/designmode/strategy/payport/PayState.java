package com.study.gupao.designmode.strategy.payport;

public class PayState {

    private int code;

    private Object data;

    private String msg;

    public PayState(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "支付完成："+this.code+",支付详情："+this.msg;
    }
}
