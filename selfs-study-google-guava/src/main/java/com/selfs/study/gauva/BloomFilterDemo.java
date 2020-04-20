package com.selfs.study.gauva;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;
/**
 * 布隆过滤器
 */
import java.nio.charset.Charset;

public class BloomFilterDemo {

    @Test
    public void BloomFilter(){
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), 100000, 0.1);
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
