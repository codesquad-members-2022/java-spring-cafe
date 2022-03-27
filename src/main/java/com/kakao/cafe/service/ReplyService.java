package com.kakao.cafe.service;

import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.exception.UnAuthorizationException;
import com.kakao.cafe.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ReplyService {
    private static final String REPLY_NOT_FOUND_EXCEPTION = "해당 댓글을 찾을 수 없습니다.";
    private static final String UN_AUTHORIZATION_EXCEPTION = "해당 댓글을 수정할 권한이 없습니다.";
    private final ReplyRepository repository;

    public ReplyService(ReplyRepository repository) {
        this.repository = repository;
    }

    public Reply addReply(String contents, Long questionId, User user) {
        Reply reply = new Reply(questionId, user.getUserId(), contents, LocalDateTime.now());
        Long saveId = repository.save(reply);
        reply.setId(saveId);
        return reply;
    }

    private Reply findReplyById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(REPLY_NOT_FOUND_EXCEPTION));
    }

    public List<Reply> findAllReplyOnArticle(Long articleId) {
        List<Reply> replies = repository.findAllReplyOnArticle(articleId);
        Collections.reverse(replies);
        return replies;
    }

    public Long deleteReply(Long id, User user) {
        if (isUserHasAuthorization(id, user)) {
            return repository.delete(id);
        }
        throw new UnAuthorizationException(UN_AUTHORIZATION_EXCEPTION);
    }

    private boolean isUserHasAuthorization(Long articleId, User sessionUser) {
        Reply findReply = findReplyById(articleId);
        return sessionUser.isOwnReply(findReply);
    }
}
