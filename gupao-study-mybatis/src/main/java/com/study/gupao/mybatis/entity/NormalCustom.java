package com.study.gupao.mybatis.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class NormalCustom {

    private String name;

    private int age;

    private int sex;

    private int isVip;

    private Date birth;

    private Timestamp createDate;

    private String job;

    private String idNum;

    private String addr;

    private List<CustomOrder> orderList;

    public NormalCustom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public List<CustomOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<CustomOrder> orderList) {
        this.orderList = orderList;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "NormalCustom{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", isVip=" + isVip +
                ", birth=" + birth +
                ", createDate=" + createDate +
                ", job='" + job + '\'' +
                ", idNum='" + idNum + '\'' +
                ", addr='" + addr + '\'' +
                ", orderList=" + orderList +
                '}';
    }
}
