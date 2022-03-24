package com.kakao.cafe.domain.reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {

    Reply save(Reply reply);
    List<Reply> findAllByArticleId(Integer articleId);
    Boolean deleteOne(Integer id);
}
