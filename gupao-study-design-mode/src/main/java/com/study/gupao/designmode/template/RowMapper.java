package com.study.gupao.designmode.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RowMapper<T> {

    public T mapRow(ResultSet resultSet, int row) throws SQLException;
}
