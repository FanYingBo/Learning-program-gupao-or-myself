package com.study.test;

/**
 *
 * java位运算
 */
public class BitOperation {

    public static void main(String[] args) {
        // 0000 0010 --2的反码--> 1111 1101--进1--> 1111 1110(-2)--右移三位-->
        // 1111 1111 --去1--> 1111 1110 --反码--> 0000 0001
        // 负的数高位为 1
        System.out.println(-2 >> 3);// 0010  0001 有符号右移右移
        System.out.println(2 << 1);// 0010  0100 左移
        //1111 1111 1111 1111 1111 1111 1111 1110(-2) --右移3位--> 0001 1111 1111 1111 1111 1111 1111 1111
        System.out.println(Integer.toBinaryString(-2 >>> 3));//  无符号右移
        System.out.println(~1);// 0000 0001(1) 1111 1110(-2) 取反
        System.out.println(1 & 2);//0001 0010   0000 按位与 两个位数同为1时取1 其余取0
        System.out.println(1 | 2);//0001 0010 0011 按位或 两个位数其中一个为1 取1 其余取0
        System.out.println(1 ^ 2);//0001 0010 0011 相同取0 不同取1
    }
}
