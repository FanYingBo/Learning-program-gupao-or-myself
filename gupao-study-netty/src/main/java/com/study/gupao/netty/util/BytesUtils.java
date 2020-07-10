package com.study.gupao.netty.util;

/**
 *  字节转换
 */
public class BytesUtils {

    public static byte[] intToBytes(int cmd){
        byte[] bytes = new byte[4];
        bytes[0] = (byte)((cmd >> 24) & 0xff);
        bytes[1] = (byte)((cmd >> 16) & 0xff);
        bytes[2] = (byte)((cmd >> 8) & 0xff);
        bytes[3] = (byte)(cmd & 0xff);
        return bytes;
    }

    public static int bytesToInt(byte[] bytes){
        int value = (int) ((bytes[0] & 0xFF)
                | ((bytes[1] & 0xFF)<<8)
                | ((bytes[2] & 0xFF)<<16)
                | ((bytes[3] & 0xFF)<<24));
        return value;
    }
}
