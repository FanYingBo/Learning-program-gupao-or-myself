package com.study.jdk5.lang.thread.app;

import java.util.concurrent.LinkedBlockingQueue;

public class HandleRequest extends Thread implements RequestProcess {

    private LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue();

    private final RequestProcess saveRequest;

    public HandleRequest(RequestProcess saveRequest) {
        this.saveRequest = saveRequest;
    }

    @Override
    public void run() {
        while(true){
            try {
                Request request = linkedBlockingQueue.take();
                System.out.println("handle request:"+request);
                saveRequest.processRequest(request);
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
