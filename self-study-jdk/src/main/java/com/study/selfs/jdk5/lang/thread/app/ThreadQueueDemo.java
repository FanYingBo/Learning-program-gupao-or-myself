package com.study.selfs.jdk5.lang.thread.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadQueueDemo {
    public static void main(String[] args) {
        ProcessThread processThread = new ProcessThread();
        processThread.start();
    }
}

class ProcessThread extends Thread{

    private HandleRequest handleRequest;

    public ProcessThread() {
        SaveRequest saveRequest = new SaveRequest();
        saveRequest.start();
        handleRequest = new HandleRequest(saveRequest);
        handleRequest.start();
    }

    @Override
    public void run() {
        while(true){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String msg= br.readLine();
                Request request = new Request(msg);
                handleRequest.processRequest(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
