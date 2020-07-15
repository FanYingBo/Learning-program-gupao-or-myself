package com.selfs.study.gauva;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterDemo {

    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(1);
        for(int i = 0;i < 10 ;i++){
            double acquire = rateLimiter.acquire();
            System.out.println("等待时间："+ acquire);
        }
    }
}
