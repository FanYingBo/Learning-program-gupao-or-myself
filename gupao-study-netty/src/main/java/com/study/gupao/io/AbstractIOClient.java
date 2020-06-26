package com.study.gupao.io;

public abstract class AbstractIOClient implements IOClient{

    @Override
    public void run(){
        try{
            start();
        }catch (Exception exception){
            close();
        }

    }
}
