package com.study.jdk5.util.enummap;

import java.util.EnumMap;

/**
 * @since 1.5
 * EnumMap 可作为策略模式配置  是对Enum的包装
 */
public class EnumMapDemo {

    public static void main(String[] args) {
        EnumMap enumMap = new EnumMap(Foo.class);
        enumMap.put(Foo.ONE,1);
        enumMap.put(Foo.TWO,2);
        System.out.println(enumMap);
    }

}

enum Foo {
   ONE,TWO
}

