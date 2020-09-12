package com.study.self.interview.difficulties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel 读取文件
 */
public class TestFileChannel {

    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\batch\\address1.txt","rw");
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer bytes = ByteBuffer.allocate(1024);
            int position = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while(channel.read(bytes,position) >= 0){
                position += bytes.position();
//                bytes.flip(); // 如果flip 则通过limit截取
                String string = new String(bytes.array(), 0, bytes.position());
                stringBuffer.append(string); // 如果没有flip 则通过position截取
                bytes.clear();
            }
            System.out.println(position);
            System.out.println(channel.size());
            System.out.println(stringBuffer.toString().split("\n").length);
//            long size = channel.size();
//            long remain = size - position;
//            ByteBuffer allocate = ByteBuffer.allocate((int) remain);
//            channel.read(allocate);
//            System.out.println(new String(allocate.array()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
