package com.kakao.cafe.domain.reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {

    Reply save(Reply reply);
    Optional<String> findWriterById(Integer id);
    List<Reply> findAllByArticleId(Integer articleId);
    boolean deleteOne(Integer id);
    boolean hasReplyOfAnotherWriter(Integer articleId, String writer);
}
