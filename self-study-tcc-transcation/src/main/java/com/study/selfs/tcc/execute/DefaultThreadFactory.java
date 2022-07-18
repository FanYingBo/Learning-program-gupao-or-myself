package com.study.selfs.tcc.execute;

import java.util.concurrent.ThreadFactory;

public class DefaultThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(Boolean.FALSE);
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
}
