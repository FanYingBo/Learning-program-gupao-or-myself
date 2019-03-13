package com.study.gupao.mybatis.mapper;

import com.study.gupao.mybatis.entity.CustomOrder;
import com.study.gupao.mybatis.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomOrderMapper {

    public List<CustomOrder> queryAllForList();

    public List<CustomOrder> queryAllForListByForeignKey(@Param("name") String name);

    public CustomOrder findByPrimaryKey(@Param("orderCode") String primaryKey);

    public void update(CustomOrder entity);

    public void update(CustomOrder entity, String primaryKey);

    public void delete(CustomOrder entity);

    public void insert(CustomOrder entity);
}
