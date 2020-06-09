package com.study.gupao.designmode.observer.core;

import com.study.gupao.designmode.observer.enumeration.MouseEventType;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *  动态代理 无侵入性监听
 */
public class EventProxy implements MethodInterceptor {

    private Class<?> proxyedClass;


    private Object target;

    private Class<?> callBackType;


    private Map<String,Enum> eventMethodMap;

    private Map<String, Method> invokeMap;

    public Object getInstance(Class<?> superClass, Class<?> callBackType){

        this.eventMethodMap = new HashMap<>();
        this.eventMethodMap.put("onClick",MouseEventType.ON_CLICK);
        this.eventMethodMap.put("onDblClick",MouseEventType.ON_DBL_CLICK);
        this.eventMethodMap.put("onMouseMove",MouseEventType.ON_MOUSE_MOVE);
        this.eventMethodMap.put("onMouseDown",MouseEventType.ON_MOUSE_DOWN);

        this.proxyedClass = superClass;
        this.callBackType = callBackType;

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.proxyedClass);
        enhancer.setCallback(this);
        loadListenerMethod();
        return enhancer.create();
    }
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("我是代理事件....");
        proxy.invokeSuper(obj,args);
        if(eventMethodMap.containsKey(method.getName())){
            invokMethod(method.getName());
        }
        return null;
    }

    private void invokMethod( String methodName){
        Enum eventType = this.eventMethodMap.get(methodName);
        try {
            this.target = callBackType.newInstance();
            Method callBackMethod = callBackType.getMethod(methodName,Event.class);
            Object listener =  this.proxyedClass.newInstance();
            Method addListener = this.invokeMap.get("addListener");
            addListener.invoke(listener,new Object[]{eventType,this.target,callBackMethod});
            Method triggerMethod = this.invokeMap.get("trigger");
            triggerMethod.invoke(listener,eventType);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void loadListenerMethod(){
        this.invokeMap = new HashMap<>();
        Class<?> superclass = this.proxyedClass.getSuperclass();
        Method[] methods =   superclass.getDeclaredMethods();
        for(Method method : methods){
            method.setAccessible(true);
            this.invokeMap.put(method.getName(),method);
        }
    }
}
