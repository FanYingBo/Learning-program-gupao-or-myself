package com.study.test.util;

import java.util.Random;

public class StrUtils {

    private static char[] chars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n'
            ,'o','p','q','r','s','t','u','v','w','x','y','z'};

    public static String getRanStr(int len){
        Random random = new Random();
        StringBuilder sb = new StringBuilder("");
        for(int i = 0;i < len;i++){
            sb.append(chars[random.nextInt(chars.length-1)]);
        }
        return sb.toString();
    }
}
