package com.study.self.util.encrypt;

import com.study.self.util.time.TimeWatcher;

import java.util.concurrent.TimeUnit;

public class EncryptPerformance {

    public static void main(String[] args) {
        int count = 100000;
        TimeWatcher timeWatcher = new TimeWatcher();
        timeWatcher.startWatcher();
        for(int i = 0;i < count; i ++){
            String sha256 = EncryptUtil.getMD5("33232312hdhaaweqe");
            System.out.println(sha256);
        }
        long escapeTime = timeWatcher.getEscapeTime(TimeUnit.NANOSECONDS);
        System.out.println("total time "+escapeTime);
        System.out.println("average time "+escapeTime/count);
    }
}
