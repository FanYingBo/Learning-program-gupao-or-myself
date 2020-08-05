package com.study.selfs.bitopt;

import org.junit.Test;

import java.util.Random;

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

    /**
     * 判断是否为2的指数
     */
    @Test
    public void isPowerOfTwo(){
        int[] orgData = {2,8,12,14,19,24,32,64,127,128};
        Random ran = new Random();
        int i = ran.nextInt(orgData.length);
        int num = orgData[i];
        if((num&-num) == num){
            System.out.println(num + " 是 2 的指数");
        }else{
            System.out.println(num + " 不是 2 的指数");
        }
    }

    /**
     *除以2
     */
    @Test
    public void getMidNum(){
        Random ran = new Random();
        int anInt = ran.nextInt(100);
        int mid = anInt >>> 1;
        System.out.println(anInt + " 除以2等于 "+ mid);
    }
}
