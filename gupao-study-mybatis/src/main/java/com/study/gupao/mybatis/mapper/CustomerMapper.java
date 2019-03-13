package com.study.gupao.mybatis.mapper;

import com.study.gupao.mybatis.entity.Customer;

import java.util.List;

public interface CustomerMapper {

    public List<Customer> queryAllForList();

    public List<Customer> queryAllForList(Object ... condition);

    public Customer findByPrimaryKey(String primaryKey);

    public void insert(Customer entity);

    public void update(Customer entity);

    public void update(Customer entity,String primaryKey);

    public void delete(Customer entity);
}
