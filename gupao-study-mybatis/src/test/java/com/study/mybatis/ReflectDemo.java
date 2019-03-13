package com.study.mybatis;

import java.lang.reflect.*;
import java.util.List;

public class ReflectDemo {

    public static void main(String[] args) {

        Class<?> userClass = User.class;
        Field[] declaredFields = userClass.getDeclaredFields();
        for(Field filed : declaredFields){
            Type type = filed.getType();
            if(type instanceof GenericArrayType){
                System.out.println(type.getTypeName());
            }else if(type instanceof TypeVariable){
                System.out.println(type.getTypeName());
            }else if(type instanceof ParameterizedType){
                System.out.println(type.getTypeName());
            }else if(type instanceof Class){
                System.out.println(type.getTypeName());
            }
        }

    }
}

class User{

    private String username;

    private Integer age;

    private List<Order> orderList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
class Order{

}


