package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {
    Long save(Reply reply);

    Optional<Reply> findById(Long id);

    List<Reply> findAllReplyOnArticle(Long parentId);

    Long delete(Long id);

    void update(Long id, Reply reply);
}
