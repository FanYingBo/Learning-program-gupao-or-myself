package com.study.jdk5.nio.channels.filechannel;

import org.junit.Test;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @see java.nio.channels.FileChannel
 */
public class FileChannelDemo {

    @Test
    public void copy(){
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
    @Test
    public void write(){
        File file = new File("E:\\data\\test.txt");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            String str = "test write file";
            MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, str.getBytes().length);
            map.put(str.getBytes());
            System.out.println(map.position()); // 返回追加文件的地址 ，可以顺序写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
