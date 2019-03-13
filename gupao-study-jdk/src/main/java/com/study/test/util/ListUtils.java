package com.study.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 测试用集合生成
 */
public class ListUtils {

    public static List<Integer> getRanIntList(int size, int num){
        List<Integer> intList = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < size;i++){
            intList.add(random.nextInt(num));
        }
        return intList;
    }

    public static List<String> getRanStr(int size,int len){
        List<String> stringList = new ArrayList<>();
        for(int i = 0; i < size;i++){
            stringList.add(StrUtils.getRanStr(len));
        }
        return stringList;
    }
}
