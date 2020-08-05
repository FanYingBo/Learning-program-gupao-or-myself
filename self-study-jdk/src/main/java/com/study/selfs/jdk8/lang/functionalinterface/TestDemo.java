package com.study.selfs.jdk8.lang.functionalinterface;

public class TestDemo {
    public static void main(String[] args) {
        TestFunctionalInterface<String> sfs = str->{};
        TestFunctionalInterface<String> tTestFunctionalInterface = System.out::println;
        tTestFunctionalInterface.accept("hello world");
        TestHook.supplier("hello world").accept(System.out::println);
    }
}
class TestHook<T> {
    private T t;

    TestHook(T t) {
        this.t = t;
    }

    public static <T> TestHook supplier( final T t){
        return new TestHook(t);
    }

    public void accept(TestFunctionalInterface<T> functionalInterface){
        functionalInterface.accept(t);
    }
}
