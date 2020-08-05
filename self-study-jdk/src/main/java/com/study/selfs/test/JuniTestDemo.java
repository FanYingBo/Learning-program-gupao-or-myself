package com.study.selfs.test;

import org.junit.*;

public class JuniTestDemo{

    @Before
    public void before(){
        print("BEFORE");
    }

    @BeforeClass
    public static void beforeClass(){
        print("BEFORE CLASS");
    }

    @Test
    public void test(){
        print("TEST CLASS");
    }

    @After
    public void after(){
        print("AFTER");
    }

    @AfterClass
    public  static void afterClass(){
        print("AFTER CLASS");
    }



    public static void print(String msg){
        System.out.println(msg);
    }

}
