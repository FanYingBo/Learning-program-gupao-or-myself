package com.study.self.interview.difficulties.leecode;

import org.junit.Test;

/**
 * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进 Z 字形排列。
 *
 * 比如输入字符串为 "PAYPALISHIRING"行数为 3 时，排列如下：
 *
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
 *
 * 请你实现这个将字符串进行指定行数变换的函数：
 *
 * string convert(string s, int numRows);
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/zigzag-conversion
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class ZTypeString {

    @Test
    public void testString(){
        System.out.println(convert("ABCDe", 4));
    }

    private String convert(String str, int row){
        if(row < 2){
            return str;
        }
        int cycle = row + (row - 2);
        int len = cycle - row;
        int mod = str.length() % cycle;
        int loop = mod > 0? str.length() / cycle + 1: str.length()/cycle;
        int remain = mod > row ? mod - row : 1;
        int secondOrder = mod > 0 ? len * loop + remain + loop : len * loop + loop;
        char[][] chars = new char[row][secondOrder];
        int index1 = 0;
        int index2 = 0;
        int count = 0;
        for(int i = 0;i < str.length();i++){
            char c = str.charAt(i);
            chars[index1][index2] = c;
            if(count < row - 1){
                index1 ++;
                count ++;
            }else if(count >= row - 1 && count < cycle - 1){
                index2 ++;
                index1 --;
                count ++;
            }else if(count == cycle - 1){
                count = 0;
                index1= 0;
                index2 ++;
            }
        }
        String ret = "";
        for(int i= 0;i < row;i++){
            for(int j = 0;j < secondOrder; j ++){
                char c = chars[i][j];
                if(c != 0){
                  ret += c;
                }
            }
        }
        return ret;
    }
}
