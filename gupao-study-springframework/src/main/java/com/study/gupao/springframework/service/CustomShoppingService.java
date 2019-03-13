package com.study.gupao.springframework.service;

public class CustomShoppingService {

    private BaseCustomService baseCustomService;

    public CustomShoppingService(BaseCustomService baseCustomService){
        this.baseCustomService = baseCustomService;
    }
    public void meetSomeone(String name){
        print(baseCustomService.getCustomGreetint(name));
    }

    private void print(String message){
        System.out.println(message);
    }

}
