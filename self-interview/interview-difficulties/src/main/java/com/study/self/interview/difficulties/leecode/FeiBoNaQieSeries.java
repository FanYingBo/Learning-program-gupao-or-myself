package com.study.self.interview.difficulties.leecode;

import org.junit.Test;

/**
 * 斐波那契数列
 * F(0) = 0;
 * F(1) = 1;
 * F(N) = F(N - 1)+F(N - 2)
 */
public class FeiBoNaQieSeries {

    @Test
    public void testCalc(){
        System.out.println(calcN(3));
    }

    public int calcN(int n){
        int n1 = 0;
        int n2 = 1;
        if(n == 0){
            return n1;
        }
        if(n == 1){
            return n2;
        }
        int ret = 0;
        for(int i = 1;i < n;i++){
            ret = n1 + n2;
            n1 = n2;
            n2 = ret;
        }
        return ret;
    }
}
