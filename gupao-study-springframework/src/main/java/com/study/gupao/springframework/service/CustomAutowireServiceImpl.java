package com.study.gupao.springframework.service;

public class CustomAutowireServiceImpl implements BaseCustomService {


    /**
     * 静态内部类
     */
    public static class CustomAutowireStaticClass{
       public static int offset = 2;

    }

    public int getCount(){
        return CustomAutowireStaticClass.offset;
    }


}
