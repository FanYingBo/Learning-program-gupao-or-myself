package com.selfs.study.gauva;

import com.google.common.base.Joiner;
import org.junit.Test;

public class JoinerDemo {
    @Test
    public void Joiner(){
        Joiner joiner = Joiner.on(",").skipNulls();
        String join = joiner.join(new String[]{"dada","ddsa"});
        System.out.println(join);
    }

}
