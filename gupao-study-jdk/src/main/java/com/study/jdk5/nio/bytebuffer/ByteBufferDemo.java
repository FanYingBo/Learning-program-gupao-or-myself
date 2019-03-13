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
                .putInt(122121)
                .putInt(2121122121)
                .putChar('事');
        byte b = byteBuffer.get(3);
        byte[] array = byteBuffer.array();
        byteBuffer.flip();
        System.out.println(b);
        System.out.println(Arrays.toString(array));
    }


}
