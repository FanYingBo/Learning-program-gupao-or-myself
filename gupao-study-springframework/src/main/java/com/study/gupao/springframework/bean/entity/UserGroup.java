package com.study.gupao.springframework.bean.entity;

import java.util.Collection;

public class UserGroup {

    private Collection<User> users;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
