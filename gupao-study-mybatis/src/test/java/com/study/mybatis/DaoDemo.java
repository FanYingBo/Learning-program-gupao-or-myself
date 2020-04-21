package com.study.mybatis;

import com.study.gupao.mybatis.dao.CustomOrderDao;
import com.study.gupao.mybatis.dao.NormalCustomDao;
import com.study.gupao.mybatis.dao.ProductInfoDao;
import com.study.gupao.mybatis.dao.UserDao;
import com.study.gupao.mybatis.entity.CustomOrder;
import com.study.gupao.mybatis.entity.NormalCustom;
import com.study.gupao.mybatis.entity.ProductInfo;
import com.study.gupao.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DaoDemo {

    private static NormalCustomDao normalCustomDao;

    private static CustomOrderDao customOrderDao;

    private static ProductInfoDao productInfoDao;

    private static UserDao userDao;

    private static final Log log = LogFactory.getLog(DaoDemo.class);
    static {
        try {
            InputStream resourceAsStream = Resources.getResourceAsStream("config/log4j.properties");
            PropertyConfigurator.configure(resourceAsStream);

//            normalCustomDao = new NormalCustomDao();
//
//            customOrderDao = new CustomOrderDao();
//
//            productInfoDao = new ProductInfoDao();

            userDao = new UserDao();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
//        testQueryCustomList();
//        testInsertCustom();
//        udateCustom();
//        customOrderInsert("bilibili");
//        queryCustomOne("tom");

//        getProductInfo("WEUIOOYEE6E8U82S");
//        productInfoInsert("WEUIOOYEE6E8U82S");
//        queryCustomOrderOne("1536841943500");
        List<User> users = userDao.queryAllForList();
        System.out.println(users);
    }
    private static void testQueryCustomList() throws Exception {
        List<NormalCustom> normalCustoms = normalCustomDao.queryAllForList();
        System.out.println(normalCustoms.get(0));
    }
    private static void testInsertCustom() throws Exception {
        NormalCustom normalCustom_ = new NormalCustom("youtubi");
        normalCustom_.setAge(23);
        normalCustom_.setIsVip(0);
        normalCustom_.setAddr("互联网");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1990,2,3);
        normalCustom_.setBirth(calendar.getTime());
        normalCustom_.setJob("java");
        normalCustom_.setCreateDate(new Timestamp(new Date().getTime()));
        normalCustomDao.insert(normalCustom_);
    }
    private static NormalCustom queryCustomOne(String name) throws Exception {
//        List<NormalCustom> normalCustomList = normalCustomDao.queryAllForList(name);
        NormalCustom normalCustom = normalCustomDao.getByPrimaryKey(name);
        System.out.println(normalCustom.getAddr());
        Thread.sleep(10000);
        // 延时查询 会对未获取的关联数据进行延时查询   懒加载
        System.out.println(normalCustom.getOrderList());
//        System.out.println(normalCustomList);
        return normalCustom;
    }
    private static void udateCustom() throws Exception {
        NormalCustom normalCustom = queryCustomOne("tom");
        normalCustom.setCreateDate(new Timestamp(new Date().getTime()));
        normalCustom.setAge(29);
        normalCustom.setAddr("湖南");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1989,5,3);
        normalCustom.setBirth(calendar.getTime());
        normalCustom.setJob("架构师");
        normalCustom.setIdNum("2344577366333822");
        normalCustomDao.update(normalCustom);
    }

    private static void customOrderInsert(String name) throws Exception {

        CustomOrder customOrder = new CustomOrder();
        customOrder.setOrderCode(System.currentTimeMillis()+"");
        customOrder.setName(name);
        customOrder.setCreateDate(new Date());
        customOrder.setIsReceive(0);
        customOrder.setProductCode("WEUIOOYEE6E8U82S");
        customOrderDao.insert(customOrder);
    }

    private static void productInfoInsert(String productCode) throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductCode(productCode);
        productInfo.setSupplyId(1);
        productInfo.setDescription("6G+64G骁龙845");
        productInfo.setSaleType(1);
        productInfoDao.insert(productInfo);
    }
    private static void getProductInfo(String productCode) throws Exception {
        ProductInfo byPrimaryKey = productInfoDao.getByPrimaryKey(productCode);
        System.out.println(byPrimaryKey);
    }

    private static ProductInfo queryProductOne(String name) throws Exception {
        ProductInfo byPrimaryKey = productInfoDao.getByPrimaryKey(name);
        System.out.println(byPrimaryKey);
        return byPrimaryKey;
    }

    private static CustomOrder queryCustomOrderOne(String name) throws Exception {
        CustomOrder byPrimaryKey = customOrderDao.getByPrimaryKey(name);
        System.out.println(byPrimaryKey.getProductInfo());
        return byPrimaryKey;
    }
}
