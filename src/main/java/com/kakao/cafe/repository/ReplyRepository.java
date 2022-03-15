package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Reply;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository {

    Reply save(Reply reply);

    Optional<Reply> findById(Integer replyId);

    List<Reply> findByArticleId(Integer articleId);

    void deleteById(Integer replyId);

}
