package com.study.gupao.springframework.service;

public interface BaseCustomService {

    public default String getCustomGreetint(String name){
        return "hello world!";
    }
}
