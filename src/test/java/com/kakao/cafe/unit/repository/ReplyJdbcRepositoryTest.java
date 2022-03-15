package com.kakao.cafe.unit.repository;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.repository.jdbc.KeyHolderFactory;
import com.kakao.cafe.repository.jdbc.ReplyJdbcRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

@ExtendWith(MockitoExtension.class)
public class ReplyJdbcRepositoryTest {

    @InjectMocks
    private ReplyJdbcRepository replyRepository;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private KeyHolderFactory keyHolderFactory;

    @Mock
    private QueryProps queryProps;

    private Reply reply;

    @BeforeEach
    public void setUp() {
        reply = new Reply(1, 1, "userId", "comment", LocalDateTime.now());

        given(queryProps.get(any()))
            .willReturn("");
    }

    @Test
    @DisplayName("댓글 객체를 저장소에 저장한다")
    public void savePersistTest() {
        // given
        reply = new Reply(1, "userId", "comment");

        given(keyHolderFactory.newKeyHolder())
            .willReturn(new GeneratedKeyHolder(List.of(Map.of("reply_id", 1))));

        given(jdbcTemplate.update(any(), any(), any()))
            .willReturn(1);

        // when
        Reply savedReply = replyRepository.save(reply);

        // then
        then(savedReply.getReplyId()).isEqualTo(1);
        then(savedReply.getArticleId()).isEqualTo(1);
        then(savedReply.getUserId()).isEqualTo("userId");
        then(savedReply.getComment()).isEqualTo("comment");
    }

    @Test
    @DisplayName("댓글 id 로 댓글 객체를 조회한다")
    public void findByIdTest() {
        // given
        given(jdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class),
            any(RowMapper.class))).willReturn(reply);

        // when
        Optional<Reply> findReply = replyRepository.findById(reply.getReplyId());

        // then
        then(findReply).hasValueSatisfying(reply -> {
            then(reply.getReplyId()).isEqualTo(1);
            then(reply.getArticleId()).isEqualTo(1);
            then(reply.getUserId()).isEqualTo("userId");
            then(reply.getComment()).isEqualTo("comment");
        });
    }

    @Test
    @DisplayName("질문 id 로 댓글 컬렉션을 조회한다")
    public void findByArticleIdTest() {
        // given
        given(jdbcTemplate.query(any(String.class), any(MapSqlParameterSource.class),
            any(RowMapper.class))).willReturn(List.of(reply));

        // when
        List<Reply> findReplies = replyRepository.findByArticleId(1);

        // then
        then(findReplies).containsExactlyElementsOf(List.of(reply));
    }

    @Test
    @DisplayName("댓글 id 가 포함된 댓글 객체를 업데이트한다")
    public void saveMergeTest() {
        // given
        given(jdbcTemplate.update(any(String.class), any(BeanPropertySqlParameterSource.class)))
            .willReturn(1);

        // when
        Reply savedReply = replyRepository.save(reply);

        // then
        then(savedReply).isEqualTo(reply);
    }

    @Test
    @DisplayName("댓글 id 로 댓글 객체를 삭제한다")
    public void deleteReplyTest() {
        // given
        given(jdbcTemplate.update(any(String.class), any(MapSqlParameterSource.class)))
            .willReturn(1);

        // when
        replyRepository.deleteById(reply.getReplyId());
    }

    @Test
    @DisplayName("유저 아이디와 질문 id 로 댓글 개수를 조회한다")
    public void countByArticleIdAndUserIdTest() {
        // given
        given(jdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class),
            eq(Integer.class))).willReturn(1);

        // when
        Integer count = replyRepository.countByArticleIdAndNotUserId("otherId",
            reply.getArticleId());

        // then
        then(count).isEqualTo(1);
    }

}
