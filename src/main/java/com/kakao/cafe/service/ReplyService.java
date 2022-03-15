package com.kakao.cafe.service;

import com.kakao.cafe.domain.Reply;
import com.kakao.cafe.dto.ReplyResponse;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.NotFoundException;
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

    public ReplyResponse updateReply(SessionUser user, Integer replyId, String comment) {
        // Reply 도메인 객체를 저장소로부터 반환
        Reply reply = findUserReply(user, replyId);

        // Reply 도메인 객체를 업데이트한 후 저장소에 저장
        Reply savedReply = replyRepository.save(reply.update(comment));

        // Reply 도메인 객체를 ReplyResponse 도메인 객체로 변환
        return Mapper.map(savedReply, ReplyResponse.class);
    }

    public void deleteReply(SessionUser user, Integer replyId) {
        findUserReply(user, replyId);

        replyRepository.deleteById(replyId);
    }

    private Reply findUserReply(SessionUser user, Integer replyId) {
        // Reply 도메인 객체를 저장소로부터 반환
        Reply reply = replyRepository.findById(replyId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.REPLY_NOT_FOUND));

        // 유저가 작성한 댓글인지 검증
        user.checkUserId(reply.getUserId());

        return reply;
    }

}
