package com.study.jdk5.nio.bytebuffer;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 *
 * @since java 4
 * @see java.nio.ByteBuffer
 */
public class ByteBufferDemo {

    private File file;

    @Before
    public void init(){
        file = new File("D://test.txt");
    }

    /**
     * 直接对文件的操作，可以刷新磁盘的映射
     */
    @Test
    public void testMappedByteBuffer(){
        try {
            String data = "hello";
            RandomAccessFile randomAccessFile = new RandomAccessFile(file.getAbsoluteFile(),"rw");
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, data.getBytes().length);
            mappedByteBuffer.put(data.getBytes());
            // mappedByteBuffer.put((byte)1); BufferOverflowException
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 直接缓冲区
     * {@link java.nio.DirectByteBuffer}
     */
    @Test
    public void testDirectByteBuffer(){
        // DirectByteBuffer
        // 直接从内存中取，跳过堆内存
        // zero copy 零拷贝
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);


    }
    /**
     * 读取文件
     * @throws Exception
     */
    @Test
    public void testFileByteBuffer() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        // HeapByteBuffer
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
