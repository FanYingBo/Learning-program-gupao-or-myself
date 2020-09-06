package com.study.self.interview.difficulties;

import java.nio.channels.FileChannel;

public class FindThread extends Thread{

    private InternetLinkFileQuery internetLinkFileQuery;

    public FindThread(InternetLinkFileQuery internetLinkFileQuery){
        this.internetLinkFileQuery = internetLinkFileQuery;
    }

    @Override
    public void run() {
        internetLinkFileQuery.addThread(this);

        FileChannel fileChannel = internetLinkFileQuery.fileChannel();

        long threadMemory = internetLinkFileQuery.getEveryThreadMemory();

        long byteBufferLen = threadMemory / 2;

        byte[] bytes = new byte[1024];

    }
}
