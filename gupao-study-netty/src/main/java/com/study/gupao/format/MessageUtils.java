package com.study.gupao.format;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SocketChannel;


@Slf4j
public class MessageUtils {

    public static void decodeStringMessage(SocketChannel socketChannel, StringBuffer buffer,String receiveMessage,String border) throws IOException {
        StringBuffer afterBuffer = new StringBuffer("");
        String afterMessage = "";
        int borderIndex;
        if((borderIndex = receiveMessage.indexOf(border)) >=0){

            String beforeMessage = receiveMessage.substring(0, borderIndex);
            buffer.append(beforeMessage);
            log.info("read from the channel: "+socketChannel.getRemoteAddress()+" message: "+buffer.toString());
            afterMessage = receiveMessage.substring(borderIndex+1);
            if(afterMessage.trim().length() > 0){
                decodeStringMessage(socketChannel,afterBuffer,afterMessage,border);
            }
        }
    }

    public static void decodeStringMessage(StringBuffer buffer,String receiveMessage,String border) throws IOException {
        StringBuffer afterBuffer = new StringBuffer("");
        String afterMessage = "";
        int borderIndex;
        if((borderIndex = receiveMessage.indexOf(border)) >=0){
            buffer.append(receiveMessage.substring(0,borderIndex));
            log.info(" message: "+buffer.toString());
            afterMessage = receiveMessage.substring(borderIndex+1);
            if(afterMessage.length() > 0){
                decodeStringMessage(afterBuffer,afterMessage,border);
            }
        }
    }
}
