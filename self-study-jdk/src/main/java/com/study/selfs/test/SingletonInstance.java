package com.study.selfs.test;

/**
 *
 * 正确的双重检验锁实现
 * 原因：
 * 1.对象在实例化时并不是马上实例化，需要经过类的加载，验证，准备，解析，初始化，因此在类加载的阶段就会出现线程并发访问创建多个实例对象的问题。
 * 2.如果直接通过 synchronized 对方法修饰，
 *   public synchronized static SingletonInstance getInstance()
 *   或者是 synchronized(SingletonInstance.class)
 *   就会造成多个线程访问时锁的竞争产生性能上的损耗
 * 3.初始化的过程：
 *    a.在对内存中为对象分配内存(不包括静态变量)
 *    b.对实例变量进行赋值，赋默认值
 *    c.先初始化父类，再初始化子类，先执行实例化代码为变量赋值，再执行构造方法
 *    b.将对象的内存地址赋值给该变量
 * 分析：
 * 两个线程同时访问时，
 * 1.第一次判断如果该对象未初始化，就进入同步代码块，如果已经初始化不仅如此同步代码块，直接返回初始化后的值（避免锁的竞争）
 * 2.如果第一次判断对象未初始化，其他线程已经对线程初始化了，在当前线程进入同步代码块时，第二次判断后就不需要再实例化该对象
 *   如果当前线程第二次判断的结果也是未初始化（此时其他线程正在进行初始化过程，并未对该变量赋值对象的地址信息， ”==“ 判断的是对象的地址信息），
 *   所以当前线程依然有可能对该对象进行实例化，所以要对该对象通过 volatile 修饰，防止指令重排，当前线程获取到的是其他线程实例化后的值，
 *   该线程的读（read）发生在其他线程（write）之后，避免对象被实例化多次。
 */
public class SingletonInstance extends Parent{

    public static volatile SingletonInstance instance;
    public static int a = 0;
    public int i = init();
    static{
        a = 1;
        System.out.println("静态代码SingletonInstance");
    }


    public SingletonInstance(){
        super();
        System.out.println("构造方法"); // 后执行
    }

    public int init(){
        System.out.println("变量赋值SingletonInstance"); //先执行
        return 10;
    }

    public  static SingletonInstance getInstance(){
        if(instance == null){
            synchronized (SingletonInstance.class){
                if(instance == null){
                    instance = new SingletonInstance();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        int a = SingletonInstance.a;
        SingletonInstance instance = SingletonInstance.getInstance();
    }
}

class Parent{

    public int j = initJ();

    static{
        System.out.println("静态代码Parent");
    }

    public int initJ(){
        System.out.println("变量赋值Parent"); //先执行
        return 19;
    }
}

