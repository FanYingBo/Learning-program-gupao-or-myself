package com.study.gupao.mybatis.dao;

import com.study.gupao.mybatis.entity.NormalCustom;
import com.study.gupao.mybatis.entity.ProductInfo;
import com.study.gupao.mybatis.mapper.NormalCustomMapper;

import java.util.List;

public class NormalCustomDao extends BaseDaoSupport<NormalCustom,String> {

    private Class<NormalCustomMapper> mapperClass = NormalCustomMapper.class;

    private NormalCustomMapper normalCustomMapper;

    public NormalCustomDao() {
        super();
        this.normalCustomMapper = getMapper(this.mapperClass);
    }

    @Override
    public List<NormalCustom> queryAllForList() throws Exception {
        return this.normalCustomMapper.queryAllForList(null);
    }

    @Override
    public List<NormalCustom> queryAllForList(Object ... condition) throws Exception {
        return this.normalCustomMapper.queryAllForList(condition[0].toString());
    }

    @Override
    public NormalCustom getByPrimaryKey(String s) throws Exception {
        return this.normalCustomMapper.findByPrimaryKey(s);
    }

    @Override
    public void insert(NormalCustom entity) throws Exception {
        this.normalCustomMapper.insert(entity);
    }

    @Override
    public void update(NormalCustom entity) throws Exception {
        this.normalCustomMapper.update(entity);
    }

}
