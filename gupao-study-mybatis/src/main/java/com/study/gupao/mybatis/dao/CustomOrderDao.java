package com.study.gupao.mybatis.dao;

import com.study.gupao.mybatis.entity.CustomOrder;
import com.study.gupao.mybatis.mapper.CustomOrderMapper;

import java.util.List;

public class CustomOrderDao extends BaseDaoSupport<CustomOrder,String> {

    private Class<CustomOrderMapper> mapperClass = CustomOrderMapper.class;

    private CustomOrderMapper customOrderMapper;

    public CustomOrderDao() {
        super();
        this.customOrderMapper = getMapper(this.mapperClass);
    }

    @Override
    public List<CustomOrder> queryAllForList() throws Exception {
        return this.customOrderMapper.queryAllForList();
    }

    @Override
    public List<CustomOrder> queryAllForList(Object... condition) throws Exception {
        return this.customOrderMapper.queryAllForListByForeignKey(condition[0].toString());
    }

    @Override
    public CustomOrder getByPrimaryKey(String s) throws Exception {
        return this.customOrderMapper.findByPrimaryKey(s);
    }

    @Override
    public void insert(CustomOrder entity) throws Exception {
        this.customOrderMapper.insert(entity);
    }

    @Override
    public void update(CustomOrder entity) throws Exception {
        this.customOrderMapper.update(entity);
    }

}
