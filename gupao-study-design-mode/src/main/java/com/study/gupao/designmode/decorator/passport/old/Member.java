package com.study.gupao.designmode.decorator.passport.old;

public class Member {

    private String username;

    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
