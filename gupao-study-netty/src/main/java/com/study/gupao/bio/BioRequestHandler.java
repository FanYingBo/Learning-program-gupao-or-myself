package com.study.gupao.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 处理请求
 */
@Slf4j
public class BioRequestHandler implements Runnable {

    private Socket requestSocket;

    public BioRequestHandler(Socket requestSocket) {
        this.requestSocket = requestSocket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
            printWriter = new PrintWriter(requestSocket.getOutputStream(),true);
            String data;
            while((data = bufferedReader.readLine()) != null){
               log.info("receive from the client:"+data);
               // 结束符
               if("&&".equals(data)){
                   break;
               }
                String response = BioRequestResolver.processRequest(data);
                printWriter.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(printWriter != null){
                printWriter.close();
            }
            if(requestSocket != null){
                try {
                    requestSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
