package com.study.mybatis;

import com.study.gupao.mybatis.entity.Customer;

public interface CustomMapper extends BaseMapper<Customer,String>{

    @Override
    public Customer selectOne(String primaryKey);
}
