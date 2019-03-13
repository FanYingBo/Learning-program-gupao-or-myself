package com.study.jdk5.lang.thread.app;

public class Request {

    private String msg;


    public Request(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Request{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
