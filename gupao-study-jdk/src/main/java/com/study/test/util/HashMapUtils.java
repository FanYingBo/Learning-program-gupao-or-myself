package com.study.test.util;

import java.util.HashMap;
import java.util.Map;

public class HashMapUtils {
    /**
     *  key int  value  str
     * @param size
     * @return
     */
    public static Map<Integer,String> getRanMap(int size){
        HashMap<Integer,String> hashMap = new HashMap<>();
        for(int i = 0;i < size;i++){
            hashMap.put(i,StrUtils.getRanStr(3));
        }
        return hashMap;
    }
}
