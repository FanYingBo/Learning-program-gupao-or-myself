package com.study.mybatis;

import com.study.gupao.mybatis.dao.BaseDaoSupport;
import com.study.gupao.mybatis.entity.Customer;
import com.study.gupao.mybatis.entity.NormalCustom;
import com.study.gupao.mybatis.mapper.NormalCustomMapper;

public class OrmControlDemo {

    private static int ssdd;

    private static String dsa;

    public static void main(String[] args) {
        ssdd = ssdd+121212;
        dsa += "2121";
        System.out.println(ssdd);
        System.out.println(dsa);
    }
//        BaseDaoSupport<NormalCustomMapper,NormalCustom,String> baseDaoSupport = new BaseDaoSupport<NormalCustomMapper,NormalCustom,String>(NormalCustomMapper.class);
//        NormalCustom customer = baseDaoSupport.getByPrimaryKey("jack");
//        System.out.println(customer);

}
