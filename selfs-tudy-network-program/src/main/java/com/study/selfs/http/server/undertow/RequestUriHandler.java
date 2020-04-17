package com.study.selfs.http.server.undertow;

public class RequestUriHandler implements Handler {
    @Override
    public String handleAndReturnString(String name) {
        return "Hello " + name;
    }
}
