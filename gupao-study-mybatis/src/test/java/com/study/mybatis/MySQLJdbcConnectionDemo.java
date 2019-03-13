package com.study.mybatis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLJdbcConnectionDemo {

    public static List<String> columnList = new ArrayList<>();

    public static void main(String[] args) throws SQLException {
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.8.156:3306/devlopment?serverTimezone=UTC&useSSL=false","root","root123");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from tb_normal_custom where name=?");
            preparedStatement.setString(1,"bilibili");
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            int index = 1;
            while(index < columnCount){
                columnList.add(metaData.getColumnName(index));
                index ++;
            }
            while(resultSet.next()){
                for(int i = 0;i < columnCount-1;i++){
                    print(columnList.get(i),resultSet.getObject(columnList.get(i)));
                }
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                resultSet.close();
            }
            if(connection != null){
                connection.close();
            }

        }
    }

    private static void print(String columnName, Object value){
        System.out.printf("列 %s 的值：%s \n",columnName,value);
    }
}
