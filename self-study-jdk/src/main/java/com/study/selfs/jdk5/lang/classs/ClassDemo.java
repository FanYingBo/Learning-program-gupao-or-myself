package com.study.selfs.jdk5.lang.classs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @see java.lang.Class
 * Class.forName 与 ClassLoader.loadClass的区别
 * 前者加载静态代码块完成类的初始化 后者不会加载静态代码块，只是把字节码加载到jvm中
 * ClassLoader.loadClass()后的class调用其方法之前会完成静态代码块的初始化工作
 */
public class ClassDemo {

    public static void main(String[] args) {

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            Class<?> aClass = classLoader.loadClass("com.study.selfs.jdk5.lang.classs.Foo");
            System.out.println("------获取方法-----");
            Method getNum_ = aClass.getMethod("print", null);
            System.out.println("------调用方法-----");
            Object invoke = getNum_.invoke(null);
            System.out.println("------调用方法结束-----");
            System.out.println("静态变量i的值："+invoke);
//            Foo instance = (Foo)aClass.newInstance();//实例化的时候初始化静态代码块
//            System.out.println(instance.getNum());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        } catch (InvocationTargetException e) {
//            e.printStackTrace();
        }
        System.out.println("-----------------------");
        try {
            Class<?> clazz = Class.forName("com.study.selfs.jdk5.lang.classs.Foo");
            Class<?> calzzs = Class.forName("com.study.selfs.jdk5.lang.classs.Foo",true,classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
class Foo{

    private static int i = 0;
    private  int j = 0;
    static{
        System.out.println("FOO static block execute");
        i = 1;
        System.out.println("静态变量 i = "+i);
    }
    public static int getNum_(){
        return i;
    }


    public int getNum(){
        return i;
    }

    public static void print(){
        System.out.println("静态方法调用");
    }
}
