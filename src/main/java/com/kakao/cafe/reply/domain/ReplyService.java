package com.kakao.cafe.reply.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kakao.cafe.common.utils.session.SessionUser;
import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserService;

@Service
public class ReplyService {
	private final UserService userService;
	private final ReplyRepository replyRepository;

	public ReplyService(UserService userService, ReplyRepository replyRepository) {
		this.userService = userService;
		this.replyRepository = replyRepository;
	}

	public void leaveAComment(Long questionId, String content, Object sessionUser) {
		SessionUser loginInfo = (SessionUser)sessionUser;
		User user = userService.getUserByUserId(loginInfo.getUserId());
		Reply reply = ReplyFactory.createOf(user.getName(), content, questionId, user.getUserId(), user.getId());
		replyRepository.save(reply);
	}

	public List<ReplyDto.Response> getListOfReplyByArticle(long articleId) {
		List<Reply> replies = replyRepository.findByArticleId(articleId);
		return replies.stream()
				.map(ReplyDto.Response::new)
				.collect(Collectors.toUnmodifiableList());
	}
}
