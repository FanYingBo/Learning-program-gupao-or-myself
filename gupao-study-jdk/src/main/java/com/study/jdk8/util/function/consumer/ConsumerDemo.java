package com.study.jdk8.util.function.consumer;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * 函数式接口：
 * @see java.util.function.Consumer
 *
 * lambda表达式
    1、如果形参列表是空的，只需要保留（）即可
    2、如果没有返回值。只需要在{}写执行语句即可
    3、如果接口的抽象方法只有一个形参，（）可以省略，只需要参数的名称即可
    4、如果执行语句只有一行，可以省略{}，但是如果有返回值时，情况特殊。
    5、如果函数式接口的方法有返回值，必须给定返回值，如果执行语句只有一句，还可以简写，即省去大括号和return以及最后的；号。(如果要省去return已经后面的分号，大括号是一定也要省去的)
    6、形参列表的数据类型会自动推断，只需要参数名称。
    注意:
   （1）使用lambda表达式只能是对于接口，对于抽象类或者普通类都是不能用的.
   （2）并且函数式接口中只能有1个抽象方法与n个静态方法，如果存在多个抽象方法，则不能作为函数式接口(lambda)使用了
    @FunctionInterface
    其实作用就是提示其他人或者编译器，该接口是函数式接口，该接口内只能有一个抽象方法(默认方法不限)。当某些不知道的人 在该接口中又添加了方法时，编译器会有警告。
    所以，@FunctionInterface接口仅仅只是提示作用
 */
public class ConsumerDemo {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello","world","function");
        // 函数式定义
        Consumer<String> consumer = o -> System.out.println("第一个consumer："+o);
        Consumer<String> consumerThen = o -> System.out.println("第二个consumer："+o.toUpperCase());
        Consumer<String> stringConsumer = consumer.andThen(consumerThen);
//        list.forEach(stringConsumer);

        consumer.accept("2121");
        consumerThen.accept("dasdada");
        stringConsumer.accept("dadwwwe");
    }


}
