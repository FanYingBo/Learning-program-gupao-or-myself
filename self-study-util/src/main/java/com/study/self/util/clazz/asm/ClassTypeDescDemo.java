package com.study.self.util.clazz.asm;

public class ClassTypeDescDemo {

    public static void main(String[] args) {
        Object obj = new String[]{""};
        System.out.println(obj.getClass().getCanonicalName());
    }
}
