package com.study.selfs.test;

import java.io.*;

/**
 * transient 修饰词
 * 被transient修饰的字段不能被序列化
 *
 */
public class TransientDemo {

    public static void main(String[] args) {
        Person person = new Person("jack","212121231","农民");
        File file = new File("ser.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file,false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(person);
            objectOutputStream.close();
            fileOutputStream.close();
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Person dereperson = (Person) objectInputStream.readObject();
            System.out.println(dereperson.toString());
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
class Person implements Serializable{

    private static final long serialVersionUID = 6522560439332778165L;

    private String usrname;

    private String password;

    private String job;

    public Person(String usrname, String password, String job) {
        this.usrname = usrname;
        this.password = password;
        this.job = job;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Person{" +
                "usrname='" + usrname + '\'' +
                ", password='" + password + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}