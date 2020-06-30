package com.study.bitopt;

import org.junit.Test;

/**
 * BitOperation test
 */
public class BitOperation {
    /**
     * take the median
     */
    @Test
    public void takeTheMid(){
        int low = 5, high = 20;
        int mid = (low + high) >>> 1;
        System.out.println(mid);
    }

    /**
     * Odd-even judgement
     */
    @Test
    public void oddEven(){
        int ran = (int)(Math.random() * 10);
        if((ran&1)==0){
            System.out.println(ran + "是偶数");
        }else{
            System.out.println(ran + "是奇数");
        }
    }
}
