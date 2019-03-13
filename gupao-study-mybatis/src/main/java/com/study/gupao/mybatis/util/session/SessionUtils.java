package com.study.gupao.mybatis.util.session;

import com.study.gupao.mybatis.exception.ValidateException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SessionUtils {

    private static final String resource = "mybatis-config.xml";

    public static SqlSession getSqlSession(){
        SqlSession sqlSession = getSqlSessionFactory().openSession(Boolean.TRUE);
        return sqlSession;
    }

    private static SqlSessionFactory getSqlSessionFactory(){
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            return sqlSessionFactory;
        } catch (IOException e) {
            return null;
        }
    }

    public static void  sessionCommit(SqlSession sqlSession) throws ValidateException {
        sessionCommit(sqlSession,false);
    }
    public static void sessionCommit(SqlSession sqlSession,boolean isRollback) throws ValidateException {
        if(sqlSession != null){
            if(isRollback){
                sqlSession.rollback();
            }else{
                sqlSession.commit();
            }
        }else{
            throw new ValidateException("sqlSession is required not null");
        }
    }

    public static void closeSession(SqlSession sqlSession) throws ValidateException {
        if(sqlSession != null){
            sqlSession.close();
        }else{
            throw new ValidateException("sqlSession is required not null");
        }
    }
}
