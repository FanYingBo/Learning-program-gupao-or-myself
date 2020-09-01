package com.study.selfs.test;

/**
 * 一个类只能继承一个父类，但可以继承多个接口
 * 一个接口可以继承多个接口
 */
public class InterfaceClassDemo {


}

interface Foo{

}
interface Hoo{

}
abstract class Boo implements Foo,Hoo{


}
abstract class Coo{


}

class Eoo extends Boo implements Foo,Hoo{

}


