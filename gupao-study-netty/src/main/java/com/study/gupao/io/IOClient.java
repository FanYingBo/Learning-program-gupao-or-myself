package com.study.gupao.io;

import java.io.IOException;

public interface IOClient extends Runnable{

    public void start() throws IOException;

    public boolean close();
}
