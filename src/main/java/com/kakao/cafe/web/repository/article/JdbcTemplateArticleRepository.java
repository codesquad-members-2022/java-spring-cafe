package com.kakao.cafe.web.repository.article;

import com.kakao.cafe.web.domain.article.Article;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateArticleRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Article save(Article article) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("article").usingGeneratedKeyColumns("id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("writer", article.getWriter());
		parameters.put("title", article.getTitle());
		parameters.put("content", article.getContent());
		parameters.put("localDateTime", article.getLocalDateTime());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		article.setId(key.longValue());
		return article;
	}

	@Override
	public Optional<Article> findById(Long id) {
		List<Article> result = jdbcTemplate.query("SELECT * FROM ARTICLE WHERE id = ?", articleRowMapper(), id);
		return result.stream().findAny();
	}

	@Override
	public List<Article> findAll() {
		return jdbcTemplate.query("SELECT * FROM ARTICLE", articleRowMapper());
	}

	private RowMapper<Article> articleRowMapper() {
		return (rs, rowNum) -> {
			Article article = new Article(rs.getString("writer"), rs.getString("title"), rs.getString("content"));
			article.setId(rs.getLong("id"));
			article.setLocalDateTime(rs.getString("localDateTime"));
			return article;
		};
	}

}
