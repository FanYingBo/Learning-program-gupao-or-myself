package com.study.gupao.format;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.channels.SocketChannel;


public class MessageUtils {

    private static final Log log = LogFactory.getLog(MessageUtils.class);


    public static void decodeStringMessage(SocketChannel socketChannel, StringBuffer buffer,String receiveMessage,String border) throws IOException {
        StringBuffer afterBuffer = new StringBuffer("");
        String afterMessage = "";
        int borderIndex;
        if((borderIndex = receiveMessage.indexOf(border)) >=0){

            String beforeMessage = receiveMessage.substring(0, borderIndex);
            buffer.append(beforeMessage);
            log.info("read from the channel: "+socketChannel.getRemoteAddress()+" message: "+buffer.toString());
            afterMessage = receiveMessage.substring(borderIndex+1);
            if(afterMessage.trim().length() > 0 && !afterMessage.endsWith(border)){
                decodeStringMessage(socketChannel,afterBuffer,afterMessage,border);
            }
        }
    }

    public static void decodeStringMessage(String receiveMessage,String border){
        String afterMessage = "";
        int borderIndex;
//        System.out.println(receiveMessage);
        if((borderIndex = receiveMessage.indexOf(border)) >=0){
            String substring = receiveMessage.substring(0, borderIndex);
            if( substring.length() > 0){
                log.info("message: "+substring);
            }
            afterMessage = receiveMessage.substring(borderIndex + 1);
            if(afterMessage.length() > 0){
                decodeStringMessage(afterMessage,border);
            }
        }
    }

    public static void decodeByteStringMessage(byte[] bytes, String border){
        decodeStringMessage(new String(bytes).trim(),border);
    }

    public static void main(String[] args) {
        String border = "&";
        String message = new String("&client-thread_first_26782&lient-thread_first_26714&&client-thread_first_16870&nt-thread_first_4232&");
        System.out.println(message.endsWith(border));
    }
}
