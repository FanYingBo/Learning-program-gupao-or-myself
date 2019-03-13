package com.study.jdk5.util.concurrent.atomic.atomicinteger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThreadPoolListener {

    public List<Thread> threadList = new ArrayList<>();

    public void listenThread(){

        for(;;){
            Iterator<Thread> iterator = threadList.iterator();
            while(iterator.hasNext()){
                Thread next = iterator.next();
                if(!next.isAlive()){
                    iterator.remove();
                }
            }
            if(threadList.isEmpty()){
                break;
            }
        }
    }

}
