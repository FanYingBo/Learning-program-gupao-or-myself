package com.study.jdk5.nio.channels.filechannel;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @see java.nio.channels.FileChannel
 */
public class FileChannelDemo {

    public static void main(String[] args) {
        File file = new File("D:\\com.zip");
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(new File("D:\\test\\com.zip"));
            FileChannel inputChannel = fileInputStream.getChannel();
            FileChannel outputChannel = fileOutputStream.getChannel();
            outputChannel.transferFrom(inputChannel,0,inputChannel.size());
            fileInputStream.close();
            inputChannel.close();
            fileOutputStream.close();
            outputChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
