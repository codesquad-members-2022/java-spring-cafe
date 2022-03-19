package com.kakao.cafe.reply.domain;

import java.util.List;

import com.kakao.cafe.common.db.Repository;

public interface ReplyRepository extends Repository<Reply, Long> {
	List<Reply> findByArticleId(long articleId);

	void delete(Long replyId);
}
