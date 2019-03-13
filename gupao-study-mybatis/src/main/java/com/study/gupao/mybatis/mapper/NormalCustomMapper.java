package com.study.gupao.mybatis.mapper;

import com.study.gupao.mybatis.entity.NormalCustom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NormalCustomMapper {

    public List<NormalCustom> queryAllForList(@Param("name") String name);

    public NormalCustom findByPrimaryKey(@Param("name") String primaryKey);

    public void add(NormalCustom entity);

    public void update(NormalCustom entity);

    public void update(NormalCustom entity,String primaryKey);

    public void delete(NormalCustom entity);

    public void insert(NormalCustom entity);
}
