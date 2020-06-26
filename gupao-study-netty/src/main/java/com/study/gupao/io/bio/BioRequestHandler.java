package com.study.gupao.io.bio;

import com.study.gupao.format.MessageUtils;
import com.study.gupao.format.StringBorderBuild;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * 处理请求
 */
public class BioRequestHandler implements Runnable {

    private static final Log log = LogFactory.getLog(BioRequestHandler.class);
    private Socket requestSocket;
    private StringBorderBuild borderBuild;

    public BioRequestHandler(Socket requestSocket, StringBorderBuild borderBuild) {
        this.requestSocket = requestSocket;
        this.borderBuild = borderBuild;
    }

    @Override
    public void run() {
        try {
            requestSocket.setKeepAlive(true);
            InputStream inputStream = requestSocket.getInputStream();
            OutputStream outputStream = requestSocket.getOutputStream();
            byte[] bytes = new byte[1024];
            while(inputStream.read(bytes) != -1){
                MessageUtils.decodeByteStringMessage(bytes, borderBuild.getEndBorder());
//                Random random = new Random();
//                Thread.sleep(1000);
//                System.out.println(new String(bytes));
                outputStream.write(("echo from service"+borderBuild.getEndBorder()).getBytes());
                outputStream.flush();
                Thread.sleep(1000);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
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
