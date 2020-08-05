package com.study.selfs.jdk5.util.observable;


import java.util.Observable;
import java.util.Observer;

/**
 *
 * @see 1.0
 * @see java.util.Observable
 */
public class ObservableDemo {

    public static void main(String[] args) {
        MyObservable observable = new MyObservable();
        observable.setChanged();
        observable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object value) {
                System.out.println(value);
            }
        });
        observable.notifyObservers("hello world");
    }
    public static class  MyObservable extends Observable{
        @Override
        public void setChanged() {
            super.setChanged();
        }
    }
}
