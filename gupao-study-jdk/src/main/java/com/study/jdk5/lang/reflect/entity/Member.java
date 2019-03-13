package com.study.jdk5.lang.reflect.entity;

import com.study.jdk5.lang.reflect.entity.anno.EntityDemo;

@EntityDemo(value = "member",clazz = Member.class)
public class Member {

    private Integer id;

    private String name;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String join(String clubname,Integer clubId,boolean isNew){
        String str = "老社团成员";
        if(isNew){
            str = "新社团成员";
        }

        return "clubName:"+clubname+" clubId:"+clubId+" isNew:"+str;
    }
}
