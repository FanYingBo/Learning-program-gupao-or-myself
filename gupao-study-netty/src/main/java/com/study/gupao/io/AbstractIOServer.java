package com.study.gupao.io;

import com.study.gupao.io.IOServer;

public abstract class AbstractIOServer implements IOServer {

    protected int serverPort;

    protected boolean isStart;

    @Override
    public void run(){
        try{
            start();
        }catch (Exception exception){
            close();
        }
    }

}
