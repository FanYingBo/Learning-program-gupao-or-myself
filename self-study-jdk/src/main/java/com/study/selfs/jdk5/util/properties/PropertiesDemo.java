package com.study.selfs.jdk5.util.properties;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * properties
 */
public class PropertiesDemo {

    @Test
    public void propertiesLoad(){
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("./test.properties"));
            String property = properties.getProperty("demo.test");
            System.out.println(property);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * properties 可以转xml
     */
    @Test
    public void propertiesToXml(){
        Properties properties = new Properties();
        properties.setProperty("name","jack");
        properties.setProperty("age","23");
        try {
            properties.storeToXML(System.out,"","UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
