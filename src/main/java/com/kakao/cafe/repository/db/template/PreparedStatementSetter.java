package com.kakao.cafe.repository.db.template;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {
    void setParameters(PreparedStatement pstmt) throws SQLException;
}
