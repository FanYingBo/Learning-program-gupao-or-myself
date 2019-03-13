package com.study.mybatis;

import com.study.gupao.mybatis.entity.Customer;
import com.study.gupao.mybatis.entity.NormalCustom;
import com.study.gupao.mybatis.mapper.CustomerMapper;
import com.study.gupao.mybatis.mapper.NormalCustomMapper;
import com.study.gupao.mybatis.util.session.SessionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;

public class TestMapper {


    private static final Log log = LogFactory.getLog(TestMapper.class);

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("config/log4j.properties");
        PropertyConfigurator.configure(inputStream);
        SqlSession sqlSession = SessionUtils.getSqlSession();
        NormalCustomMapper mapper = sqlSession.getMapper(NormalCustomMapper.class);
        NormalCustom customer = (NormalCustom) mapper.findByPrimaryKey("jack");
        System.out.println(customer);

//        Customer customerAdd = new Customer();
//        customerAdd.setUsername("ben");
//        customerAdd.setPasswd("w1212");
//        customerAdd.setAge(34);
//        customerAdd.setCreatedate(new Date());
//        customerAdd.setSex('男');
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(1993,6,20);
//        customerAdd.setBirth(calendar.getTime());
//        mapper.addCustomer(customerAdd);

//        customer.setPasswd("wqwass1e333");
//        mapper.update(customer);
        sqlSession.commit();
        sqlSession.close();

    }

}
