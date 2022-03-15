package com.kakao.cafe.repository.jdbc;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.repository.ReplyRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyJdbcRepository implements ReplyRepository {

    private static final String REPLY_ID_CAMEL = "replyId";
    private static final String REPLY_ID_SNAKE = "reply_id";
    private static final String ARTICLE_ID_CAMEL = "articleId";
    private static final String ARTICLE_ID_SNAKE = "article_id";
    private static final String USER_ID = "user_id";
    private static final String COMMENT = "comment";
    private static final String CREATED_DATE = "created_date";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final KeyHolderFactory keyHolderFactory;
    private final QueryProps queryProps;

    public ReplyJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate,
        KeyHolderFactory keyHolderFactory, QueryProps queryProps) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolderFactory = keyHolderFactory;
        this.queryProps = queryProps;
    }

    @Override
    public Reply save(Reply reply) {
        if (reply.getReplyId() == null) {
            // persist
            String sql = queryProps.get(Query.INSERT_REPLY);
            KeyHolder keyHolder = keyHolderFactory.newKeyHolder();

            reply.setCreatedDate(LocalDateTime.now());
            jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(reply), keyHolder);

            if (keyHolder.getKey() != null) {
                reply.setReplyId(keyHolder.getKey().intValue());
            }
            return reply;
        }
        // merge
        String sql = queryProps.get(Query.UPDATE_REPLY);
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(reply));

        return reply;
    }

    @Override
    public Optional<Reply> findById(Integer replyId) {
        String sql = queryProps.get(Query.SELECT_REPLY);

        try {
            Reply reply = jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource().addValue(REPLY_ID_CAMEL, replyId),
                (rs, rowNum) ->
                    new Reply(
                        rs.getInt(REPLY_ID_SNAKE),
                        rs.getInt(ARTICLE_ID_SNAKE),
                        rs.getString(USER_ID),
                        rs.getString(COMMENT),
                        rs.getObject(CREATED_DATE, LocalDateTime.class)
                    )
            );
            return Optional.ofNullable(reply);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reply> findByArticleId(Integer articleId) {
        String sql = queryProps.get(Query.SELECT_REPLIES);

        return jdbcTemplate.query(sql,
            new MapSqlParameterSource().addValue(ARTICLE_ID_CAMEL, articleId),
            (rs, rowNum) -> new Reply(
                rs.getInt(REPLY_ID_SNAKE),
                rs.getInt(ARTICLE_ID_SNAKE),
                rs.getString(USER_ID),
                rs.getString(COMMENT),
                rs.getObject(CREATED_DATE, LocalDateTime.class)
            )
        );
    }

    @Override
    public void deleteById(Integer replyId) {

    }
}
