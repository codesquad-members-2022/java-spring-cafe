package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class JdbcArticleRepository implements ArticleRepository{

    private final DataSource dataSource;

    public JdbcArticleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Article article) {
        String sql = "insert into article(subject, content, uploadDate, writer) values(?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, article.getSubject());
            pstmt.setString(2, article.getContent());
            pstmt.setString(3, article.getUploadDate());
            pstmt.setString(4, article.getWriter());
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Article> getArticles() {
        List<Article> articleList = new ArrayList<>();

        String sql = "select * from article";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            Article article;
            while(rs.next()) {
                article = new Article(rs.getString("writer"),
                                              rs.getString("subject"),
                                              rs.getString("content"));
                article.setArticleId(rs.getInt("id"));
                articleList.add(article);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return articleList;
    }

    @Override
    public Article findById(int articleId) {
        String sql = "select * from article where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(articleId));

            rs = pstmt.executeQuery();

            if(rs.next()) {
                Article article = new Article(rs.getString("writer"),
                        rs.getString("subject"),
                        rs.getString("content"));
                article.setArticleId(rs.getInt("id"));
                close(conn, pstmt, rs);
                return article;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public void clearStorage() {

    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
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
                DataSourceUtils.releaseConnection(conn, dataSource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
