package com.study.gupao.mybatis.dao;

import com.study.gupao.mybatis.entity.ProductInfo;
import com.study.gupao.mybatis.mapper.ProductInfoMapper;

import java.util.List;

public class ProductInfoDao extends BaseDaoSupport<ProductInfo,String> {

    private Class<ProductInfoMapper> mapperClass = ProductInfoMapper.class;

    private ProductInfoMapper productInfoMapper;

    public ProductInfoDao() {
        super();
        this.productInfoMapper = getMapper(this.mapperClass);
    }

    @Override
    public List<ProductInfo> queryAllForList() throws Exception {
        return this.productInfoMapper.queryAllForList();
    }

    @Override
    public List<ProductInfo> queryAllForList(Object... condition) throws Exception {
        return null;
    }

    @Override
    public ProductInfo getByPrimaryKey(String s) throws Exception {
        return this.productInfoMapper.findByPrimaryKey(s);
    }

    @Override
    public void insert(ProductInfo entity) throws Exception {
        this.productInfoMapper.insert(entity);
    }

    @Override
    public void update(ProductInfo entity) throws Exception {
        this.productInfoMapper.update(entity);
    }
}
