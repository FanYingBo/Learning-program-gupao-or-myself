package com.study.mybatis;

import com.study.dubbo.mybatis.anno.mapper.CustomOrderMapper;
import com.study.dubbo.mybatis.anno.mapper.NormalCustomMapper;
import com.study.dubbo.mybatis.anno.mapper.ProductInfoMapper;
import com.study.dubbo.mybatis.entity.CustomOrder;
import com.study.dubbo.mybatis.entity.NormalCustom;
import com.study.dubbo.mybatis.entity.ProductInfo;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;

import java.util.List;

public class AoontationDemo {

    public static void main(String[] args) {
        PooledDataSource pooledDataSource = new PooledDataSource();
        pooledDataSource.setDriver("com.mysql.cj.jdbc.Driver");
        pooledDataSource.setUrl("jdbc:mysql://192.168.8.156:3306/devlopment?serverTimezone=UTC&useSSL=false");
        pooledDataSource.setUsername("root");
        pooledDataSource.setPassword("root123");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development",transactionFactory,pooledDataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setLazyLoadingEnabled(true);
        configuration.setCacheEnabled(true);
        TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
        typeAliasRegistry.registerAlias(CustomOrder.class);
        typeAliasRegistry.registerAlias(NormalCustom.class);
        typeAliasRegistry.registerAlias(ProductInfo.class);
        MapperRegistry mapperRegistry = configuration.getMapperRegistry();
        mapperRegistry.addMapper(CustomOrderMapper.class);
        mapperRegistry.addMapper(ProductInfoMapper.class);
        mapperRegistry.addMapper(NormalCustomMapper.class);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sessionFactory.openSession(true);
        CustomOrderMapper customOrderMapper = sqlSession.getMapper(CustomOrderMapper.class);
        List<CustomOrder> customOrders = customOrderMapper.queryAllForList();
        System.out.println(customOrders);
        ProductInfoMapper productInfoMapper = sqlSession.getMapper(ProductInfoMapper.class);
        ProductInfo productInfo = productInfoMapper.findByPrimaryKey("WEUIOOYEE6E8U82S");
        System.out.println(productInfo);
        NormalCustomMapper normalCustomMapper = sqlSession.getMapper(NormalCustomMapper.class);
        List<NormalCustom> normalCustoms = normalCustomMapper.queryAllForList();
        System.out.println(normalCustoms);
//        List<CustomOrder> bilibili = customOrderMapper.queryAllForListByName("bilibili");
//        System.out.println(bilibili);
    }
}
