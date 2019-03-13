package com.study.gupao.designmode.template;

import com.study.gupao.designmode.template.entity.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板模式
 *
 */
public class JDBCTemplate {

     private DataSource dataSource;

     public JDBCTemplate(DataSource dataSource){
         this.dataSource = dataSource;
     }

     private Connection getConnection() throws SQLException {
         return this.dataSource.getConnection();
     }

     private PreparedStatement getPreparedStatement(Connection connection,String sql) throws SQLException {
         return connection.prepareStatement(sql);
     }

     private ResultSet getResultSet(PreparedStatement preparedStatement) throws SQLException {
         return preparedStatement.executeQuery();
     }

     private void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
         if(preparedStatement != null){
             preparedStatement.close();
         }

     }

     private void closeResult(ResultSet resultSet) throws SQLException {
         if(resultSet != null){
             resultSet.close();
         }

     }

     private void closeConnection(Connection connection) throws SQLException {
         if(connection != null){
             connection.close();
         }
     }
     private List<?> processResult(ResultSet resultSet,RowMapper<?> rowMapper,Object[] values) throws SQLException {
         List<Object> dataList = new ArrayList<>();
         int row = 0;
         while(resultSet.next()){
             dataList.add(rowMapper.mapRow(resultSet,row++));
         }
         return dataList;
     }
     public List<?> excuteQuery(String sql, RowMapper<?> rowMapper, Object[] values){
         //1.获取连接
         try {
             Connection connection = this.getConnection();

             PreparedStatement preparedStatement = this.getPreparedStatement(connection,sql);

             ResultSet resultSet = this.getResultSet(preparedStatement);
             List<?> results = processResult(resultSet,rowMapper,values);
             this.closeResult(resultSet);
             this.closePreparedStatement(preparedStatement);
             this.closeConnection(connection);
             return results;
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }


}
