package com.study.self.interview.difficulties.filequery;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FindThread implements Runnable{

    private InternetLinkFileQuery internetLinkFileQuery;

    private int runId;

    private long threadStartPosition = 0;

    private long threadEndPosition = 0;

    private String strBuffer;

    public FindThread(int runId, InternetLinkFileQuery internetLinkFileQuery){
        this.runId = runId;
        this.internetLinkFileQuery = internetLinkFileQuery;
    }

    public void cutFile(){

        FileChannel fileChannel = internetLinkFileQuery.fileChannel();

        long threadMemory = internetLinkFileQuery.getEveryThreadMemory();

        long byteBufferLen = threadMemory;

        long totalNum = byteBufferLen / 1024 + 1;

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        StringBuffer stringBuilder = new StringBuffer();

        long threadEnd = threadStartPosition;
        try {
            while(true){
                if (!(fileChannel.read(byteBuffer,threadEnd) != -1 && totalNum >= 0)) break;
                threadEnd += byteBuffer.position();
                stringBuilder.append(new String(byteBuffer.array(),0,byteBuffer.position()));
                byteBuffer.clear();
                totalNum -- ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(stringBuilder.lastIndexOf("\n") < stringBuilder.length() - 1){
            strBuffer = stringBuilder.substring(0, stringBuilder.lastIndexOf("\n"));
            String endString = stringBuilder.substring(stringBuilder.lastIndexOf("\n") + 1);
            threadEnd -= endString.getBytes().length;
        }else{
            strBuffer = stringBuilder.toString();
        }
        threadEndPosition = threadEnd;
    }

    @Override
    public void run() {
        try{
            System.out.println("threadStartPosition: "+threadStartPosition);
            System.out.println("threadEndPosition: "+threadEndPosition);
            if(!"".equals(strBuffer)){
                int start = strBuffer.indexOf("\n");
                String startStr = strBuffer.substring(0, start + 1);
                String endStr = strBuffer.substring(strBuffer.lastIndexOf("www"));
                System.out.println("id:"+runId+" startStr: "+startStr+ "endStr: "+endStr);
            }
            if(strBuffer.indexOf(internetLinkFileQuery.getQueryStr()) >= 0){
                internetLinkFileQuery.setResult();
                stop();
            }
            markThreadSize();
            removeThis();
            checkIfStop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkIfStop(){
        if(this.threadEndPosition >= this.internetLinkFileQuery.getFileSize()){
            stop();
        }
    }

    private void stop(){
        this.internetLinkFileQuery.stop();
    }

    private void removeThis(){
        internetLinkFileQuery.remove(this.runId);
    }

    public void markThreadSize(){
        this.internetLinkFileQuery.addCompletableSize(this.threadEndPosition - this.threadStartPosition);
    }

    public void setThreadStart(long threadOffset) {
        this.threadStartPosition = threadOffset;
    }

    public long getThreadEnd(){
        return this.threadEndPosition;
    }

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }
}
