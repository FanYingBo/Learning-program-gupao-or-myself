package com.gupao.study.zookeeper.distribute.lock;

public class LockException extends RuntimeException{

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }
}
