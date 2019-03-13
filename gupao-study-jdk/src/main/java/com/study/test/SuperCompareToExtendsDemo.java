package com.study.test;

import com.study.test.child.BlueTooth;
import com.study.test.child.Computer;
import com.study.test.child.Mobile;
import com.study.test.parent.Electronics;
import com.study.test.parent.TechnologyProduct;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SuperCompareToExtendsDemo {

    public static void main(String[] args) {
        List<? super Electronics> childs = new LinkedList<>();// ? super T 只能接受 T及其子类
        childs.add(new Computer());
        childs.add(new Mobile());
        childs.add(new BlueTooth());
        childs.add(new Electronics());
        List<? extends Electronics> childs_ = new ArrayList<>();
        childs_.add(null); // 除了null其他元素都不能被添加
        for(Object child: childs){
            if(child instanceof Computer){
                Computer computer =  (Computer)child;
                computer.display();
                computer.input();
            }else if(child instanceof Mobile){
                Mobile mobel = (Mobile)child;
                mobel.loudsSpeak();
            }else if(child instanceof BlueTooth){
                BlueTooth blueTooth = (BlueTooth)child;
                blueTooth.loudsSpeak();
            }else if(child instanceof Electronics){
                Electronics blueTooth = (Electronics)child;
                System.out.println("父类");
            }

        }






    }

}
