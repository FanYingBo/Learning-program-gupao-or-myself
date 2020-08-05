package com.study.selfs.jdk5.util.objects;

import java.util.Objects;

/**
 * Objects 工具类 JDK1.7
 * @see java.util.Objects
 */
public class ObjectsDemo {

    public static void main(String[] args) {
        Person person = null;
        // 判断Object 是不是为空
        System.out.println(Objects.isNull(person));
        // 不能为空，抛出NullPointException
//        Objects.requireNonNull(person);
        // 不能为空，并抛出message
//        Objects.requireNonNull(person,"人物不能为空");
        Person perso1 = new Person("jeck","2212121");
        Person perso2 = new Person("jeck","2212121");
        System.out.println(Objects.equals(perso1,perso2));


    }

}
