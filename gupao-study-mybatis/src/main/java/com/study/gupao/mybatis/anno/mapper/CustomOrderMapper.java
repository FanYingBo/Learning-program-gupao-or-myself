package com.study.gupao.mybatis.anno.mapper;

import com.study.gupao.mybatis.entity.CustomOrder;
import com.study.gupao.mybatis.entity.ProductInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.DateTypeHandler;
import org.apache.ibatis.type.SqlTimeTypeHandler;
import org.apache.ibatis.type.SqlTimestampTypeHandler;

import java.util.List;

public interface CustomOrderMapper {


    @Results(id="customOrderMap",value = {
       @Result(property = "orderCode",column = "order_code" ,id=true),
       @Result(property = "productCode",column = "product_code"),
       @Result(property = "name",column = "name"),
       @Result(property = "createDate",column = "create_date",typeHandler = SqlTimeTypeHandler.class),
       @Result(property = "isReceive",column = "is_receive"),
       @Result(property = "productInfo",column = "product_code",
               one = @One(select = "com.study.gupao.mybatis.anno.mapper.ProductInfoMapper.findByPrimaryKey"),
               javaType = ProductInfo.class)})
    @Select("SELECT t1.product_code,t1.order_code,t1.name,t1.product_code,t1.is_receive FROM tb_custom_order t1")
    public List<CustomOrder> queryAllForList();

    @ResultMap("customOrderMap")
    @Select("SELECT t1.product_code,t1.order_code,t1.name,t1.product_code,t1.is_receive FROM tb_custom_order t1 WHERE t1.name=#{name}")
    public List<CustomOrder> queryAllForListByName(@Param("name") String name);

    public CustomOrder findByPrimaryKey(String primaryKey);

    public void update(CustomOrder entity);

    public void update(CustomOrder entity, String primaryKey);

    public void delete(CustomOrder entity);

    public void insert(CustomOrder entity);
}
