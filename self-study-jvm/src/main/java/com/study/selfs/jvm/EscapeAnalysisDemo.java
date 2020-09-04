package com.study.selfs.jvm;

/**
 * 对象的栈上分配与逃逸分析
 *   虚拟机参数设置如下，表示做了逃逸分析  消耗时间在10毫秒以下
 * -server  -Xmx10M  -Xms10M
 *     -XX:+DoEscapeAnalysis  -XX:+PrintGC
 */
public class EscapeAnalysisDemo {

    public static void main(String[] args) {
        int count = 10240;
        for(int i = 0 ; i < count; i ++){
            TestObject object = ObjectFactory.create();
//            System.out.println(object);
        }
    }
}
class ObjectFactory{

    public static TestObject create(){

        TestObject object = new TestObject();

        return object;
    }

    public static void alloc(){
        byte[] bytes = new byte[1024];
    }
}

class TestObject{

    private byte[] bytes = new byte[1024];

}