package com.study.jdk5.util.concurrent.atomic.atomicreferencefieldupdater;

public class UpdateStatus {

    public volatile Integer status;

    public UpdateStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
