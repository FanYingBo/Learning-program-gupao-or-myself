package com.study.gupao.buffer;

import java.nio.ByteBuffer;

/**
 *
 */
public class ReadWriteBuffer {

    private ByteBuffer readBuffer;

    private ByteBuffer writeBuffer;

    public ReadWriteBuffer(int readCapacity, int writeCapacity){
        this.readBuffer = ByteBuffer.allocate(readCapacity);
        this.writeBuffer = ByteBuffer.allocate(writeCapacity);
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public ByteBuffer getWriteBuffer() {
        return writeBuffer;
    }
}
