package com.study.jdk8.lang.system;


import org.junit.Test;

import java.util.Map;

/**
 * java.lang.System
 *
 */
public class SystemDemo{

    /**
     *
     * System.getenv()
     * 环境变量 返回Map
     */
    @Test
    public void getSystemEnvironment(){
        Map<String,String> environment =  System.getenv();
        for(Map.Entry<String, String> entry:environment.entrySet()){
            System.out.println(entry.getKey()+" -- "+entry.getValue());
        }
    }

    /**
     * System.getProperties()
     * 系统参数
     */
    @Test
    public void getSystemProoerties(){
        Map<String,Object> environment =  (Map)System.getProperties();
        for(Map.Entry<String, Object> entry:environment.entrySet()){
            System.out.println(entry.getKey()+" -- "+entry.getValue());
        }
    }
}
