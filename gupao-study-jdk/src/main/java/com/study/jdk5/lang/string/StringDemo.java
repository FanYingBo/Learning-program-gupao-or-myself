package com.study.jdk5.lang.string;

import org.junit.Test;

import java.util.LinkedList;

/**
 * @see java.lang.String
 * 1.字符串拼接性能对比： +=""<String.valueOf()<StringBuffer<StringBuilder
 * 2.String == 与 equals
 */
public class StringDemo {


    @Test
    public void strEquals(){
        String a = new String("aa");
        String s = "aa";
        System.out.println(" == "+ (a == s));
        System.out.println(" intern "+ (a.intern()==s));//常量池
        System.out.println(" equals "+ a.equals(s));

    }
    @Test
    public void strAppend(){
        String str = "";
        long start = System.currentTimeMillis();
        for(int num = 0;num < 100000;num++){
            str += num+"";
        }
        long end = System.currentTimeMillis();
        System.out.println("字符串拼接    耗时："+(end-start));

        str = "";
        start = System.currentTimeMillis();
        for(int num = 0;num < 100000;num++){
            str += String.valueOf(num);
        }
        end = System.currentTimeMillis();
        System.out.println("字符串转换    耗时："+(end-start));

        // append方法synchronized修饰了
        StringBuffer sb = new StringBuffer("");
        start = System.currentTimeMillis();
        for(int num = 0;num < 100000;num++){
            sb.append(num);
        }
        String s = sb.toString();
        end = System.currentTimeMillis();
        System.out.println("字符串转换    耗时："+(end-start));

        StringBuilder sb_ = new StringBuilder("");
        start = System.currentTimeMillis();
        for(int num = 0;num < 100000;num++){
            sb_.append(num);
        }
        String s1 = sb_.toString();
        end = System.currentTimeMillis();
        System.out.println("字符串builder    耗时："+(end-start));
    }

    // a21edb3c6bda1f2b4f58e6458901ec37
    /**
     * 把16进制字符串转换成字节数组
     * @param hex
     * @return byte[]
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static byte[] int2ByteArray(int i){
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    /**
     * 反转字符串
     */
    @Test
    public void testReversal(){
        String orgStr = "give me the result";
        String[] strArr = orgStr.split(" ");
        LinkedList<String> list = new LinkedList<>();
        for(String str : strArr){
            list.addFirst(str);
        }
        String join = String.join(" ",list);
        System.out.println(join);
    }
}
