package com.study.gupao.mybatis.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 *  {@link org.apache.ibatis.executor.Executor}
 *  {@link org.apache.ibatis.executor.parameter.ParameterHandler}
 *  {@link org.apache.ibatis.executor.resultset.ResultSetHandler}
 *  {@link StatementHandler}
 */
@Intercepts(@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class}))
public class StatementHandlerInterceptor implements Interceptor {

    private static final Log logger = LogFactory.getLog(StatementHandlerInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info("invocation execute:" + invocation);
        logger.info("invocation arg:" + invocation.getArgs());
        logger.info("invocation method:" + invocation.getMethod());
        logger.info("invocation target:" + invocation.getTarget());
        RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        logger.info("invocation sql:" + boundSql.getSql());
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(Boolean.TRUE);
        field.set(boundSql,boundSql.getSql() + " limit 0 , 50");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // wrap 这里通过装饰器和代理模式来拦截 @Signature 中的方法
        logger.info("plugin target:"+target);
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
