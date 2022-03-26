package com.kakao.cafe.repository.db;

import com.kakao.cafe.config.QueryLoader;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.repository.Query;
import com.kakao.cafe.repository.ReplyRepository;
import com.kakao.cafe.repository.db.template.DbTemplate;
import com.kakao.cafe.repository.db.template.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DbReplyRepository implements ReplyRepository {
    private final DataSource dataSource;
    private final QueryLoader queryLoader;

    public DbReplyRepository(DataSource dataSource, QueryLoader queryLoader) {
        this.dataSource = dataSource;
        this.queryLoader = queryLoader;
    }

    @Override
    public Long save(Reply reply) {
        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.SAVE_REPLY);
        Long replyId = template.executeUpdate(SQL, reply.getParentArticleId(), reply.getUserId(), reply.getContents(), reply.getLocalDateTime());
        reply.setId(replyId);
        return replyId;
    }

    @Override
    public Optional<Reply> findById(Long id) {
        RowMapper<Reply> mapper = new RowMapper<Reply>() {
            @Override
            public Reply rowMapper(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    Reply reply = new Reply(rs.getLong("parent_article_id"),
                            rs.getString("user_id"),
                            rs.getString("contents"),
                            rs.getTimestamp("local_date_time").toLocalDateTime());
                    reply.setId(rs.getLong("id"));
                    return reply;
                }
                return null;
            }
        };

        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.SELECT_REPLY);
        return Optional.ofNullable(template.executeQuery(SQL, mapper, id));
    }

    @Override
    public List<Reply> findAllReplyOnArticle(Long parentId) {
        RowMapper<Reply> mapper = new RowMapper<Reply>() {
            @Override
            public Reply rowMapper(ResultSet rs) throws SQLException {
                Reply reply = new Reply(rs.getLong("parent_article_id"),
                        rs.getString("user_id"),
                        rs.getString("contents"),
                        rs.getTimestamp("local_date_time").toLocalDateTime());
                reply.setId(rs.getLong("id"));
                return reply;
            }
        };

        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.SELECT_REPLYS);
        return template.list(SQL, mapper, parentId);
    }

    @Override
    public Long delete(Long id) {
        DbTemplate template = new DbTemplate(dataSource);
        String SQL = queryLoader.get(Query.DELETE_REPLY);
        return template.executeUpdate(SQL, id);
    }

    @Override
    public void update(Long id, Reply reply) {

    }
}
