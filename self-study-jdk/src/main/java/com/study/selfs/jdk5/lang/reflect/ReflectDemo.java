package com.study.selfs.jdk5.lang.reflect;

import com.study.selfs.jdk5.lang.reflect.entity.Member;
import com.study.selfs.jdk5.lang.reflect.entity.anno.EntityDemo;

import java.lang.reflect.*;

/**
 * java.lang.reflect.Method
 */
public class ReflectDemo {

    public static void main(String[] args) {
        // class
        Class<?> clazz = Member.class;
        Object member = null;
        try {
            member = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 获取包
        Package aPackage = clazz.getPackage();
        System.out.println("aPackage name："+aPackage.getName());
        EntityDemo annotation = clazz.getAnnotation(EntityDemo.class);
        System.out.println("注解值："+annotation.value());
        EntityDemo[] annotationsByType = clazz.getAnnotationsByType(EntityDemo.class);
        System.out.println("注解值："+annotationsByType[0].value());
        // 获取所有属性
        Field[] fields = clazz.getFields();
        try {
            // 获得某个方法
            Method joinMethod=clazz.getMethod("join",new Class[]{String.class,Integer.class,boolean.class});
            // 获取方法名
            String methodName = joinMethod.getName();
            System.out.println("methodName："+methodName);
            // 方法调用
            Object invoke = joinMethod.invoke(member, new Object[]{"达到团", 2312, true});
            System.out.println("invoke："+invoke.toString());
            // 获取参数返回值类型
            Class<?> returnType = joinMethod.getReturnType();
            System.out.println("returnType："+returnType);
            // 获取参数类型
            Class<?>[] parameterTypes = joinMethod.getParameterTypes();

            // 获得参数
            Parameter[] parameters = joinMethod.getParameters();
            Type[] genericParameterTypes = joinMethod.getGenericParameterTypes();
            for(int i = 0;i< parameterTypes.length;i++){
                System.out.println("paramType_"+i+"："+parameterTypes[i].getName());
//                System.out.println("paramType_"+i+"："+parameterTypes[i].getCanonicalName());
                System.out.println("parameter_"+i+"："+parameters[i].getName()+"--"+parameters[i].getParameterizedType());
                System.out.println("genericParameterType_"+i+"："+genericParameterTypes[i].getTypeName());

            }
            // 获取方法所在的class
            Class<?> declaringClass = joinMethod.getDeclaringClass();
            System.out.println("Class Name"+declaringClass.getName());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
