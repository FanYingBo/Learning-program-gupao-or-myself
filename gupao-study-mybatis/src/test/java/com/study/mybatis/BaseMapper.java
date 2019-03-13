package com.study.mybatis;

public interface BaseMapper<T,Pk> {

    public T selectOne(Pk primaryKey);

}
