package com.kakao.cafe.domain.reply;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcTemplateReplyRepository implements ReplyRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcTemplateReplyRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reply save(Reply reply) {
        if (!reply.hasId()) {
            return insert(reply);
        }
        return update(reply);
    }

    @Override
    public Optional<String> findWriterById(Integer id) {
        String sql = "select writer from cafe_reply where id = :id";
        try {
            String writer = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), String.class);
            return Optional.ofNullable(writer);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reply> findAllByArticleId(Integer articleId) {
        String sql ="select id, articleId, writer, contents, writtenTime from cafe_reply where articleId = :articleId";
        return jdbcTemplate.query(sql, new MapSqlParameterSource("articleId", articleId), replyRowMapper());
    }

    @Override
    public Boolean deleteOne(Integer id) {
        String sql = "delete from cafe_reply where id = :id";
        return jdbcTemplate.update(sql, new MapSqlParameterSource("id", id)) == 1;
    }

    private Reply insert(Reply reply) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into cafe_reply (articleId, writer, contents, writtenTime) values (:articleId, :writer, :contents, :writtenTime);";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(reply), keyHolder);
        reply.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return reply;
    }

    private Reply update(Reply reply) {
        String sql = "update cafe_reply set contents = :contents where id = :id";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(reply));
        return reply;
    }

    private RowMapper<Reply> replyRowMapper() {
        return (rs, rowNum) -> Reply.of(rs.getInt("id"),
                rs.getInt("articleId"),
                rs.getString("writer"),
                rs.getString("contents"),
                rs.getObject("writtenTime", LocalDateTime.class));
    }
}
