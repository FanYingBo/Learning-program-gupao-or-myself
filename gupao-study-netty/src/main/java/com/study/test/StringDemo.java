package com.study.test;

import com.study.gupao.format.MessageUtils;

import java.io.IOException;

public class StringDemo {

    public static void main(String[] args) {
        String str = "^21222&dasdada&^21222&dasdada&^21222&dasdada&";
//        System.out.println(str.indexOf("&"));
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            MessageUtils.decodeStringMessage(stringBuffer,str,"&");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
