package com.study.jdk5.nio.bytebuffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @since java 4
 * @see java.nio.ByteBuffer
 */
public class ByteBufferDemo {

    public static void main(String[] args) {
        byte[] bytes = new byte[10];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes)
                .put((byte)1).put((byte)2);
        byte b = byteBuffer.get(3);
        byte[] array = byteBuffer.array();
        byteBuffer.flip();
        System.out.println(b);
        System.out.println(Arrays.toString(array));
        while(byteBuffer.hasRemaining()){
            byte b1 = byteBuffer.get();
            System.out.println(b1);
        }
    }


}
