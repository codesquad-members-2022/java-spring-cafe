package com.kakao.cafe.repository.db.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RowsMapper {
    List<Object> rowsMapper(ResultSet rs) throws SQLException;
}
