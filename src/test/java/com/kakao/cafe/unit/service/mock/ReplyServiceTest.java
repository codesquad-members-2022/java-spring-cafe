package com.kakao.cafe.unit.service.mock;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.dto.ReplyResponse;
import com.kakao.cafe.repository.ReplyRepository;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.session.SessionUser;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceTest {

    @InjectMocks
    private ReplyService replyService;

    @Mock
    private ReplyRepository replyRepository;

    private SessionUser sessionUser;
    private Reply reply;

    @BeforeEach
    public void setUp() {
        sessionUser = new SessionUser(1, "userId", "userPassword", "userName",
            "user@example.com");

        reply = new Reply(1, 1, "userId", "comment", LocalDateTime.now());
    }

    @Test
    @DisplayName("댓글을 작성하고 저장소에 저장한다")
    public void commentTest() {
        // given
        given(replyRepository.save(any()))
            .willReturn(reply);

        // when
        ReplyResponse savedReply = replyService.comment(sessionUser, 1, "comment");

        // then
        then(savedReply.getReplyId()).isEqualTo(1);
        then(savedReply.getArticleId()).isEqualTo(1);
        then(savedReply.getUserId()).isEqualTo("userId");
        then(savedReply.getComment()).isEqualTo("comment");
    }

}
