package com.study.gupao.mybatis.mapper;

import com.study.gupao.mybatis.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductInfoMapper {

    public List<ProductInfo> queryAllForList();


    public ProductInfo findByPrimaryKey(@Param("productCode") String primaryKey);

    public void add(ProductInfo entity);

    public void update(ProductInfo entity);

    public void update(ProductInfo entity, String primaryKey);

    public void delete(ProductInfo entity);

    public void insert(ProductInfo entity);
}
