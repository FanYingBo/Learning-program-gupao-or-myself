package com.study.gupao.mybatis.dao;

import com.study.gupao.mybatis.exception.ValidateException;
import com.study.gupao.mybatis.util.session.SessionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public abstract class BaseDaoSupport<E,PrimaryKey>{

    private static final Log log = LogFactory.getLog(BaseDaoSupport.class);

    private SqlSession sqlSession;

    /**
     * 查询所有
     * @return
     * @throws Exception
     */
    public abstract List<E> queryAllForList() throws Exception;
    /**
     * 条件查找
     * @param condition
     * @return
     * @throws Exception
     */
    public abstract List<E> queryAllForList(Object ... condition) throws Exception;
    /**
     * 主键查询单个实例
     * @param primaryKey
     * @return
     */
    public  abstract E getByPrimaryKey(PrimaryKey primaryKey)  throws Exception;

    /**
     * 插入
     * @param entity
     * @throws Exception
     */
    public abstract void insert(E entity) throws Exception;


    public abstract void update(E entity) throws Exception;


    public BaseDaoSupport(){
        init();
    }

    private void init(){
        buildSession();
    }

    protected  <T> T getMapper(Class<T> mapperClass){
        T mapper = sqlSession.getMapper(mapperClass);
        return mapper;
    }

    protected final void buildSession(){
        if(this.sqlSession != null){
            clossSession();
        }
        this.sqlSession = SessionUtils.getSqlSession();
    }
    public void sessionTransControl(boolean isCommit){
        try {
            if (isCommit) {
                SessionUtils.sessionCommit(this.sqlSession);
            } else {
                SessionUtils.sessionCommit(this.sqlSession, true);
            }
        } catch (Exception e) {
            if(log.isWarnEnabled()){
                log.warn("关闭 session 失败！");
            }
        }

    }
    protected final void clossSession(){
        try {
            SessionUtils.closeSession(this.sqlSession);
        } catch (ValidateException e) {
            if(log.isWarnEnabled()){
                log.warn("关闭 session 失败！");
            }
        }
    }
}
