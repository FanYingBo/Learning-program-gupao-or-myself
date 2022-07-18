package com.study.selfs.tcc.exception;

public class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ServiceException warp(Throwable e){
        return new ServiceException(e.getMessage(), e.getCause());
    }

    public static ServiceException warp(String msg){
        return new ServiceException(msg);
    }
}
