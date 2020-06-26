package com.study.gupao.io;

public interface IOServer extends Runnable{
    /**
     * 启动IO server
     */
    void start();

    /**
     * 关闭
     * @return
     */
    boolean close();
}
