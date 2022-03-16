package com.kakao.cafe.repository.db;

import com.kakao.cafe.config.QueryLoader;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.Query;
import com.kakao.cafe.repository.db.template.DbTemplate;
import com.kakao.cafe.repository.db.template.RowMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class DbArticleRepository implements ArticleRepository {
    private final DataSource dataSource;
    private final QueryLoader queryLoader;

    public DbArticleRepository(DataSource dataSource, QueryLoader queryLoader) {
        this.dataSource = dataSource;
        this.queryLoader = queryLoader;
    }

    @Override
    public Long save(Article article) {
        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.SAVE_ARTICLE);
        Long saveId = template.executeUpdate(SQL, article.getUserId(), article.getTitle(), article.getContents(), article.getLocalDateTime());
        article.setId(saveId);
        return saveId;
    }

    @Override
    public Optional<Article> findById(Long id) {
        RowMapper<Article> mapper = new RowMapper<Article>() {
            @Override
            public Article rowMapper(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    Article article = new Article(rs.getString("user_id"), rs.getString("title"), rs.getString("contents"), rs.getTimestamp("local_date_time").toLocalDateTime());
                    article.setId(rs.getLong("id"));
                    return article;
                }
                return null;
            }
        };

        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.SELECT_ARTICLE);
        return Optional.ofNullable(template.executeQuery(SQL, mapper, id));
    }

    @Override
    public List<Article> findAll() {
        RowMapper<Article> mapper = new RowMapper<>() {
            @Override
            public Article rowMapper(ResultSet rs) throws SQLException {
                Article article = new Article(rs.getString("user_id"), rs.getString("title"), rs.getString("contents"), rs.getTimestamp("local_date_time").toLocalDateTime());
                article.setId(rs.getLong("id"));
                return article;
            }
        };

        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.SELECT_ARTICLES);
        return template.list(SQL, mapper);
    }

    @Override
    public Long delete(Long id) {
        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.DELETE_ARTICLE);
        return template.executeUpdate(SQL, id);
    }

    @Override
    public void update(Long id, Article article) {
        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.UPDATE_ARTICLES);
        template.executeUpdate(SQL,
                article.getUserId(),
                article.getTitle(),
                article.getContents(),
                Timestamp.valueOf(article.getLocalDateTime()),
                article.getId()
        );
    }
}
