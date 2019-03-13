package com.study.gupao.springframework.service;

public class CustomServiceImpl implements BaseCustomService {

    @Override
    public String getCustomGreetint(String name) {
        return "hello "+name;
    }

}
