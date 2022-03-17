package com.kakao.cafe.service;

import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.repository.ReplyRepository;
import com.kakao.cafe.web.questions.dto.ReplyDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReplyService {
    private final ReplyRepository repository;

    public ReplyService(ReplyRepository repository) {
        this.repository = repository;
    }

    public Long addReply(ReplyDto dto, Long questionId) {
        Reply reply = new Reply(questionId, dto.getUserId(), dto.getContents(), LocalDateTime.now());
        return repository.save(reply);
    }

    public List<Reply> findAllReplyOnArticle(Long articleId) {
        return repository.findAllReplyOnArticle(articleId);
    }
}
