package com.study.gupao.io;

public interface IOClient extends Runnable{

    public void start() throws Exception;

    public boolean close();
}
