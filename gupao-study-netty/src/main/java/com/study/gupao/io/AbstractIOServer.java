package com.study.gupao.io;

public abstract class AbstractIOServer implements IOServer {

    protected int serverPort;

    protected boolean isStart;

    @Override
    public void run(){
        try{
            start();
        }catch (Exception exception){
            exception.printStackTrace();
            close();
        }
    }

}
