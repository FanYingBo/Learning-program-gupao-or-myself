package com.study.gupao.mybatis.dao;

import com.study.gupao.mybatis.entity.User;
import com.study.gupao.mybatis.mapper.UserMapper;

import java.util.List;

public class UserDao extends BaseDaoSupport<User,Integer>{

    private Class<UserMapper> mapperClass = UserMapper.class;

    private UserMapper userMapper;

    public UserDao() {
        super();
        this.userMapper = getMapper(mapperClass);
    }

    @Override
    public List<User> queryAllForList() throws Exception {
        return userMapper.getAllUsers();
    }

    @Override
    public List<User> queryAllForList(Object... condition) throws Exception {
        return null;
    }

    @Override
    public User getByPrimaryKey(Integer integer) throws Exception {
        return null;
    }

    @Override
    public void insert(User entity) throws Exception {

    }

    @Override
    public void update(User entity) throws Exception {

    }
}
