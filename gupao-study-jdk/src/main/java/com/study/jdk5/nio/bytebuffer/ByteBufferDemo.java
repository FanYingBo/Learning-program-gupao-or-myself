package com.study.jdk5.nio.bytebuffer;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 *
 * @since java 4
 * @see java.nio.ByteBuffer
 */
public class ByteBufferDemo {

    /**
     * 读取文件
     * @throws Exception
     */
    @Test
    public void testFileByteBuffer() throws Exception {
        File file = new File("D:\\data.dat");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10); // bytebuffer 可以设置读取指定长度的信息
        StringBuffer stringBuffer = new StringBuffer();
        System.out.println(channel.isOpen());
        while(channel.read(byteBuffer) > 0){
            stringBuffer.append(new String(byteBuffer.array()));
            System.out.println("channel position: "+channel.position());
            System.out.println("bytebuffer position: "+byteBuffer.position());
            byteBuffer.clear();
        }

        System.out.printf(stringBuffer.toString());
        channel.close();
        fileInputStream.close();
    }
    /**
     *  mark = -1;  标记当前位置
     *  position = 0;  位置 warp() 会赋值为0 put的时候会对当前位子加一逐个赋值
     *  limit; flip时记录flip之前的position put操作之后进行flip可以重置当前position
     *  capacity;
     */
    @Test
    public void testByteBuffer(){
        byte[] bytes = new byte[10];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes)
                .put((byte)1).put((byte)2).put((byte)3);
        byte b = byteBuffer.get(2);
        byte[] array = byteBuffer.array();
        System.out.println("before flip position:"+byteBuffer.position());
        System.out.println("before flip limit:"+byteBuffer.limit());
        byteBuffer.flip();
        System.out.println("after flip position:"+byteBuffer.position());
        System.out.println("after flip limit:"+byteBuffer.limit());
        System.out.println(b);
        System.out.println(Arrays.toString(array));
        while(byteBuffer.hasRemaining()){
            byte b1 = byteBuffer.get();
            System.out.println("position: "+byteBuffer.position());
            System.out.println("value: "+b1);
        }
    }


}
