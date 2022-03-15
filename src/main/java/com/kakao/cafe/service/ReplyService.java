package com.kakao.cafe.service;

import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.dto.ReplyResponse;
import com.kakao.cafe.repository.ReplyRepository;
import com.kakao.cafe.session.SessionUser;
import com.kakao.cafe.util.Mapper;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public ReplyResponse comment(SessionUser user, Integer articleId, String comment) {
        // 입력받은 인자를 모아 Reply 객체로 구성
        Reply reply = new Reply(articleId, user.getUserId(), comment);

        // Reply 도메인 객체를 저장소에 저장
        Reply savedReply = replyRepository.save(reply);

        // Reply 도메인 객체를 ReplyResponse 도메인 객체로 변환
        return Mapper.map(savedReply, ReplyResponse.class);
    }

}
