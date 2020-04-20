package com.selfs.study.gauva;

import com.google.common.base.Joiner;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

public class JoinerDemo {
    @Test
    public void Joiner(){
        Joiner joiner = Joiner.on(",").skipNulls();
        String join = joiner.join(new String[]{"dada","ddsa"});
        System.out.println(join);
    }

}
