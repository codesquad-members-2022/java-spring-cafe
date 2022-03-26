package com.kakao.cafe.repository;

import com.kakao.cafe.config.QueryLoader;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.repository.db.DbReplyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@JdbcTest
@Import(QueryLoader.class)
@Sql({"classpath:/schema.sql", "classpath:/data.sql"})
public class JdbcReplyRepositoryTest {
    private final DbReplyRepository repository;

    @Autowired
    public JdbcReplyRepositoryTest(DataSource dataSource, QueryLoader loader) {
        this.repository = new DbReplyRepository(dataSource, loader);
    }

    Article article;
    Reply reply1, reply2;

    @BeforeEach
    public void setUp() {
        reply1 = new Reply(1L, "test", "reply", LocalDateTime.now());
        reply2 = new Reply(1L, "test2", "reply2", LocalDateTime.now());
    }

    @Test
    @DisplayName("댓글이 정상적으로 저장되는지 확인")
    public void saveTest() {
        // when
        Long replyId = repository.save(reply1);
        Optional<Reply> findReply = repository.findById(replyId);

        // then
        then(findReply).hasValueSatisfying(r -> {
            then(r.getParentArticleId()).isEqualTo(1L);
            then(r.getUserId()).isEqualTo("test");
            then(r.getContents()).isEqualTo("reply");
        });
    }

    @Test
    @DisplayName("reply의 id로 해당 reply 조회해 오기")
    public void findByIdTest() {
        // given
        Long saveId1 = repository.save(reply1);

        // when
        Reply findReply = repository.findById(saveId1).get();

        // then
        Assertions.assertThat(findReply).isEqualTo(reply1);
    }

    @Test
    @DisplayName("Article의 id값으로 달려있는 댓글 전부 조회하기")
    public void findAllReplyOnArticleTest() {
        // given
        Long saveId1 = repository.save(reply1);
        Long saveId2 = repository.save(reply2);

        // when
        List<Reply> replies = repository.findAllReplyOnArticle(1L);

        // then
        then(replies).containsExactly(reply1, reply2);
    }

    @Test
    public void deleteReplyTest() {
        // given
        Long saveId1 = repository.save(reply1);

        // when
        Long deleteId = repository.delete(saveId1);

        // then
        Optional<Reply> findReply = repository.findById(deleteId);
        then(findReply).isEqualTo(Optional.empty());
    }
}
