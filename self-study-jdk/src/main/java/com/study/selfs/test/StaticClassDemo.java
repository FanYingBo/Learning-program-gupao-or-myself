package com.study.selfs.test;

public class StaticClassDemo {

    static class Foo{
        int value = 1;

        public void setValue(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }

    }

    public static void setFoo(Foo foo){
        foo.setValue(2);
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setValue(1);
        setFoo(foo);
        System.out.println(foo.getValue());
    }
}
