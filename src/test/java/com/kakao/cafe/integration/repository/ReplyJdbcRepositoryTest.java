package com.kakao.cafe.integration.repository;

import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.jdbc.ArticleJdbcRepository;
import com.kakao.cafe.repository.jdbc.GeneratedKeyHolderFactory;
import com.kakao.cafe.repository.jdbc.KeyHolderFactory;
import com.kakao.cafe.repository.jdbc.ReplyJdbcRepository;
import com.kakao.cafe.repository.jdbc.UserJdbcRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@ActiveProfiles(profiles = "local")
@Sql("classpath:/schema-h2.sql")
@Import({GeneratedKeyHolderFactory.class, QueryProps.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DisplayName("ReplyJdbcRepository JDBC 통합 테스트")
public class ReplyJdbcRepositoryTest {

    private final ArticleJdbcRepository articleRepository;
    private final ReplyJdbcRepository replyRepository;
    private final UserJdbcRepository userRepository;

    @Autowired
    public ReplyJdbcRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate,
        KeyHolderFactory keyHolderFactory, QueryProps queryProps) {
        this.articleRepository = new ArticleJdbcRepository(jdbcTemplate, keyHolderFactory,
            queryProps);
        this.replyRepository = new ReplyJdbcRepository(jdbcTemplate, keyHolderFactory, queryProps);
        this.userRepository = new UserJdbcRepository(jdbcTemplate, queryProps);
    }

    private Article article;
    private Reply reply;
    private User user;

    @BeforeEach
    public void setUp() {
        user = userRepository.save(
            new User("userId", "userPassword", "userName", "user@example.com"));
        article = articleRepository.save(new Article("userId", "title", "contents"));

        reply = new Reply(article.getArticleId(), user.getUserId(), "comment");
    }

    @Test
    @DisplayName("댓글 객체를 저장소에 저장한다")
    public void savePersistTest() {
        // when
        Reply savedReply = replyRepository.save(reply);
        Optional<Reply> findReply = replyRepository.findById(savedReply.getReplyId());

        // then
        then(findReply).hasValueSatisfying(reply -> {
            then(reply.getArticleId()).isEqualTo(article.getArticleId());
            then(reply.getUserId()).isEqualTo("userId");
            then(reply.getComment()).isEqualTo("comment");
        });
    }

    @Test
    @DisplayName("댓글 id 로 댓글 객체를 조회한다")
    public void findByIdTest() {
        // given
        Reply savedReply = replyRepository.save(reply);

        // when
        Optional<Reply> findReply = replyRepository.findById(savedReply.getReplyId());

        // then
        then(findReply).hasValueSatisfying(reply -> {
            then(reply.getArticleId()).isEqualTo(article.getArticleId());
            then(reply.getUserId()).isEqualTo("userId");
            then(reply.getComment()).isEqualTo("comment");
        });
    }

    @Test
    @DisplayName("질문 id 로 댓글 컬렉션을 조회한다")
    public void findByArticleIdTest() {
        // given
        replyRepository.save(reply);

        // when
        List<Reply> findReplies = replyRepository.findByArticleId(article.getArticleId());

        // then
        then(findReplies).containsExactlyElementsOf(List.of(reply));
    }

    @Test
    @DisplayName("댓글 id 가 포함된 댓글 객체를 업데이트한다")
    public void saveMergeTest() {
        // given
        Reply savedReply = replyRepository.save(reply);

        Reply changedReply = new Reply(savedReply.getReplyId(), savedReply.getArticleId(),
            savedReply.getUserId(), "otherComment", LocalDateTime.now());

        // when
        replyRepository.save(changedReply);
        Optional<Reply> findReply = replyRepository.findById(savedReply.getReplyId());

        // then
        then(findReply)
            .hasValueSatisfying(reply -> {
                then(reply.getArticleId()).isEqualTo(savedReply.getArticleId());
                then(reply.getUserId()).isEqualTo(savedReply.getUserId());
                then(reply.getComment()).isEqualTo("otherComment");
            });
    }

    @Test
    @DisplayName("댓글 id 로 댓글 객체를 삭제한다")
    public void deleteReplyTest() {
        // given
        Reply savedReply = replyRepository.save(reply);

        // when
        replyRepository.deleteById(savedReply.getReplyId());
        Optional<Reply> findReply = replyRepository.findById(savedReply.getReplyId());

        // then
        then(findReply).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("유저 아이디와 질문 id 로 댓글 개수를 조회한다")
    public void countByArticleIdAndUserId() {
        // given
        Reply savedReply = replyRepository.save(reply);

        // when
        Integer userCount = replyRepository.countByArticleIdAndNotUserId("userId",
            savedReply.getArticleId());

        Integer otherCount = replyRepository.countByArticleIdAndNotUserId("otherId",
            savedReply.getArticleId());

        // then
        then(userCount).isEqualTo(0);
        then(otherCount).isEqualTo(1);
    }
}
