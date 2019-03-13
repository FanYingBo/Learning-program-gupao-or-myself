package com.study.jdk5.lang.thread.app;

import java.util.concurrent.LinkedBlockingQueue;

public class SaveRequest extends Thread implements RequestProcess {

    private LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue();

    @Override
    public void run() {
        while(true){
            try {
                Request take = linkedBlockingQueue.take();
                System.out.println("print request:"+take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void processRequest(Request saveRequest) {
        linkedBlockingQueue.add(saveRequest);
    }
}
