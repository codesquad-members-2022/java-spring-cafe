package com.kakao.cafe.unit.service.mock;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.dto.ReplyResponse;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.ReplyRepository;
import com.kakao.cafe.service.ReplyService;
import com.kakao.cafe.session.SessionUser;
import java.time.LocalDateTime;
import java.util.Optional;
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

    @Test
    @DisplayName("댓글을 업데이트하고 저장소에 저장한다")
    public void updateReplyTest() {
        // given
        given(replyRepository.findById(any()))
            .willReturn(Optional.of(reply));

        given(replyRepository.save(any()))
            .willReturn(new Reply(1, 1, "userId", "otherComment", LocalDateTime.now()));

        // when
        ReplyResponse savedReply = replyService.updateReply(sessionUser, 1, "otherComment");

        // then
        then(savedReply.getReplyId()).isEqualTo(1);
        then(savedReply.getArticleId()).isEqualTo(1);
        then(savedReply.getUserId()).isEqualTo("userId");
        then(savedReply.getComment()).isEqualTo("otherComment");
    }

    @Test
    @DisplayName("댓글을 업데이트할 때 존재하지 않는 댓글 id 를 입력하면 예외를 반환한다")
    public void updateReplyNotFoundTest() {
        // given
        given(replyRepository.findById(any()))
            .willThrow(new NotFoundException(ErrorCode.REPLY_NOT_FOUND));

        // when
        Throwable throwable = catchThrowable(
            () -> replyService.updateReply(sessionUser, 1, "otherComment"));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.REPLY_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("댓글을 업데이트할 때 세션 유저와 댓글을 작성한 유저가 일치하지 않을 경우 예외를 반환한다")
    public void updateReplyValidateTest() {
        // given
        SessionUser sessionOther = new SessionUser(1, "otherId", "otherPassword", "otherName",
            "other@example.com");

        given(replyRepository.findById(any()))
            .willReturn(Optional.of(reply));

        // when
        Throwable throwable = catchThrowable(
            () -> replyService.updateReply(sessionOther, 1, "otherComment"));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INVALID_REPLY_WRITER.getMessage());
    }
}
