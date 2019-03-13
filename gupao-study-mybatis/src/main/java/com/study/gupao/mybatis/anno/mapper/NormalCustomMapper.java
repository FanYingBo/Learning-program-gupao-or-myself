package com.study.gupao.mybatis.anno.mapper;

import com.study.gupao.mybatis.entity.CustomOrder;
import com.study.gupao.mybatis.entity.NormalCustom;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.DateTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.SqlTimestampTypeHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface NormalCustomMapper {
    @Results(id="normalCustomMap", value ={
            @Result(property = "idNum",column = "id_number"),
            @Result(property = "isVip",column = "is_vip"),
            @Result(property = "createDate",column = "createdate",typeHandler = SqlTimestampTypeHandler.class),
            @Result(property = "orderList",column = "name",javaType = ArrayList.class,
                    many = @Many(select = "com.study.gupao.mybatis.anno.mapper.CustomOrderMapper.queryAllForListByName"))} )
    @ConstructorArgs(value={@Arg(column = "name",javaType = String.class)})
    @Select(" SELECT t1.name,t1.sex,t1.age,t1.birth,t1.id_number,t1.is_vip,t1.addr,t1.job FROM tb_normal_custom t1")
    public List<NormalCustom> queryAllForList();


    public NormalCustom findByPrimaryKey(String primaryKey);

    public void add(NormalCustom entity);

    public void update(NormalCustom entity);

    public void update(NormalCustom entity, String primaryKey);

    public void delete(NormalCustom entity);

    public void insert(NormalCustom entity);
}
