package com.study.gupao.designmode.adapter;

import com.study.gupao.designmode.adapter.passport.SignServiceAdapter;

public class AdapterDemo {

    public static void main(String[] args) {
        SignServiceAdapter service = new SignServiceAdapter();
        System.out.println(service.loginByQQ("212wweess"));
    }

}
