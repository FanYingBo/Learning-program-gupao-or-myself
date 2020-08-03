package com.selfs.study.gauva;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * 布隆过滤器
 */

/**
 * 维基百科
 * http://en.wikipedia.org/wiki/File:Bloom_filter_fp_probability.svg
 * 布隆过滤器的证明过程
 */
public class BloomFilterDemo {

    @Test
    public void BloomFilter(){
        int expectedInsertions = 6;
        double falsePositive = 0.0001;
        /**
         * 1. 通过插入次数 n 和false positive 计算数组大小m
         * 2.通过数组大小m和插入次数n 来计算hash次数 k
         */
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), expectedInsertions, falsePositive);
        /**
         * (过程复杂)
         * 1.多次hash
         * 2.hash 值对BitArray的大小m 取模 判断当前hash位是否是0 若不是则返回true，若是则返回false
         */
        bloomFilter.put("tom");
        bloomFilter.put("jeck");
        bloomFilter.put("tony");
        bloomFilter.put("hello");
        bloomFilter.put("world");
        bloomFilter.put("word");
        boolean mightContain = bloomFilter.mightContain("word");
        System.out.println(mightContain);
    }
}
