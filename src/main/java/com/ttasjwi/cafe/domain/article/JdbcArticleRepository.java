package com.ttasjwi.cafe.domain.article;

import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Timestamp.valueOf;

public class JdbcArticleRepository implements ArticleRepository{

    private final DataSource dataSource;

    public JdbcArticleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long save(Article article) {
        String sql = "INSERT INTO\n" +
                "article(title, content, writer, reg_date_time)\n" +
                "values(?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getContent());
            pstmt.setString(3, article.getWriter());
            pstmt.setTimestamp(4, valueOf(article.getRegDateTime()));
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                Long articleId = rs.getLong("article_id");
                article.initArticleId(articleId);
                return articleId;
            }
            throw new SQLException("article Id 조회 실패");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Article> findByArticleId(Long articleId) {
        String sql =
                "SELECT article_id, title, content, writer, reg_date_time\n" +
                        "FROM article\n" +
                        "WHERE article_id= ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, articleId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Article article = buildArticleFromResultSet(rs);
                return Optional.of(article);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Article> findAll() {

        String sql =
                "SELECT article_id, title, content, writer, reg_date_time\n" +
                        "FROM article";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Article> articles = new ArrayList<>();

            while (rs.next()) {
                Article article = buildArticleFromResultSet(rs);
                articles.add(article);
            }
            return articles;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private Article buildArticleFromResultSet(ResultSet rs) throws SQLException {
        return Article.builder()
                .articleId(rs.getLong("article_id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .writer(rs.getString("writer"))
                .regDateTime(rs.getTimestamp("reg_date_time").toLocalDateTime())
                .build();
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

}
