package com.kakao.cafe.domain.reply;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
class JdbcTemplateReplyRepositoryTest {

    private final JdbcTemplateReplyRepository jdbcTemplateReplyRepository;
    private Reply reply;

    @Autowired
    public JdbcTemplateReplyRepositoryTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplateReplyRepository = new JdbcTemplateReplyRepository(namedParameterJdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        reply = Reply.newInstance(1, "writer1", "contents");
    }

    @Test
    @DisplayName("기존에 저장되어 있지 않은 답변이라면, 저장하고, id를 부여한다.")
    void save_new_Reply_test() {

        Reply saved = jdbcTemplateReplyRepository.save(reply);

        assertThat(saved.hasId()).isTrue();
        assertThat(saved.getId()).isEqualTo(1);
        assertThat(saved.getArticleId()).isEqualTo(reply.getArticleId());
        assertThat(saved.getWriter()).isEqualTo(reply.getWriter());
        assertThat(saved.getContents()).isEqualTo(reply.getContents());
        assertThat(saved.getWrittenTime()).isEqualTo(reply.getWrittenTime());
    }

    @Test
    @DisplayName("id를 가지고 있는 (이미 저장한)답변이라면, 정보를 업데이트한다.")
    void save_reply_update_test() {

        Reply saved = jdbcTemplateReplyRepository.save(reply);
        Reply updateReply = Reply.of(1, 1, "writer", "updatedContents", reply.getWrittenTime());

        Reply updated = jdbcTemplateReplyRepository.save(updateReply);

        assertThat(updated).isEqualTo(saved);
        assertThat(updated.getContents()).isNotEqualTo(saved.getContents());
    }

    @Test
    @DisplayName("reply의 id로 해당 writer를 찾아 optional로 반환한다.")
    void findWriterByIdTest() {

        Reply saved = jdbcTemplateReplyRepository.save(reply);

        Optional<String> writerById = jdbcTemplateReplyRepository.findWriterById(saved.getId());

        assertThat(writerById).isNotEmpty();
        assertThat(writerById.get()).isEqualTo(saved.getWriter());

    }

    @Test
    @DisplayName("찾는 id의 reply가 없으면, optional.empty를 반환한다.")
    void findWriterByIdTest_not_found() {

        jdbcTemplateReplyRepository.save(reply);

        Optional<String> writerById = jdbcTemplateReplyRepository.findWriterById(2);

        assertThat(writerById).isEmpty();

    }

    @Test
    @DisplayName("하나의 질문 게시글에 포함된 답변을 모두 반환한다.")
    void findAllByArticleIdTest() {
        Reply saved = jdbcTemplateReplyRepository.save(reply);
        Reply replyOfAnotherArticle = jdbcTemplateReplyRepository.save(Reply.newInstance(2, "anotherWriter", "contents"));

        List<Reply> all = jdbcTemplateReplyRepository.findAllByArticleId(1);

        assertThat(all.size()).isEqualTo(1);
        assertThat(all).contains(saved);
        assertThat(all).doesNotContain(replyOfAnotherArticle);
    }

    @Test
    @DisplayName("id로 해당하는 답변을 삭제한다.")
    void deleteOne() {
        Reply saved = jdbcTemplateReplyRepository.save(reply);

        boolean isDeleted = jdbcTemplateReplyRepository.deleteOne(saved.getId());

        assertThat(isDeleted).isTrue();
        assertThat(jdbcTemplateReplyRepository.findWriterById(saved.getId())).isEmpty();
    }

    @Test
    void hasReplyOfAnotherWriter() {
        jdbcTemplateReplyRepository.save(reply);
        Reply replyOfAnotherWriter = Reply.newInstance(1, "AnotherWriter", "contents");
        jdbcTemplateReplyRepository.save(replyOfAnotherWriter);

        boolean hasReplyOfAnotherWriter = jdbcTemplateReplyRepository.hasReplyOfAnotherWriter(1, "writer1");

        assertThat(hasReplyOfAnotherWriter).isTrue();
    }
}
