package com.study.gupao.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

public class BloomFilterDemo {

    public static void main(String[] args) {
        BloomFilter bloomFilter = BloomFilter
                .create(
                        Funnels.stringFunnel(Charset.defaultCharset())
                        , 1000000
                        , 0.001
                );
        bloomFilter.put("tom");
        System.out.println(bloomFilter.mightContain("tom"));

//        System.out.println(Math.log(7.38905609893065));//自然数e 的对数
//        System.out.println(Math.exp(2));// e的幂次方
    }

}
