package com.study.self.util.time;

import java.util.concurrent.TimeUnit;

public class TimeWatcher {

    private long startTime;

    private long lastEscapeTime;

    private long totalEscapeTime;


    public TimeWatcher(){
        this.startTime = System.currentTimeMillis();
    }

    public void startWatcher(){
        resetTime();
    }

    public long getEscapeTime(TimeUnit timeUnit){
        continueTime();
        long convert = timeUnit.convert(System.currentTimeMillis() - this.startTime + this.lastEscapeTime, TimeUnit.MILLISECONDS);
        continueTime();
        return convert;
    }

    public long getEndEscapeTime(TimeUnit timeUnit){
        continueTime();
        long convert = timeUnit.convert(System.currentTimeMillis() - this.startTime + this.totalEscapeTime, TimeUnit.MILLISECONDS);
        continueTime();
        return convert;
    }

    private void continueTime(){
        this.lastEscapeTime = System.currentTimeMillis() - this.startTime;
        this.totalEscapeTime += this.lastEscapeTime;
        this.startTime = System.currentTimeMillis();
    }

    private void resetTime(){
        this.startTime = System.currentTimeMillis();
        this.lastEscapeTime = System.currentTimeMillis() - this.startTime;
        this.totalEscapeTime += this.lastEscapeTime;

    }
}
