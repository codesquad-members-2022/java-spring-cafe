package com.kakao.cafe.repository.db;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class DbArticleRepository implements ArticleRepository {
    private final DataSource dataSource;

    public DbArticleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long save(Article article) {
        String SQL = "INSERT INTO article (user_id, title, contents, local_date_time) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, article.getUserId());
            pstmt.setString(2, article.getTitle());
            pstmt.setString(3, article.getContents());
            pstmt.setTimestamp(4, Timestamp.valueOf(article.getLocalDateTime()));
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                article.setId(rs.getLong(1));
                return article.getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
        return -1L;
    }

    @Override
    public Optional<Article> findById(Long id) {
        String SQL = "SELECT id, user_id, title, contents, local_date_time FROM article WHERE id = (?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Article article = new Article(
                        rs.getString("user_id"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("local_date_time").toLocalDateTime()
                );
                article.setId(rs.getLong("id"));
                return Optional.ofNullable(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
        return Optional.empty();
    }

    @Override
    public List<Article> findAll() {
        String SQL = "SELECT id, user_id, title, contents, local_date_time FROM article";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Article> list = new ArrayList<>();

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Article article = new Article(
                        rs.getString("user_id"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("local_date_time").toLocalDateTime()
                );
                article.setId(rs.getLong("id"));
                list.add(article);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
        return list;
    }

    @Override
    public boolean delete(Long id) {
        String SQL = "DELETE FROM article WHERE id = (?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setLong(1, id);
            int affectedLine = pstmt.executeUpdate();

            if (affectedLine == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
        return false;
    }

    @Override
    public void update(Long id, Article article) {
        String SQL = "UPDATE article SET user_id = (?), title = (?), contents = (?), local_date_time = (?) WHERE id = (?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, article.getUserId());
            pstmt.setString(2, article.getTitle());
            pstmt.setString(3, article.getContents());
            pstmt.setTimestamp(4, Timestamp.valueOf(article.getLocalDateTime()));
            pstmt.setLong(5, article.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbCleaner.close(connection, pstmt, rs, dataSource);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
}
