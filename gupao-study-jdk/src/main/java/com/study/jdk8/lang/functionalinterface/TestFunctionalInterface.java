package com.study.jdk8.lang.functionalinterface;

/**
 * 自定义函数式接口
 * @param <T>
 */
@FunctionalInterface
public interface TestFunctionalInterface<T> {

    void accept(T t);
}
