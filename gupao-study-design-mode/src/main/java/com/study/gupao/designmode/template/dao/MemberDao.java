package com.study.gupao.designmode.template.dao;

import com.study.gupao.designmode.template.JDBCTemplate;
import com.study.gupao.designmode.template.RowMapper;
import com.study.gupao.designmode.template.entity.Member;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDao {

    private JDBCTemplate jdbcTemplate = new JDBCTemplate(null);

    public MemberDao(DataSource dataSource) {
    }

    public List<?> query(){
        String sql ="select * from member";
        List<?> objects = null;
        try {
            objects = jdbcTemplate.excuteQuery(sql, new RowMapper<Member>() {
                @Override
                public Member mapRow(ResultSet resultSet, int row) throws SQLException {
                    Member member = new Member();
                    member.setUsername(resultSet.getString("username"));
                    member.setPassword(resultSet.getString("password"));
                    member.setAddr(resultSet.getString("addr"));
                    member.setAge(resultSet.getInt("age"));
                    return member;
                }
            }, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objects;
    }
//    public Object processResult(ResultSet resultSet) throws Exception {
//        Member member = new Member();
//        member.setUsername(resultSet.getString("username"));
//        member.setPassword(resultSet.getString("password"));
//        member.setAddr(resultSet.getString("addr"));
//        member.setAge(resultSet.getInt("age"));
//        return member;
//    }
}
