package com.study.mybatis;

public class InnerClass {


    public class InnerClassChild{
        {
            System.out.println(" inner class static ");
        }
        public void print(){
            System.out.println(" inner class method ");
        }
    }

    public void run(){
        InnerClassChild innerClassChild = new InnerClassChild();
        innerClassChild.print();
    }


}
