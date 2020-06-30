package com.study.leecode;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class NonRepeatChar {

    public static void main(String[] args) {
        String str = "abcahdsakjjsdsadasdaddwqweeww";
//        String str = "au";
        System.out.println(nonRepeatCharLen(str));
    }

    public static int nonRepeatCharLen(String s){
        int[] indexCache = new int[128];
        int maxLen = 0;
        int sign = 0;
        for(int i = 0;i < s.length();i++){
            char index = s.charAt(i);
            int lowIndex = indexCache[index];
            sign = Math.max(sign,lowIndex);
            maxLen = Math.max(maxLen,i - sign + 1);
            indexCache[index] = i + 1;
        }
        return maxLen;
    }

    public static int nonRepeatCharLen2(String s){
        // 记录字符上一次出现的位置
        int[] last = new int[128];
        for(int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int n = s.length();

        int res = 0;
        int start = 0; // 窗口开始位置
        for(int i = 0; i < n; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);
            res   = Math.max(res, i - start + 1);
            last[index] = i;
        }

        return res;
    }
}
