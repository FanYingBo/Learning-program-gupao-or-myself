package com.study.self.util.clazz.javaassist;

import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 *javaassit 动态生成字节码
 *
 */
public class JavaassistDemo {

    public static void main(String[] args) throws CannotCompileException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("com.study.HelloDemo");
        try {
            CtField ctField = new CtField(classPool.get("java.lang.String"), "name", ctClass);
            ctClass.addField(ctField);
            CtConstructor ctConstructor = new CtConstructor(new CtClass[]{classPool.get("java.lang.String")}, ctClass);
            ctConstructor.setModifiers(Modifier.PUBLIC);
            ctConstructor.setBody("this.name = $1;");
            ctClass.addConstructor(ctConstructor);

            CtMethod ctSetMethod = new CtMethod(CtClass.voidType,"setName",new CtClass[]{classPool.get("java.lang.String")},ctClass);
            ctSetMethod.setModifiers(Modifier.PUBLIC);
            ctSetMethod.setBody("this.name = $1;");

            CtMethod ctGetMethod = new CtMethod(classPool.get("java.lang.String"),"getName",new CtClass[]{},ctClass);
            ctGetMethod.setModifiers(Modifier.PUBLIC);
            ctGetMethod.setBody("return this.name;");
            // CtMethod 第一种方式
            CtMethod sayHello = CtMethod.make("public void sayHello(){System.out.println(\"hello, \"+this.name);}", ctClass);
            sayHello.setModifiers(Modifier.PUBLIC);
            sayHello.setBody("System.out.println(\"hello, \"+this.name);");
            // 第二种方式
            CtMethod greetMethod = new CtMethod(CtClass.voidType,"greeting",new CtClass[]{},ctClass);
            greetMethod.setModifiers(Modifier.PUBLIC);
            greetMethod.setBody("System.out.println(\"nice to meet you, \"+this.name);");

            ctClass.addMethod(sayHello);
            ctClass.addMethod(greetMethod);
            Class aClass = ctClass.toClass();
            Constructor constructor = aClass.getConstructor(String.class);
            Object tom = constructor.newInstance("tom");
            Method hello = aClass.getMethod("sayHello");
            Method greeting = aClass.getMethod("greeting");
            hello.invoke(tom);
            greeting.invoke(tom);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
