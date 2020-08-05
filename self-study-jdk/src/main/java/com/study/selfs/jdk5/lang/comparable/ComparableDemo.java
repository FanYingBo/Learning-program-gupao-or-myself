package com.study.selfs.jdk5.lang.comparable;

/**
 *
 * @see java.lang.Comparable
 */
public class ComparableDemo {

    public static void main(String[] args) {
        Person per1 = new Person("jack",21,'0');
        Person per2 = new Person("jedr",21,'0');
        int i = per1.compareTo(per2);// 比较两个人是不是同一个人
        if( i == 0){
            System.out.println("per1 per2 是同一个人");
        }
        System.out.println(i);
    }
}

class Person implements Comparable<Person> {

    private String name;

    private int age;

    private char sex;

    public Person(String name, int age, char sex) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.age,o.age)|Character.compare(this.sex,o.sex)|this.name.compareTo(o.name);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

