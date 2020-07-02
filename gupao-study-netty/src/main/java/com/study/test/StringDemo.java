package com.study.test;

import com.study.gupao.format.MessageUtils;

import java.io.IOException;

public class StringDemo {

    public static void main(String[] args) {
        String str = "&client-thread_first_26782&lient-thread_first_26714&&client-thread_first_16870&nt-thread_first_4232&";
//        System.out.println(str.indexOf("&"));
        MessageUtils.decodeStringMessage(str,"&");
    }
}
